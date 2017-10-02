package com.tt.rds.app;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tt.rds.app.bean.PointType;
import com.tt.rds.app.bean.User;
import com.tt.rds.app.common.ConstantValue;
import com.tt.rds.app.common.EventBusMSG;
import com.tt.rds.app.common.EventBusMSGLong;
import com.tt.rds.app.common.EventBusMSGNormal;
import com.tt.rds.app.common.GPSService;
import com.tt.rds.app.common.LocationExtended;
import com.tt.rds.app.common.Track;
import com.tt.rds.app.db.DatabaseHandler;
import com.tt.rds.app.net.HttpMethods;
import com.tt.rds.app.activity.ActivityTaskManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainApplication extends Application implements GpsStatus.Listener, LocationListener {
    //history
    String[] normal ;
    String[] all ;
    private HttpMethods httpMethods;

    private User user;
    private boolean useFilter = false;
    private List<String> collectPointList;

    public List<String> getCollectPointList() {
        return collectPointList;
    }

    public void setCollectPointList(List<String> collectPointList) {
        this.collectPointList = collectPointList;
    }

    public void switchCollectPointList(boolean isAll) {
        this.collectPointList.clear();
        if (isAll) {
            for (int i = 0; i < normal.length; i++) {
                collectPointList.add(i, normal[i]);
            }
        } else {
            for (int i = 0; i < all.length; i++) {
                collectPointList.add(i, all[i]);
            }
        }

    }
    //==history

    //----------------CP--------------------------------
    //private static final float M_TO_FT = 3.280839895f;
    private static final int NOT_AVAILABLE = -100000;

    //private static final int UM_METRIC_MS = 0;
    private static final int UM_METRIC_KMH = 1;
    //private static final int UM_IMPERIAL_FPS = 8;
    //private static final int UM_IMPERIAL_MPH = 9;

    private static final int STABILIZERVALUE = 3000;            // The application discards fixes for 3000 ms (minimum)
    private static final int DEFAULTHANDLERTIMER = 5000;        // The timer for turning off GPS on exit
    private static final int GPSUNAVAILABLEHANDLERTIMER = 7000; // The "GPS temporary unavailable" timer
    private int StabilizingSamples = 3;

    private static final int GPS_DISABLED = 0;
    private static final int GPS_OUTOFSERVICE = 1;
    private static final int GPS_TEMPORARYUNAVAILABLE = 2;
    private static final int GPS_SEARCHING = 3;
    private static final int GPS_STABILIZING = 4;
    private static final int GPS_OK = 5;

    // Preferences Variables
    // private boolean prefKeepScreenOn = true;                 // DONE in GPSActivity
    private boolean prefShowDecimalCoordinates = false;
    private int prefUM = UM_METRIC_KMH;
    private float prefGPSdistance = 0f;
    private long prefGPSupdatefrequency = 1000L;
    private boolean prefEGM96AltitudeCorrection = false;
    private double prefAltitudeCorrection = 0d;
    private boolean prefExportKML = true;
    private boolean prefExportGPX = true;
    private boolean prefExportTXT = false;
    private int prefKMLAltitudeMode = 0;
    private int prefShowTrackStatsType = 0;
    private int prefShowDirections = 0;
    private SharedPreferences sf_login;

    private boolean PermissionsChecked = false;                 // If the flag is false the GPSActivity will check for permissions

    private LocationExtended PrevFix = null;
    private boolean isPrevFixRecorded = false;

    private LocationExtended PrevRecordedFix = null;

    private boolean MustUpdatePrefs = true;                     // True if preferences needs to be updated

    private boolean isCurrentTrackVisible = false;
    private boolean isContextMenuShareVisible = false;          // True if "Share with ..." menu is visible
    private boolean isContextMenuViewVisible = false;           // True if "View in *" menu is visible
    private boolean isContextMenuEnabled = false;               // True if the Share + View + Export menus are enabled (Permission to write storage)
    private String ViewInApp = "";                              // The string of default app name for "View"
    // "" in case of selector

    // Singleton instance
    private static MainApplication singleton;

    public static MainApplication getInstance() {
        return singleton;
    }


    DatabaseHandler GPSDataBase;
    private String PlacemarkDescription = "";
    private boolean Recording = false;
    private boolean PlacemarkRequest = false;
    private long OpenInViewer = -1;                    // The index to be opened in viewer
    private long Share = -1;                                // The index to be Shared
    private boolean isGPSLocationUpdatesActive = false;
    private int GPSStatus = GPS_SEARCHING;

    private boolean NewTrackFlag = false;                   // The variable that handle the double-click on "Track Finished"
    final Handler newtrackhandler = new Handler();
    Runnable newtrackr = new Runnable() {
        @Override
        public void run() {
            NewTrackFlag = false;
        }
    };

    private LocationManager mlocManager = null;             // GPS LocationManager
    private int _NumberOfSatellites = 0;
    private int _NumberOfSatellitesUsedInFix = 0;

    private int _Stabilizer = StabilizingSamples;
    private int HandlerTimer = DEFAULTHANDLERTIMER;

    private LocationExtended _currentLocationExtended = null;
    private LocationExtended _currentPlacemark = null;
    private Track _currentTrack = null;
    private List<PointType> pointTypes;
    private List<Map<String,Object>> userLoginInfo;
    private List<Track> _ArrayListTracks = Collections.synchronizedList(new ArrayList<Track>());

    static SparseArray<Bitmap> thumbsArray = new SparseArray<>();       // The Array containing the Tracks Thumbnail

    //    Exporter Ex;
    private AsyncUpdateThreadClass asyncUpdateThread = new AsyncUpdateThreadClass();

    final Handler handler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            setGPSLocationUpdates(false);
        }
    };

    final Handler gpsunavailablehandler = new Handler();
    Runnable unavailr = new Runnable() {

        @Override
        public void run() {
            if ((GPSStatus == GPS_OK) || (GPSStatus == GPS_STABILIZING)) {
                GPSStatus = GPS_TEMPORARYUNAVAILABLE;
                EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
            }
        }
    };

    //----------------CP--------------------------------

    // -------------------------------------------------------------------- Service
    Intent GPSServiceIntent;
    GPSService GPSLoggerService;
    boolean isGPSServiceBound = false;

    private ServiceConnection GPSServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            GPSService.LocalBinder binder = (GPSService.LocalBinder) service;
            GPSLoggerService = binder.getServiceInstance();                     //Get instance of your service!
            Log.w("myApp", "[#] GPSApplication.java - GPSSERVICE CONNECTED - onServiceConnected event");
            isGPSServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.w("myApp", "[#] GPSApplication.java - GPSSERVICE DISCONNECTED - onServiceDisconnected event");
            isGPSServiceBound = false;
        }
    };

    private void StartAndBindGPSService() {
        GPSServiceIntent = new Intent(MainApplication.this, GPSService.class);
        startService(GPSServiceIntent);                                                    //Starting the service
        bindService(GPSServiceIntent, GPSServiceConnection, Context.BIND_AUTO_CREATE);     //Binding to the service!
        Log.w("myApp", "[#] GPSApplication.java - StartAndBindGPSService");
    }


    /* private void UnbindGPSService() {                                                //UNUSED
        try {
            unbindService(GPSServiceConnection);                                        //Unbind to the service
            Log.w("myApp", "[#] GPSApplication.java - Service unbound");
        } catch (Exception e) {
            Log.w("myApp", "[#] GPSApplication.java - Unable to unbind the GPSService");
        }
    } */

    public void StopAndUnbindGPSService() {
        try {
            unbindService(GPSServiceConnection);                                        //Unbind to the service
            Log.w("myApp", "[#] GPSApplication.java - Service unbound");
        } catch (Exception e) {
            Log.w("myApp", "[#] GPSApplication.java - Unable to unbind the GPSService");
        }
        try {
            stopService(GPSServiceIntent);                                                  //Stop the service
            Log.w("myApp", "[#] GPSApplication.java - Service stopped");
        } catch (Exception e) {
            Log.w("myApp", "[#] GPSApplication.java - Unable to stop GPSService");
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Resources res = getResources();
        normal = res.getStringArray(R.array.points_normal);
        all = res.getStringArray(R.array.points_all);
        collectPointList = new ArrayList<String>(Arrays.asList(normal));

        singleton = this;

        StartAndBindGPSService();

        EventBus.getDefault().register(this);

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);     // Location Manager

        File sd = new File(Environment.getExternalStorageDirectory() + "/GPSLogger");   // Create the Directories if not exist
        if (!sd.exists()) {
            sd.mkdir();
            Log.w("myApp", "[#] GPSApplication.java - Folder created: " + sd.getAbsolutePath());
        }
        sd = new File(Environment.getExternalStorageDirectory() + "/GPSLogger/AppData");
        if (!sd.exists()) {
            sd.mkdir();
            Log.w("myApp", "[#] GPSApplication.java - Folder created: " + sd.getAbsolutePath());
        }

        sd = new File(getApplicationContext().getFilesDir() + "/Thumbnails");
        if (!sd.exists()) {
            sd.mkdir();
            Log.w("myApp", "[#] GPSApplication.java - Folder created: " + sd.getAbsolutePath());
        }


        GPSDataBase = new DatabaseHandler(this);

        sf_login = getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
        // Initialize the Database

        // Prepare the current track
        if (GPSDataBase.getLastTrackID() == 0) GPSDataBase.addTrack(new Track());       // Creation of the first track if the DB is empty
        _currentTrack = GPSDataBase.getLastTrack();                                     // Get the last track

//        LoadPreferences();                                                              // Load Settings

        // ----------------------------------------------------------------------------------------

        asyncUpdateThread.start();
        AsyncTODO ast = new AsyncTODO();
        ast.TaskType = "TASK_NEWTRACK";
        ast.location = null;
        AsyncTODOQueue.add(ast);

//        asyncThumbsLoaderThreadClass.start();

    }

    @Override
    public void onTerminate() {
        Log.w("myApp", "[#] GPSApplication.java - onTerminate");
        GPSDataBase.close();
        EventBus.getDefault().unregister(this);
        StopAndUnbindGPSService();
        super.onTerminate();
    }

    @Subscribe
    public void onEvent(EventBusMSGLong msg) {
        if (msg.MSGType == EventBusMSG.TRACK_SETPROGRESS) {
            long trackid = msg.id;
            long progress = msg.Value;
            if ((trackid > 0) && (progress >= 0)) {
                synchronized(_ArrayListTracks) {
                    for (Track T : _ArrayListTracks) {
                        if (T.getId() == trackid) T.setProgress((int) progress);
                    }
                }
            }
            return;
        }
    }

    @Subscribe
    public void onEvent(EventBusMSGNormal msg) {
        if (msg.MSGType == EventBusMSG.TRACK_EXPORTED) {
            long trackid = msg.id;
            if (trackid > 0) {
                synchronized(_ArrayListTracks) {
                    for (Track T : _ArrayListTracks) {
                        if (T.getId() == trackid) {
                            T.setProgress(0);
                            EventBus.getDefault().post(EventBusMSG.UPDATE_TRACKLIST);
                            if (trackid == OpenInViewer) {
                                OpenInViewer = -1;
                                File file = new File(Environment.getExternalStorageDirectory() + "/GPSLogger/AppData/", T.getName() + ".kml");
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.google-earth.kml+xml");
                                startActivity(intent);
                            }
                            if (trackid == Share) {
                                Share = -1;
                                EventBus.getDefault().post(new EventBusMSGNormal(EventBusMSG.INTENT_SEND, trackid));
                            }
                        }
                    }
                }
            }
            return;
        }
        if (msg.MSGType == EventBusMSG.DELETE_TRACK) {
            AsyncTODO ast = new AsyncTODO();
            ast.TaskType = "TASK_DELETE_TRACK " + msg.id;
            ast.location = null;
            AsyncTODOQueue.add(ast);
            return;
        }
        if (msg.MSGType == EventBusMSG.TOAST_UNABLE_TO_WRITE_THE_FILE) {
            long trackid = msg.id;
            if (trackid > 0) {
                synchronized(_ArrayListTracks) {
                    for (Track T : _ArrayListTracks) {
                        if (T.getId() == trackid) {
                            T.setProgress(0);
                            EventBus.getDefault().post(EventBusMSG.UPDATE_TRACKLIST);
                            if (trackid == OpenInViewer) {
                                OpenInViewer = -1;
                            }
                            if (trackid == Share) {
                                Share = -1;
                            }
                        }
                    }
                }
            }
            return;
        }
    }

    @Subscribe
    public void onEvent(Short msg) {
        if (msg == EventBusMSG.NEW_TRACK) {
            AsyncTODO ast = new AsyncTODO();
            ast.TaskType = "TASK_NEWTRACK";
            ast.location = null;
            AsyncTODOQueue.add(ast);
            return;
        }
        if (msg == EventBusMSG.ADD_PLACEMARK) {
            AsyncTODO ast = new AsyncTODO();
            ast.TaskType = "TASK_ADDPLACEMARK";
            ast.location = _currentPlacemark;
            _currentPlacemark.setDescription(PlacemarkDescription);
            AsyncTODOQueue.add(ast);
            return;
        }
        if (msg == EventBusMSG.APP_PAUSE) {
            handler.postDelayed(r, getHandlerTimer());  // Starts the switch-off handler (delayed by HandlerTimer)
            System.gc();                                // Clear mem from released objects with Garbage Collector
            //UnbindGPSService();
            return;
        }
        if (msg == EventBusMSG.APP_RESUME) {
            //Log.w("myApp", "[#] GPSApplication.java - Received EventBusMSG.APP_RESUME");
            handler.removeCallbacks(r);                 // Cancel the switch-off handler
            setHandlerTimer(DEFAULTHANDLERTIMER);
            setGPSLocationUpdates(true);
            if (MustUpdatePrefs) {
                MustUpdatePrefs = false;
                LoadPreferences();
            }
            StartAndBindGPSService();
            return;
        }
        if (msg == EventBusMSG.UPDATE_SETTINGS) {
            MustUpdatePrefs = true;
            return;
        }
    }

    public void setGPSLocationUpdates (boolean state) {
        // Request permissions = https://developer.android.com/training/permissions/requesting.html

        if (!state && !getRecording() && isGPSLocationUpdatesActive
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            GPSStatus = GPS_SEARCHING;
            mlocManager.removeGpsStatusListener(this);
            mlocManager.removeUpdates(this);
            isGPSLocationUpdatesActive = false;
        }
        if (state && !isGPSLocationUpdatesActive
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mlocManager.addGpsStatusListener(this);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, prefGPSupdatefrequency, 0, this); // Requires Location update
            isGPSLocationUpdatesActive = true;
            StabilizingSamples = (int) Math.ceil(STABILIZERVALUE / prefGPSupdatefrequency);
        }
    }

    public void updateGPSLocationFrequency () {

        if (isGPSLocationUpdatesActive
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mlocManager.removeGpsStatusListener(this);
            mlocManager.removeUpdates(this);
            StabilizingSamples = (int) Math.ceil(STABILIZERVALUE / prefGPSupdatefrequency);
            mlocManager.addGpsStatusListener(this);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, prefGPSupdatefrequency, 0, this);
        }
    }

    public void updateSats() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            final GpsStatus gs = this.mlocManager.getGpsStatus(null);
            int sats_inview = 0;    // Satellites in view;
            int sats_used = 0;      // Satellites used in fix;

            Iterable<GpsSatellite> sats = gs.getSatellites();
            for (GpsSatellite sat : sats) {
                sats_inview++;
                if (sat.usedInFix()) sats_used++;
                //Log.w("myApp", "[#] GPSApplication.java - updateSats: i=" + i);
            }
            _NumberOfSatellites = sats_inview;
            _NumberOfSatellitesUsedInFix = sats_used;
        } else {
            _NumberOfSatellites = NOT_AVAILABLE;
            _NumberOfSatellitesUsedInFix = NOT_AVAILABLE;
        }
        //Log.w("myApp", "[#] GPSApplication.java - updateSats: Total=" + _NumberOfSatellites + " Used=" + _NumberOfSatellitesUsedInFix);
    }

    // ------------------------------------------------------------------------- GpsStatus.Listener
    @Override
    public void onGpsStatusChanged(final int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                // TODO: get here the status of the GPS, and save into a GpsStatus to be used for satellites visualization;
                // Use GpsStatus getGpsStatus (GpsStatus status)
                // https://developer.android.com/reference/android/location/LocationManager.html#getGpsStatus(android.location.GpsStatus)
                updateSats();
                break;
        }
    }

    // --------------------------------------------------------------------------- LocationListener
    @Override
    public void onLocationChanged(Location loc) {
        //if ((loc != null) && (loc.getProvider().equals(LocationManager.GPS_PROVIDER)) {
        if (loc != null) {      // Location data is valid
            //Log.w("myApp", "[#] GPSApplication.java - onLocationChanged: provider=" + loc.getProvider());
            LocationExtended eloc = new LocationExtended(loc);
            eloc.setNumberOfSatellites(getNumberOfSatellites());
            eloc.setNumberOfSatellitesUsedInFix(getNumberOfSatellitesUsedInFix());
            boolean ForceRecord = false;

            gpsunavailablehandler.removeCallbacks(unavailr);                            // Cancel the previous unavail countdown handler
            gpsunavailablehandler.postDelayed(unavailr, GPSUNAVAILABLEHANDLERTIMER);    // starts the unavailability timeout (in 7 sec.)

            if (GPSStatus != GPS_OK) {
                if (GPSStatus != GPS_STABILIZING) {
                    GPSStatus = GPS_STABILIZING;
                    _Stabilizer = StabilizingSamples;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
                }
                else _Stabilizer--;
                if (_Stabilizer == 0) GPSStatus = GPS_OK;
                PrevFix = eloc;
                PrevRecordedFix = eloc;
                isPrevFixRecorded = true;
            }

            // Save fix in case this is a STOP or a START (the speed is "old>0 and new=0" or "old=0 and new>0")
            if ((PrevFix != null) && (PrevFix.getLocation().hasSpeed()) && (eloc.getLocation().hasSpeed()) && (GPSStatus == GPS_OK) && (Recording)
                    && (((eloc.getLocation().getSpeed() == 0) && (PrevFix.getLocation().getSpeed() != 0)) || ((eloc.getLocation().getSpeed() != 0) && (PrevFix.getLocation().getSpeed() == 0)))) {
                if (!isPrevFixRecorded) {                   // Record the old sample if not already recorded
                    AsyncTODO ast = new AsyncTODO();
                    ast.TaskType = "TASK_ADDLOCATION";
                    ast.location = PrevFix;
                    AsyncTODOQueue.add(ast);
                    PrevRecordedFix = PrevFix;
                    isPrevFixRecorded = true;
                }

                ForceRecord = true;                         // + Force to record the new
            }

            if (GPSStatus == GPS_OK) {
                AsyncTODO ast = new AsyncTODO();
                if ((Recording) && ((prefGPSdistance == 0) || (PrevRecordedFix == null) || (ForceRecord) || (loc.distanceTo(PrevRecordedFix.getLocation()) >= prefGPSdistance))) {
                    PrevRecordedFix = eloc;
                    ast.TaskType = "TASK_ADDLOCATION";
                    ast.location = eloc;
                    AsyncTODOQueue.add(ast);
                    isPrevFixRecorded = true;
                } else {
                    ast.TaskType = "TASK_UPDATEFIX";
                    ast.location = eloc;
                    AsyncTODOQueue.add(ast);
                    isPrevFixRecorded = false;
                }

                if (PlacemarkRequest) {
                    _currentPlacemark = new LocationExtended(loc);
                    _currentPlacemark.setNumberOfSatellites(getNumberOfSatellites());
                    _currentPlacemark.setNumberOfSatellitesUsedInFix(getNumberOfSatellitesUsedInFix());
                    PlacemarkRequest = false;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
                    EventBus.getDefault().post(EventBusMSG.REQUEST_ADD_PLACEMARK);
                }
                PrevFix = eloc;
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        GPSStatus = GPS_DISABLED;
        EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
    }

    @Override
    public void onProviderEnabled(String provider) {
        GPSStatus = GPS_SEARCHING;
        EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // This is called when the GPS status changes
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                //Log.w("myApp", "[#] GPSApplication.java - GPS Out of Service");
                gpsunavailablehandler.removeCallbacks(unavailr);            // Cancel the previous unavail countdown handler
                GPSStatus = GPS_OUTOFSERVICE;
                EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
                //Toast.makeText( getApplicationContext(), "GPS Out of Service", Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                //Log.w("myApp", "[#] GPSApplication.java - GPS Temporarily Unavailable");
                gpsunavailablehandler.removeCallbacks(unavailr);            // Cancel the previous unavail countdown handler
                GPSStatus = GPS_TEMPORARYUNAVAILABLE;
                EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
                //Toast.makeText( getApplicationContext(), "GPS Temporarily Unavailable", Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.AVAILABLE:
                gpsunavailablehandler.removeCallbacks(unavailr);            // Cancel the previous unavail countdown handler
                //Log.w("myApp", "[#] GPSApplication.java - GPS Available: " + _NumberOfSatellites + " satellites");
                break;
        }
    }


    public void UpdateTrackList() {
//        long ID = GPSDataBase.getLastTrackID();
//        if (ID > 0) {
//            synchronized(_ArrayListTracks) {
//                _ArrayListTracks.clear();
//                _ArrayListTracks.addAll(GPSDataBase.getTracksList(0, ID - 1));
//                if ((ID > 1) && (GPSDataBase.getTrack(ID - 1) != null)) {
//                    String fname = (ID - 1) + ".png";
//                    File file = new File(getApplicationContext().getFilesDir() + "/Thumbnails/", fname);
//                    if (!file.exists()) Th = new Thumbnailer(ID - 1);
//                }
//                if (_currentTrack.getNumberOfLocations() + _currentTrack.getNumberOfPlacemarks() > 0) {
//                    Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: current track (" + _currentTrack.getId() + ") visible into the tracklist");
//                    _ArrayListTracks.add(0, _currentTrack);
//                } else
//                    Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: current track not visible into the tracklist");
//            }
//            EventBus.getDefault().post(EventBusMSG.UPDATE_TRACKLIST);
//            //Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: Added " + _ArrayListTracks.size() + " tracks");
//        }
    }


    // GPSDatabase ----------------------
    //init point type data for new login user
    public void initPointType(){
        String username= sf_login.getString(ConstantValue.current_user,"");
        GPSDataBase.initPointType(username);
    }
    //return all point type info
    public List<PointType> getUsualPoints(){
        String username= sf_login.getString(ConstantValue.current_user,"");
        pointTypes = GPSDataBase.getUsualPoints(username);
        return pointTypes;
    }
    //update all point type info
    public void setUsualPoints(int[] typelists){
        String username= sf_login.getString(ConstantValue.current_user,"");
        GPSDataBase.updateUsualPoints(username,typelists);
    }

    //get all user login info locally
    public List<Map<String,Object>> getUserLoginInfo(){
        userLoginInfo=GPSDataBase.getUserLoginInfo();
        return userLoginInfo;
    }

    //update user login info to database
    public void updateUserLoginInfo(String[] userinfo){
        GPSDataBase.updateUserLoginInfo(userinfo);
    }


    // PREFERENCES LOADER ------------------------------------------------------------------------------

    private void LoadPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // ---------Conversion from the previous versions of GPS Logger preferences
        if (preferences.contains("prefShowImperialUnits")) {       // The old boolean setting for imperial units in v.1.1.5
            Log.w("myApp", "[#] GPSApplication.java - Old setting prefShowImperialUnits present. Converting to new preference PrefUM.");
            boolean imperialUM = preferences.getBoolean("prefShowImperialUnits", false);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("prefUM", (imperialUM ? "8" : "0"));
            editor.remove("prefShowImperialUnits");
            editor.commit();
        }
        // -----------------------------------------------------------------------

        //prefKeepScreenOn = preferences.getBoolean("prefKeepScreenOn", true);
        prefShowDecimalCoordinates = preferences.getBoolean("prefShowDecimalCoordinates", false);
        prefUM = Integer.valueOf(preferences.getString("prefUM", "0")) + Integer.valueOf(preferences.getString("prefUMSpeed", "1"));
        prefGPSdistance = Float.valueOf(preferences.getString("prefGPSdistance", "0"));
        prefEGM96AltitudeCorrection = preferences.getBoolean("prefEGM96AltitudeCorrection", false);
        prefAltitudeCorrection = Double.valueOf(preferences.getString("prefAltitudeCorrection", "0"));
        Log.w("myApp", "[#] GPSApplication.java - Manual Correction set to " + prefAltitudeCorrection + " m");
        prefExportKML = preferences.getBoolean("prefExportKML", true);
        prefExportGPX = preferences.getBoolean("prefExportGPX", true);
        prefExportTXT = preferences.getBoolean("prefExportTXT", false);
        prefKMLAltitudeMode = Integer.valueOf(preferences.getString("prefKMLAltitudeMode", "1"));
        prefShowTrackStatsType = Integer.valueOf(preferences.getString("prefShowTrackStatsType", "0"));
        prefShowDirections = Integer.valueOf(preferences.getString("prefShowDirections", "0"));

        long oldGPSupdatefrequency = prefGPSupdatefrequency;
        prefGPSupdatefrequency = Long.valueOf(preferences.getString("prefGPSupdatefrequency", "1000"));

        // ---------------------------------------------- Update the GPS Update Frequency if needed
        if (oldGPSupdatefrequency != prefGPSupdatefrequency) updateGPSLocationFrequency();

        // ---------------------------------------------------------------- Load EGM Grid if needed
//        EGM96 egm96 = EGM96.getInstance();
//        if (egm96 != null) {
//            if (!egm96.isEGMGridLoaded()) {
//                egm96.LoadGridFromFile(Environment.getExternalStorageDirectory() + "/GPSLogger/AppData/WW15MGH.DAC", getApplicationContext().getFilesDir() + "/WW15MGH.DAC");
//            }
//        }

        // ------------------------------------------------------------------- Request of UI Update
        EventBus.getDefault().post(EventBusMSG.APPLY_SETTINGS);
        EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
        EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
        EventBus.getDefault().post(EventBusMSG.UPDATE_TRACKLIST);
    }



    // THE THREAD THAT DOES ASYNCHRONOUS OPERATIONS ---------------------------------------------------


    class AsyncTODO {
        String TaskType;
        LocationExtended location;
    }

    private BlockingQueue<AsyncTODO> AsyncTODOQueue = new LinkedBlockingQueue<>();

    private class AsyncUpdateThreadClass extends Thread {

        Track track;
        LocationExtended locationExtended;

        public AsyncUpdateThreadClass() {
        }

        public void run() {

            track = _currentTrack;
            EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
            UpdateTrackList();

            while (true) {
                AsyncTODO asyncTODO;
                try {
                    asyncTODO = AsyncTODOQueue.take();
                } catch (InterruptedException e) {
                    Log.w("myApp", "[!] Buffer not available: " + e.getMessage());
                    break;
                }

                // Task: Create new track (if needed)
                if (asyncTODO.TaskType.equals("TASK_NEWTRACK")) {
                    if ((track.getNumberOfLocations() != 0) || (track.getNumberOfPlacemarks() != 0)) {
                        // ---- Delete 2 thumbs files forward - in case of user deleted DB in App manager (pngs could be already presents for the new IDS)
                        String fname = (track.getId() + 1) + ".png";
                        File file = new File(getApplicationContext().getFilesDir() + "/Thumbnails/", fname);
                        if (file.exists()) file.delete();
                        fname = (track.getId() + 2) + ".png";
                        file = new File(getApplicationContext().getFilesDir() + "/Thumbnails/", fname);
                        if (file.exists()) file.delete();
                        track = new Track();
                        // ----
                        track.setId(GPSDataBase.addTrack(track));
                        Log.w("myApp", "[#] GPSApplication.java - TASK_NEWTRACK: " + track.getId());
                        _currentTrack = track;
                        UpdateTrackList();
                    } else
                        Log.w("myApp", "[#] GPSApplication.java - TASK_NEWTRACK: Track " + track.getId() + " already empty (New track not created)");
                    _currentTrack = track;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
                }

                // Task: Add location to current track
                if (asyncTODO.TaskType.equals("TASK_ADDLOCATION")) {
                    locationExtended = new LocationExtended(asyncTODO.location.getLocation());
                    locationExtended.setNumberOfSatellites(asyncTODO.location.getNumberOfSatellites());
                    locationExtended.setNumberOfSatellitesUsedInFix(asyncTODO.location.getNumberOfSatellitesUsedInFix());
                    _currentLocationExtended = locationExtended;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
                    track.add(locationExtended);
                    GPSDataBase.addLocationToTrack(locationExtended, track);
                    _currentTrack = track;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
                    if (_currentTrack.getNumberOfLocations() + _currentTrack.getNumberOfPlacemarks() == 1)
                        UpdateTrackList();
                }

                // Task: Add a placemark to current track
                if (asyncTODO.TaskType.equals("TASK_ADDPLACEMARK")) {
                    locationExtended = new LocationExtended(asyncTODO.location.getLocation());
                    locationExtended.setDescription(asyncTODO.location.getDescription());
                    locationExtended.setNumberOfSatellites(asyncTODO.location.getNumberOfSatellites());
                    locationExtended.setNumberOfSatellitesUsedInFix(asyncTODO.location.getNumberOfSatellitesUsedInFix());
                    track.addPlacemark(locationExtended);
                    GPSDataBase.addPlacemarkToTrack(locationExtended, track);
                    _currentTrack = track;
                    EventBus.getDefault().post(EventBusMSG.UPDATE_TRACK);
                    if (_currentTrack.getNumberOfLocations() + _currentTrack.getNumberOfPlacemarks() == 1)
                        UpdateTrackList();
                }

                // Task: Update current Fix
                if (asyncTODO.TaskType.equals("TASK_UPDATEFIX")) {
                    _currentLocationExtended = new LocationExtended(asyncTODO.location.getLocation());
                    _currentLocationExtended.setNumberOfSatellites(asyncTODO.location.getNumberOfSatellites());
                    _currentLocationExtended.setNumberOfSatellitesUsedInFix(asyncTODO.location.getNumberOfSatellitesUsedInFix());
                    EventBus.getDefault().post(EventBusMSG.UPDATE_FIX);
                }

                if (asyncTODO.TaskType.contains("TASK_DELETE_TRACK")) {
                    Log.w("myApp", "[#] GPSApplication.java - Deleting Track ID = " + asyncTODO.TaskType.split(" ")[1]);
                    if (Integer.valueOf(asyncTODO.TaskType.split(" ")[1]) >= 0) {
                        long selectedtrackID = Integer.valueOf(asyncTODO.TaskType.split(" ")[1]);
                        synchronized (_ArrayListTracks) {
                            if (!_ArrayListTracks.isEmpty() && (selectedtrackID >= 0)) {
                                int i = 0;
                                boolean found = false;
                                do {
                                    if (_ArrayListTracks.get(i).getId() == selectedtrackID) {
                                        found = true;
                                        GPSDataBase.DeleteTrack(_ArrayListTracks.get(i).getId());
                                        Log.w("myApp", "[#] GPSApplication.java - Track " + _ArrayListTracks.get(i).getId() + " deleted.");
                                        _ArrayListTracks.remove(i);
                                    }
                                    i++;
                                } while ((i < _ArrayListTracks.size()) && !found);
                                //Log.w("myApp", "[#] GPSApplication.java - now DB Contains " + GPSDataBase.getLocationsTotalCount() + " locations");
                                //if (found) UpdateTrackList();
                            }
                        }
                    }
                }
            }
        }
    }
    //----------------CP--------------------------------

    // ------------------------------------------------------------------------ Getters and Setters
    public boolean getNewTrackFlag() {
        return NewTrackFlag;
    }

    public void setNewTrackFlag(boolean newTrackFlag) {
        if (newTrackFlag) {
            NewTrackFlag = true;
            newtrackhandler.removeCallbacks(newtrackr);         // Cancel the previous newtrackr handler
            newtrackhandler.postDelayed(newtrackr, 1500);       // starts the new handler
        } else {
            NewTrackFlag = false;
            newtrackhandler.removeCallbacks(newtrackr);         // Cancel the previous newtrackr handler
        }
    }

    public boolean isContextMenuShareVisible() {
        return isContextMenuShareVisible;
    }

    public boolean isContextMenuViewVisible() {
        return isContextMenuViewVisible;
    }

    public String getViewInApp() {
        return ViewInApp;
    }

    public boolean isPermissionsChecked() {
        return PermissionsChecked;
    }

    public void setPermissionsChecked(boolean permissionsChecked) {
        PermissionsChecked = permissionsChecked;
    }

    public void setHandlerTimer(int handlerTimer) {
        HandlerTimer = handlerTimer;
    }

    public int getHandlerTimer() {
        return HandlerTimer;
    }

    public int getGPSStatus() {
        return GPSStatus;
    }

    public int getPrefKMLAltitudeMode() {
        return prefKMLAltitudeMode;
    }

    public void setOpenInViewer(long openInViewer) {
        OpenInViewer = openInViewer;
    }

    public long getOpenInViewer() {
        return OpenInViewer;
    }

    public long getShare() {
        return Share;
    }

    public void setShare(long share) {
        Share = share;
    }

    public double getPrefAltitudeCorrection() {
        return prefAltitudeCorrection;
    }

    public boolean getPrefEGM96AltitudeCorrection() {
        return prefEGM96AltitudeCorrection;
    }

    public boolean getPrefShowDecimalCoordinates() {
        return prefShowDecimalCoordinates;
    }

    public boolean getPrefExportKML() {
        return prefExportKML;
    }

    public boolean getPrefExportGPX() {
        return prefExportGPX;
    }

    public boolean getPrefExportTXT() {
        return prefExportTXT;
    }

    public int getPrefUM() {
        return prefUM;
    }

    public int getPrefShowTrackStatsType() {
        return prefShowTrackStatsType;
    }

    public int getPrefShowDirections() {
        return prefShowDirections;
    }

    public LocationExtended getCurrentLocationExtended() {
        return _currentLocationExtended == null ? null : _currentLocationExtended;
    }

    public void setPlacemarkDescription(String Description) {
        this.PlacemarkDescription = Description;
    }

    public Track getCurrentTrack() {
        return _currentTrack == null ? null : _currentTrack;
    }

    public int getNumberOfSatellites() {
        return _NumberOfSatellites;
    }

    public int getNumberOfSatellitesUsedInFix() {
        return _NumberOfSatellitesUsedInFix;
    }

    public boolean getRecording() {
        return Recording;
    }

    public void setRecording(boolean recordingState) {
        PrevRecordedFix = null;
        Recording = recordingState;
    }

    public boolean getPlacemarkRequest() { return PlacemarkRequest; }

    public void setPlacemarkRequest(boolean placemarkRequest) { PlacemarkRequest = placemarkRequest; }

    public List<Track> getTrackList() {
        return _ArrayListTracks;
    }

    public boolean isCurrentTrackVisible() {
        return isCurrentTrackVisible;
    }

    public void setisCurrentTrackVisible(boolean currentTrackVisible) {
        isCurrentTrackVisible = currentTrackVisible;
    }

    public boolean isContextMenuEnabled() {
        return isContextMenuEnabled;
    }

    // --------------------------------------------------------------------------------------------

    //------------------------------------------------history------------------------------------------------


    //-------history1-------


    public HttpMethods getHttpMethods() {
        return this.httpMethods;
    }

    public User getUser() {
        return this.user;
    }

    public boolean setUser(User user) {
        if (user == null) {
            return false;
        }
        this.user = user;
        return true;
    }

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }
    //------------------------------------------------history------------------------------------------------


}
