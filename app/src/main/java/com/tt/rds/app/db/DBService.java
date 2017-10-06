package com.tt.rds.app.db;

import android.content.Context;

import com.tt.rds.app.app.Common;
import com.tt.rds.app.bean.DaoMaster;
import com.tt.rds.app.bean.DaoSession;
import com.tt.rds.app.bean.PictureDao;
import com.tt.rds.app.bean.PointBridge;
import com.tt.rds.app.bean.PointBridgeDao;
import com.tt.rds.app.bean.PointCulvertDao;
import com.tt.rds.app.bean.PointFerryDao;
import com.tt.rds.app.bean.PointMarkerDao;
import com.tt.rds.app.bean.PointSchoolDao;
import com.tt.rds.app.bean.PointSign;
import com.tt.rds.app.bean.PointSignDao;
import com.tt.rds.app.bean.PointStandardVillage;
import com.tt.rds.app.bean.PointStandardVillageDao;
import com.tt.rds.app.bean.PointTown;
import com.tt.rds.app.bean.PointTownDao;
import com.tt.rds.app.bean.PointTunnel;
import com.tt.rds.app.bean.PointTunnelDao;
import com.tt.rds.app.bean.PointTypeDao;
import com.tt.rds.app.bean.PointVillage;
import com.tt.rds.app.bean.PointVillageDao;
import com.tt.rds.app.bean.TtPointDao;
import com.tt.rds.app.bean.UserDao;
import com.tt.rds.app.bean.UserPointTypeDao;

/**
 * Created by guobinli on 2017/10/5.
 */

public class DBService {
    private static final String DB_NAME = Common.DATABASE_NAME;
    private DaoSession daoSession;
    private static DBService mInstance = null;

    /**
     * 获取DaoFactory的实例
     *
     * @return
     */
    public static DBService getInstance() {
        if (mInstance == null) {
            synchronized (DBService.class) {
                if (mInstance == null) {
                    mInstance = new DBService();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
    }

    public UserDao getUserDao(){
        return daoSession.getUserDao();
    }

    public UserPointTypeDao getUserPointTypeDao(){
        return daoSession.getUserPointTypeDao();
    }

    public PointTypeDao getPointTypeDao(){
        return daoSession.getPointTypeDao();
    }

    public TtPointDao getTtPointDao(){
        return daoSession.getTtPointDao();
    }

    public PictureDao getPictureDao(){
        return daoSession.getPictureDao();
    }

    public PointMarkerDao getPointMarkerDao(){
        return daoSession.getPointMarkerDao();
    }

    public PointBridgeDao getPointBridgeDao(){
        return daoSession.getPointBridgeDao();
    }

    public PointCulvertDao getPointCulvertDao(){
        return daoSession.getPointCulvertDao();
    }

    public PointFerryDao getPointFerryDao(){
        return daoSession.getPointFerryDao();
    }

    public PointSchoolDao getPointSchoolDao(){
        return daoSession.getPointSchoolDao();
    }

    public PointSignDao getPointSignDao(){
        return daoSession.getPointSignDao();
    }

    public PointStandardVillageDao getPointStandardVillageDao(){
        return daoSession.getPointStandardVillageDao();
    }

    public PointTownDao getPointTownDao(){
        return daoSession.getPointTownDao();
    }

    public PointTunnelDao getPointTunnelDao(){
        return daoSession.getPointTunnelDao();
    }

    public PointVillageDao getPointVillageDao(){
        return daoSession.getPointVillageDao();
    }

}