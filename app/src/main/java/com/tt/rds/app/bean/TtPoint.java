package com.tt.rds.app.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtPoint {
    @Id(autoincrement = true)
    private Long ttPointId;//id
    @Index(unique = true)
    private String guid;//guid
    @NotNull
    private String name;//* 名称
    private String code;//* 编号

    private Long pTypeId;//关联 -点类型

    private String pathName; //路线名称
    private Long   pathCode;  //路线代码
    private String  sectionNo;//路段序列号
    @NotNull
    private String adminCode;  //行政区划
    @NotNull
    private  double lat;  //经度
    @NotNull
    private  double lon; //纬度
    @NotNull
    private  double alt; //高度

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//pic -id
    private List<Picture> pictures;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1468197283)
    private transient TtPointDao myDao;

    @Generated(hash = 1128426348)
    public TtPoint(Long ttPointId, String guid, @NotNull String name, String code,
            Long pTypeId, String pathName, Long pathCode, String sectionNo,
            @NotNull String adminCode, double lat, double lon, double alt) {
        this.ttPointId = ttPointId;
        this.guid = guid;
        this.name = name;
        this.code = code;
        this.pTypeId = pTypeId;
        this.pathName = pathName;
        this.pathCode = pathCode;
        this.sectionNo = sectionNo;
        this.adminCode = adminCode;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    @Generated(hash = 1646122471)
    public TtPoint() {
    }

    public Long getTtPointId() {
        return this.ttPointId;
    }

    public void setTtPointId(Long ttPointId) {
        this.ttPointId = ttPointId;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPTypeId() {
        return this.pTypeId;
    }

    public void setPTypeId(Long pTypeId) {
        this.pTypeId = pTypeId;
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

    public String getSectionNo() {
        return this.sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 392622972)
    public List<Picture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            List<Picture> picturesNew = targetDao._queryTtPoint_Pictures(ttPointId);
            synchronized (this) {
                if (pictures == null) {
                    pictures = picturesNew;
                }
            }
        }
        return pictures;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1035739203)
    public synchronized void resetPictures() {
        pictures = null;
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
