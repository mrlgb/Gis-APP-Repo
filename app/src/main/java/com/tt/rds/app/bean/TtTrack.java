package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtTrack {
    @Id(autoincrement = true)
    private Long trackId;
    @Index(unique = true)
    private String guid;//guid

    private String track_name;//* 路线名称
    private String location_from;//*
    private String location_to;//*

    private double start_latitude;
    private double start_longitude;
    private double start_altitude;
    private float start_accuracy;
    private float start_speed;
    private long start_time;

    private double end_latitude;
    private double end_longitude;
    private double end_altitude;
    private float end_accuracy;
    private float end_speed;
    private long end_time;

    private long last_fix_time;
    private double last_step_dst_latitude;
    private double last_step_dst_longitude;
    private float last_step_dst_accuracy;
    private double last_step_alittude_altitude;
    private float last_step_alittude_accuracy;

    private double min_latitude;
    private double  min_longitude;

    private double  max_latitude;
    private double  max_longitude;

    private long  duration;
    private long  duration_moving;

    private float  distance;
    private float  distance_in_progress;
    private long  distance_last_altitude;

    private double  altitude_up;
    private double  altitude_down;
    private double altitude_in_progress;

    private float speed_max;
    private float speed_average;
    private float speed_average_moving;

    private long number_of_locations;
    private long number_of_placemarks;
    private int type;

    private int validmap;

    @Generated(hash = 117718825)
    public TtTrack(Long trackId, String guid, String track_name,
            String location_from, String location_to, double start_latitude,
            double start_longitude, double start_altitude, float start_accuracy,
            float start_speed, long start_time, double end_latitude,
            double end_longitude, double end_altitude, float end_accuracy,
            float end_speed, long end_time, long last_fix_time,
            double last_step_dst_latitude, double last_step_dst_longitude,
            float last_step_dst_accuracy, double last_step_alittude_altitude,
            float last_step_alittude_accuracy, double min_latitude,
            double min_longitude, double max_latitude, double max_longitude,
            long duration, long duration_moving, float distance,
            float distance_in_progress, long distance_last_altitude,
            double altitude_up, double altitude_down, double altitude_in_progress,
            float speed_max, float speed_average, float speed_average_moving,
            long number_of_locations, long number_of_placemarks, int type,
            int validmap) {
        this.trackId = trackId;
        this.guid = guid;
        this.track_name = track_name;
        this.location_from = location_from;
        this.location_to = location_to;
        this.start_latitude = start_latitude;
        this.start_longitude = start_longitude;
        this.start_altitude = start_altitude;
        this.start_accuracy = start_accuracy;
        this.start_speed = start_speed;
        this.start_time = start_time;
        this.end_latitude = end_latitude;
        this.end_longitude = end_longitude;
        this.end_altitude = end_altitude;
        this.end_accuracy = end_accuracy;
        this.end_speed = end_speed;
        this.end_time = end_time;
        this.last_fix_time = last_fix_time;
        this.last_step_dst_latitude = last_step_dst_latitude;
        this.last_step_dst_longitude = last_step_dst_longitude;
        this.last_step_dst_accuracy = last_step_dst_accuracy;
        this.last_step_alittude_altitude = last_step_alittude_altitude;
        this.last_step_alittude_accuracy = last_step_alittude_accuracy;
        this.min_latitude = min_latitude;
        this.min_longitude = min_longitude;
        this.max_latitude = max_latitude;
        this.max_longitude = max_longitude;
        this.duration = duration;
        this.duration_moving = duration_moving;
        this.distance = distance;
        this.distance_in_progress = distance_in_progress;
        this.distance_last_altitude = distance_last_altitude;
        this.altitude_up = altitude_up;
        this.altitude_down = altitude_down;
        this.altitude_in_progress = altitude_in_progress;
        this.speed_max = speed_max;
        this.speed_average = speed_average;
        this.speed_average_moving = speed_average_moving;
        this.number_of_locations = number_of_locations;
        this.number_of_placemarks = number_of_placemarks;
        this.type = type;
        this.validmap = validmap;
    }

    @Generated(hash = 1036543980)
    public TtTrack() {
    }

    public Long getTrackId() {
        return this.trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTrack_name() {
        return this.track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getLocation_from() {
        return this.location_from;
    }

    public void setLocation_from(String location_from) {
        this.location_from = location_from;
    }

    public String getLocation_to() {
        return this.location_to;
    }

    public void setLocation_to(String location_to) {
        this.location_to = location_to;
    }

    public double getStart_latitude() {
        return this.start_latitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public double getStart_longitude() {
        return this.start_longitude;
    }

    public void setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public double getStart_altitude() {
        return this.start_altitude;
    }

    public void setStart_altitude(double start_altitude) {
        this.start_altitude = start_altitude;
    }

    public float getStart_accuracy() {
        return this.start_accuracy;
    }

    public void setStart_accuracy(float start_accuracy) {
        this.start_accuracy = start_accuracy;
    }

    public float getStart_speed() {
        return this.start_speed;
    }

    public void setStart_speed(float start_speed) {
        this.start_speed = start_speed;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public double getEnd_latitude() {
        return this.end_latitude;
    }

    public void setEnd_latitude(double end_latitude) {
        this.end_latitude = end_latitude;
    }

    public double getEnd_longitude() {
        return this.end_longitude;
    }

    public void setEnd_longitude(double end_longitude) {
        this.end_longitude = end_longitude;
    }

    public double getEnd_altitude() {
        return this.end_altitude;
    }

    public void setEnd_altitude(double end_altitude) {
        this.end_altitude = end_altitude;
    }

    public float getEnd_accuracy() {
        return this.end_accuracy;
    }

    public void setEnd_accuracy(float end_accuracy) {
        this.end_accuracy = end_accuracy;
    }

    public float getEnd_speed() {
        return this.end_speed;
    }

    public void setEnd_speed(float end_speed) {
        this.end_speed = end_speed;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getLast_fix_time() {
        return this.last_fix_time;
    }

    public void setLast_fix_time(long last_fix_time) {
        this.last_fix_time = last_fix_time;
    }

    public double getLast_step_dst_latitude() {
        return this.last_step_dst_latitude;
    }

    public void setLast_step_dst_latitude(double last_step_dst_latitude) {
        this.last_step_dst_latitude = last_step_dst_latitude;
    }

    public double getLast_step_dst_longitude() {
        return this.last_step_dst_longitude;
    }

    public void setLast_step_dst_longitude(double last_step_dst_longitude) {
        this.last_step_dst_longitude = last_step_dst_longitude;
    }

    public float getLast_step_dst_accuracy() {
        return this.last_step_dst_accuracy;
    }

    public void setLast_step_dst_accuracy(float last_step_dst_accuracy) {
        this.last_step_dst_accuracy = last_step_dst_accuracy;
    }

    public double getLast_step_alittude_altitude() {
        return this.last_step_alittude_altitude;
    }

    public void setLast_step_alittude_altitude(double last_step_alittude_altitude) {
        this.last_step_alittude_altitude = last_step_alittude_altitude;
    }

    public float getLast_step_alittude_accuracy() {
        return this.last_step_alittude_accuracy;
    }

    public void setLast_step_alittude_accuracy(float last_step_alittude_accuracy) {
        this.last_step_alittude_accuracy = last_step_alittude_accuracy;
    }

    public double getMin_latitude() {
        return this.min_latitude;
    }

    public void setMin_latitude(double min_latitude) {
        this.min_latitude = min_latitude;
    }

    public double getMin_longitude() {
        return this.min_longitude;
    }

    public void setMin_longitude(double min_longitude) {
        this.min_longitude = min_longitude;
    }

    public double getMax_latitude() {
        return this.max_latitude;
    }

    public void setMax_latitude(double max_latitude) {
        this.max_latitude = max_latitude;
    }

    public double getMax_longitude() {
        return this.max_longitude;
    }

    public void setMax_longitude(double max_longitude) {
        this.max_longitude = max_longitude;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration_moving() {
        return this.duration_moving;
    }

    public void setDuration_moving(long duration_moving) {
        this.duration_moving = duration_moving;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance_in_progress() {
        return this.distance_in_progress;
    }

    public void setDistance_in_progress(float distance_in_progress) {
        this.distance_in_progress = distance_in_progress;
    }

    public long getDistance_last_altitude() {
        return this.distance_last_altitude;
    }

    public void setDistance_last_altitude(long distance_last_altitude) {
        this.distance_last_altitude = distance_last_altitude;
    }

    public double getAltitude_up() {
        return this.altitude_up;
    }

    public void setAltitude_up(double altitude_up) {
        this.altitude_up = altitude_up;
    }

    public double getAltitude_down() {
        return this.altitude_down;
    }

    public void setAltitude_down(double altitude_down) {
        this.altitude_down = altitude_down;
    }

    public double getAltitude_in_progress() {
        return this.altitude_in_progress;
    }

    public void setAltitude_in_progress(double altitude_in_progress) {
        this.altitude_in_progress = altitude_in_progress;
    }

    public float getSpeed_max() {
        return this.speed_max;
    }

    public void setSpeed_max(float speed_max) {
        this.speed_max = speed_max;
    }

    public float getSpeed_average() {
        return this.speed_average;
    }

    public void setSpeed_average(float speed_average) {
        this.speed_average = speed_average;
    }

    public float getSpeed_average_moving() {
        return this.speed_average_moving;
    }

    public void setSpeed_average_moving(float speed_average_moving) {
        this.speed_average_moving = speed_average_moving;
    }

    public long getNumber_of_locations() {
        return this.number_of_locations;
    }

    public void setNumber_of_locations(long number_of_locations) {
        this.number_of_locations = number_of_locations;
    }

    public long getNumber_of_placemarks() {
        return this.number_of_placemarks;
    }

    public void setNumber_of_placemarks(long number_of_placemarks) {
        this.number_of_placemarks = number_of_placemarks;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValidmap() {
        return this.validmap;
    }

    public void setValidmap(int validmap) {
        this.validmap = validmap;
    }

}


