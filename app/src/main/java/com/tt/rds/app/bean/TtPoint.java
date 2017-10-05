package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtPoint {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;//* 名称
    private String code;//* 编号


    private Long pointTypeId;
    @ToOne(joinProperty = "pointTypeId")
    private PointType pointType;

    private String pathName; //路线名称
    private Long   pathCode;  //路线代码
    private String adminCode;  //行政区划

    private  double lat;  //经度
    private  double lon; //纬度
    private  double alt; //高度

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1468197283)
    private transient TtPointDao myDao;
    @Generated(hash = 1400091712)
    public TtPoint(Long id, @NotNull String name, String code, Long pointTypeId,
            String pathName, Long pathCode, String adminCode, double lat,
            double lon, double alt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.pointTypeId = pointTypeId;
        this.pathName = pathName;
        this.pathCode = pathCode;
        this.adminCode = adminCode;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }
    @Generated(hash = 1646122471)
    public TtPoint() {
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
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Long getPointTypeId() {
        return this.pointTypeId;
    }
    public void setPointTypeId(Long pointTypeId) {
        this.pointTypeId = pointTypeId;
    }
    public String getPathName() {
        return this.pathName;
    }
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
    public Long getPathCode() {
        return this.pathCode;
    }
    public void setPathCode(Long pathCode) {
        this.pathCode = pathCode;
    }
    public String getAdminCode() {
        return this.adminCode;
    }
    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return this.lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getAlt() {
        return this.alt;
    }
    public void setAlt(double alt) {
        this.alt = alt;
    }
    @Generated(hash = 1801614840)
    private transient Long pointType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1284281190)
    public PointType getPointType() {
        Long __key = this.pointTypeId;
        if (pointType__resolvedKey == null
                || !pointType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PointTypeDao targetDao = daoSession.getPointTypeDao();
            PointType pointTypeNew = targetDao.load(__key);
            synchronized (this) {
                pointType = pointTypeNew;
                pointType__resolvedKey = __key;
            }
        }
        return pointType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2024105239)
    public void setPointType(PointType pointType) {
        synchronized (this) {
            this.pointType = pointType;
            pointTypeId = pointType == null ? null : pointType.getId();
            pointType__resolvedKey = pointTypeId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1630712071)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTtPointDao() : null;
    }



}
