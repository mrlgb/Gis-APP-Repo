package cn.edu.hfuu.gis.gisapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenchen on 2017/8/4.
 */

public class SubmitQusetionnaire {
    @SerializedName("code")
    private int code;
    @SerializedName("result")
    private boolean result ;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
