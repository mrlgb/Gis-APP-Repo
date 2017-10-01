package com.tt.rds.app.bean;

/**
 * Created by Xinxin on 2017/9/30.
 */

public class PointType {
    private String name;
    private int usually;   // 1: 常用

    public String getName(){
        return this.name;
    }

    public void setName(String mName){
        this.name=mName;
    }

    public int getUsually(){
        return this.usually;
    }

    public void setUsually(int musually){
        this.usually=musually;
    }


}
