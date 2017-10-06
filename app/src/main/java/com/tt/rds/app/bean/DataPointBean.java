package com.tt.rds.app.bean;

/**
 * Created by Alpha Dog on 2017/10/4.
 */

public class DataPointBean {
   private String   UserID;
    private String  PointCode;
    private  String PathName;
    private String  PathCode;
    private String  AdminCode;
    private String  Type;
    private Double  Lat;
    private Double  Alt;
    private Double  Lon;
    private String  Note;
    private String Picker;
    private String Time;
    private int UpLoadFlag;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPointCode() {
        return PointCode;
    }

    public void setPointCode(String pointCode) {
        PointCode = pointCode;
    }

    public String getPathName() {
        return PathName;
    }

    public void setPathName(String pathName) {
        PathName = pathName;
    }

    public String getPathCode() {
        return PathCode;
    }

    public void setPathCode(String pathCode) {
        PathCode = pathCode;
    }

    public String getAdminCode() {
        return AdminCode;
    }

    public void setAdminCode(String adminCode) {
        AdminCode = adminCode;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getAlt() {
        return Alt;
    }

    public void setAlt(Double alt) {
        Alt = alt;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getPicker() {
        return Picker;
    }

    public void setPicker(String picker) {
        Picker = picker;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getUpLoadFlag() {
        return UpLoadFlag;
    }

    public void setUpLoadFlag(int upLoadFlag) {
        UpLoadFlag = upLoadFlag;
    }



}
