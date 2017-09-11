import android.content.Context;
import android.os.Looper;
import android.util.Log;

import android.os.Process;

import cn.edu.hfuu.gis.gisapp.MainApplication;
import cn.edu.hfuu.gis.gisapp.util.ToastUtil;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String TAG = "CrashHandler";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (handleException(ex) || this.mDefaultHandler == null) {
            try {
                Thread.sleep(3000);
                ((MainApplication) this.mContext).managerActivity(null, true);
            } catch (InterruptedException e) {
                Log.e("info", "error : ", e);
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
            return;
        }
        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            public void run() {
                Looper.prepare();
                ToastUtil.showToast(CrashHandler.this.mContext, "exit" + ex.getMessage());
                Looper.loop();
            }
        }.start();
        return true;
    }
}