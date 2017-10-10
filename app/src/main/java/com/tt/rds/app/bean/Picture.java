package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class Picture {

    @Id(autoincrement = true)
    private Long picId;
    @Index(unique = true)
    private String guid;//guid
    @NotNull
    private String name;//name
    @NotNull
    private String path="";//路径
    private Date date;
    private int type;//照片类型0-路段开始点；1-路段结束点；2-路线开始点；3-路线结束点；4-点
    private long userId; //用户
    @Generated(hash = 982682998)
    public Picture(Long picId, String guid, @NotNull String name,
            @NotNull String path, Date date, int type, long userId) {
        this.picId = picId;
        this.guid = guid;
        this.name = name;
        this.path = path;
        this.date = date;
        this.type = type;
        this.userId = userId;
    }
    @Generated(hash = 1602548376)
    public Picture() {
    }
    public Long getPicId() {
        return this.picId;
    }
    public void setPicId(Long picId) {
        this.picId = picId;
    }
    public String getGuid() {
        return this.guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
