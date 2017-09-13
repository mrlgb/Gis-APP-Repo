package com.tt.rds.app.net;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.tt.rds.app.service.ProgressCancelListener;
import com.tt.rds.app.dialog.LoadingDialog;


public class ProgressDialogHandler extends Handler {
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    public static final int SHOW_PROGRESS_DIALOG = 1;
    private boolean cancelable;
    private Context context;
    private LoadingDialog loadingDialog;
//    private PromptDialog promptDialog ;
    private Activity activity;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }
    public ProgressDialogHandler(Activity activity, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        this.activity=activity;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(String tips) {
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this.context);
            this.loadingDialog.setCancelable(this.cancelable);
            if (this.cancelable) {
                this.loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        ProgressDialogHandler.this.mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!this.loadingDialog.isShowing()) {
                this.loadingDialog.show();
            }
        }
//        if (this.promptDialog==null&&cancelable){
//            this.promptDialog=new PromptDialog(activity);
//            promptDialog.showLoading(tips);
//        }
    }

    private void dismissProgressDialog() {
        if (this.loadingDialog != null) {
            this.loadingDialog.dismiss();
            this.loadingDialog = null;
        }
//        if (this.promptDialog!=null){
//
//            promptDialog.dismiss();
//            promptDialog=null;
//        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                initProgressDialog(msg.obj == null ? "加载中....." : msg.obj.toString());
                return;
            case 2:
                dismissProgressDialog();
                return;
            default:
                return;
        }
    }
}
