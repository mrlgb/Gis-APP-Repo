package com.tt.rds.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DrawStatus;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedEvent;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedListener;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.io.File;
import java.util.List;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.activity.point.PointMarkerActivity;
import com.tt.rds.app.activity.usersetting.AboutActivity;
import com.tt.rds.app.activity.usersetting.CollectStaticActivity;
import com.tt.rds.app.activity.usersetting.FeedbackActivity;
import com.tt.rds.app.activity.usersetting.LoginActivity;
import com.tt.rds.app.activity.usersetting.PointSetActivity;
import com.tt.rds.app.activity.usersetting.UserSettingActivity;
import com.tt.rds.app.app.Constant;
import com.tt.rds.app.bean.AppBitmap;
import com.tt.rds.app.bean.UserInfo;
import com.tt.rds.app.common.ConstantValue;
import com.tt.rds.app.common.EventBusMSG;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String HEAD_PATH = Environment.getExternalStorageState()+"/GPSLogger/Headers/";

    private BottomSheetBehavior mBottomSheetBehavior;
    private View mBottomSheet;
    private GridView gv;

    private int count = 0;
    private MapView mMapView;
    private ImageView mHeader;
    private TextView mUsername,mAddress;
    private NavigationView navigationView;

    private Button showall_button, hide_button;
    private Button cancelCollectBtn, addSpliterBtn, pauseCollectBtn, stopCollectBtn;
    private List<String> collectPointList;
    private ArrayAdapter<String> gridViewArrayAdapter;

    final MainApplication gpsApplication = MainApplication.getInstance();
    DrawerLayout drawer;
    private LocationDisplay mLocationDisplay;
    double mScale = 0.0;

    private int requestCode = 2;
    String[] reqPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
            .ACCESS_COARSE_LOCATION};

    private UserInfo userInfo;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

        initToolBarAnDrawerLayout();

        initArcgisMap();

        findViewById(R.id.bSearch).setOnClickListener(this);
        //Implement click listeners
        findViewById(R.id.BtnPointCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnLineCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnQuery_main).setOnClickListener(this);

        findViewById(R.id.btnLinePause).setOnClickListener(this);
        findViewById(R.id.btnLineCancel).setOnClickListener(this);
        findViewById(R.id.btnLineStop).setOnClickListener(this);
        findViewById(R.id.btnAddspliter).setOnClickListener(this);

        findViewById(R.id.btnZoomIn).setOnClickListener(this);
        findViewById(R.id.btnZoomOut).setOnClickListener(this);

        findViewById(R.id.btnLineClear).setOnClickListener(this);
        findViewById(R.id.btnMyLocation).setOnClickListener(this);
//
        initBottomSheet();

        File file_photo = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/tt/collectionpo");
        if (!file_photo.exists())
            file_photo.mkdirs();
        Constant.myCaptureFile = file_photo.getPath() + "/";
    }


    private void initBottomGridView() {
        // Populate a List from Array elements
        collectPointList = getApp().getCollectPointList();

        // Create a new ArrayAdapter
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.list_item_tag, collectPointList);

        // Data bind GridView with ArrayAdapter (String Array elements)
        gv.setAdapter(gridViewArrayAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected/clicked item text
//                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                if (selectedItem.equals("标志点")) {
                    Intent intent = new Intent(MainActivity.this, PointMarkerActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void initBottomSheet() {
        mBottomSheet = findViewById(R.id.bottomSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        //By default set BottomSheet Behavior as Collapsed and Height 0
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setSkipCollapsed(false);

////
//        //If you want to handle callback of Sheet Behavior you can use below code
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        Log.d(TAG, "State Collapsed");
//                        break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        Log.d(TAG, "State Dragging");
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED:
//                        Log.d(TAG, "State Expanded");
//                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        Log.d(TAG, "State Hidden");
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        Log.d(TAG, "State Settling");
//                        break;
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        gv = (GridView) findViewById(R.id.gv);
        initBottomGridView();
        showall_button = (Button) findViewById(R.id.show_button);
        hide_button = (Button) findViewById(R.id.hide_button);
        showall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInGV(gridViewArrayAdapter);
                mBottomSheetBehavior.setPeekHeight(100 * (collectPointList.size() / 3 + 1));
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        hide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void changeInGV(ArrayAdapter<String> gridViewArrayAdapter) {
        // Get the second item from ArrayAdapter
        if (showall_button.getText().equals("全部")) {
            getApp().switchCollectPointList(false);
            showall_button.setText("常用");
        } else {
            showall_button.setText("全部");
            getApp().switchCollectPointList(true);
        }

        // Update the GridView
        gridViewArrayAdapter.notifyDataSetChanged();

        // Confirm the deletion
//        Toast.makeText(getApplicationContext(),
//                "Removed : " + secondItemText, Toast.LENGTH_SHORT).show();
    }

    private void initArcgisMap() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // inflate MapView from layout
        mMapView = (MapView) findViewById(R.id.mapView);
        // create a MapImageLayer with dynamically generated map images
        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.sample_service_url));
//        ArcGISTiledLayer mapTiledLayer= new ArcGISTiledLayer("http://gis.ncgl.cn/arcgis/rest/services/yjcmapQS20160520/MapServer");
        // create an empty map instance
        ArcGISMap map = new ArcGISMap(Basemap.createTopographic());
//        ArcGISMap map = new ArcGISMap();
        // add map image layer as operational layer
        map.getOperationalLayers().add(mapImageLayer);
        // set the map to be displayed in this view
        mMapView.setMap(map);

        mScale = mMapView.getMapScale();


        // create an initial viewpoint using an envelope (of two points, bottom left and top right)
        Envelope envelope = new Envelope(new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator()),
                new Point(13098547.3108814, 3773861.02646202, SpatialReferences.getWebMercator()));
        //set viewpoint on mapview
        mMapView.setViewpointGeometryAsync(envelope, 100.0);

        // get the MapView's LocationDisplay
        mLocationDisplay = mMapView.getLocationDisplay();


        // Listen to changes in the status of the location data source.
        mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
            @Override
            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {

                // If LocationDisplay started OK, then continue.
                if (dataSourceStatusChangedEvent.isStarted())
                    return;

                // No error is reported, then continue.
                if (dataSourceStatusChangedEvent.getError() == null)
                    return;

                // If an error is found, handle the failure to start.
                // Check permissions to see if failure may be due to lack of permissions.
                boolean permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[0]) ==
                        PackageManager.PERMISSION_GRANTED;
                boolean permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[1]) ==
                        PackageManager.PERMISSION_GRANTED;

                if (!(permissionCheck1 && permissionCheck2)) {
                    // If permissions are not already granted, request permission from the user.
                    ActivityCompat.requestPermissions(MainActivity.this, reqPermissions, requestCode);
                } else {
                    // Report other unknown failure types to the user - for example, location services may not
                    // be enabled on the device.
                    String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
                            .getSource().getLocationDataSource().getError().getMessage());
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        //[DocRef: Name=Monitor map drawing, Category=Work with maps, Topic=Display a map]
        mMapView.addDrawStatusChangedListener(new DrawStatusChangedListener() {
            @Override
            public void drawStatusChanged(DrawStatusChangedEvent drawStatusChangedEvent) {
                if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.IN_PROGRESS) {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d("drawStatusChanged", "spinner visible");
                } else if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.COMPLETED) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        //[DocRef: END]

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Location permission was granted. This would have been triggered in response to failing to start the
            // LocationDisplay, so try starting this again.
            mLocationDisplay.startAsync();
        } else {
            // If permission was denied, show toast to inform user what was chosen. If LocationDisplay is started again,
            // request permission UX will be shown again, option should be shown to allow never showing the UX again.
            // Alternative would be to disable functionality so request is not shown again.
            Toast.makeText(MainActivity.this, getResources().getString(R.string.location_permission_denied), Toast
                    .LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtnPointCollect_main:
                Log.d(TAG, count + "");
                mBottomSheetBehavior.setPeekHeight(200);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.BtnLineCollect_main:
                //
                Log.d(TAG, "Click button Begin Line collect main");
//                Intent intent1 = new Intent(MainActivity.this,BegincollectingActivity.class);
//                startActivity(intent1);
                final Boolean grs = gpsApplication.getRecording();
                boolean newRecordingState = !grs;
                gpsApplication.setRecording(newRecordingState);
                break;
            case R.id.BtnQuery_main:
                //
                Log.d(TAG, "Click button query main");
                Intent intent2 = new Intent(MainActivity.this, DataQueryActivity.class);
                startActivity(intent2);
                break;
            case R.id.bSearch:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLineStop:
                Log.d(TAG, "Click button stop main");
//                Intent intent3 = new Intent(MainActivity.this,FinishcollectingActivity.class);
//                startActivity(intent3);
                gpsApplication.setNewTrackFlag(false);
                gpsApplication.setRecording(false);
                EventBus.getDefault().post(EventBusMSG.NEW_TRACK);
                Toast.makeText(this, "STOP Collecting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMyLocation:
                //
                mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                if (!mLocationDisplay.isStarted())
                    mLocationDisplay.startAsync();
                else {
                    mLocationDisplay.stop();
                }
                break;
            case R.id.btnZoomIn:
                mScale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(mScale * 0.5);
                break;
            case R.id.btnZoomOut:
                mScale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(mScale * 2);
                break;

        }
    }

    public void initToolBarAnDrawerLayout() {
        //设置我们的ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initHeadersInDrawer();



    }


    //Init the headers, user name, user address in drawer
    private void initHeadersInDrawer(){
        View headerView=navigationView.getHeaderView(0);
        mHeader=headerView.findViewById(R.id.user_head_img);
        mUsername=headerView.findViewById(R.id.user_anonymus);
        mAddress=headerView.findViewById(R.id.user_address);

        if(!judgeIfLogin()){
            mHeader.setImageResource(R.drawable.ic_launcher_logo);
            mUsername.setText("未登录");
            mAddress.setText("");
        }
        else {
            String currentUser;
            SharedPreferences sf= getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
            currentUser=sf.getString(ConstantValue.current_user,"未登录");
            userInfo=gpsApplication.getUserLoginInfo(currentUser);
            String fileName=HEAD_PATH+"header_"+currentUser+".png";
            File file = new File(fileName);
            if(file.exists()){
                Bitmap bitmap= AppBitmap.getBitmapFromFilePath(fileName);
                mHeader.setImageBitmap(bitmap);
            }
            else{
                mHeader.setImageResource(R.drawable.ic_launcher_logo);
            }
            if(userInfo.getAnonymous().equals("")){
                mUsername.setText(currentUser);
            }
            else {
                mUsername.setText(userInfo.getAnonymous());
            }
            mAddress.setText(userInfo.getAddress());

        }

        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, UserSettingActivity.class);
                startActivity(intent);
                drawer.closeDrawer(Gravity.START);
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Log.d(TAG,"Launch AboutActivity");
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        Log.d(TAG,"Check if it's login state");
        if(!judgeIfLogin()){
            drawer.closeDrawer(Gravity.START);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }

        if (id == R.id.nav_collectsum) {
            Log.d(TAG,"Launch CollectStaticActivity");
            Intent intent = new Intent(MainActivity.this, CollectStaticActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pointsetting) {
            Log.d(TAG,"Launch PointSetActivity");
            Intent intent = new Intent(MainActivity.this, PointSetActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_emptystorage) {

        } else if (id == R.id.nav_feedback) {
            Log.d(TAG,"Launch FeedBackActivity");
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_switchuser) {
            Log.d(TAG,"Launch LoginActivity");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            Log.d(TAG,"Logout");
            SharedPreferences sf= getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putInt(ConstantValue.login_state,0);
            editor.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //judge if it's login state, yes: do nothing; No: start login activity
    private boolean judgeIfLogin(){
        SharedPreferences sf = getSharedPreferences(ConstantValue.login_preference_name, MODE_PRIVATE);
        int loginState = sf.getInt(ConstantValue.login_state, 0);
        if (loginState == 0) {

            return false;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }
}
