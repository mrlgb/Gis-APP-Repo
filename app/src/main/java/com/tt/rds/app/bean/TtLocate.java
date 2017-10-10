package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtLocate {
    @Id(autoincrement = true)
    private Long ttLocateId;

    private String name;
    @NotNull
    private Long trackId;//* 路线ID
    @ToOne(joinProperty = "trackId")
    private TtTrack ttTrack;

    private int nr;
    private double latitude;
    private double longitude;
    private double altitude;
    private float speed;
    private float accuracy;
    private float bearing;
    private  long time;
    private  int number_of_satellites;
    private int type;
    private  int number_of_satellites_used_in_fix;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1946546202)
    private transient TtLocateDao myDao;
    @Generated(hash = 931093817)
    public TtLocate(Long ttLocateId, String name, @NotNull Long trackId, int nr,
            double latitude, double longitude, double altitude, float speed,
            float accuracy, float bearing, long time, int number_of_satellites,
            int type, int number_of_satellites_used_in_fix) {
        this.ttLocateId = ttLocateId;
        this.name = name;
        this.trackId = trackId;
        this.nr = nr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.accuracy = accuracy;
        this.bearing = bearing;
        this.time = time;
        this.number_of_satellites = number_of_satellites;
        this.type = type;
        this.number_of_satellites_used_in_fix = number_of_satellites_used_in_fix;
    }
    @Generated(hash = 1744326166)
    public TtLocate() {
    }
    public Long getTtLocateId() {
        return this.ttLocateId;
    }
    public void setTtLocateId(Long ttLocateId) {
        this.ttLocateId = ttLocateId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getTrackId() {
        return this.trackId;
    }
    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }
    public int getNr() {
        return this.nr;
    }
    public void setNr(int nr) {
        this.nr = nr;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getAltitude() {
        return this.altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public float getSpeed() {
        return this.speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getAccuracy() {
        return this.accuracy;
    }
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
    public float getBearing() {
        return this.bearing;
    }
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getNumber_of_satellites() {
        return this.number_of_satellites;
    }
    public void setNumber_of_satellites(int number_of_satellites) {
        this.number_of_satellites = number_of_satellites;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getNumber_of_satellites_used_in_fix() {
        return this.number_of_satellites_used_in_fix;
    }
    public void setNumber_of_satellites_used_in_fix(
            int number_of_satellites_used_in_fix) {
        this.number_of_satellites_used_in_fix = number_of_satellites_used_in_fix;
    }
    @Generated(hash = 1097723977)
    private transient Long ttTrack__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1393479101)
    public TtTrack getTtTrack() {
        Long __key = this.trackId;
        if (ttTrack__resolvedKey == null || !ttTrack__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TtTrackDao targetDao = daoSession.getTtTrackDao();
            TtTrack ttTrackNew = targetDao.load(__key);
            synchronized (this) {
                ttTrack = ttTrackNew;
                ttTrack__resolvedKey = __key;
            }
        }
        return ttTrack;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1485798809)
    public void setTtTrack(@NotNull TtTrack ttTrack) {
        if (ttTrack == null) {
            throw new DaoException(
                    "To-one property 'trackId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ttTrack = ttTrack;
            trackId = ttTrack.getTrackId();
            ttTrack__resolvedKey = trackId;
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
    @Generated(hash = 415320081)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTtLocateDao() : null;
    }


}
