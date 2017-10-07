package com.tt.rds.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import butterknife.ButterKnife;

import com.tt.rds.app.app.GPSApplication;

public abstract class BaseDialog extends Dialog {
    protected Context context;
    protected GPSApplication mainGPSApplication;

    protected abstract int getLayoutResId();

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
        this.mainGPSApplication = (GPSApplication) context.getApplicationContext();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.mainGPSApplication = (GPSApplication) context.getApplicationContext();
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
