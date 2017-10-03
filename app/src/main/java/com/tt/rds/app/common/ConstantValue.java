package com.tt.rds.app.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by guobinli on 2017/9/17.
 */

public class ConstantValue {
    public static final String login_preference_name="loginstate";
    public static final String current_user="currentuser";
    public static final String login_state="login";

    public static final int MODIFY_ANONYMOUS=0x0101;
    public static final int MODIFY_PHONE=0x0102;
    public static final int MODIFY_EMAIL=0x0103;
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
