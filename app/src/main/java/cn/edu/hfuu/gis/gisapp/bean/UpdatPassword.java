package cn.edu.hfuu.gis.gisapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenchen on 2017/8/3.
 */

public class UpdatPassword {
    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private boolean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}