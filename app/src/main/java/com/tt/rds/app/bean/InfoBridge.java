package com.tt.rds.app.bean;

/**
 * Created by lgb on 2017/9/18.
 */

public class InfoBridge {
    private String name;//*
    private String NoID;//*
    private String LineName;
    private String LineNo;
    private String LineSpliterNo;

    private int SSXZ;//*
    private int SJFZ;//*
    private int AKJF;//*
    private int ACLNX;//*
    private boolean IsDanger;//*
    private int KJKS;//*
    private double Length;

    //
    private String mangerName;//*
    private double Latitude;//*
    private double Longitude;//*
    private String Collector;
    private String Remarks;

    public InfoBridge() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoID() {
        return NoID;
    }

    public void setNoID(String noID) {
        NoID = noID;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public String getLineNo() {
        return LineNo;
    }

    public void setLineNo(String lineNo) {
        LineNo = lineNo;
    }

    public String getLineSpliterNo() {
        return LineSpliterNo;
    }

    public void setLineSpliterNo(String lineSpliterNo) {
        LineSpliterNo = lineSpliterNo;
    }

    public int getSSXZ() {
        return SSXZ;
    }

    public void setSSXZ(int SSXZ) {
        this.SSXZ = SSXZ;
    }

    public int getSJFZ() {
        return SJFZ;
    }

    public void setSJFZ(int SJFZ) {
        this.SJFZ = SJFZ;
    }

    public int getAKJF() {
        return AKJF;
    }

    public void setAKJF(int AKJF) {
        this.AKJF = AKJF;
    }

    public int getACLNX() {
        return ACLNX;
    }

    public void setACLNX(int ACLNX) {
        this.ACLNX = ACLNX;
    }

    public boolean isDanger() {
        return IsDanger;
    }

    public void setDanger(boolean danger) {
        IsDanger = danger;
    }

    public int getKJKS() {
        return KJKS;
    }

    public void setKJKS(int KJKS) {
        this.KJKS = KJKS;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public String getMangerName() {
        return mangerName;
    }

    public void setMangerName(String mangerName) {
        this.mangerName = mangerName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCollector() {
        return Collector;
    }

    public void setCollector(String collector) {
        Collector = collector;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
