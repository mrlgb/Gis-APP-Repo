package com.tt.rds.app.db;

import android.content.Context;

import com.tt.rds.app.app.Common;
import com.tt.rds.app.bean.DaoMaster;
import com.tt.rds.app.bean.DaoSession;
import com.tt.rds.app.bean.PictureDao;
import com.tt.rds.app.bean.PointMarkerDao;
import com.tt.rds.app.bean.PointTypeDao;
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

    public PointTypeDao getPointTypeDao(){
        return daoSession.getPointTypeDao();
    }

    public UserPointTypeDao getUserPointTypeDao(){
        return daoSession.getUserPointTypeDao();
    }

    public TtPointDao getTtPointDao(){
        return daoSession.getTtPointDao();
    }

    public PointMarkerDao getPointMarkerDao(){
        return daoSession.getPointMarkerDao();
    }

    public PictureDao getPictureDao(){
        return daoSession.getPictureDao();
    }

}