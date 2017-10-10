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
public class PointCulvert {

    @Id(autoincrement = true)
    private Long pCulvertId;
    @NotNull
    private String name="";//名称
    @NotNull
    private String code="";//编码
    private String  centerMarkNo;//中心桩号
    private String span;//跨径
    private String height;//净高
    private String category;//类型
    private String buildType;//建设性质

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
    @Generated(hash = 685078524)
    private transient PointCulvertDao myDao;
    @Generated(hash = 1596235249)
    public PointCulvert(Long pCulvertId, @NotNull String name, @NotNull String code,
            String centerMarkNo, String span, String height, String category,
            String buildType, String remark, long userId, Long ttPointId) {
        this.pCulvertId = pCulvertId;
        this.name = name;
        this.code = code;
        this.centerMarkNo = centerMarkNo;
        this.span = span;
        this.height = height;
        this.category = category;
        this.buildType = buildType;
        this.remark = remark;
        this.userId = userId;
        this.ttPointId = ttPointId;
    }
    @Generated(hash = 1683587651)
    public PointCulvert() {
    }
    public Long getPCulvertId() {
        return this.pCulvertId;
    }
    public void setPCulvertId(Long pCulvertId) {
        this.pCulvertId = pCulvertId;
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
    public String getCenterMarkNo() {
        return this.centerMarkNo;
    }
    public void setCenterMarkNo(String centerMarkNo) {
        this.centerMarkNo = centerMarkNo;
    }
    public String getSpan() {
        return this.span;
    }
    public void setSpan(String span) {
        this.span = span;
    }
    public String getHeight() {
        return this.height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBuildType() {
        return this.buildType;
    }
    public void setBuildType(String buildType) {
        this.buildType = buildType;
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
    @Generated(hash = 1249309707)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPointCulvertDao() : null;
    }


}
