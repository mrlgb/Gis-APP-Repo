package com.tt.rds.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tt.rds.app.R;
import com.tt.rds.app.app.GPSApplication;
import com.tt.rds.app.activity.pointcollect.MarkerActivity;
import com.tt.rds.app.activity.usersetting.AboutActivity;
import com.tt.rds.app.activity.usersetting.CollectStaticActivity;
import com.tt.rds.app.activity.usersetting.FeedbackActivity;
import com.tt.rds.app.activity.usersetting.LoginActivity;
import com.tt.rds.app.activity.usersetting.PointSetActivity;
import com.tt.rds.app.activity.usersetting.UserSettingActivity;
import com.tt.rds.app.app.Common;
import com.tt.rds.app.app.Constant;
import com.tt.rds.app.bean.AppBitmap;
import com.tt.rds.app.bean.User;
import com.tt.rds.app.bean.UserDao;
import com.tt.rds.app.common.EventBusMSG;
import com.tt.rds.app.common.LocationExtended;
import com.tt.rds.app.common.PhysicalData;
import com.tt.rds.app.common.PhysicalDataFormatter;
import com.tt.rds.app.common.Track;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //-----------------------------
    private static final int NOT_AVAILABLE = -100000;

    private final int GPS_DISABLED = 0;
    private final int GPS_OUTOFSERVICE = 1;
    private final int GPS_TEMPORARYUNAVAILABLE = 2;
    private final int GPS_SEARCHING = 3;
    private final int GPS_STABILIZING = 4;
    private final int GPS_OK = 5;

    private PhysicalDataFormatter phdformatter = new PhysicalDataFormatter();

    private TextView TVLatitude;
    private TextView TVLongitude;
    private TextView TVLatitudeUM;
    private TextView TVLongitudeUM;
    private TextView TVAltitude;
    private TextView TVAltitudeUM;
    private TextView TVSpeed;
    private TextView TVSpeedUM;
    private TextView TVBearing;
    private TextView TVAccuracy;
    private TextView TVAccuracyUM;
    private TextView TVGPSFixStatus;
    private TextView TVDirectionUM1;

    private TableLayout TLCoordinates;
    private TableLayout TLAltitude;
    private TableLayout TLSpeed;
    private TableLayout TLBearing;
    private TableLayout TLAccuracy;

    public PhysicalData phdLatitude;
    public PhysicalData phdLongitude;
    public PhysicalData phdAltitude;
    public PhysicalData phdSpeed;
    public PhysicalData phdBearing;
    public PhysicalData phdAccuracy;

    private LocationExtended location;
    private double AltitudeManualCorrection;
    private int prefDirections;
    private int GPSStatus;
    private boolean EGMAltitudeCorrection;
    private boolean isValidAltitude;
    //-----------------------------
    private PhysicalDataFormatter phdformatter2 = new PhysicalDataFormatter();

    private TextView TVDuration;
    private TextView TVTrackName;
    private TextView TVTrackID;
    private TextView TVDistance;
    private TextView TVDistanceUM;
    private TextView TVMaxSpeed;
    private TextView TVMaxSpeedUM;
    private TextView TVAverageSpeed;
    private TextView TVAverageSpeedUM;
    private TextView TVAltitudeGap;
    private TextView TVAltitudeGapUM;
    private TextView TVOverallDirection;
    private TextView TVTrackStatus;
    private TextView TVDirectionUM2;

    private TableLayout TLTrack;
    private TableLayout TLDuration;
    private TableLayout TLSpeedMax;
    private TableLayout TLSpeedAvg;
    private TableLayout TLDistance;
    private TableLayout TLAltitudeGap;
    private TableLayout TLOverallDirection;

    private PhysicalData phdDuration2;
    private PhysicalData phdSpeedMax2;
    private PhysicalData phdSpeedAvg2;
    private PhysicalData phdDistance2;
    private PhysicalData phdAltitudeGap2;
    private PhysicalData phdOverallDirection2;

    private String FTrackID = "";
    private String FTrackName = "";

    private Track track;
    private int prefDirections2;
    private boolean EGMAltitudeCorrection2;
    private boolean isValidAltitude2;

    //-----------------------------


    private BottomSheetBehavior mBottomSheetBehavior;
    private View mBottomSheet;
    private GridView gv;

    private int count = 0;
    private MapView mMapView;
    private ImageView mHeader;
    private TextView mUsername, mAddress;
    private NavigationView navigationView;

    private Button showall_button, hide_button;
    private Button cancelCollectBtn, addPlaceMarkBtn, pauseCollectBtn, stopCollectBtn;
    private Button startPathCollectBtn, clearUIBtn;
    private boolean displayOn = true;
    private List<String> collectPointList;
    private ArrayAdapter<String> gridViewArrayAdapter;
    private FrameLayout gpsfixLayout, trackLayout;

    Toast ToastClickAgain;

    final GPSApplication gpsGPSApplication = GPSApplication.getInstance();
    DrawerLayout drawer;
    private LocationDisplay mLocationDisplay;
    double mScale = 0.0;

    private int requestCode = 2;
    String[] reqPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
            .ACCESS_COARSE_LOCATION};


    private User current_user;
    private UserDao userDao;

    //-----------------------------
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int RESULT_SETTINGS = 1;

    private boolean prefKeepScreenOn = true;

    //-----------------------------


    public void Update() {
//        Log.w("myApp", "[#] GPSFix - Update(Location location)");
        location = gpsGPSApplication.getCurrentLocationExtended();
        AltitudeManualCorrection = gpsGPSApplication.getPrefAltitudeCorrection();
        prefDirections = gpsGPSApplication.getPrefShowDirections();
        GPSStatus = gpsGPSApplication.getGPSStatus();

        if ((location != null) && (GPSStatus == GPS_OK)) {

            phdLatitude = phdformatter.format(location.getLatitude(), PhysicalDataFormatter.FORMAT_LATITUDE);
            phdLongitude = phdformatter.format(location.getLongitude(), PhysicalDataFormatter.FORMAT_LONGITUDE);
            phdSpeed = phdformatter.format(location.getSpeed(), PhysicalDataFormatter.FORMAT_SPEED);
            phdAltitude = phdformatter.format(location.getAltitudeCorrected(AltitudeManualCorrection, EGMAltitudeCorrection), PhysicalDataFormatter.FORMAT_ALTITUDE);
            phdBearing = phdformatter.format(location.getBearing(), PhysicalDataFormatter.FORMAT_BEARING);
            phdAccuracy = phdformatter.format(location.getAccuracy(), PhysicalDataFormatter.FORMAT_ACCURACY);

            TVLatitude.setText(phdLatitude.Value);
            TVLongitude.setText(phdLongitude.Value);
            TVLatitudeUM.setText(phdLatitude.UM);
            TVLongitudeUM.setText(phdLongitude.UM);
            TVAltitude.setText(phdAltitude.Value);
            TVAltitudeUM.setText(phdAltitude.UM);
            TVSpeed.setText(phdSpeed.Value);
            TVSpeedUM.setText(phdSpeed.UM);
            TVBearing.setText(phdBearing.Value);
            TVAccuracy.setText(phdAccuracy.Value);
            TVAccuracyUM.setText(phdAccuracy.UM);

            // Colorize the Altitude textview depending on the altitude EGM Correction

            TVAltitude.setTextColor(isValidAltitude ? getResources().getColor(R.color.red) : getResources().getColor(R.color.green));
            TVAltitudeUM.setTextColor(isValidAltitude ? getResources().getColor(R.color.red) : getResources().getColor(R.color.green));


            TVGPSFixStatus.setVisibility(View.GONE);

            TVDirectionUM1.setVisibility(prefDirections == 0 ? View.GONE : View.VISIBLE);

            TLCoordinates.setVisibility(phdLatitude.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLAltitude.setVisibility(phdAltitude.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLSpeed.setVisibility(phdSpeed.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLBearing.setVisibility(phdBearing.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLAccuracy.setVisibility(phdAccuracy.Value.equals("") ? View.INVISIBLE : View.VISIBLE);

        } else {
            TLCoordinates.setVisibility(View.INVISIBLE);
            TLAltitude.setVisibility(View.INVISIBLE);
            TLSpeed.setVisibility(View.INVISIBLE);
            TLBearing.setVisibility(View.INVISIBLE);
            TLAccuracy.setVisibility(View.INVISIBLE);

            TVGPSFixStatus.setVisibility(View.VISIBLE);
            switch (GPSStatus) {
                case GPS_DISABLED:
                    TVGPSFixStatus.setText(R.string.gps_disabled);
                    break;
                case GPS_OUTOFSERVICE:
                    TVGPSFixStatus.setText(R.string.gps_out_of_service);
                    break;
                case GPS_TEMPORARYUNAVAILABLE:
                    TVGPSFixStatus.setText(R.string.gps_temporary_unavailable);
                    break;
                case GPS_SEARCHING:
                    TVGPSFixStatus.setText(R.string.gps_searching);
                    break;
                case GPS_STABILIZING:
                    TVGPSFixStatus.setText(R.string.gps_stabilizing);
                    break;
            }

        }

        track = gpsGPSApplication.getCurrentTrack();
        prefDirections2 = gpsGPSApplication.getPrefShowDirections();
        EGMAltitudeCorrection2 = gpsGPSApplication.getPrefEGM96AltitudeCorrection();

        if ((track != null) && (track.getNumberOfLocations() + track.getNumberOfPlacemarks() > 0)) {

            FTrackID = getString(R.string.track_id) + " " + String.valueOf(track.getId());
            FTrackName = track.getName();
            phdDuration2 = phdformatter2.format(track.getPrefTime(), PhysicalDataFormatter.FORMAT_DURATION);
            phdSpeedMax2 = phdformatter2.format(track.getSpeedMax(), PhysicalDataFormatter.FORMAT_SPEED);
            phdSpeedAvg2 = phdformatter2.format(track.getPrefSpeedAverage(), PhysicalDataFormatter.FORMAT_SPEED_AVG);
            phdDistance2 = phdformatter2.format(track.getEstimatedDistance(), PhysicalDataFormatter.FORMAT_DISTANCE);
            phdAltitudeGap2 = phdformatter.format(track.getEstimatedAltitudeGap(EGMAltitudeCorrection2), PhysicalDataFormatter.FORMAT_ALTITUDE);
            phdOverallDirection2 = phdformatter2.format(track.getBearing(), PhysicalDataFormatter.FORMAT_BEARING);

            TVTrackID.setText(FTrackID);
            TVTrackName.setText(FTrackName);
            TVDuration.setText(phdDuration2.Value);
            TVMaxSpeed.setText(phdSpeedMax2.Value);
            TVAverageSpeed.setText(phdSpeedAvg2.Value);
            TVDistance.setText(phdDistance2.Value);
            TVAltitudeGap.setText(phdAltitudeGap2.Value);
            TVOverallDirection.setText(phdOverallDirection2.Value);

            TVMaxSpeedUM.setText(phdSpeedMax2.UM);
            TVAverageSpeedUM.setText(phdSpeedAvg2.UM);
            TVDistanceUM.setText(phdDistance2.UM);
            TVAltitudeGapUM.setText(phdAltitudeGap2.UM);

            // Colorize the Altitude Gap textview depending on the altitude filter
            isValidAltitude2 = track.isValidAltitude();
            TVAltitudeGap.setTextColor(isValidAltitude2 ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.colorPrimary));
            TVAltitudeGapUM.setTextColor(isValidAltitude2 ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.colorPrimary));


            TVDirectionUM2.setVisibility(prefDirections == 0 ? View.GONE : View.VISIBLE);

            TLTrack.setVisibility(FTrackName.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLDuration.setVisibility(phdDuration2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLSpeedMax.setVisibility(phdSpeedMax2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLSpeedAvg.setVisibility(phdSpeedAvg2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLDistance.setVisibility(phdDistance2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLOverallDirection.setVisibility(phdOverallDirection2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);
            TLAltitudeGap.setVisibility(phdAltitudeGap2.Value.equals("") ? View.INVISIBLE : View.VISIBLE);

        } else {

            TLTrack.setVisibility(View.INVISIBLE);
            TLDuration.setVisibility(View.INVISIBLE);
            TLSpeedMax.setVisibility(View.INVISIBLE);
            TLSpeedAvg.setVisibility(View.INVISIBLE);
            TLDistance.setVisibility(View.INVISIBLE);
            TLOverallDirection.setVisibility(View.INVISIBLE);
            TLAltitudeGap.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBarAnDrawerLayout();

        initArcgisMap();

        initView();

        initBottomSheet();

    }

    private void initView() {
        findViewById(R.id.bSearch).setOnClickListener(this);


        startPathCollectBtn = (Button) findViewById(R.id.BtnLineCollect_main);
        addPlaceMarkBtn = (Button) findViewById(R.id.btnAddspliter);

        //Implement click listeners
        findViewById(R.id.BtnPointCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnLineCollect_main).setOnClickListener(this);
        findViewById(R.id.BtnQuery_main).setOnClickListener(this);

        findViewById(R.id.btnLinePause).setOnClickListener(this);
        findViewById(R.id.btnLineCancel).setOnClickListener(this);
        findViewById(R.id.btnPathCollectStop).setOnClickListener(this);
        findViewById(R.id.btnAddspliter).setOnClickListener(this);

        findViewById(R.id.btnZoomIn).setOnClickListener(this);
        findViewById(R.id.btnZoomOut).setOnClickListener(this);

        clearUIBtn = (Button) findViewById(R.id.btnLineClear);
        findViewById(R.id.btnLineClear).setOnClickListener(this);
        findViewById(R.id.btnMyLocation).setOnClickListener(this);

        TVLatitude = (TextView) findViewById(R.id.id_textView_Latitude);
        TVLongitude = (TextView) findViewById(R.id.id_textView_Longitude);
        TVLatitudeUM = (TextView) findViewById(R.id.id_textView_LatitudeUM);
        TVLongitudeUM = (TextView) findViewById(R.id.id_textView_LongitudeUM);
        TVAltitude = (TextView) findViewById(R.id.id_textView_Altitude);
        TVAltitudeUM = (TextView) findViewById(R.id.id_textView_AltitudeUM);
        TVSpeed = (TextView) findViewById(R.id.id_textView_Speed);
        TVSpeedUM = (TextView) findViewById(R.id.id_textView_SpeedUM);
        TVBearing = (TextView) findViewById(R.id.id_textView_Bearing);
        TVAccuracy = (TextView) findViewById(R.id.id_textView_Accuracy);
        TVAccuracyUM = (TextView) findViewById(R.id.id_textView_AccuracyUM);
        TVGPSFixStatus = (TextView) findViewById(R.id.id_textView_GPSFixStatus);
        TVDirectionUM1 = (TextView) findViewById(R.id.id_textView_BearingUM);

        TLCoordinates = (TableLayout) findViewById(R.id.id_TableLayout_Coordinates);
        TLAltitude = (TableLayout) findViewById(R.id.id_TableLayout_Altitude);
        TLSpeed = (TableLayout) findViewById(R.id.id_TableLayout_Speed);
        TLBearing = (TableLayout) findViewById(R.id.id_TableLayout_Bearing);
        TLAccuracy = (TableLayout) findViewById(R.id.id_TableLayout_Accuracy);


        TVDuration = (TextView) findViewById(R.id.id_textView_Duration);
        TVTrackID = (TextView) findViewById(R.id.id_textView_TrackIDLabel);
        TVTrackName = (TextView) findViewById(R.id.id_textView_TrackName);
        TVDistance = (TextView) findViewById(R.id.id_textView_Distance);
        TVMaxSpeed = (TextView) findViewById(R.id.id_textView_SpeedMax);
        TVAverageSpeed = (TextView) findViewById(R.id.id_textView_SpeedAvg);
        TVAltitudeGap = (TextView) findViewById(R.id.id_textView_AltitudeGap);
        TVOverallDirection = (TextView) findViewById(R.id.id_textView_OverallDirection);
        TVDirectionUM2 = (TextView) findViewById(R.id.id_textView_OverallDirectionUM);

        TVDistanceUM = (TextView) findViewById(R.id.id_textView_DistanceUM);
        TVMaxSpeedUM = (TextView) findViewById(R.id.id_textView_SpeedMaxUM);
        TVAverageSpeedUM = (TextView) findViewById(R.id.id_textView_SpeedAvgUM);
        TVAltitudeGapUM = (TextView) findViewById(R.id.id_textView_AltitudeGapUM);

        TLTrack = (TableLayout) findViewById(R.id.id_tableLayout_TrackName);
        TLDuration = (TableLayout) findViewById(R.id.id_tableLayout_Duration);
        TLSpeedMax = (TableLayout) findViewById(R.id.id_tableLayout_SpeedMax);
        TLDistance = (TableLayout) findViewById(R.id.id_tableLayout_Distance);
        TLSpeedAvg = (TableLayout) findViewById(R.id.id_tableLayout_SpeedAvg);
        TLAltitudeGap = (TableLayout) findViewById(R.id.id_tableLayout_AltitudeGap);
        TLOverallDirection = (TableLayout) findViewById(R.id.id_tableLayout_OverallDirection);

        ToastClickAgain = Toast.makeText(this, getString(R.string.toast_track_finished_click_again), Toast.LENGTH_SHORT);

        gpsfixLayout = (FrameLayout) findViewById(R.id.gpsfixFrameLayout);
        trackLayout = (FrameLayout) findViewById(R.id.trackFrameLayout);

    }

    private void createPhotoPath() {
        File file_photo = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/tt/collectionpo");
        if (!file_photo.exists())
            file_photo.mkdirs();
        Constant.myCaptureFile = file_photo.getPath() + "/";
    }

    private void initBottomGridView() {
        // Populate a List from Array elements
        collectPointList = gpsGPSApplication.getCollectPointList();

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
                    Intent intent = new Intent(MainActivity.this, MarkerActivity.class);
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
            gpsGPSApplication.switchCollectPointList(false);
            showall_button.setText("常用");
        } else {
            showall_button.setText("全部");
            gpsGPSApplication.switchCollectPointList(true);
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
                            Log.w("myApp", "[#] MainActivity - ACCESS_FINE_LOCATION = PERMISSION_GRANTED");
                        } else {
                            Log.w("myApp", "[#] MainActivity - ACCESS_FINE_LOCATION = PERMISSION_DENIED");
                        }
                    }

                    if (perms.containsKey(Manifest.permission.INTERNET)) {
                        if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                            Log.w("myApp", "[#] MainActivity - INTERNET = PERMISSION_GRANTED");
                        } else {
                            Log.w("myApp", "[#] MainActivity - INTERNET = PERMISSION_DENIED");
                        }
                    }

                    if (perms.containsKey(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.w("myApp", "[#] MainActivity - WRITE_EXTERNAL_STORAGE = PERMISSION_GRANTED");
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
//                            EGM96 egm96 = EGM96.getInstance();
//                            if (egm96 != null) {
//                                if (!egm96.isEGMGridLoaded()) {
//                                    //Log.w("myApp", "[#] GPSApplication.java - Loading EGM Grid...");
//                                    egm96.LoadGridFromFile(Environment.getExternalStorageDirectory() + "/GPSLogger/AppData/WW15MGH.DAC", getApplicationContext().getFilesDir() + "/WW15MGH.DAC");
//                                }
//                            }
                        }

                        if (perms.containsKey(PackageManager.PERMISSION_GRANTED)) {
                            mLocationDisplay.startAsync();
                        } else {
                            Log.w("myApp", "[#] MainActivity - WRITE_EXTERNAL_STORAGE = PERMISSION_DENIED");
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.location_permission_denied), Toast
                                    .LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // Location permission was granted. This would have been triggered in response to failing to start the
//            // LocationDisplay, so try starting this again.
//            mLocationDisplay.startAsync();
//        } else {
//            // If permission was denied, show toast to inform user what was chosen. If LocationDisplay is started again,
//            // request permission UX will be shown again, option should be shown to allow never showing the UX again.
//            // Alternative would be to disable functionality so request is not shown again.
//            Toast.makeText(MainActivity.this, getResources().getString(R.string.location_permission_denied), Toast
//                    .LENGTH_SHORT).show();
//
//        }
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
                Log.d(TAG, "start path collect!");
//                Intent intent1 = new Intent(MainActivity.this,BegincollectingActivity.class);
//                startActivity(intent1);
                onPlacemarkRequest();
                ontoggleRecordGeoPoint();
                break;
            case R.id.btnAddspliter:
                Log.d(TAG, "path collect--add placemark!");
                onPlacemarkRequest();
                break;

            case R.id.btnPathCollectStop:
                if (gpsGPSApplication.getNewTrackFlag()) {
                    // This is the second click
                    GPSApplication.getInstance().setNewTrackFlag(false);
                    GPSApplication.getInstance().setRecording(false);
                    EventBus.getDefault().post(EventBusMSG.NEW_TRACK);
                    ToastClickAgain.cancel();
//                    Toast.makeText(this, getString(R.string.toast_track_saved_into_tracklist), Toast.LENGTH_SHORT).show();
                    Snackbar.make(mMapView, "路线保存成功！", Snackbar.LENGTH_LONG).show();
                } else {
                    // This is the first click
                    gpsGPSApplication.setNewTrackFlag(true); // Start the timer
                    ToastClickAgain.show();
                }

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
            case R.id.btnLineClear:
//                if (clearUIBtn.getText().equals("清除")) {
//                    gpsfixLayout.setVisibility(View.GONE);
//                    trackLayout.setVisibility(View.GONE);
//                    clearUIBtn.setText("显示");
//                } else {
//                    gpsfixLayout.setVisibility(View.VISIBLE);
//                    trackLayout.setVisibility(View.VISIBLE);
//                    clearUIBtn.setText("清除");
//                }
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
    private void initHeadersInDrawer() {
        View headerView = navigationView.getHeaderView(0);
        mHeader = headerView.findViewById(R.id.user_head_img);
        mUsername = headerView.findViewById(R.id.user_anonymus);
        mAddress = headerView.findViewById(R.id.user_address);


        updateHeaderView();
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!judgeIfLogin()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Common.USER_STATE_CHANGE);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserSettingActivity.class);
                    startActivityForResult(intent, Common.USER_STATE_CHANGE);
                }
                drawer.closeDrawer(Gravity.START);
            }
        });
    }

    private void updateHeaderView() {
        if (!judgeIfLogin()) {
            mHeader.setImageResource(R.drawable.ic_launcher_logo);
            mUsername.setText("未登录");
            mAddress.setText("");
        } else {
            userDao = gpsGPSApplication.getDbService().getUserDao();
            SharedPreferences sf = getSharedPreferences(Common.login_preference_name, MODE_PRIVATE);
            String cur_username = sf.getString(Common.current_user, "");
            current_user = userDao.queryBuilder().where(UserDao.Properties.User.eq(cur_username)).build().unique();

            String fileName = Common.HEADER_PATH + "header_" + current_user.getUser() + ".jpg";
            File file = new File(fileName);
            if (file.exists()) {
                Bitmap bitmap = AppBitmap.getBitmapFromFilePath(fileName);
                mHeader.setImageBitmap(AppBitmap.getRoundBitmap(bitmap));
            } else {
                mHeader.setImageResource(R.drawable.ic_launcher_logo);
            }
            if (current_user.getAnonymous().equals("")) {
                mUsername.setText(current_user.getUser());
            } else {
                mUsername.setText(current_user.getAnonymous());
            }
            mAddress.setText(current_user.getAddress());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.USER_STATE_CHANGE && resultCode == Common.USER_STATE_CHANGE_BACK) {
            //update headerView
            updateHeaderView();
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Log.d(TAG, "Launch AboutActivity");
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        Log.d(TAG, "Check if it's login state");
        if (!judgeIfLogin()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, Common.USER_STATE_CHANGE);
            drawer.closeDrawer(Gravity.START);
            return false;
        }

        if (id == R.id.nav_collectsum) {
            Log.d(TAG, "Launch CollectStaticActivity");
            Intent intent = new Intent(MainActivity.this, CollectStaticActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pointsetting) {
            Log.d(TAG, "Launch PointSetActivity");
            Intent intent = new Intent(MainActivity.this, PointSetActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_emptystorage) {

        } else if (id == R.id.nav_feedback) {
            Log.d(TAG, "Launch FeedBackActivity");
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_switchuser) {
            Log.d(TAG, "Launch LoginActivity");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, Common.USER_STATE_CHANGE);

        } else if (id == R.id.nav_exit) {
            Log.d(TAG, "Logout");
            SharedPreferences sf = getSharedPreferences(Common.login_preference_name, MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putInt(Common.login_state, 0);
            editor.commit();
            updateHeaderView();
            //----exit
            ShutdownApp();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //judge if it's login state, yes: do nothing; No: start login activity
    private boolean judgeIfLogin() {
        SharedPreferences sf = getSharedPreferences(Common.login_preference_name, MODE_PRIVATE);
        int loginState = sf.getInt(Common.login_state, 0);
        if (loginState == 0) {

            return false;
        }
        return true;
    }

    @Override
    public void onResume() {

        EventBus.getDefault().register(this);
        Log.w("myApp", "[#] MainActivity - onResume()");
        EventBus.getDefault().post(EventBusMSG.APP_RESUME);
//        if (menutrackfinished != null) menutrackfinished.setVisible(!GPSApplication.getInstance().getCurrentTrack().getName().equals(""));

        // Check for runtime Permissions (for Android 23+)
//        if (!GPSApplication.getInstance().isPermissionsChecked()) {
//            GPSApplication.getInstance().setPermissionsChecked(true);
//            CheckPermissions();
//        }

        mMapView.resume();
        super.onResume();

    }

    public void CheckPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(this, p);
            Log.w("myApp", "[#] MainActivity - " + p + " = PERMISSION_" + (result == PackageManager.PERMISSION_GRANTED ? "GRANTED" : "DENIED"));
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().post(EventBusMSG.APP_PAUSE);
        Log.w("myApp", "[#] MainActivity - onPause()");
        EventBus.getDefault().unregister(this);

        super.onPause();
        mMapView.pause();
    }

    @Subscribe
    public void onEvent(Short msg) {

        if (msg == EventBusMSG.UPDATE_FIX) {
            (MainActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Update();
                }
            });
        }

        if (msg == EventBusMSG.REQUEST_ADD_PLACEMARK) {
            // Show Placemark Dialog
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentPlacemarkDialog placemarkDialog = new FragmentPlacemarkDialog();
//            placemarkDialog.show(fm, "");
            return;
        }

        if (msg == EventBusMSG.UPDATE_TRACK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if (menutrackfinished != null) menutrackfinished.setVisible(!GPSApplication.getInstance().getCurrentTrack().getName().equals(""));
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

    public void ontoggleRecordGeoPoint() {

        final Boolean grs = gpsGPSApplication.getRecording();
        boolean newRecordingState = !grs;
        gpsGPSApplication.setRecording(newRecordingState);
//        tableLayoutGeoPoints.setBackgroundColor(newRecordingState ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorTrackBackground));
        startPathCollectBtn.setBackgroundColor(newRecordingState ?
                getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.green));
    }

    public void onPlacemarkRequest() {

        final Boolean pr = gpsGPSApplication.getPlacemarkRequest();
        boolean newPlacemarkRequestState = !pr;
        gpsGPSApplication.setPlacemarkRequest(newPlacemarkRequestState);
        addPlaceMarkBtn.setBackgroundColor(newPlacemarkRequestState ?
                getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.green));

    }


    private void ShutdownApp() {
        if ((GPSApplication.getInstance().getCurrentTrack().getNumberOfLocations() > 0)
                || (GPSApplication.getInstance().getCurrentTrack().getNumberOfPlacemarks() > 0)
                || (GPSApplication.getInstance().getRecording())
                || (GPSApplication.getInstance().getPlacemarkRequest())) {

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Material Design Dialog");
            builder.setMessage(getResources().getString(R.string.message_exit_confirmation));
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    gpsGPSApplication.setRecording(false);
                    gpsGPSApplication.setPlacemarkRequest(false);
                    EventBus.getDefault().post(EventBusMSG.NEW_TRACK);
                    gpsGPSApplication.StopAndUnbindGPSService();
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();

        } else {
            gpsGPSApplication.setRecording(false);
            gpsGPSApplication.setPlacemarkRequest(false);
            gpsGPSApplication.StopAndUnbindGPSService();

            finish();
        }
    }

}
