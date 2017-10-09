package com.tt.rds.app.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrlgb on 2017/10/3.
 */

public class Common {

    public static final String DATABASE_NAME = "GPS-APP";
    public static final String login_preference_name="loginstate";
    public static final String current_user="currentuser";
    public static final String login_state="login";
    public static final String HEADER_PATH = Environment.getExternalStorageDirectory().getPath()+"/GPSLogger/Headers/";


    public static final int USER_STATE_CHANGE=0x0201;
    public static final int USER_STATE_CHANGE_BACK=0x0202;
    public static final int MODIFY_INFO=0x0101;
    public static final int MODIFY_ANONYMOUS=0x0102;
    public static final int MODIFY_PHONE=0x0103;
    public static final int MODIFY_EMAIL=0x0104;
    public static final int CAMERA_CAPTURE=0x0105;
    public static final int GALLERY_SELECT=0x0106;
    public static final int MODIFY_ADDR=0x0107;
    public static final int MODIFY_SIGNATURE=0x0108;
    public static final int MODIFY_CANCEL=0x0111;
    public static final String[] points_all = new String[]{
            "桥梁",
            "隧道",
            "渡口",
            "涵洞",
            "乡镇",
            "建制村",
            "自然村",
            "学校",
            "标志标牌",
            "标志点"
    };
    public static final String[] points_all_db = new String[]{
            "bridge",
            "tunnel",
            "ferry",
            "culvert",
            "country",
            "jzvillage",
            "naturevillage",
            "school",
            "logo",
            "mark"
    };


    public static int[] points_type = new int[]{
            1,1,1,1,1,0,0,0,1,1,

    };

    public static String myCaptureFile;

    public static class ExceptionHandler {
        private static final String TAG = "ExceptionHandler";
        private static DateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        /**
         * 收集设备参数信息
         *
         * @param ctx
         */
        private static Map<String, String> collectDeviceInfo(Context ctx) {
            Map<String, String> infos = new HashMap<String, String>();
            try {
                PackageManager pm = ctx.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                        PackageManager.GET_ACTIVITIES);
                if (pi != null) {
                    String versionName = pi.versionName == null ? "null"
                            : pi.versionName;
                    String versionCode = pi.versionCode + "";
                    infos.put("versionName", versionName);
                    infos.put("versionCode", versionCode);
                }
            } catch (PackageManager.NameNotFoundException e) {
//                LogUtils.e(e);
            }
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    infos.put(field.getName(), field.get(null).toString());
//                    LogUtils.d(field.getName() + " : " + field.get(null));
                } catch (Exception e) {
//                    LogUtils.e(e);
                }
            }
            return infos;
        }
    }

    // 获取系统当前时间
    public static String getCunrrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String cunrrentTime = format.format(new Date());
        return cunrrentTime;
    }

    // 获取系统当前时间
    public static String getCunrrentTime1() {
        Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String cunrrentTime = String.valueOf(year) + "-"
                + String.valueOf(month) + "-" + String.valueOf(date) + " "
                + String.format("%02d", hour) + ":"
                + String.format("%02d", minute);
        return cunrrentTime;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (lowerBound <= 3) {
            lowerBound = lowerBound + 1;
        }

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;

        }

    }
}
