package cn.edu.hfuu.gis.gisapp;

import android.app.Activity;
import android.app.Application;
import cn.edu.hfuu.gis.gisapp.activity.ActivityTaskManager;
import cn.edu.hfuu.gis.gisapp.bean.User;
import cn.edu.hfuu.gis.gisapp.net.HttpMethods;

public class MainApplication extends Application {
    private ActivityTaskManager activityTaskManager;
    private HttpMethods httpMethods;

    private User user;
    private boolean useFilter = false;

    @Override
    public void onCreate() {
        super.onCreate();
        this.activityTaskManager = ActivityTaskManager.getInstance();
        this.httpMethods = HttpMethods.getInstance();
//        CrashHandler.getInstance().init(this);
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
