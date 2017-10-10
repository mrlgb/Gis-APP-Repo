package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class PointTunnel {

    @Id(autoincrement = true)
    private Long pTunnelId;
    @NotNull
    private String name="";//名称
    @NotNull
    private String code="";//编码
    private String  manageOrg;//管养单位
    private double length;
    private double width;
    private double heigth;
    private int buidYear;


    private String remark="";
    @NotNull
    private long userId; //用户

    private Long ttPointId;//外键关联  -点id
    @ToOne(joinProperty = "ttPointId")
    private TtPoint ttPoint;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1586664661)
    private transient PointTunnelDao myDao;
    @Generated(hash = 326645117)
    public PointTunnel(Long pTunnelId, @NotNull String name, @NotNull String code,
            String manageOrg, double length, double width, double heigth,
            int buidYear, String remark, long userId, Long ttPointId) {
        this.pTunnelId = pTunnelId;
        this.name = name;
        this.code = code;
        this.manageOrg = manageOrg;
        this.length = length;
        this.width = width;
        this.heigth = heigth;
        this.buidYear = buidYear;
        this.remark = remark;
        this.userId = userId;
        this.ttPointId = ttPointId;
    }
    @Generated(hash = 1844957327)
    public PointTunnel() {
    }
    public Long getPTunnelId() {
        return this.pTunnelId;
    }
    public void setPTunnelId(Long pTunnelId) {
        this.pTunnelId = pTunnelId;
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
    public String getManageOrg() {
        return this.manageOrg;
    }
    public void setManageOrg(String manageOrg) {
        this.manageOrg = manageOrg;
    }
    public double getLength() {
        return this.length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getWidth() {
        return this.width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeigth() {
        return this.heigth;
    }
    public void setHeigth(double heigth) {
        this.heigth = heigth;
    }
    public int getBuidYear() {
        return this.buidYear;
    }
    public void setBuidYear(int buidYear) {
        this.buidYear = buidYear;
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
    @Generated(hash = 1284379037)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPointTunnelDao() : null;
    }


}
