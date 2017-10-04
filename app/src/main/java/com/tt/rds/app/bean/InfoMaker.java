package com.tt.rds.app.bean;

/**
 * Created by lgb on 2017/9/18.
 * 1. 根据墨刀UI界面设计类
 * 2. 每个变量都要加注释，如果是必填项，请加*
 * 3. 类的名称：请与layout_xml文件名字对应起来
 * 4. 变量名命名规则：如果由多个单词组成，每个单词首字母大写，但首字母小写；或者由拼音首字母全大写组成
 *
 */

public class InfoMaker {
    private String name;//* 名称
    private String code;//* 编号
    private String category; //类型
    private String adminCode;  //行政区划

    //
    private String mangerName;//*   管理单位名称
    private double latitude;//*     纬度
    private double longitude;//*    经度
    private String collector;//     采集人
    private String remarks;//       备注

    public InfoMaker() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getMangerName() {
        return mangerName;
    }

    public void setMangerName(String mangerName) {
        this.mangerName = mangerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
