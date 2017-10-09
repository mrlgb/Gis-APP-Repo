package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
/**
 * Created by Xinxin on 2017/9/30.
 */

@Entity
public class PointType {
    @Id(autoincrement = true)
    private Long pTypeId;

    @NotNull
    private String name;//* 名称

    @NotNull
    private int usually;   // 1: 常用

    @Generated(hash = 1155645018)
    public PointType(Long pTypeId, @NotNull String name, int usually) {
        this.pTypeId = pTypeId;
        this.name = name;
        this.usually = usually;
    }

    @Generated(hash = 849107993)
    public PointType() {
    }

    public Long getPTypeId() {
        return this.pTypeId;
    }

    public void setPTypeId(Long pTypeId) {
        this.pTypeId = pTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsually() {
        return this.usually;
    }

    public void setUsually(int usually) {
        this.usually = usually;
    }






}
