package com.tt.rds.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by guobinli on 2017/10/5.
 */

@Entity
public class TtSection {
    @Id(autoincrement = true)
    private Long ttSectionId;//id
    @Index(unique = true)
    private String guid;//guid
    @NotNull
    private String name;//* 名称
    private String code;//* 编号
    //---------------------------
    //路段开始点属性

    //---------------------------
    //路段结束点属性

    //---------------------------
    @NotNull
    @ToMany(referencedJoinProperty ="trackId" )//track ids
    private List<TtTrack> ttTracks;

    @NotNull
    @ToMany(referencedJoinProperty ="picId" )//pic -ids
    private List<Picture> pictures;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 178013295)
    private transient TtSectionDao myDao;

    @Generated(hash = 1714713065)
    public TtSection(Long ttSectionId, String guid, @NotNull String name,
            String code) {
        this.ttSectionId = ttSectionId;
        this.guid = guid;
        this.name = name;
        this.code = code;
    }

    @Generated(hash = 1407662733)
    public TtSection() {
    }

    public Long getTtSectionId() {
        return this.ttSectionId;
    }

    public void setTtSectionId(Long ttSectionId) {
        this.ttSectionId = ttSectionId;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 531587841)
    public List<TtTrack> getTtTracks() {
        if (ttTracks == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TtTrackDao targetDao = daoSession.getTtTrackDao();
            List<TtTrack> ttTracksNew = targetDao
                    ._queryTtSection_TtTracks(ttSectionId);
            synchronized (this) {
                if (ttTracks == null) {
                    ttTracks = ttTracksNew;
                }
            }
        }
        return ttTracks;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1802443791)
    public synchronized void resetTtTracks() {
        ttTracks = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1986783155)
    public List<Picture> getPictures() {
        if (pictures == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            List<Picture> picturesNew = targetDao
                    ._queryTtSection_Pictures(ttSectionId);
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
    @Generated(hash = 977609646)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTtSectionDao() : null;
    }


}
