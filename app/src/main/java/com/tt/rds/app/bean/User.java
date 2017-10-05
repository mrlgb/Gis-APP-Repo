package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String user="";
    private String password="";
    private String anonymous="";
    private String phone="";
    private String email="";
    private String gender="";
    private String address="";
    private String signature="";
    @Generated(hash = 834348130)
    public User(Long id, @NotNull String user, String password, String anonymous,
            String phone, String email, String gender, String address,
            String signature) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.anonymous = anonymous;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.signature = signature;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAnonymous() {
        return this.anonymous;
    }
    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSignature() {
        return this.signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
