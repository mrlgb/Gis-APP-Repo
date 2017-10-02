/*
 * DatabaseHandler - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 1/5/2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.tt.rds.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.util.Xml;

import com.tt.rds.app.bean.Constant;
import com.tt.rds.app.bean.PointType;
import com.tt.rds.app.common.ConstantValue;
import com.tt.rds.app.common.LatLng;
import com.tt.rds.app.common.LocationExtended;
import com.tt.rds.app.common.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;          // Updated to 2 in v2.1.3 (code 14)
    private static final int LOCATION_TYPE_LOCATION = 1;
    private static final int LOCATION_TYPE_PLACEMARK = 2;

    // Database Name
    private static final String DATABASE_NAME = "GPSLogger";

    // -------------------------------------------------------------------------------- Table names
    private static final String TABLE_LOCATIONS = "locations";
    private static final String TABLE_TRACKS = "tracks";
    private static final String TABLE_PLACEMARKS = "placemarks";
    private static final String TABLE_POINTTYPE = "pointtypes";
    private static final String TABLE_USERLOGIN = "userlogin";


    // ----------------------------------------------------------------------- Common Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TRACK_ID = "track_id";

    // --------------------------------------------------------------- Location Table Columns names
    private static final String KEY_LOCATION_NUMBER = "nr";
    private static final String KEY_LOCATION_LATITUDE = "latitude";
    private static final String KEY_LOCATION_LONGITUDE = "longitude";
    private static final String KEY_LOCATION_ALTITUDE = "altitude";
    private static final String KEY_LOCATION_SPEED = "speed";
    private static final String KEY_LOCATION_ACCURACY = "accuracy";
    private static final String KEY_LOCATION_BEARING = "bearing";
    private static final String KEY_LOCATION_TIME = "time";
    private static final String KEY_LOCATION_NUMBEROFSATELLITES = "number_of_satellites";
    private static final String KEY_LOCATION_TYPE = "type";
    private static final String KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX = "number_of_satellites_used_in_fix";

    // ---------------------------------------------------------------------------- Placemarks adds
    private static final String KEY_LOCATION_NAME = "name";

    // ------------------------------------------------------------------ Track Table Columns names
    private static final String KEY_TRACK_NAME = "name";
    private static final String KEY_TRACK_FROM = "location_from";
    private static final String KEY_TRACK_TO = "location_to";


    private static final String KEY_TRACK_START_LATITUDE        = "start_latitude";
    private static final String KEY_TRACK_START_LONGITUDE       = "start_longitude";
    private static final String KEY_TRACK_START_ALTITUDE        = "start_altitude";
    private static final String KEY_TRACK_START_ACCURACY        = "start_accuracy";
    private static final String KEY_TRACK_START_SPEED           = "start_speed";
    private static final String KEY_TRACK_START_TIME            = "start_time";

    private static final String KEY_TRACK_LASTFIX_TIME = "lastfix_time";

    private static final String KEY_TRACK_END_LATITUDE = "end_latitude";
    private static final String KEY_TRACK_END_LONGITUDE = "end_longitude";
    private static final String KEY_TRACK_END_ALTITUDE = "end_altitude";
    private static final String KEY_TRACK_END_ACCURACY = "end_accuracy";
    private static final String KEY_TRACK_END_SPEED = "end_speed";
    private static final String KEY_TRACK_END_TIME = "end_time";

    private static final String KEY_TRACK_LASTSTEPDST_LATITUDE = "laststepdst_latitude";
    private static final String KEY_TRACK_LASTSTEPDST_LONGITUDE = "laststepdst_longitude";
    private static final String KEY_TRACK_LASTSTEPDST_ACCURACY = "laststepdst_accuracy";

    private static final String KEY_TRACK_LASTSTEPALT_ALTITUDE = "laststepalt_altitude";
    private static final String KEY_TRACK_LASTSTEPALT_ACCURACY = "laststepalt_accuracy";

    private static final String KEY_TRACK_MIN_LATITUDE = "min_latitude";
    private static final String KEY_TRACK_MIN_LONGITUDE = "min_longitude";

    private static final String KEY_TRACK_MAX_LATITUDE = "max_latitude";
    private static final String KEY_TRACK_MAX_LONGITUDE = "max_longitude";

    private static final String KEY_TRACK_DURATION = "duration";
    private static final String KEY_TRACK_DURATION_MOVING = "duration_moving";

    private static final String KEY_TRACK_DISTANCE = "distance";
    private static final String KEY_TRACK_DISTANCE_INPROGRESS = "distance_in_progress";
    private static final String KEY_TRACK_DISTANCE_LASTALTITUDE = "distance_last_altitude";

    private static final String KEY_TRACK_ALTITUDE_UP = "altitude_up";
    private static final String KEY_TRACK_ALTITUDE_DOWN = "altitude_down";
    private static final String KEY_TRACK_ALTITUDE_INPROGRESS = "altitude_in_progress";

    private static final String KEY_TRACK_SPEED_MAX = "speed_max";
    private static final String KEY_TRACK_SPEED_AVERAGE = "speed_average";
    private static final String KEY_TRACK_SPEED_AVERAGEMOVING = "speed_average_moving";

    private static final String KEY_TRACK_NUMBEROFLOCATIONS = "number_of_locations";
    private static final String KEY_TRACK_NUMBEROFPLACEMARKS = "number_of_placemarks";
    private static final String KEY_TRACK_TYPE = "type";

    private static final String KEY_TRACK_VALIDMAP = "validmap";

    // ---------------------------------------------------------------------------- pointstates
    private static final String KEY_POINT_USERID = "userid"; //用户名
    private static final String KEY_POINT_CODE = "code";    //点位类型编码
    private static final String KEY_POINT_NAME = "name";    //点位类型名称
    private static final String KEY_POINT_TYPE = "type";    //类型
    private static final String KEY_POINT_SUBTYPE = "subtype";
    private static final String KEY_POINT_USUALLY = "usually";//是否常用

    // ---------------------------------------------------------------------------- userlogin
    private static final String KEY_USER = "username"; //用户名
    private static final String KEY_PASSWORD = "password";    //密码



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRACKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRACKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"    // 0
                + KEY_TRACK_NAME + " TEXT,"                         // 1
                + KEY_TRACK_FROM + " TEXT,"                         // 2
                + KEY_TRACK_TO + " TEXT,"                           // 3
                + KEY_TRACK_START_LATITUDE + " REAL,"               // 4
                + KEY_TRACK_START_LONGITUDE + " REAL,"              // 5
                + KEY_TRACK_START_ALTITUDE + " REAL,"               // 6
                + KEY_TRACK_START_ACCURACY + " REAL,"               // 7
                + KEY_TRACK_START_SPEED + " REAL,"                  // 8
                + KEY_TRACK_START_TIME + " REAL,"                   // 9
                + KEY_TRACK_LASTFIX_TIME + " REAL,"                 // 10
                + KEY_TRACK_END_LATITUDE + " REAL,"                 // 11
                + KEY_TRACK_END_LONGITUDE + " REAL,"                // 12
                + KEY_TRACK_END_ALTITUDE + " REAL,"                 // 13
                + KEY_TRACK_END_ACCURACY + " REAL,"                 // 14
                + KEY_TRACK_END_SPEED + " REAL,"                    // 15
                + KEY_TRACK_END_TIME + " REAL,"                     // 16
                + KEY_TRACK_LASTSTEPDST_LATITUDE + " REAL,"         // 17
                + KEY_TRACK_LASTSTEPDST_LONGITUDE + " REAL,"        // 18
                + KEY_TRACK_LASTSTEPDST_ACCURACY + " REAL,"         // 19
                + KEY_TRACK_LASTSTEPALT_ALTITUDE + " REAL,"         // 20
                + KEY_TRACK_LASTSTEPALT_ACCURACY + " REAL,"         // 21
                + KEY_TRACK_MIN_LATITUDE + " REAL,"                 // 22
                + KEY_TRACK_MIN_LONGITUDE + " REAL,"                // 23
                + KEY_TRACK_MAX_LATITUDE + " REAL,"                 // 24
                + KEY_TRACK_MAX_LONGITUDE + " REAL,"                // 25
                + KEY_TRACK_DURATION + " REAL,"                     // 26
                + KEY_TRACK_DURATION_MOVING + " REAL,"              // 27
                + KEY_TRACK_DISTANCE + " REAL,"                     // 28
                + KEY_TRACK_DISTANCE_INPROGRESS + " REAL,"          // 29
                + KEY_TRACK_DISTANCE_LASTALTITUDE + " REAL,"        // 30
                + KEY_TRACK_ALTITUDE_UP + " REAL,"                  // 31
                + KEY_TRACK_ALTITUDE_DOWN + " REAL,"                // 32
                + KEY_TRACK_ALTITUDE_INPROGRESS + " REAL,"          // 33
                + KEY_TRACK_SPEED_MAX + " REAL,"                    // 34
                + KEY_TRACK_SPEED_AVERAGE + " REAL,"                // 35
                + KEY_TRACK_SPEED_AVERAGEMOVING + " REAL,"          // 36
                + KEY_TRACK_NUMBEROFLOCATIONS + " INTEGER,"         // 37
                + KEY_TRACK_NUMBEROFPLACEMARKS + " INTEGER,"        // 38
                + KEY_TRACK_VALIDMAP + " INTEGER,"                  // 39
                + KEY_TRACK_TYPE + " INTEGER " + ")";               // 40
        db.execSQL(CREATE_TRACKS_TABLE);

        String CREATE_LOCATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"                // 0
                + KEY_TRACK_ID + " INTEGER,"                                    // 1
                + KEY_LOCATION_NUMBER + " INTEGER,"                             // 2
                + KEY_LOCATION_LATITUDE + " REAL,"                              // 3
                + KEY_LOCATION_LONGITUDE + " REAL,"                             // 4
                + KEY_LOCATION_ALTITUDE + " REAL,"                              // 5
                + KEY_LOCATION_SPEED + " REAL,"                                 // 6
                + KEY_LOCATION_ACCURACY + " REAL,"                              // 7
                + KEY_LOCATION_BEARING + " REAL,"                               // 8
                + KEY_LOCATION_TIME + " REAL,"                                  // 9
                + KEY_LOCATION_NUMBEROFSATELLITES + " INTEGER,"                 // 10
                + KEY_LOCATION_TYPE + " INTEGER,"                               // 11
                + KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX + " INTEGER" + ")";  // 12
        db.execSQL(CREATE_LOCATIONS_TABLE);

        String CREATE_PLACEMARKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACEMARKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"                // 0
                + KEY_TRACK_ID + " INTEGER,"                                    // 1
                + KEY_LOCATION_NUMBER + " INTEGER,"                             // 2
                + KEY_LOCATION_LATITUDE + " REAL,"                              // 3
                + KEY_LOCATION_LONGITUDE + " REAL,"                             // 4
                + KEY_LOCATION_ALTITUDE + " REAL,"                              // 5
                + KEY_LOCATION_SPEED + " REAL,"                                 // 6
                + KEY_LOCATION_ACCURACY + " REAL,"                              // 7
                + KEY_LOCATION_BEARING + " REAL,"                               // 8
                + KEY_LOCATION_TIME + " REAL,"                                  // 9
                + KEY_LOCATION_NUMBEROFSATELLITES + " INTEGER,"                 // 10
                + KEY_LOCATION_TYPE + " INTEGER,"                               // 11
                + KEY_LOCATION_NAME + " TEXT,"                                  // 12
                + KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX + " INTEGER" + ")";  // 13
        db.execSQL(CREATE_PLACEMARKS_TABLE);


        String CREATE_POINTTYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POINTTYPE + "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"          // 0
                + KEY_USER + " VARCHAR(30) NOT NULL,"
                + KEY_POINT_USERID + " VARCHAR(20) NOT NULL,"                      // 1
                + KEY_POINT_CODE + " VARCHAR(20) NOT NULL,"                        // 2
                + KEY_POINT_NAME + " VARCHAR(30) NOT NULL,"                               // 3
                + KEY_POINT_TYPE + " VARCHAR(20),"                                 // 4
                + KEY_POINT_SUBTYPE + " DOUBLE,"                                   // 5
                + KEY_POINT_USUALLY + " INTEGER DEFAULT(0)" + ")";                 // 6
        db.execSQL(CREATE_POINTTYPE_TABLE);
//        initPointTypeTableData(db);

        String CREATE_USERLOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERLOGIN + "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"          // 0
                + KEY_USER + " VARCHAR(30) NOT NULL,"                               // 1
                + KEY_PASSWORD + " VARCHAR(30)" + ")";                 // 2
        db.execSQL(CREATE_USERLOGIN_TABLE);


        //平台参数-公路类别表
        String CREATE_Pparams_Type_TABLE ="CREATE TABLE IF NOT EXISTS Pparams_Type (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "typename varchar(50)," +				//公路类别名
                "version integer)";
        db.execSQL(CREATE_Pparams_Type_TABLE);

        //平台参数-索引表
        String CREATE_Pparams_Index_TABLE ="CREATE TABLE IF NOT EXISTS Pparams_Index (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "typename varchar(50)," +				//公路类别名
                "code varchar(20)," +					//公路字段编码
                "name varchar(50) )";				//公路字段名称
        db.execSQL(CREATE_Pparams_Index_TABLE);

        //平台参数-参数值表
        String CREATE_Pparams_Items_TABLE= "CREATE TABLE IF NOT EXISTS Pparams_Items (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "typename varchar(50)," +				//公路类别名
                "indexcode varchar(20)," +				//公路字段编码
                "name varchar(50)," +					//公路字段选项名称
                "value integer)";						//公路字段选项值
        db.execSQL(CREATE_Pparams_Items_TABLE);

        //数据_点位表
        String CREATE_Data_Point_TABLE="CREATE TABLE IF NOT EXISTS Data_Point (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL," +	//所属点位类型编码:0-桥梁，1-隧道，2-渡口，3-涵洞，4-乡镇，5-建制村，
                // 6-自然村，7-学校，8-标志标牌，9-标志点
                "PathName varchar(20)," +			//路线名称
                "PathCode varchar(20)," +		//路线代码GUID
                "AdminCode varchar(20)," +		//行政区划
                "Type varchar(20)," +				//类型
                "Lat double," +						//纬度
                "Alt double," +						//高度
                "Lon double," +						//经度
                "Note varchar(100)," +				//备注
                "Picker varchar(20),"+				//用户名
                "Time date,"+						//采集时间
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Data_Point_TABLE);

        //数据_点位照片表
        String CREATE_Data_PointImage_TABLE="CREATE TABLE IF NOT EXISTS Data_PointImage (" +
                        "ID INTEGER NOT NULL primary key autoincrement," +
                        "UserID varchar(20) NOT NULL," +		//用户名
                        "PointCode varchar(20) NOT NULL," +		//所属点位类型编码
                        "PointNumbler varchar(20) NOT NULL," +	//所属点位编号
                        "ImagePath varchar(128) NOT NULL," +	//图片路径
                        "Lon double," +							//经度
                        "Lat double," +							//纬度
                        "Alt double," +							//高度
                        "Picker varchar(20),"+					//用户名
                        "Time date)";							//拍照时间
        db.execSQL(CREATE_Data_PointImage_TABLE);


        //数据_桥梁表
        String CREATE_Bridge_TABLE="CREATE TABLE IF NOT EXISTS Point_Bridge (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//桥名称
                "Code varchar(20)," +		//桥代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Bridge_TABLE);

        //数据_隧道表
        String CREATE_Tunnnel_TABLE="CREATE TABLE IF NOT EXISTS Point_Tunnel (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Tunnnel_TABLE);

        //数据_渡口表
        String CREATE_Ferry_TABLE="CREATE TABLE IF NOT EXISTS Point_Ferry (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Ferry_TABLE);

        //数据_渡口表
        String CREATE_Culvert_TABLE="CREATE TABLE IF NOT EXISTS Point_Culvert (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Culvert_TABLE);

        //数据_乡镇表
        String CREATE_Town_TABLE="CREATE TABLE IF NOT EXISTS Point_Town (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Town_TABLE);


        //数据_自然村表
        String CREATE_Village_TABLE="CREATE TABLE IF NOT EXISTS Point_Village (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Village_TABLE);

        //数据_建制村表
        String CREATE_StandardVillage_TABLE="CREATE TABLE IF NOT EXISTS Point_StandardVillage (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_StandardVillage_TABLE);


        //数据_学校表
        String CREATE_School_TABLE="CREATE TABLE IF NOT EXISTS Point_School (" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_School_TABLE);

        //数据_标志点表
        String CREATE_Sign_TABLE="CREATE TABLE IF NOT EXISTS Point_Sign(" +
                "ID INTEGER NOT NULL primary key autoincrement," +
                "UserID varchar(20) NOT NULL," +	//用户名
                "PointCode varchar(20) NOT NULL,"+    //外键关联 Data_Point
                "ImageCode varchar(20) NOT NULL,"+    //外键关联 Data_PointImage
                "Name varchar(20)," +			//名称
                "Code varchar(20)," +		//代码
                //........
                "UpLoadFlag integer default 0)";	//是否上报
        db.execSQL(CREATE_Sign_TABLE);




    }


    private static final int NOT_AVAILABLE = -100000;

    private static final String DATABASE_ALTER_TABLE_LOCATIONS_TO_V2 = "ALTER TABLE "
            + TABLE_LOCATIONS + " ADD COLUMN " + KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX + " INTEGER DEFAULT " +  NOT_AVAILABLE + ";";
    private static final String DATABASE_ALTER_TABLE_PLACEMARKS_TO_V2 = "ALTER TABLE "
            + TABLE_PLACEMARKS + " ADD COLUMN " + KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX + " INTEGER DEFAULT " +  NOT_AVAILABLE + ";";

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Use this function in case of DB version upgrade.

        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEMARKS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKS);

        // Create tables again
        //onCreate(db);

        switch (oldVersion)
        {
            case 1:
                //upgrade from version 1 to 2
                //Log.w("myApp", "[#] DatabaseHandler.java - onUpgrade: from version 1 to 2 ...");
                db.execSQL(DATABASE_ALTER_TABLE_LOCATIONS_TO_V2);
                db.execSQL(DATABASE_ALTER_TABLE_PLACEMARKS_TO_V2);
            //case 2:
                //upgrade from version 2 to 3
            //    db.execSQL(DATABASE_ALTER_TEAM_TO_V3);

                //and so on.. do not add breaks so that switch will
                //start at oldVersion, and run straight through to the latest
        }
        //Log.w("myApp", "[#] DatabaseHandler.java - onUpgrade: DB upgraded to version " + newVersion);
    }

// ----------------------------------------------------------------------- LOCATIONS AND PLACEMARKS

    // Add new Location and update the corresponding track
    public void addLocationToTrack(LocationExtended location, Track track) {
        SQLiteDatabase db = this.getWritableDatabase();

        Location loc = location.getLocation();

        ContentValues locvalues = new ContentValues();
        locvalues.put(KEY_TRACK_ID, track.getId());
        locvalues.put(KEY_LOCATION_NUMBER, track.getNumberOfLocations());
        locvalues.put(KEY_LOCATION_LATITUDE, loc.getLatitude());
        locvalues.put(KEY_LOCATION_LONGITUDE, loc.getLongitude());
        locvalues.put(KEY_LOCATION_ALTITUDE, loc.hasAltitude() ? loc.getAltitude() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_SPEED, loc.hasSpeed() ? loc.getSpeed() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_ACCURACY, loc.hasAccuracy() ? loc.getAccuracy() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_BEARING, loc.hasBearing() ? loc.getBearing() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_TIME, loc.getTime());
        locvalues.put(KEY_LOCATION_NUMBEROFSATELLITES, location.getNumberOfSatellites());
        locvalues.put(KEY_LOCATION_TYPE, LOCATION_TYPE_LOCATION);
        locvalues.put(KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX, location.getNumberOfSatellitesUsedInFix());

        ContentValues trkvalues = new ContentValues();
        trkvalues.put(KEY_TRACK_NAME, track.getName());
        trkvalues.put(KEY_TRACK_FROM, "");
        trkvalues.put(KEY_TRACK_TO, "");

        trkvalues.put(KEY_TRACK_START_LATITUDE, track.getStart_Latitude());
        trkvalues.put(KEY_TRACK_START_LONGITUDE, track.getStart_Longitude());
        trkvalues.put(KEY_TRACK_START_ALTITUDE, track.getStart_Altitude());
        trkvalues.put(KEY_TRACK_START_ACCURACY, track.getStart_Accuracy());
        trkvalues.put(KEY_TRACK_START_SPEED, track.getStart_Speed());
        trkvalues.put(KEY_TRACK_START_TIME, track.getStart_Time());

        trkvalues.put(KEY_TRACK_LASTFIX_TIME, track.getLastFix_Time());

        trkvalues.put(KEY_TRACK_END_LATITUDE, track.getEnd_Latitude());
        trkvalues.put(KEY_TRACK_END_LONGITUDE, track.getEnd_Longitude());
        trkvalues.put(KEY_TRACK_END_ALTITUDE, track.getEnd_Altitude());
        trkvalues.put(KEY_TRACK_END_ACCURACY, track.getEnd_Accuracy());
        trkvalues.put(KEY_TRACK_END_SPEED, track.getEnd_Speed());
        trkvalues.put(KEY_TRACK_END_TIME, track.getEnd_Time());

        trkvalues.put(KEY_TRACK_LASTSTEPDST_LATITUDE, track.getLastStepDistance_Latitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_LONGITUDE, track.getLastStepDistance_Longitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_ACCURACY, track.getLastStepDistance_Accuracy());

        trkvalues.put(KEY_TRACK_LASTSTEPALT_ALTITUDE, track.getLastStepAltitude_Altitude());
        trkvalues.put(KEY_TRACK_LASTSTEPALT_ACCURACY, track.getLastStepAltitude_Accuracy());

        trkvalues.put(KEY_TRACK_MIN_LATITUDE, track.getMin_Latitude());
        trkvalues.put(KEY_TRACK_MIN_LONGITUDE, track.getMin_Longitude());

        trkvalues.put(KEY_TRACK_MAX_LATITUDE, track.getMax_Latitude());
        trkvalues.put(KEY_TRACK_MAX_LONGITUDE, track.getMax_Longitude());

        trkvalues.put(KEY_TRACK_DURATION, track.getDuration());
        trkvalues.put(KEY_TRACK_DURATION_MOVING, track.getDuration_Moving());

        trkvalues.put(KEY_TRACK_DISTANCE, track.getDistance());
        trkvalues.put(KEY_TRACK_DISTANCE_INPROGRESS, track.getDistanceInProgress());
        trkvalues.put(KEY_TRACK_DISTANCE_LASTALTITUDE, track.getDistanceLastAltitude());

        trkvalues.put(KEY_TRACK_ALTITUDE_UP, track.getAltitude_Up());
        trkvalues.put(KEY_TRACK_ALTITUDE_DOWN, track.getAltitude_Down());
        trkvalues.put(KEY_TRACK_ALTITUDE_INPROGRESS, track.getAltitude_InProgress());

        trkvalues.put(KEY_TRACK_SPEED_MAX, track.getSpeedMax());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGE, track.getSpeedAverage());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGEMOVING, track.getSpeedAverageMoving());

        trkvalues.put(KEY_TRACK_NUMBEROFLOCATIONS, track.getNumberOfLocations());
        trkvalues.put(KEY_TRACK_NUMBEROFPLACEMARKS, track.getNumberOfPlacemarks());
        trkvalues.put(KEY_TRACK_TYPE, track.getType());

        trkvalues.put(KEY_TRACK_VALIDMAP, track.getValidMap());

        db.beginTransaction();
        db.insert(TABLE_LOCATIONS, null, locvalues);                // Insert the new Location
        db.update(TABLE_TRACKS, trkvalues, KEY_ID + " = ?",
                new String[] { String.valueOf(track.getId()) });    // Update the corresponding Track
        db.setTransactionSuccessful();
        db.endTransaction();

        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Add new Placemark and update the corresponding track
    public void addPlacemarkToTrack(LocationExtended placemark, Track track) {
        SQLiteDatabase db = this.getWritableDatabase();

        Location loc = placemark.getLocation();

        ContentValues locvalues = new ContentValues();
        locvalues.put(KEY_TRACK_ID, track.getId());
        locvalues.put(KEY_LOCATION_NUMBER, track.getNumberOfPlacemarks());
        locvalues.put(KEY_LOCATION_LATITUDE, loc.getLatitude());
        locvalues.put(KEY_LOCATION_LONGITUDE, loc.getLongitude());
        locvalues.put(KEY_LOCATION_ALTITUDE, loc.hasAltitude() ? loc.getAltitude() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_SPEED, loc.hasSpeed() ? loc.getSpeed() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_ACCURACY, loc.hasAccuracy() ? loc.getAccuracy() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_BEARING, loc.hasBearing() ? loc.getBearing() : NOT_AVAILABLE);
        locvalues.put(KEY_LOCATION_TIME, loc.getTime());
        locvalues.put(KEY_LOCATION_NUMBEROFSATELLITES, placemark.getNumberOfSatellites());
        locvalues.put(KEY_LOCATION_TYPE, LOCATION_TYPE_PLACEMARK);
        locvalues.put(KEY_LOCATION_NAME, placemark.getDescription());
        locvalues.put(KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX, placemark.getNumberOfSatellitesUsedInFix());

        ContentValues trkvalues = new ContentValues();
        trkvalues.put(KEY_TRACK_NAME, track.getName());
        trkvalues.put(KEY_TRACK_FROM, "");
        trkvalues.put(KEY_TRACK_TO, "");

        trkvalues.put(KEY_TRACK_START_LATITUDE, track.getStart_Latitude());
        trkvalues.put(KEY_TRACK_START_LONGITUDE, track.getStart_Longitude());
        trkvalues.put(KEY_TRACK_START_ALTITUDE, track.getStart_Altitude());
        trkvalues.put(KEY_TRACK_START_ACCURACY, track.getStart_Accuracy());
        trkvalues.put(KEY_TRACK_START_SPEED, track.getStart_Speed());
        trkvalues.put(KEY_TRACK_START_TIME, track.getStart_Time());

        trkvalues.put(KEY_TRACK_LASTFIX_TIME, track.getLastFix_Time());

        trkvalues.put(KEY_TRACK_END_LATITUDE, track.getEnd_Latitude());
        trkvalues.put(KEY_TRACK_END_LONGITUDE, track.getEnd_Longitude());
        trkvalues.put(KEY_TRACK_END_ALTITUDE, track.getEnd_Altitude());
        trkvalues.put(KEY_TRACK_END_ACCURACY, track.getEnd_Accuracy());
        trkvalues.put(KEY_TRACK_END_SPEED, track.getEnd_Speed());
        trkvalues.put(KEY_TRACK_END_TIME, track.getEnd_Time());

        trkvalues.put(KEY_TRACK_LASTSTEPDST_LATITUDE, track.getLastStepDistance_Latitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_LONGITUDE, track.getLastStepDistance_Longitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_ACCURACY, track.getLastStepDistance_Accuracy());

        trkvalues.put(KEY_TRACK_LASTSTEPALT_ALTITUDE, track.getLastStepAltitude_Altitude());
        trkvalues.put(KEY_TRACK_LASTSTEPALT_ACCURACY, track.getLastStepAltitude_Accuracy());

        trkvalues.put(KEY_TRACK_MIN_LATITUDE, track.getMin_Latitude());
        trkvalues.put(KEY_TRACK_MIN_LONGITUDE, track.getMin_Longitude());

        trkvalues.put(KEY_TRACK_MAX_LATITUDE, track.getMax_Latitude());
        trkvalues.put(KEY_TRACK_MAX_LONGITUDE, track.getMax_Longitude());

        trkvalues.put(KEY_TRACK_DURATION, track.getDuration());
        trkvalues.put(KEY_TRACK_DURATION_MOVING, track.getDuration_Moving());

        trkvalues.put(KEY_TRACK_DISTANCE, track.getDistance());
        trkvalues.put(KEY_TRACK_DISTANCE_INPROGRESS, track.getDistanceInProgress());
        trkvalues.put(KEY_TRACK_DISTANCE_LASTALTITUDE, track.getDistanceLastAltitude());

        trkvalues.put(KEY_TRACK_ALTITUDE_UP, track.getAltitude_Up());
        trkvalues.put(KEY_TRACK_ALTITUDE_DOWN, track.getAltitude_Down());
        trkvalues.put(KEY_TRACK_ALTITUDE_INPROGRESS, track.getAltitude_InProgress());

        trkvalues.put(KEY_TRACK_SPEED_MAX, track.getSpeedMax());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGE, track.getSpeedAverage());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGEMOVING, track.getSpeedAverageMoving());

        trkvalues.put(KEY_TRACK_NUMBEROFLOCATIONS, track.getNumberOfLocations());
        trkvalues.put(KEY_TRACK_NUMBEROFPLACEMARKS, track.getNumberOfPlacemarks());
        trkvalues.put(KEY_TRACK_TYPE, track.getType());

        trkvalues.put(KEY_TRACK_VALIDMAP, track.getValidMap());

        db.beginTransaction();
        db.insert(TABLE_PLACEMARKS, null, locvalues);                // Insert the new Location
        db.update(TABLE_TRACKS, trkvalues, KEY_ID + " = ?",
                new String[] { String.valueOf(track.getId()) });    // Update the corresponding Track
        db.setTransactionSuccessful();
        db.endTransaction();

        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Get single Location
    public LocationExtended getLocation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        LocationExtended extdloc = null;
        double lcdata_double;
        float lcdata_float;

        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] {KEY_ID,
                        KEY_LOCATION_LATITUDE,
                        KEY_LOCATION_LONGITUDE,
                        KEY_LOCATION_ALTITUDE,
                        KEY_LOCATION_SPEED,
                        KEY_LOCATION_ACCURACY,
                        KEY_LOCATION_BEARING,
                        KEY_LOCATION_TIME,
                        KEY_LOCATION_NUMBEROFSATELLITES,
                        KEY_LOCATION_NUMBEROFSATELLITESUSEDINFIX}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Location lc = new Location("DB");
            lc.setLatitude(cursor.getDouble(1));
            lc.setLongitude(cursor.getDouble(2));

            lcdata_double = cursor.getDouble(3);
            if (lcdata_double != NOT_AVAILABLE) lc.setAltitude(lcdata_double);
            //else lc.removeAltitude();

            lcdata_float = cursor.getFloat(4);
            if (lcdata_float != NOT_AVAILABLE) lc.setSpeed(lcdata_float);
            //else lc.removeSpeed();

            lcdata_float = cursor.getFloat(5);
            if (lcdata_float != NOT_AVAILABLE) lc.setAccuracy(lcdata_float);
            //else lc.removeAccuracy();

            lcdata_float = cursor.getFloat(6);
            if (lcdata_float != NOT_AVAILABLE) lc.setBearing(lcdata_float);
            //else lc.removeBearing();

            lc.setTime(cursor.getLong(7));


            extdloc = new LocationExtended(lc);
            extdloc.setNumberOfSatellites(cursor.getInt(8));
            extdloc.setNumberOfSatellitesUsedInFix(cursor.getInt(9));

            cursor.close();
        }
        return extdloc != null ? extdloc : null;
    }



    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LocationExtended> getLocationsList(long TrackID, long startNumber, long endNumber) {

        List<LocationExtended> locationList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS + " WHERE "
                + KEY_TRACK_ID + " = " + TrackID + " AND "
                + KEY_LOCATION_NUMBER + " BETWEEN " + startNumber + " AND " + endNumber
                + " ORDER BY " + KEY_LOCATION_NUMBER;

        //Log.w("myApp", "[#] DatabaseHandler.java - getLocationList(" + TrackID + ", " + startNumber + ", " +endNumber + ") ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double lcdata_double;
        float lcdata_float;

        if (cursor != null) {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Location lc = new Location("DB");
                    lc.setLatitude(cursor.getDouble(3));
                    lc.setLongitude(cursor.getDouble(4));

                    lcdata_double = cursor.getDouble(5);
                    if (lcdata_double != NOT_AVAILABLE) lc.setAltitude(lcdata_double);
                    //else lc.removeAltitude();

                    lcdata_float = cursor.getFloat(6);
                    if (lcdata_float != NOT_AVAILABLE) lc.setSpeed(lcdata_float);
                    //else lc.removeSpeed();

                    lcdata_float = cursor.getFloat(7);
                    if (lcdata_float != NOT_AVAILABLE) lc.setAccuracy(lcdata_float);
                    //else lc.removeAccuracy();

                    lcdata_float = cursor.getFloat(8);
                    if (lcdata_float != NOT_AVAILABLE) lc.setBearing(lcdata_float);
                    //else lc.removeBearing();

                    lc.setTime(cursor.getLong(9));

                    LocationExtended extdloc = new LocationExtended(lc);
                    extdloc.setNumberOfSatellites(cursor.getInt(10));
                    extdloc.setNumberOfSatellitesUsedInFix(cursor.getInt(12));

                    locationList.add(extdloc); // Add Location to list
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return locationList;
    }

    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LocationExtended> getPlacemarksList(long TrackID, long startNumber, long endNumber) {

        List<LocationExtended> placemarkList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACEMARKS + " WHERE "
                + KEY_TRACK_ID + " = " + TrackID + " AND "
                + KEY_LOCATION_NUMBER + " BETWEEN " + startNumber + " AND " + endNumber
                + " ORDER BY " + KEY_LOCATION_NUMBER;

        //Log.w("myApp", "[#] DatabaseHandler.java - getLocationList(" + TrackID + ", " + startNumber + ", " +endNumber + ") ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double lcdata_double;
        float lcdata_float;

        if (cursor != null) {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Location lc = new Location("DB");
                    lc.setLatitude(cursor.getDouble(3));
                    lc.setLongitude(cursor.getDouble(4));

                    lcdata_double = cursor.getDouble(5);
                    if (lcdata_double != NOT_AVAILABLE) lc.setAltitude(lcdata_double);
                    //else lc.removeAltitude();

                    lcdata_float = cursor.getFloat(6);
                    if (lcdata_float != NOT_AVAILABLE) lc.setSpeed(lcdata_float);
                    //else lc.removeSpeed();

                    lcdata_float = cursor.getFloat(7);
                    if (lcdata_float != NOT_AVAILABLE) lc.setAccuracy(lcdata_float);
                    //else lc.removeAccuracy();

                    lcdata_float = cursor.getFloat(8);
                    if (lcdata_float != NOT_AVAILABLE) lc.setBearing(lcdata_float);
                    //else lc.removeBearing();

                    lc.setTime(cursor.getLong(9));

                    LocationExtended extdloc = new LocationExtended(lc);
                    extdloc.setNumberOfSatellites(cursor.getInt(10));
                    extdloc.setNumberOfSatellitesUsedInFix(cursor.getInt(13));
                    extdloc.setDescription(cursor.getString(12));

                    placemarkList.add(extdloc); // Add Location to list
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return placemarkList;
    }


    // Getting a list of Locations associated to a specified track, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<LatLng> getLatLngList(long TrackID, long startNumber, long endNumber) {

        List<LatLng> latlngList = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_TRACK_ID + "," + KEY_LOCATION_LATITUDE + "," + KEY_LOCATION_LONGITUDE + "," + KEY_LOCATION_NUMBER
                + " FROM " + TABLE_LOCATIONS + " WHERE "
                + KEY_TRACK_ID + " = " + TrackID + " AND "
                + KEY_LOCATION_NUMBER + " BETWEEN " + startNumber + " AND " + endNumber
                + " ORDER BY " + KEY_LOCATION_NUMBER;

        //Log.w("myApp", "[#] DatabaseHandler.java - getLocationList(" + TrackID + ", " + startNumber + ", " +endNumber + ") ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LatLng latlng = new LatLng();
                    latlng.setLatitude(cursor.getDouble(1));
                    latlng.setLongitude(cursor.getDouble(2));

                    latlngList.add(latlng); // Add Location to list
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return latlngList;
    }


    // Get the total amount of Locations stored in the DB
    public long getLocationsTotalCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        long result = cursor.getCount();
        cursor.close();
        return result;
    }


    // Get the number of Locations of a Track
    public long getLocationsCount(long TrackID) {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS + " WHERE " + KEY_TRACK_ID + " = " + TrackID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        long result = cursor.getCount();
        cursor.close();
        return result;
    }


    // Get last Location ID
    public long getLastLocationID(long TrackID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;

        String query = "SELECT " + KEY_ID + " FROM " + TABLE_LOCATIONS +
                " WHERE " + KEY_TRACK_ID + " = " + TrackID + " ORDER BY " + KEY_ID + " DESC LIMIT 1";

        //Log.w("myApp", "[#] DatabaseHandler.java - getLastLocationID(): " + query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            //Log.w("myApp", "[#] !null");
            if (cursor.moveToFirst()) {
                result = cursor.getLong(0);
            } //else Log.w("myApp", "[#] DatabaseHandler.java - Location table is empty");
            cursor.close();
        }
        //Log.w("myApp", "[#] RETURN getLastTrack()");
        return result;
    }




// ----------------------------------------------------------------------------------------- TRACKS

    // Delete the track with the specified ID;
    // The method deletes also Placemarks and Locations associated to the specified track
    public void DeleteTrack(long TrackID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(TABLE_PLACEMARKS, KEY_TRACK_ID + " = ?",
                new String[] { String.valueOf(TrackID) });    // Delete track's Placemarks
        db.delete(TABLE_LOCATIONS, KEY_TRACK_ID + " = ?",
                new String[] { String.valueOf(TrackID) });    // Delete track's Locations
        db.delete(TABLE_TRACKS, KEY_ID + " = ?",
                new String[] { String.valueOf(TrackID) });    // Delete track
        db.setTransactionSuccessful();
        db.endTransaction();

        //Log.w("myApp", "[#] DatabaseHandler.java - addLocation: Location " + track.getNumberOfLocations() + " added into track " + track.getID());
    }


    // Add a new Track, returns the TrackID
    public long addTrack(Track track) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues trkvalues = new ContentValues();
        trkvalues.put(KEY_TRACK_NAME, track.getName());
        trkvalues.put(KEY_TRACK_FROM, "");
        trkvalues.put(KEY_TRACK_TO, "");

        trkvalues.put(KEY_TRACK_START_LATITUDE, track.getStart_Latitude());
        trkvalues.put(KEY_TRACK_START_LONGITUDE, track.getStart_Longitude());
        trkvalues.put(KEY_TRACK_START_ALTITUDE, track.getStart_Altitude());
        trkvalues.put(KEY_TRACK_START_ACCURACY, track.getStart_Accuracy());
        trkvalues.put(KEY_TRACK_START_SPEED, track.getStart_Speed());
        trkvalues.put(KEY_TRACK_START_TIME, track.getStart_Time());

        trkvalues.put(KEY_TRACK_LASTFIX_TIME, track.getLastFix_Time());

        trkvalues.put(KEY_TRACK_END_LATITUDE, track.getEnd_Latitude());
        trkvalues.put(KEY_TRACK_END_LONGITUDE, track.getEnd_Longitude());
        trkvalues.put(KEY_TRACK_END_ALTITUDE, track.getEnd_Altitude());
        trkvalues.put(KEY_TRACK_END_ACCURACY, track.getEnd_Accuracy());
        trkvalues.put(KEY_TRACK_END_SPEED, track.getEnd_Speed());
        trkvalues.put(KEY_TRACK_END_TIME, track.getEnd_Time());

        trkvalues.put(KEY_TRACK_LASTSTEPDST_LATITUDE, track.getLastStepDistance_Latitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_LONGITUDE, track.getLastStepDistance_Longitude());
        trkvalues.put(KEY_TRACK_LASTSTEPDST_ACCURACY, track.getLastStepDistance_Accuracy());

        trkvalues.put(KEY_TRACK_LASTSTEPALT_ALTITUDE, track.getLastStepAltitude_Altitude());
        trkvalues.put(KEY_TRACK_LASTSTEPALT_ACCURACY, track.getLastStepAltitude_Accuracy());

        trkvalues.put(KEY_TRACK_MIN_LATITUDE, track.getMin_Latitude());
        trkvalues.put(KEY_TRACK_MIN_LONGITUDE, track.getMin_Longitude());

        trkvalues.put(KEY_TRACK_MAX_LATITUDE, track.getMax_Latitude());
        trkvalues.put(KEY_TRACK_MAX_LONGITUDE, track.getMax_Longitude());

        trkvalues.put(KEY_TRACK_DURATION, track.getDuration());
        trkvalues.put(KEY_TRACK_DURATION_MOVING, track.getDuration_Moving());

        trkvalues.put(KEY_TRACK_DISTANCE, track.getDistance());
        trkvalues.put(KEY_TRACK_DISTANCE_INPROGRESS, track.getDistanceInProgress());
        trkvalues.put(KEY_TRACK_DISTANCE_LASTALTITUDE, track.getDistanceLastAltitude());

        trkvalues.put(KEY_TRACK_ALTITUDE_UP, track.getAltitude_Up());
        trkvalues.put(KEY_TRACK_ALTITUDE_DOWN, track.getAltitude_Down());
        trkvalues.put(KEY_TRACK_ALTITUDE_INPROGRESS, track.getAltitude_InProgress());

        trkvalues.put(KEY_TRACK_SPEED_MAX, track.getSpeedMax());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGE, track.getSpeedAverage());
        trkvalues.put(KEY_TRACK_SPEED_AVERAGEMOVING, track.getSpeedAverageMoving());

        trkvalues.put(KEY_TRACK_NUMBEROFLOCATIONS, track.getNumberOfLocations());
        trkvalues.put(KEY_TRACK_NUMBEROFPLACEMARKS, track.getNumberOfPlacemarks());
        trkvalues.put(KEY_TRACK_TYPE, track.getType());

        trkvalues.put(KEY_TRACK_VALIDMAP, track.getValidMap());

        long TrackID;
        // Inserting Row
        TrackID = (db.insert(TABLE_TRACKS, null, trkvalues));

        //Log.w("myApp", "[#] DatabaseHandler.java - addTrack " + TrackID);

        return TrackID; // Insert this in the track ID !!!
    }


    // Get Track
    public Track getTrack(long TrackID) {

        Track track = null;

        String selectQuery = "SELECT  * FROM " + TABLE_TRACKS + " WHERE "
                + KEY_ID + " = " + TrackID;

        //Log.w("myApp", "[#] DatabaseHandler.java - getTrackList(" + startNumber + ", " +endNumber + ") ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Log.w("myApp", "[#] DatabaseHandler.java - getTrack(" + TrackID + ")");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                track = new Track();
                track.FromDB(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),

                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getFloat(7),
                        cursor.getFloat(8),
                        cursor.getLong(9),

                        cursor.getLong(10),

                        cursor.getDouble(11),
                        cursor.getDouble(12),
                        cursor.getDouble(13),
                        cursor.getFloat(14),
                        cursor.getFloat(15),
                        cursor.getLong(16),

                        cursor.getDouble(17),
                        cursor.getDouble(18),
                        cursor.getFloat(19),

                        cursor.getDouble(20),
                        cursor.getFloat(21),

                        cursor.getDouble(22),
                        cursor.getDouble(23),
                        cursor.getDouble(24),
                        cursor.getDouble(25),

                        cursor.getLong(26),
                        cursor.getLong(27),

                        cursor.getFloat(28),
                        cursor.getFloat(29),
                        cursor.getLong(30),

                        cursor.getDouble(31),
                        cursor.getDouble(32),
                        cursor.getDouble(33),

                        cursor.getFloat(34),
                        cursor.getFloat(35),
                        cursor.getFloat(36),

                        cursor.getLong(37),
                        cursor.getLong(38),

                        cursor.getInt(39),
                        cursor.getInt(40));
            }
            cursor.close();
        }
        return track;
    }


    // Get last TrackID
    public long getLastTrackID() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;

        String query = "SELECT " + KEY_ID + " FROM " + TABLE_TRACKS + " ORDER BY " + KEY_ID + " DESC LIMIT 1";

        //Log.w("myApp", "[#] DatabaseHandler.java - getLastTrackID(): " + query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            //Log.w("myApp", "[#] !null");
            if (cursor.moveToFirst()) {
                result = cursor.getLong(0);
            } //else Log.w("myApp", "[#] Tracks table is empty");
            cursor.close();
        }
        //Log.w("myApp", "[#] RETURN getLastTrack()");
        //Log.w("myApp", "[#] DatabaseHandler.java - LastTrackID = " + result);
        return result;
    }


    // Get last TrackID
    public Track getLastTrack() {
        return getTrack(getLastTrackID());
    }


    // Getting a list of Tracks, with number between startNumber and endNumber
    // Please note that limits both are inclusive!
    public List<Track> getTracksList(long startNumber, long endNumber) {

        List<Track> trackList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_TRACKS + " WHERE "
                + KEY_ID + " BETWEEN " + startNumber + " AND " + endNumber
                + " ORDER BY " + KEY_ID + " DESC";

        //Log.w("myApp", "[#] DatabaseHandler.java - getTrackList(" + startNumber + ", " +endNumber + ") ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Track trk = new Track();
                    trk.FromDB(cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),

                            cursor.getDouble(4),
                            cursor.getDouble(5),
                            cursor.getDouble(6),
                            cursor.getFloat(7),
                            cursor.getFloat(8),
                            cursor.getLong(9),

                            cursor.getLong(10),

                            cursor.getDouble(11),
                            cursor.getDouble(12),
                            cursor.getDouble(13),
                            cursor.getFloat(14),
                            cursor.getFloat(15),
                            cursor.getLong(16),

                            cursor.getDouble(17),
                            cursor.getDouble(18),
                            cursor.getFloat(19),

                            cursor.getDouble(20),
                            cursor.getFloat(21),

                            cursor.getDouble(22),
                            cursor.getDouble(23),
                            cursor.getDouble(24),
                            cursor.getDouble(25),

                            cursor.getLong(26),
                            cursor.getLong(27),

                            cursor.getFloat(28),
                            cursor.getFloat(29),
                            cursor.getLong(30),

                            cursor.getDouble(31),
                            cursor.getDouble(32),
                            cursor.getDouble(33),

                            cursor.getFloat(34),
                            cursor.getFloat(35),
                            cursor.getFloat(36),

                            cursor.getLong(37),
                            cursor.getLong(38),

                            cursor.getInt(39),
                            cursor.getInt(40));

                    trackList.add(trk);             // Add Track to list
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return trackList;
    }


    //-------------------------PointType_Table--------------------------

    //init pointtype table in onCreate()
    public void initPointType(String userName){
        SQLiteDatabase db=this.getReadableDatabase();

        String insert,query;
        query = "SELECT * FROM " + TABLE_POINTTYPE
                + " WHERE "+KEY_USER+"= '"+userName+"'";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()>0){
            Log.d("MainApplication","Already save point type data for this user.");
        }
        else{
            for(int i=0;i< ConstantValue.points_all_db.length;i++){
                insert="INSERT INTO " + TABLE_POINTTYPE
                        +"("+KEY_USER+","+KEY_POINT_NAME+","+KEY_POINT_USUALLY+")"
                        + " VALUES('" + userName + "','" + ConstantValue.points_all_db[i]
                        + "','" + ConstantValue.points_type[i] + "')";
                db.execSQL(insert);
            }
        }

    }

    //return all point type info
    public List<PointType> getUsualPoints(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        List<PointType> points = new ArrayList<>();

        List<String> point_names_db= Arrays.asList(ConstantValue.points_all_db);
        List<String> point_names=Arrays.asList(ConstantValue.points_all);

        String query = "SELECT * FROM " + TABLE_POINTTYPE+ " WHERE "+KEY_USER+"= '"+userName+"'";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            String ptname= cursor.getString(2);
            int ptusual=cursor.getInt(3);
            PointType pt_tmp=new PointType();
            pt_tmp.setName(point_names.get(point_names_db.indexOf(ptname)));
            pt_tmp.setUsually(ptusual);
            points.add(pt_tmp);
        }
        return  points;


    }

    //update point type info
    public void updateUsualPoints(String userName,int[] pointUsuals){
        SQLiteDatabase db = this.getReadableDatabase();
        String updatePT;
        for(int i=0;i<pointUsuals.length;i++){
            updatePT="UPDATE "+ TABLE_POINTTYPE
                    + " SET "+ KEY_POINT_USUALLY+"="+pointUsuals[i]
                    + " WHERE " + KEY_USER+" = '"+userName+"' AND "
                    + KEY_POINT_NAME+" = '"+ConstantValue.points_all_db[i]+"'";
            db.execSQL(updatePT);
        }
    }

    //-------------------------USERLOGIN_Table--------------------------
    public void updateUserLoginInfo(String[] userinfo){
        SQLiteDatabase db = this.getReadableDatabase();
        String query="SELECT * FROM " + TABLE_USERLOGIN + " WHERE "
                + KEY_USER + " = '"+userinfo[0]+"'";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("DebugInfo",query);
        Log.d("DebugInfo",cursor.getCount()+"");
        String mPwd="";
        if(cursor.moveToNext()){
            mPwd=cursor.getString(2);
            Log.d("DebugInfo",mPwd);
            if(mPwd.equals(userinfo[1])){
                //if exist, return
                return;
            }
            else if(!userinfo[1].equals("")){
                //if user exists, pwd changed, update
                String updateUP="UPDATE " + TABLE_USERLOGIN
                        + " SET "+KEY_PASSWORD + " = '"+userinfo[1]+"' WHERE "
                        + KEY_USER + " = '"+userinfo[0]+"'";
                db.execSQL(updateUP);

            }
        }
        else {
            //if not exist, insert
            String insert="INSERT INTO " + TABLE_USERLOGIN
                    +"("+KEY_USER+","+KEY_PASSWORD+")"
                    + " VALUES('" + userinfo[0] + "','" + userinfo[1] + "')";
            db.execSQL(insert);
        }
    }

    public List<Map<String,Object>> getUserLoginInfo(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<Map<String,Object>> users = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USERLOGIN;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            String username= cursor.getString(1);
            String password= cursor.getString(2);
            Map<String,Object> userinfo=new HashMap<String,Object>();
            userinfo.put("user",username);
            userinfo.put("password",password);
            users.add(userinfo);
        }
        return  users;

    }


    // Getting the list of all Tracks in the DB
    /* NOT USED, COMMENTED OUT !!
    public List<Track> getTracksList() {

        List<Track> trackList = new ArrayList<Track>();

        String selectQuery = "SELECT  * FROM " + TABLE_TRACKS
                + " ORDER BY " + KEY_ID + " DESC";

        //Log.w("myApp", "[#] DatabaseHandler.java - getTrackList() ==> " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Track trk = new Track();
                    trk.FromDB(cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),

                            cursor.getDouble(4),
                            cursor.getDouble(5),
                            cursor.getDouble(6),
                            cursor.getFloat(7),
                            cursor.getFloat(8),
                            cursor.getLong(9),

                            cursor.getLong(10),

                            cursor.getDouble(11),
                            cursor.getDouble(12),
                            cursor.getDouble(13),
                            cursor.getFloat(14),
                            cursor.getFloat(15),
                            cursor.getLong(16),

                            cursor.getDouble(17),
                            cursor.getDouble(18),
                            cursor.getFloat(19),

                            cursor.getDouble(20),
                            cursor.getFloat(21),

                            cursor.getDouble(22),
                            cursor.getDouble(23),
                            cursor.getDouble(24),
                            cursor.getDouble(25),

                            cursor.getLong(26),
                            cursor.getLong(27),

                            cursor.getFloat(28),
                            cursor.getFloat(29),
                            cursor.getLong(30),

                            cursor.getDouble(31),
                            cursor.getDouble(32),
                            cursor.getDouble(33),

                            cursor.getFloat(34),
                            cursor.getFloat(35),
                            cursor.getFloat(36),

                            cursor.getLong(37),
                            cursor.getLong(38),

                            cursor.getInt(39),
                            cursor.getInt(40));
                    trackList.add(trk);             // Add Track to list
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return trackList;
    } */
}