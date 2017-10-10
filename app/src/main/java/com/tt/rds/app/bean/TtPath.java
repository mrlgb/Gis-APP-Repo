package com.tt.rds.app.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class TtPath {

    @Id(autoincrement = true)
    private Long ttPathID;
    @NotNull
    @Index(unique = true)
    private String guid;//guid
    @NotNull
    private String name;//* 名称
    private String code;//* 编号
   //---------------------------
    private String startName="";//起点名称
    private String buildStatus;//建设情况
    private String projectOrg;//项目来源
    private String arrivalType;//畅通类别
    private int isStartDividePoint;//是否分界点
    private String startDividePointType;//起点分界点类别
    private Date buildYear;
    private Date finishDate;
    private Date refineDate;

    //---------------------------
    private int isEndDividePoint;//是否分界点
    private String endDividePointType;//起点分界点类别
    private String mileage;//里程


    //---------------------------
    private String remark="";
    @NotNull
    private long userId; //用户

    @NotNull
    @ToMany(referencedJoinProperty ="ttSectionId" )//track ids
    private List<TtSection> ttSections;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//pic -ids
    private List<Picture> pictures;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1614242185)
    private transient TtPathDao myDao;

    @Generated(hash = 2007264464)
    public TtPath(Long ttPathID, @NotNull String guid, @NotNull String name,
            String code, String startName, String buildStatus, String projectOrg,
            String arrivalType, int isStartDividePoint, String startDividePointType,
            Date buildYear, Date finishDate, Date refineDate, int isEndDividePoint,
            String endDividePointType, String mileage, String remark, long userId) {
        this.ttPathID = ttPathID;
        this.guid = guid;
        this.name = name;
        this.code = code;
        this.startName = startName;
        this.buildStatus = buildStatus;
        this.projectOrg = projectOrg;
        this.arrivalType = arrivalType;
        this.isStartDividePoint = isStartDividePoint;
        this.startDividePointType = startDividePointType;
        this.buildYear = buildYear;
        this.finishDate = finishDate;
        this.refineDate = refineDate;
        this.isEndDividePoint = isEndDividePoint;
        this.endDividePointType = endDividePointType;
        this.mileage = mileage;
        this.remark = remark;
        this.userId = userId;
    }

    @Generated(hash = 339702563)
    public TtPath() {
    }

    public Long getTtPathID() {
        return this.ttPathID;
    }

    public void setTtPathID(Long ttPathID) {
        this.ttPathID = ttPathID;
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

    public String getStartName() {
        return this.startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getBuildStatus() {
        return this.buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getProjectOrg() {
        return this.projectOrg;
    }

    public void setProjectOrg(String projectOrg) {
        this.projectOrg = projectOrg;
    }

    public String getArrivalType() {
        return this.arrivalType;
    }

    public void setArrivalType(String arrivalType) {
        this.arrivalType = arrivalType;
    }

    public int getIsStartDividePoint() {
        return this.isStartDividePoint;
    }

    public void setIsStartDividePoint(int isStartDividePoint) {
        this.isStartDividePoint = isStartDividePoint;
    }

    public String getStartDividePointType() {
        return this.startDividePointType;
    }

    public void setStartDividePointType(String startDividePointType) {
        this.startDividePointType = startDividePointType;
    }

    public Date getBuildYear() {
        return this.buildYear;
    }

    public void setBuildYear(Date buildYear) {
        this.buildYear = buildYear;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getRefineDate() {
        return this.refineDate;
    }

    public void setRefineDate(Date refineDate) {
        this.refineDate = refineDate;
    }

    public int getIsEndDividePoint() {
        return this.isEndDividePoint;
    }

    public void setIsEndDividePoint(int isEndDividePoint) {
        this.isEndDividePoint = isEndDividePoint;
    }

    public String getEndDividePointType() {
        return this.endDividePointType;
    }

    public void setEndDividePointType(String endDividePointType) {
        this.endDividePointType = endDividePointType;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2094560411)
    public List<TtSection> getTtSections() {
        if (ttSections == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TtSectionDao targetDao = daoSession.getTtSectionDao();
            List<TtSection> ttSectionsNew = targetDao
                    ._queryTtPath_TtSections(ttPathID);
            synchronized (this) {
                if (ttSections == null) {
                    ttSections = ttSectionsNew;
                }
            }
        }
        return ttSections;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 384740658)
    public synchronized void resetTtSections() {
        ttSections = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1099387551)
    public List<Picture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            List<Picture> picturesNew = targetDao._queryTtPath_Pictures(ttPathID);
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
    @Generated(hash = 1985693475)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTtPathDao() : null;
    }

}
