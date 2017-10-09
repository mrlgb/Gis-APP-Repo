package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class TtPathEnd {

    @Id(autoincrement = true)
    private Long pathEndID;
    @NotNull
    @Index(unique = true)
    private String pathName="";//路线名称
    private int isDividePoint;//是否分界点
    private String dividePointType;//起点分界点类别
    private String mileage;//里程
    private String remark="";
    @NotNull
    private long userId; //用户

    private Long ttTrackId;//外键关联  -点id
    @ToOne(joinProperty = "ttTrackId")
    private TtTrack ttTrack;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//指定与之关联的其他类的id
    private List<Picture> pictures;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 764749088)
    private transient TtPathEndDao myDao;

    @Generated(hash = 1689426616)
    public TtPathEnd(Long pathEndID, @NotNull String pathName, int isDividePoint,
            String dividePointType, String mileage, String remark, long userId,
            Long ttTrackId) {
        this.pathEndID = pathEndID;
        this.pathName = pathName;
        this.isDividePoint = isDividePoint;
        this.dividePointType = dividePointType;
        this.mileage = mileage;
        this.remark = remark;
        this.userId = userId;
        this.ttTrackId = ttTrackId;
    }

    @Generated(hash = 1780850492)
    public TtPathEnd() {
    }

    public Long getPathEndID() {
        return this.pathEndID;
    }

    public void setPathEndID(Long pathEndID) {
        this.pathEndID = pathEndID;
    }

    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public int getIsDividePoint() {
        return this.isDividePoint;
    }

    public void setIsDividePoint(int isDividePoint) {
        this.isDividePoint = isDividePoint;
    }

    public String getDividePointType() {
        return this.dividePointType;
    }

    public void setDividePointType(String dividePointType) {
        this.dividePointType = dividePointType;
    }

    public String getMileage() {
        return this.mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getTtTrackId() {
        return this.ttTrackId;
    }

    public void setTtTrackId(Long ttTrackId) {
        this.ttTrackId = ttTrackId;
    }

    @Generated(hash = 1097723977)
    private transient Long ttTrack__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1805936611)
    public TtTrack getTtTrack() {
        Long __key = this.ttTrackId;
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
    @Generated(hash = 868975773)
    public void setTtTrack(TtTrack ttTrack) {
        synchronized (this) {
            this.ttTrack = ttTrack;
            ttTrackId = ttTrack == null ? null : ttTrack.getTrackId();
            ttTrack__resolvedKey = ttTrackId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 565503397)
    public List<Picture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            List<Picture> picturesNew = targetDao
                    ._queryTtPathEnd_Pictures(pathEndID);
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
    @Generated(hash = 1160421591)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTtPathEndDao() : null;
    }
}
