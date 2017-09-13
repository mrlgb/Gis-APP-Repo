package com.tt.rds.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tt.rds.app.bean.User;
import com.tt.rds.app.net.HttpMethods;
import com.tt.rds.app.activity.ActivityTaskManager;

public class MainApplication extends Application {
    private ActivityTaskManager activityTaskManager;
    private HttpMethods httpMethods;

    private User user;
    private boolean useFilter = false;
    private String[] points_all = new String[]{
            "桥梁",
            "隧道",
            "渡口 ",
            "政界分界点 ",
            "交通标志",
            "公路收费站",
            "治超站点",
            "广告牌",
            "道班"
    };

    private String[] points_normal = new String[]{
            "桥梁",
            "隧道",
            "渡口 ",
            "政界分界点 ",
            "交通标志",
            "公路收费站"
    };

    private List<String> collectPointList;

    public List<String> getCollectPointList() {
        return collectPointList;
    }

    public void setCollectPointList(List<String> collectPointList) {
        this.collectPointList = collectPointList;
    }

    public void switchCollectPointList(boolean isAll){
        this.collectPointList.clear();
        if(isAll){
            for(int i = 0;i < points_normal.length; i ++){
                collectPointList.add(i,points_normal[i]);
            }
        }
        else {
            for(int i = 0; i < points_all.length; i ++){
                collectPointList.add(i, points_all[i]);
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.activityTaskManager = ActivityTaskManager.getInstance();
        this.httpMethods = HttpMethods.getInstance();
//        CrashHandler.getInstance().init(this);

        collectPointList = new ArrayList<String>(Arrays.asList(points_normal));

    }

    public void managerActivity(Activity activity, boolean isFlag) {
        if (activity == null) {
            this.activityTaskManager.closeAllActivity();
        } else if (isFlag) {
            this.activityTaskManager.putActivity(activity.getClass().getSimpleName(), activity);
        } else if (!isFlag) {
            this.activityTaskManager.removeActivity(activity.getClass().getSimpleName());
        }
    }

    public HttpMethods getHttpMethods() {
        return this.httpMethods;
    }

    public User getUser() {
        return this.user;
    }

    public boolean setUser(User user) {
        if (user == null) {
            return false;
        }
        this.user = user;
        return true;
    }

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }
}
