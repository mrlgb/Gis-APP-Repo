package com.tt.rds.app.bean;

/**
 * Created by Xinxin on 2017/10/3.
 */

public class UserInfo {

    private String user="";
    private String password="";
    private String anonymous="";
    private String phone="";
    private String email="";
    private String gender="";
    private String address="";
    private String signature="";

    public String getUserName(){
        return this.user;
    }

    public void setUserName(String mName){
        this.user=mName;
    }

    public String getAnonymous(){
        return this.anonymous;
    }

    public void setAnonymous(String manonymous){
        this.anonymous=manonymous;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String mpassword){
        this.password=mpassword;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String mphone){
        this.phone=mphone;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String memail){
        this.email=memail;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String mgender){
        this.gender=mgender;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String maddress){
        this.address=maddress;
    }

    public String getSignature(){
        return this.signature;
    }

    public void setSignature(String msignature){
        this.signature=msignature;
    }


}
