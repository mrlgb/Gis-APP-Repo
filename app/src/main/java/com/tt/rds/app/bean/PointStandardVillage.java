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
public class PointStandardVillage {

    @Id(autoincrement = true)
    private Long PStandardVillageId;
    @NotNull
    @Index(unique = true)
    private String name="";//名称
    @NotNull
    private String code="";//编码

    private int population;//人口数
    private int villages;//自然村数量

    private int arriveStatus;//通达现状
    private int arriveLocation;//通达位置
    private int arriveDirection;//通达方向
    private int arriveLevel;//通达行政等级
    private String arrivePathName;//通达线路名称
    private int arrivePathCode;//通达线路编号


    private String remark="";//备注
    @NotNull
    private long userId; //用户

    private Long ttPointId;//外键关联  -点id
    @ToOne(joinProperty = "ttPointId")
    private TtPoint ttPoint;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//指定与之关联的其他类的id
    private List<Picture> pictures;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1023764520)
    private transient PointStandardVillageDao myDao;

    @Generated(hash = 321445609)
    public PointStandardVillage(Long PStandardVillageId, @NotNull String name,
            @NotNull String code, int population, int villages, int arriveStatus,
            int arriveLocation, int arriveDirection, int arriveLevel,
            String arrivePathName, int arrivePathCode, String remark, long userId,
            Long ttPointId) {
        this.PStandardVillageId = PStandardVillageId;
        this.name = name;
        this.code = code;
        this.population = population;
        this.villages = villages;
        this.arriveStatus = arriveStatus;
        this.arriveLocation = arriveLocation;
        this.arriveDirection = arriveDirection;
        this.arriveLevel = arriveLevel;
        this.arrivePathName = arrivePathName;
        this.arrivePathCode = arrivePathCode;
        this.remark = remark;
        this.userId = userId;
        this.ttPointId = ttPointId;
    }

    @Generated(hash = 355826265)
    public PointStandardVillage() {
    }

    public Long getPStandardVillageId() {
        return this.PStandardVillageId;
    }

    public void setPStandardVillageId(Long PStandardVillageId) {
        this.PStandardVillageId = PStandardVillageId;
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

    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getVillages() {
        return this.villages;
    }

    public void setVillages(int villages) {
        this.villages = villages;
    }

    public int getArriveStatus() {
        return this.arriveStatus;
    }

    public void setArriveStatus(int arriveStatus) {
        this.arriveStatus = arriveStatus;
    }

    public int getArriveLocation() {
        return this.arriveLocation;
    }

    public void setArriveLocation(int arriveLocation) {
        this.arriveLocation = arriveLocation;
    }

    public int getArriveDirection() {
        return this.arriveDirection;
    }

    public void setArriveDirection(int arriveDirection) {
        this.arriveDirection = arriveDirection;
    }

    public int getArriveLevel() {
        return this.arriveLevel;
    }

    public void setArriveLevel(int arriveLevel) {
        this.arriveLevel = arriveLevel;
    }

    public String getArrivePathName() {
        return this.arrivePathName;
    }

    public void setArrivePathName(String arrivePathName) {
        this.arrivePathName = arrivePathName;
    }

    public int getArrivePathCode() {
        return this.arrivePathCode;
    }

    public void setArrivePathCode(int arrivePathCode) {
        this.arrivePathCode = arrivePathCode;
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

    public Long getTtPointId() {
        return this.ttPointId;
    }

    public void setTtPointId(Long ttPointId) {
        this.ttPointId = ttPointId;
    }

    @Generated(hash = 2065984785)
    private transient Long ttPoint__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 304307901)
    public TtPoint getTtPoint() {
        Long __key = this.ttPointId;
        if (ttPoint__resolvedKey == null || !ttPoint__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TtPointDao targetDao = daoSession.getTtPointDao();
            TtPoint ttPointNew = targetDao.load(__key);
            synchronized (this) {
                ttPoint = ttPointNew;
                ttPoint__resolvedKey = __key;
            }
        }
        return ttPoint;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 40396660)
    public void setTtPoint(TtPoint ttPoint) {
        synchronized (this) {
            this.ttPoint = ttPoint;
            ttPointId = ttPoint == null ? null : ttPoint.getTtPointId();
            ttPoint__resolvedKey = ttPointId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1537227130)
    public List<Picture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            List<Picture> picturesNew = targetDao
                    ._queryPointStandardVillage_Pictures(PStandardVillageId);
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
    @Generated(hash = 784635955)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPointStandardVillageDao() : null;
    }

}
