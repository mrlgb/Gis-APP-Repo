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
    private Long id;

    @NotNull
    private String name;//* 名称

    @NotNull
    private int usually;   // 1: 常用

    @Generated(hash = 1314763509)
    public PointType(Long id, @NotNull String name, int usually) {
        this.id = id;
        this.name = name;
        this.usually = usually;
    }

    @Generated(hash = 849107993)
    public PointType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
