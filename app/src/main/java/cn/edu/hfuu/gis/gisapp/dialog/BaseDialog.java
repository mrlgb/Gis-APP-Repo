package cn.edu.hfuu.gis.gisapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import butterknife.ButterKnife;
import cn.edu.hfuu.gis.gisapp.MainApplication;

public abstract class BaseDialog extends Dialog {
    protected Context context;
    protected MainApplication mainApplication;

    protected abstract int getLayoutResId();

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
        this.mainApplication = (MainApplication) context.getApplicationContext();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.mainApplication = (MainApplication) context.getApplicationContext();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getLayoutResId();
        if (layoutResId == 0) {
            dismiss();
            return;
        }
        setContentView(layoutResId);
        ButterKnife.bind(this);
        initDialog();
    }

    protected void initDialog() {
    }
}
