package com.tt.rds.app.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by guobinli on 2017/9/17.
 */

public class ConstantValue {
    //    public static String[] points_all = new String[]{
//            "桥梁",
//            "隧道",
//            "渡口",
//            "政界分界点",
//            "交通标志",
//            "公路收费站",
//            "治超站点",
//            "广告牌",
//            "道班"
//    };
//
//    public static String[] points_normal = new String[]{
//            "桥梁",
//            "隧道",
//            "渡口",
//            "政界分界点",
//            "交通标志",
//            "公路收费站"
//    };
    public static class DefaultDirectory {
        public final static String DEFAULT_PATH = PathUtils.getSmoothRootDirectory() + File.separator + "CollectionPoForDc";
        public final static String DIR_COMPRESS = DEFAULT_PATH + File.separator
                + "Compress" + File.separator;
        public final static String DIR_DATEBASE = DEFAULT_PATH + File.separator
                + "Database" + File.separator;
        public final static String DIR_LAYERS = DEFAULT_PATH + File.separator
                + "Layers";
        public final static String DIR_LOCATION = DEFAULT_PATH + File.separator
                + "Location" + File.separator;
        public final static String DIR_PICTURE = DEFAULT_PATH + File.separator
                + "Picture" + File.separator;
        public final static String DIR_VIDEO = DEFAULT_PATH + File.separator
                + "Video" + File.separator;

        public static boolean checkDIR(String dir) {
            // TODO Auto-generated method stub
            if (new File(dir).exists()) return true;
            else return false;
        }

        public static boolean checkSdExsit() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            } else
                return false;
        }

        public static void getSDcardState() {
        }
    }
}
