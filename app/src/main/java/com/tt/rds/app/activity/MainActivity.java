package com.tt.rds.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.common.EventBusMSG;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    final MainApplication gpsApplication = MainApplication.getInstance();
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int RESULT_SETTINGS = 1;

    private MenuItem menutrackfinished = null;

    private boolean prefKeepScreenOn = true;


    private BottomSheetBehavior mBottomSheetBehavior;
    private View mBottomSheet;
    private GridView gv;

    private int count = 0;
    private MapView mMapView;

    private Button mSearchButton;
    private Button showall_button, hide_button;
    private List<String> collectPointList;
    private ArrayAdapter<String> gridViewArrayAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolBarAndSearchBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSearchButton = (Button) findViewById(R.id.bSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });


        initArcgisMap();


        //Implement click listeners
        findViewById(R.id.BtnPointCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnLineCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnQuery_main).setOnClickListener(this);
//
        showall_button = (Button) findViewById(R.id.show_button);
        hide_button = (Button) findViewById(R.id.hide_button);

        mBottomSheet = findViewById(R.id.bottomSheet);

        initBottomSheet();

        gv = (GridView) findViewById(R.id.gv);
        initBottomGridView();
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
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                if (selectedItem.equals("桥梁")) {
                    Intent intent = new Intent(MainActivity.this, BridgeActivity.class);
                    startActivity(intent);
                }
                if (selectedItem.equals("隧道")) {
                    Intent intent = new Intent(MainActivity.this, Bridge2Activity.class);
                    startActivity(intent);
                }
            }
        });

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

    private void initBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        //By default set BottomSheet Behavior as Collapsed and Height 0
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setSkipCollapsed(false);

//
//        //If you want to handle callback of Sheet Behavior you can use below code
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void initArcgisMap() {
        // inflate MapView from layout
        mMapView = (MapView) findViewById(R.id.mapView);
        // create a MapImageLayer with dynamically generated map images
        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.sample_service_url));
        // create an empty map instance
        ArcGISMap map = new ArcGISMap();
        // add map image layer as operational layer
        map.getOperationalLayers().add(mapImageLayer);
        // set the map to be displayed in this view
        mMapView.setMap(map);
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

    private void addItemInGV(List<String> plantsList, ArrayAdapter<String> gridViewArrayAdapter) {
        count++;
        plantsList.add(plantsList.size(), "hello" + count);

        // Update the GridView
        gridViewArrayAdapter.notifyDataSetChanged();

    }

    public void ontoggleRecordGeoPoint() {
        final Boolean grs = gpsApplication.getRecording();
        boolean newRecordingState = !grs;
        gpsApplication.setRecording(newRecordingState);
//            tableLayoutGeoPoints.setBackgroundColor(newRecordingState ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorTrackBackground));
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
                ontoggleRecordGeoPoint();
                break;


        }
    }


    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void initToolBarAndSearchBar(Toolbar toolbar) {
        //设置我们的ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        ShutdownApp();
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

        if (id == R.id.nav_collectsum) {
            // Handle the camera action
        } else if (id == R.id.nav_pointsetting) {

        } else if (id == R.id.nav_emptystorage) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_switchuser) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            ShutdownApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
        EventBus.getDefault().post(EventBusMSG.APP_PAUSE);
        Log.w("myApp", "[#] MainActivity.java - onPause()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();

        EventBus.getDefault().register(this);
        Log.w("myApp", "[#] MainActivity.java - onResume()");
        EventBus.getDefault().post(EventBusMSG.APP_RESUME);

        // Check for runtime Permissions (for Android 23+)
        if (!MainApplication.getInstance().isPermissionsChecked()) {
            MainApplication.getInstance().setPermissionsChecked(true);
            CheckPermissions();
        }
    }

//    @Override
//    public void onBackPressed() {
//        //moveTaskToBack(true);
//        ShutdownApp();
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        updateBottomSheetPosition();
    }

    @Subscribe
    public void onEvent(Short msg) {

        if (msg == EventBusMSG.REQUEST_ADD_PLACEMARK) {
            // Show Placemark Dialog
            FragmentManager fm = getSupportFragmentManager();
            ///++++++++ADD LGB
//            FragmentPlacemarkDialog placemarkDialog = new FragmentPlacemarkDialog();
//            placemarkDialog.show(fm, "");
            return;
        }
        if (msg == EventBusMSG.APPLY_SETTINGS) {
            LoadPreferences();
            return;
        }
        if (msg == EventBusMSG.UPDATE_TRACK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (menutrackfinished != null)
                        menutrackfinished.setVisible(!MainApplication.getInstance().getCurrentTrack().getName().equals(""));
                }
            });
            return;
        }
        if (msg == EventBusMSG.TOAST_UNABLE_TO_WRITE_THE_FILE) {
            final Context context = this;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, getString(R.string.export_unable_to_write_file), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void LoadPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefKeepScreenOn = preferences.getBoolean("prefKeepScreenOn", true);
        if (prefKeepScreenOn) getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void ShutdownApp() {
        if ((MainApplication.getInstance().getCurrentTrack().getNumberOfLocations() > 0)
                || (MainApplication.getInstance().getCurrentTrack().getNumberOfPlacemarks() > 0)
                || (MainApplication.getInstance().getRecording())
                || (MainApplication.getInstance().getPlacemarkRequest())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.StyledDialog));
            builder.setMessage(getResources().getString(R.string.message_exit_confirmation));
            builder.setIcon(android.R.drawable.ic_menu_info_details);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainApplication.getInstance().setRecording(false);
                    MainApplication.getInstance().setPlacemarkRequest(false);
                    EventBus.getDefault().post(EventBusMSG.NEW_TRACK);
                    MainApplication.getInstance().StopAndUnbindGPSService();

                    dialog.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {

            MainApplication.getInstance().setRecording(false);
            MainApplication.getInstance().setPlacemarkRequest(false);
            MainApplication.getInstance().StopAndUnbindGPSService();

            finish();
        }
    }


    public void CheckPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(this, p);
            Log.w("myApp", "[#] GPSActivity.java - " + p + " = PERMISSION_" + (result == PackageManager.PERMISSION_GRANTED ? "GRANTED" : "DENIED"));
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();

                if (grantResults.length > 0) {
                    // Fill with actual results from user
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for permissions
                    if (perms.containsKey(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Log.w("myApp", "[#] GPSActivity.java - ACCESS_FINE_LOCATION = PERMISSION_GRANTED");
                        } else {
                            Log.w("myApp", "[#] GPSActivity.java - ACCESS_FINE_LOCATION = PERMISSION_DENIED");
                        }
                    }

                    if (perms.containsKey(Manifest.permission.INTERNET)) {
                        if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                            Log.w("myApp", "[#] GPSActivity.java - INTERNET = PERMISSION_GRANTED");
                        } else {
                            Log.w("myApp", "[#] GPSActivity.java - INTERNET = PERMISSION_DENIED");
                        }
                    }

                    if (perms.containsKey(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.w("myApp", "[#] GPSActivity.java - WRITE_EXTERNAL_STORAGE = PERMISSION_GRANTED");
                            // ---------------------------------------------------- Create the Directories if not exist
                            File sd = new File(Environment.getExternalStorageDirectory() + "/GPSLogger");
                            if (!sd.exists()) {
                                sd.mkdir();
                            }
                            sd = new File(Environment.getExternalStorageDirectory() + "/GPSLogger/AppData");
                            if (!sd.exists()) {
                                sd.mkdir();
                            }
                            sd = new File(getApplicationContext().getFilesDir() + "/Thumbnails");
                            if (!sd.exists()) {
                                sd.mkdir();
                            }
                        } else {
                            Log.w("myApp", "[#] GPSActivity.java - WRITE_EXTERNAL_STORAGE = PERMISSION_DENIED");
                        }
                    }
                }
                break;
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
