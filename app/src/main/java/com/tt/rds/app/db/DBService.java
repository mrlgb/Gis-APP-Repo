package com.tt.rds.app.db;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.tt.rds.app.app.Common;
import com.tt.rds.app.bean.DaoMaster;
import com.tt.rds.app.bean.DaoSession;
import com.tt.rds.app.bean.PictureDao;
import com.tt.rds.app.bean.PointBridgeDao;
import com.tt.rds.app.bean.PointCulvertDao;
import com.tt.rds.app.bean.PointFerryDao;
import com.tt.rds.app.bean.PointMarkerDao;
import com.tt.rds.app.bean.PointSchoolDao;
import com.tt.rds.app.bean.PointSignDao;
import com.tt.rds.app.bean.PointStandardVillageDao;
import com.tt.rds.app.bean.PointTownDao;
import com.tt.rds.app.bean.PointTunnelDao;
import com.tt.rds.app.bean.PointTypeDao;
import com.tt.rds.app.bean.PointVillageDao;
import com.tt.rds.app.bean.TtLocate;
import com.tt.rds.app.bean.TtLocateDao;
import com.tt.rds.app.bean.TtPathEndDao;
import com.tt.rds.app.bean.TtPathStartDao;
import com.tt.rds.app.bean.TtPlaceMark;
import com.tt.rds.app.bean.TtPlaceMarkDao;
import com.tt.rds.app.bean.TtPointDao;
import com.tt.rds.app.bean.TtTrack;
import com.tt.rds.app.bean.TtTrackDao;
import com.tt.rds.app.bean.UserDao;
import com.tt.rds.app.bean.UserPointTypeDao;
import com.tt.rds.app.common.LatLng;
import com.tt.rds.app.common.LocationExtended;
import com.tt.rds.app.common.Track;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guobinli on 2017/10/5.
 */

public class DBService {
    private static final String TAG = DBService.class.getSimpleName();
    private static final String DB_NAME = Common.DATABASE_NAME;
    private DaoSession daoSession;
    private static DBService mInstance = null;
    private static final int NOT_AVAILABLE = -100000;

    private static final int LOCATION_TYPE_LOCATION = 1;
    private static final int LOCATION_TYPE_PLACEMARK = 2;

    /**
     * 获取DaoFactory的实例
     *
     * @return
     */
    public static DBService getInstance() {
        if (mInstance == null) {
            synchronized (DBService.class) {
                if (mInstance == null) {
                    mInstance = new DBService();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
    }

    public UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    public UserPointTypeDao getUserPointTypeDao() {
        return daoSession.getUserPointTypeDao();
    }

    public PointTypeDao getPointTypeDao() {
        return daoSession.getPointTypeDao();
    }

    public TtPointDao getTtPointDao() {
        return daoSession.getTtPointDao();
    }

    public PictureDao getPictureDao() {
        return daoSession.getPictureDao();
    }

    public PointMarkerDao getPointMarkerDao() {
        return daoSession.getPointMarkerDao();
    }

    public PointBridgeDao getPointBridgeDao() {
        return daoSession.getPointBridgeDao();
    }

    public PointCulvertDao getPointCulvertDao() {
        return daoSession.getPointCulvertDao();
    }

    public PointFerryDao getPointFerryDao() {
        return daoSession.getPointFerryDao();
    }

    public PointSchoolDao getPointSchoolDao() {
        return daoSession.getPointSchoolDao();
    }

    public PointSignDao getPointSignDao() {
        return daoSession.getPointSignDao();
    }

    public PointStandardVillageDao getPointStandardVillageDao() {
        return daoSession.getPointStandardVillageDao();
    }

    public PointTownDao getPointTownDao() {
        return daoSession.getPointTownDao();
    }

    public PointTunnelDao getPointTunnelDao() {
        return daoSession.getPointTunnelDao();
    }

    public PointVillageDao getPointVillageDao() {
        return daoSession.getPointVillageDao();
    }


    //locations
    public TtLocateDao getTtLocateDao() {
        return daoSession.getTtLocateDao();
    }

    //placemarks
    public TtPlaceMarkDao getTtPlaceMarkDao() {
        return daoSession.getTtPlaceMarkDao();
    }

    //tracks
    public TtTrackDao getTtTrackDao() {
        return daoSession.getTtTrackDao();
    }

    //
    public TtPathStartDao getTtPathStartDao() {
        return daoSession.getTtPathStartDao();
    }

    //
    public TtPathEndDao getTtPathEndDao() {
        return daoSession.getTtPathEndDao();
    }

    // ----------------------------------------------------------------------- LOCATIONS AND PLACEMARKS
    public TtTrack TtTrack4Track(Track track) {
        TtTrack tt = new TtTrack();
        tt.setTrackId(track.getId());
        tt.setTrack_name(track.getName());

        tt.setStart_latitude(track.getStart_Latitude());
        tt.setStart_longitude(track.getStart_Longitude());
        tt.setStart_altitude(track.getStart_Altitude());
        tt.setStart_accuracy(track.getStart_Accuracy());
        tt.setStart_speed(track.getStart_Speed());
        tt.setStart_time(track.getStart_Time());

        tt.setEnd_latitude(track.getEnd_Latitude());
        tt.setEnd_longitude(track.getEnd_Longitude());
        tt.setEnd_altitude(track.getEnd_Altitude());
        tt.setEnd_accuracy(track.getEnd_Accuracy());
        tt.setEnd_speed(track.getEnd_Speed());
        tt.setEnd_time(track.getEnd_Time());

        tt.setLast_fix_time(track.getLastFix_Time());
        tt.setLast_step_dst_latitude(track.getLastStepDistance_Latitude());
        tt.setLast_step_dst_longitude(track.getLastStepDistance_Longitude());
        tt.setDistance_last_altitude(track.getDistanceLastAltitude());
        tt.setLast_step_alittude_altitude(track.getLastStepAltitude_Altitude());
        tt.setLast_step_alittude_accuracy(track.getLastStepAltitude_Accuracy());


        tt.setMin_latitude(track.getMin_Latitude());
        tt.setMin_longitude(track.getMin_Longitude());
        tt.setMax_latitude(track.getMax_Latitude());
        tt.setMax_longitude(track.getMax_Longitude());

        tt.setDuration(track.getDuration());
        tt.setDuration_moving(track.getDuration_Moving());
        tt.setDistance(track.getDistance());
        tt.setDistance_in_progress(track.getDistanceInProgress());
        tt.setDistance_last_altitude(track.getDistanceLastAltitude());

        tt.setAltitude_up(track.getAltitude_Up());
        tt.setAltitude_down(track.getAltitude_Down());
        tt.setAltitude_in_progress(track.getAltitude_InProgress());

        tt.setSpeed_average(track.getSpeedAverage());
        tt.setSpeed_average_moving(track.getSpeedAverageMoving());
        tt.setSpeed_max(track.getSpeedMax());

        tt.setNumber_of_locations(track.getNumberOfLocations());
        tt.setNumber_of_placemarks(track.getNumberOfPlacemarks());

        tt.setType(track.getType());
        tt.setValidmap(track.getValidMap());

        return tt;


    }

    public Track Track4TtTrack(TtTrack ttTrack) {
        Track t = new Track();
        t.setId(ttTrack.getTrackId());
        t.setName(ttTrack.getTrack_name());

        t.setStart_Latitude(ttTrack.getStart_latitude());
        t.setStart_Longitude(ttTrack.getStart_longitude());
        t.setStart_Altitude(ttTrack.getStart_altitude());
        t.setStart_Accuracy(ttTrack.getStart_accuracy());
        t.setStart_Speed(ttTrack.getStart_speed());
        t.setStart_Time(ttTrack.getStart_time());

        t.setEnd_Latitude(ttTrack.getEnd_latitude());
        t.setEnd_Longitude(ttTrack.getEnd_longitude());
        t.setEnd_Altitude(ttTrack.getEnd_altitude());
        t.setEnd_Accuracy(ttTrack.getEnd_accuracy());
        t.setEnd_Speed(ttTrack.getEnd_speed());
        t.setEnd_Time(ttTrack.getEnd_time());

        t.setLastFix_Time(ttTrack.getLast_fix_time());
        t.setLastStepDistance_Latitude(ttTrack.getLast_step_dst_latitude());
        t.setLastStepDistance_Longitude(ttTrack.getLast_step_dst_longitude());
        t.setLastStepDistance_Accuracy(ttTrack.getLast_step_alittude_accuracy());
        t.setLastStepAltitude_Altitude(ttTrack.getLast_step_alittude_altitude());
        t.setLastStepAltitude_Accuracy(ttTrack.getLast_step_alittude_accuracy());

        t.setMin_Latitude(ttTrack.getMin_latitude());
        t.setMin_Longitude(ttTrack.getMin_longitude());

        t.setMax_Latitude(ttTrack.getMax_latitude());
        t.setMax_Longitude(ttTrack.getMax_longitude());

        t.setDuration(ttTrack.getDuration());
        t.setDuration_Moving(ttTrack.getDuration_moving());

        t.setDistance(ttTrack.getDistance());
        t.setDistanceInProgress(ttTrack.getDistance_in_progress());
        t.setDistanceLastAltitude(ttTrack.getDistance_last_altitude());

        t.setAltitude_Up(ttTrack.getAltitude_up());
        t.setAltitude_Down(ttTrack.getAltitude_down());
        t.setAltitude_InProgress(ttTrack.getAltitude_in_progress());

        t.setSpeedMax(ttTrack.getSpeed_max());
        t.setSpeedAverage(ttTrack.getSpeed_average());
        t.setSpeedAverageMoving(ttTrack.getSpeed_average_moving());

        t.setNumberOfLocations(ttTrack.getNumber_of_locations());
        t.setNumberOfPlacemarks(ttTrack.getNumber_of_placemarks());

        t.setValidMap(ttTrack.getValidmap());
        t.setType(ttTrack.getType());
        return t;

    }


    //----------------------

    // Add new Location and update the corresponding track
    public void addLocationToTrack(LocationExtended location, Track track) {

        Location loc = location.getLocation();

        TtLocate ttLocate = new TtLocate();
        ttLocate.setTrackId(track.getId());
        ttLocate.setNr((int) track.getNumberOfLocations());
        ttLocate.setLatitude(loc.getLatitude());
        ttLocate.setLongitude(loc.getLongitude());
        ttLocate.setAltitude(loc.hasAltitude() ? loc.getAltitude() : NOT_AVAILABLE);
        ttLocate.setSpeed(loc.hasSpeed() ? loc.getSpeed() : NOT_AVAILABLE);
        ttLocate.setBearing(loc.hasAccuracy() ? loc.getAccuracy() : NOT_AVAILABLE);
        ttLocate.setTime(loc.getTime());
        ttLocate.setTime(location.getNumberOfSatellites());
        ttLocate.setType(LOCATION_TYPE_LOCATION);
        ttLocate.setNumber_of_satellites_used_in_fix(location.getNumberOfSatellitesUsedInFix());

        // insert locaions
        getTtLocateDao().insert(ttLocate);

        //Update the corresponding Track

        TtTrack ttTrack = TtTrack4Track(track);

        TtTrack ttTrack1 = getTtTrackDao().queryBuilder()
                .where(TtTrackDao.Properties.TrackId.eq(track.getId())).build().unique();
        if (ttTrack1 == null) {
            //            Toast.makeText(MainActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
        } else {

            getTtTrackDao().update(ttTrack);
        }

        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Add new Placemark and update the corresponding track
    public void addPlacemarkToTrack(LocationExtended placemark, Track track) {

        TtPlaceMark ttPlaceMark = new TtPlaceMark();
        ttPlaceMark.setTrackId(track.getId());
        ttPlaceMark.setNr((int) track.getNumberOfLocations());
        ttPlaceMark.setLatitude(placemark.getLatitude());
        ttPlaceMark.setLongitude(placemark.getLongitude());
        ttPlaceMark.setAltitude(placemark.getAltitude());
        ttPlaceMark.setSpeed(placemark.getSpeed());
        ttPlaceMark.setBearing(placemark.getAccuracy());
        ttPlaceMark.setTime(placemark.getTime());
        ttPlaceMark.setTime(placemark.getNumberOfSatellites());
        ttPlaceMark.setType(LOCATION_TYPE_LOCATION);
        ttPlaceMark.setNumber_of_satellites_used_in_fix(placemark.getNumberOfSatellitesUsedInFix());

        // insert placemark
        getTtPlaceMarkDao().insert(ttPlaceMark);

        //Update the corresponding Track

        TtTrack ttTrack = TtTrack4Track(track);

        TtTrack ttTrack1 = getTtTrackDao().queryBuilder()
                .where(TtTrackDao.Properties.TrackId.eq(track.getId())).build().unique();
        if (ttTrack1 == null) {
            //            Toast.makeText(MainActivity.this, "用户不存在!", Toast.LENGTH_SHORT).show();
        } else {

            getTtTrackDao().update(ttTrack);
        }
        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Get single Location
    public LocationExtended getLocation(long id) {

        LocationExtended extdloc = null;

        Query<TtLocate> query = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.eq(id)).build();

        for (TtLocate loc : query.list()) {
            Location lc = new Location("DB");
            lc.setLatitude(loc.getLatitude());
            lc.setLongitude(loc.getLongitude());
            lc.setAltitude(loc.getAltitude());
            lc.setSpeed(loc.getSpeed());
            lc.setBearing(loc.getBearing());

            extdloc = new LocationExtended(lc);
            extdloc.setNumberOfSatellites(loc.getNumber_of_satellites());
            extdloc.setNumberOfSatellitesUsedInFix(loc.getNumber_of_satellites_used_in_fix());
        }

        return extdloc != null ? extdloc : null;
    }


    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LocationExtended> getLocationsList(long TrackID, long startNumber, long endNumber) {

        List<LocationExtended> locationList = new ArrayList<>();
        LocationExtended extdloc = null;

        Query<TtLocate> query = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.between(startNumber, endNumber)).build();

        for (TtLocate loc : query.list()) {
            Location lc = new Location("DB");
            lc.setLatitude(loc.getLatitude());
            lc.setLongitude(loc.getLongitude());
            lc.setAltitude(loc.getAltitude());
            lc.setSpeed(loc.getSpeed());
            lc.setBearing(loc.getBearing());

            extdloc = new LocationExtended(lc);
            extdloc.setNumberOfSatellites(loc.getNumber_of_satellites());
            extdloc.setNumberOfSatellitesUsedInFix(loc.getNumber_of_satellites_used_in_fix());
            locationList.add(extdloc);
        }

        return locationList;
    }

    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LocationExtended> getPlacemarksList(long TrackID, long startNumber, long endNumber) {

        List<LocationExtended> placemarkList = new ArrayList<>();
        Query<TtPlaceMark> query = getTtPlaceMarkDao().queryBuilder().where(TtPlaceMarkDao.Properties.
                TrackId.between(startNumber, endNumber)).build();

        for (TtPlaceMark loc : query.list()) {
            Location lc = new Location("DB");
            lc.setLatitude(loc.getLatitude());
            lc.setLongitude(loc.getLongitude());
            lc.setAltitude(loc.getAltitude());
            lc.setSpeed(loc.getSpeed());
            lc.setBearing(loc.getBearing());

            LocationExtended extdloc = new LocationExtended(lc);
            extdloc = new LocationExtended(lc);
            extdloc.setNumberOfSatellites(loc.getNumber_of_satellites());
            extdloc.setNumberOfSatellitesUsedInFix(loc.getNumber_of_satellites_used_in_fix());
            placemarkList.add(extdloc);
        }

        return placemarkList;
    }


    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LatLng> getLatLngList(long TrackID, long startNumber, long endNumber) {

        List<LatLng> latlngList = new ArrayList<>();

        List<LocationExtended> locationList = new ArrayList<>();
        LocationExtended extdloc = null;

        Query<TtLocate> query = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.between(startNumber, endNumber)).build();

        for (TtLocate loc : query.list()) {

            LatLng latlng = new LatLng();
            latlng.setLatitude(loc.getLatitude());
            latlng.setLongitude(loc.getLongitude());

            latlngList.add(latlng);
        }

        return latlngList;
    }


    // Get the total amount of Locations stored in the DB
    public long getLocationsTotalCount() {

        Query<TtLocate> query = getTtLocateDao().queryBuilder().build();
        return query.list().size();
    }


    // Get the number of Locations of a Track
    public long getLocationsCount(long TrackID) {
        Query<TtLocate> query = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.eq(TrackID)).build();
        return query.list().size();
    }


    // Get last Location ID
    public long getLastLocationID(long TrackID) {

        long locId = 0;
        Query<TtLocate> query = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.eq(TrackID)).orderDesc(TtLocateDao.Properties.TrackId).limit(1).build();

        for (TtLocate loc : query.list()) {
            locId = loc.getTtLocateId();
        }
        return locId;
    }


// ----------------------------------------------------------------------------------------- TRACKS

    // Delete the track with the specified ID;
    // The method deletes also Placemarks and Locations associated to the specified track
    public void DeleteTrack(long TrackID) {

        //delete TtTrack
        Query<TtTrack> query1 = getTtTrackDao().queryBuilder().where(TtTrackDao.Properties.
                TrackId.eq(TrackID)).build();
        for (TtTrack track : query1.list()) {
            getTtTrackDao().delete(track);
        }

        //delete TtLocate
        Query<TtLocate> query2 = getTtLocateDao().queryBuilder().where(TtLocateDao.Properties.
                TrackId.eq(TrackID)).build();

        for (TtLocate loc : query2.list()) {
            getTtLocateDao().delete(loc);
        }

        //delete TtPlaceMark
        Query<TtPlaceMark> query3 = getTtPlaceMarkDao().queryBuilder().where(TtPlaceMarkDao.Properties.
                TrackId.eq(TrackID)).build();
        for (TtPlaceMark loc : query3.list()) {
            getTtPlaceMarkDao().delete(loc);
        }

        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Add a new Track, returns the TrackID
    public long addTrack(Track track) {
        TtTrack ttTrack = TtTrack4Track(track);
        //add new track (not modify!!!!)
        ttTrack.setTrackId(null);
        long trackid = getTtTrackDao().insert(ttTrack);
        Log.d(TAG, "--after add --t:[trackid]"+trackid);
        return trackid;

    }


    // Get Track
    public Track getTrack(long TrackID) {
        Track track = null;

        TtTrack ttTrack = getTtTrackDao().queryBuilder()
                .where(TtTrackDao.Properties.TrackId.eq(TrackID)).build().unique();
        track=Track4TtTrack(ttTrack);
        Log.d(TAG, track.getId()+"/"+track.getName());
        return track != null ? track : null;

    }


    // Get last TrackID
    public long getLastTrackID() {
        long trackId = 0;
        Query<TtTrack> query = getTtTrackDao().queryBuilder().orderDesc(TtTrackDao.Properties.TrackId).limit(1).build();

        for (TtTrack track : query.list()) {
            trackId = track.getTrackId();
        }
        Log.d(TAG, "getLastTrackID:[" + trackId + "]");
        return trackId;

    }


    // Get last TrackID
    public Track getLastTrack() {
        return getTrack(getLastTrackID());
    }


    // Getting a list of Tracks, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<Track> getTracksList(long startNumber, long endNumber) {

        List<Track> trackList = new ArrayList<>();

        Query<TtTrack> query = getTtTrackDao().queryBuilder().where(TtTrackDao.Properties.
                TrackId.between(startNumber, endNumber)).build();

        for (TtTrack track : query.list()) {

//            trackList.add((TtTrack)track);
        }
        return trackList;
    }

}