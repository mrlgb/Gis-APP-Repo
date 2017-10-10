package com.tt.rds.app.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

/**
 * Created by Xinxin on 2017/10/3.
 */
@Entity
public class PointBridge {

    @Id(autoincrement = true)
    private Long pBridgeId;
    @NotNull
    private String name="";//名称
    @NotNull
    private String code="";//编码

    private String status;//桥梁状况
    private int  designLoading;//设计负载
    private int materialType;//材料类型
    private int spanType;//跨径类型
    private  int dangeable;//是否危桥
    private  String managerOrg;//管养单位
    private String length;//全长
    private String width;//全宽
    private String clearWidth;//净高
    private String height;//净高
    private String spanLength;//跨径总长
    private String spanCombo;//跨径组合
    private Date buildTime;//建成时间

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
    @Generated(hash = 30477662)
    private transient PointBridgeDao myDao;
    @Generated(hash = 106538168)
    public PointBridge(Long pBridgeId, @NotNull String name, @NotNull String code,
            String status, int designLoading, int materialType, int spanType,
            int dangeable, String managerOrg, String length, String width,
            String clearWidth, String height, String spanLength, String spanCombo,
            Date buildTime, String remark, long userId, Long ttPointId) {
        this.pBridgeId = pBridgeId;
        this.name = name;
        this.code = code;
        this.status = status;
        this.designLoading = designLoading;
        this.materialType = materialType;
        this.spanType = spanType;
        this.dangeable = dangeable;
        this.managerOrg = managerOrg;
        this.length = length;
        this.width = width;
        this.clearWidth = clearWidth;
        this.height = height;
        this.spanLength = spanLength;
        this.spanCombo = spanCombo;
        this.buildTime = buildTime;
        this.remark = remark;
        this.userId = userId;
        this.ttPointId = ttPointId;
    }
    @Generated(hash = 1535943127)
    public PointBridge() {
    }
    public Long getPBridgeId() {
        return this.pBridgeId;
    }
    public void setPBridgeId(Long pBridgeId) {
        this.pBridgeId = pBridgeId;
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
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getDesignLoading() {
        return this.designLoading;
    }
    public void setDesignLoading(int designLoading) {
        this.designLoading = designLoading;
    }
    public int getMaterialType() {
        return this.materialType;
    }
    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }
    public int getSpanType() {
        return this.spanType;
    }
    public void setSpanType(int spanType) {
        this.spanType = spanType;
    }
    public int getDangeable() {
        return this.dangeable;
    }
    public void setDangeable(int dangeable) {
        this.dangeable = dangeable;
    }
    public String getManagerOrg() {
        return this.managerOrg;
    }
    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }
    public String getLength() {
        return this.length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getWidth() {
        return this.width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public String getClearWidth() {
        return this.clearWidth;
    }
    public void setClearWidth(String clearWidth) {
        this.clearWidth = clearWidth;
    }
    public String getHeight() {
        return this.height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getSpanLength() {
        return this.spanLength;
    }
    public void setSpanLength(String spanLength) {
        this.spanLength = spanLength;
    }
    public String getSpanCombo() {
        return this.spanCombo;
    }
    public void setSpanCombo(String spanCombo) {
        this.spanCombo = spanCombo;
    }
    public Date getBuildTime() {
        return this.buildTime;
    }
    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
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
    @Generated(hash = 552839362)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPointBridgeDao() : null;
    }

}
