package com.tt.rds.app.bean;

/**
 * Created by lgb on 2017/9/18.
 * 1. 根据墨刀UI界面设计类
 * 2. 每个变量都要加注释，如果是必填项，请加*
 * 3. 类的名称：请与layout_xml文件名字对应起来
 * 4. 变量名命名规则：如果由多个单词组成，每个单词首字母大写，但首字母小写；或者由拼音首字母全大写组成
 *
 */

public class InfoBridge {
    private String name;//* 桥梁名称
    private String noId;//* 桥梁编号
    private String lineName; //路线名称
    private String lineNo;  //路线编码
    private String lineSpliterNo;   //路段序列号

    private int SSXZ;//*   所属乡镇
    private int SJFZ;//*   设计荷载
    private int AKJF;//*    按跨径分
    private int ACLNX;//*   按材料年限
    private boolean isDanger;//*    是否危桥
    private int KJKS;//*    跨径孔数
    private double length;  //  桥梁长度

    //
    private String mangerName;//*   管理单位名称
    private double latitude;//*     纬度
    private double longitude;//*    经度
    private String collector;//     采集人
    private String remarks;//       备注

    public InfoBridge() {
    }



    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getNoID() {
        return noId;
    }

    public void setNoID(String mNoID) {
        this.noId = mNoID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String mLineName) {
        this.lineName = mLineName;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String mLineNo) {
        this.lineNo = mLineNo;
    }

    public String getLineSpliterNo() {
        return lineSpliterNo;
    }

    public void setLineSpliterNo(String mLineSpliterNo) {
        this.lineSpliterNo = mLineSpliterNo;
    }

    public int getSSXZ() {
        return SSXZ;
    }

    public void setSSXZ(int mSSXZ) {
        this.SSXZ = mSSXZ;
    }

    public int getSJFZ() {
        return SJFZ;
    }

    public void setSJFZ(int mSJFZ) {
        this.SJFZ = mSJFZ;
    }

    public int getAKJF() {
        return AKJF;
    }

    public void setAKJF(int mAKJF) {
        this.AKJF = mAKJF;
    }

    public int getACLNX() {
        return ACLNX;
    }

    public void setACLNX(int mACLNX) {
        this.ACLNX = mACLNX;
    }

    public boolean isDanger() {
        return isDanger;
    }

    public void setDanger(boolean danger) {
        this.isDanger = danger;
    }

    public int getKJKS() {
        return KJKS;
    }

    public void setKJKS(int mKJKS) {
        this.KJKS = mKJKS;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double mLength) {
        this.length = mLength;
    }

    public String getMangerName() {
        return mangerName;
    }

    public void setMangerName(String mMangerName) {
        this.mangerName = mMangerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double mLatitude) {
        this.latitude = mLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double mLongitude) {
        this.longitude = mLongitude;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String mCollector) {
        this.collector = mCollector;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String mRemarks) {
        this.remarks = mRemarks;
    }
}
