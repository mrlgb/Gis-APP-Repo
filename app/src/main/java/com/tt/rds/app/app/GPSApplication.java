package com.tt.rds.app.app;

import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.facebook.stetho.Stetho;
import com.tt.rds.app.R;
import com.tt.rds.app.common.EventBusMSG;
import com.tt.rds.app.common.EventBusMSGLong;
import com.tt.rds.app.common.EventBusMSGNormal;
import com.tt.rds.app.common.GPSService;
import com.tt.rds.app.common.LocationExtended;
import com.tt.rds.app.common.Track;
import com.tt.rds.app.db.DBService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GPSApplication extends Application implements GpsStatus.Listener, LocationListener {

    private static final String TAG = GPSApplication.class.getSimpleName();

    private DBService dbService;

    public DBService getDbService() {
        return dbService;
    }


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
    //.......................................
    private boolean prefShowDecimalCoordinates = false;
    private int prefUM = UM_METRIC_KMH;
    private float prefGPSdistance = 0f;
    private long prefGPSupdatefrequency = 1000L;
    //....................................
    private int prefShowTrackStatsType = 0;
    private int prefShowDirections = 0;

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
    private static GPSApplication singleton;

    public static GPSApplication getInstance() {
        return singleton;
    }


    //    DatabaseHandler GPSDataBase;------------------
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
    private List<Track> _ArrayListTracks = Collections.synchronizedList(new ArrayList<Track>());

    static SparseArray<Bitmap> thumbsArray = new SparseArray<>();       // The Array containing the Tracks Thumbnail

    //   ...............................................
    //    ...............................................
    private AsyncUpdateThreadClass asyncUpdateThread = new AsyncUpdateThreadClass();
    //    ................................................

    // The handler that switches off the location updates after a time delay:
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

    //------------------------history------------------------
    private String[] normal;
    private String[] all;
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
    //------------------------history------------------------


    // ------------------------------------------------------------------------------------ Service
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
        GPSServiceIntent = new Intent(GPSApplication.this, GPSService.class);
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


    // ------------------------------------------------------------------------ Getters and Setters

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


    public boolean getPrefShowDecimalCoordinates() {
        return prefShowDecimalCoordinates;
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

    public boolean getPlacemarkRequest() {
        return PlacemarkRequest;
    }

    public void setPlacemarkRequest(boolean placemarkRequest) {
        PlacemarkRequest = placemarkRequest;
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();
        //--------------his--------------------
        Resources res = getResources();
        normal = res.getStringArray(R.array.points_normal);
        all = res.getStringArray(R.array.points_all);
        collectPointList = new ArrayList<String>(Arrays.asList(normal));
        //--------------his--------------------

        singleton = this;

        Stetho.initializeWithDefaults(this);

        StartAndBindGPSService();

        EventBus.getDefault().register(this);

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);     // Location Manager

        //............
        // Initialize the Database
        InitGreenDAO();

        Log.d(TAG, "----------------------------------------------------------------------------------------\n");
        // Prepare the current track
        if (dbService.getLastTrackID() == 0)
            dbService.addTrack(new Track());       // Creation of the first track if the DB is empty

        _currentTrack = dbService.getLastTrack();                                     // Get the last track
        Log.d(TAG,_currentTrack.getName()+"/"+_currentTrack.getId()+"------");
        Log.d(TAG, "----------------------------------------------------------------------------------------\n");
        // Load Settings
        //................
        // ----------------------------------------------------------------------------------------

        asyncUpdateThread.start();
        AsyncTODO ast = new AsyncTODO();
        ast.TaskType = "TASK_NEWTRACK";
        ast.location = null;
        AsyncTODOQueue.add(ast);
    }


    private void InitGreenDAO() {
        dbService = new DBService();
        dbService.init(this);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onTerminate() {
        Log.w("myApp", "[#] GPSApplication.java - onTerminate");
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
                synchronized (_ArrayListTracks) {
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
                synchronized (_ArrayListTracks) {
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
                synchronized (_ArrayListTracks) {
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
            AsyncPrepareTracklistContextMenu asyncPrepareTracklistContextMenu = new AsyncPrepareTracklistContextMenu();
            asyncPrepareTracklistContextMenu.start();
            handler.removeCallbacks(r);                 // Cancel the switch-off handler
            setHandlerTimer(DEFAULTHANDLERTIMER);
            setGPSLocationUpdates(true);
            if (MustUpdatePrefs) {
                MustUpdatePrefs = false;
            }
            StartAndBindGPSService();
            return;
        }
        if (msg == EventBusMSG.UPDATE_SETTINGS) {
            MustUpdatePrefs = true;
            return;
        }
    }

    public void setGPSLocationUpdates(boolean state) {
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

    public void updateGPSLocationFrequency() {

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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


    private class AsyncPrepareTracklistContextMenu extends Thread {

        public AsyncPrepareTracklistContextMenu() {
        }

        public void run() {
            isContextMenuShareVisible = false;
            isContextMenuViewVisible = false;
            isContextMenuEnabled = false;
            ViewInApp = "";

            final PackageManager pm = getPackageManager();

            // Check permissions
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                isContextMenuEnabled = true;


            // ----- menu share
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("text/xml");
            // Verify the intent will resolve to at least one activity
            if ((intent.resolveActivity(pm) != null)) isContextMenuShareVisible = true;

            // ----- menu view
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("application/vnd.google-earth.kml+xml");
            ResolveInfo ri = pm.resolveActivity(intent, 0); // Find default app
            if (ri != null) {
                //Log.w("myApp", "[#] FragmentTracklist.java - Open with: " + ri.activityInfo.applicationInfo.loadLabel(getContext().getPackageManager()));
                List<ResolveInfo> lri = pm.queryIntentActivities(intent, 0);
                //Log.w("myApp", "[#] FragmentTracklist.java - Found " + lri.size() + " viewers:");
                for (ResolveInfo tmpri : lri) {
                    //Log.w("myApp", "[#] " + ri.activityInfo.applicationInfo.packageName + " - " + tmpri.activityInfo.applicationInfo.packageName);
                    if (ri.activityInfo.applicationInfo.packageName.equals(tmpri.activityInfo.applicationInfo.packageName)) {
                        ViewInApp = ri.activityInfo.applicationInfo.loadLabel(pm).toString();
                        //Log.w("myApp", "[#]                              DEFAULT --> " + tmpri.activityInfo.applicationInfo.loadLabel(getPackageManager()));
                    }   //else Log.w("myApp", "[#]                                          " + tmpri.activityInfo.applicationInfo.loadLabel(getContext().getPackageManager()));
                }
                isContextMenuViewVisible = true;
            }
            Log.w("myApp", "[#] GPSApplication.java - Tracklist ContextMenu prepared");
        }
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
                } else _Stabilizer--;
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
        long ID = dbService.getLastTrackID();
        if (ID > 0) {
            synchronized (_ArrayListTracks) {
                _ArrayListTracks.clear();
                _ArrayListTracks.addAll(dbService.getTracksList(0, ID - 1));
//                if ((ID > 1) && (GPSDataBase.getTrack(ID - 1) != null)) {
//                    String fname = (ID - 1) + ".png";
//                    File file = new File(getApplicationContext().getFilesDir() + "/Thumbnails/", fname);
//                    if (!file.exists()) Th = new Thumbnailer(ID - 1);
//                }
                if (_currentTrack.getNumberOfLocations() + _currentTrack.getNumberOfPlacemarks() > 0) {
                    Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: current track (" + _currentTrack.getId() + ") visible into the tracklist");
                    _ArrayListTracks.add(0, _currentTrack);
                } else
                    Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: current track not visible into the tracklist");
            }
            EventBus.getDefault().post(EventBusMSG.UPDATE_TRACKLIST);
            //Log.w("myApp", "[#] GPSApplication.java - Update Tracklist: Added " + _ArrayListTracks.size() + " tracks");
        }
    }


// PREFERENCES LOADER ------------------------------------------------------------------------------


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
                        track.setId(dbService.addTrack(track));
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
                    dbService.addLocationToTrack(locationExtended, track);
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
                    dbService.addPlacemarkToTrack(locationExtended, track);
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

//                if (asyncTODO.TaskType.contains("TASK_DELETE_TRACK")) {
//                    Log.w("myApp", "[#] GPSApplication.java - Deleting Track ID = " + asyncTODO.TaskType.split(" ")[1]);
//                    if (Integer.valueOf(asyncTODO.TaskType.split(" ")[1]) >= 0) {
//                        long selectedtrackID = Integer.valueOf(asyncTODO.TaskType.split(" ")[1]);
//                        synchronized(_ArrayListTracks) {
//                            if (!_ArrayListTracks.isEmpty() && (selectedtrackID >= 0)) {
//                                int i = 0;
//                                boolean found = false;
//                                do {
//                                    if (_ArrayListTracks.get(i).getId() == selectedtrackID) {
//                                        found = true;
//                                        GPSDataBase.DeleteTrack(_ArrayListTracks.get(i).getId());
//                                        Log.w("myApp", "[#] GPSApplication.java - Track " + _ArrayListTracks.get(i).getId() + " deleted.");
//                                        _ArrayListTracks.remove(i);
//                                    }
//                                    i++;
//                                } while ((i < _ArrayListTracks.size()) && !found);
//                                //Log.w("myApp", "[#] GPSApplication.java - now DB Contains " + GPSDataBase.getLocationsTotalCount() + " locations");
//                                //if (found) UpdateTrackList();
//                            }
//                        }
//                    }
//                }
            }
        }
    }


}
