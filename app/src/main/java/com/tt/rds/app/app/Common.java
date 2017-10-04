package com.tt.rds.app.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;

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
