package com.tt.rds.app.bean;

/**
 * Created by Alpha Dog on 2017/10/4.
 */

public class PointBean {
    private String UserID;
    private String PointCode;
    private String ImageCode;
    private String Name;
    private String Code;
    private int    UpLoadFlag;

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

    public String getImageCode() {
        return ImageCode;
    }

    public void setImageCode(String imageCode) {
        ImageCode = imageCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getUpLoadFlag() {
        return UpLoadFlag;
    }

    public void setUpLoadFlag(int upLoadFlag) {
        UpLoadFlag = upLoadFlag;
    }
}
