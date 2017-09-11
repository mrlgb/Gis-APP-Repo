package cn.edu.hfuu.gis.gisapp.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import cn.edu.hfuu.gis.gisapp.service.ProgressCancelListener;
import cn.edu.hfuu.gis.gisapp.service.SubscriberOnNextListener;
import cn.edu.hfuu.gis.gisapp.util.ToastUtil;
import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private Context context;
    private ProgressDialogHandler mProgressDialogHandler;
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private String tips;
     private Activity activity;
    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener,boolean cancelable) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
    }

    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener, String tips) {
        this(context, mSubscriberOnNextListener,true);
    }

    public ProgressSubscriber(Activity activity, SubscriberOnNextListener mSubscriberOnNextListener, boolean cancelable,String tips) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mProgressDialogHandler = new ProgressDialogHandler(activity, this, cancelable);
        this.tips = tips;
        this.activity=activity;
    }




    private void showProgressDialog() {
        if (this.mProgressDialogHandler != null) {
            this.mProgressDialogHandler.obtainMessage(1, this.tips).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (this.mProgressDialogHandler != null) {
            this.mProgressDialogHandler.obtainMessage(2).sendToTarget();
            this.mProgressDialogHandler = null;
        }
    }

    public void onStart() {
        showProgressDialog();
    }

    public void onCompleted() {
        dismissProgressDialog();
    }

    public void onError(Throwable e) {
       dismissProgressDialog();
        ToastUtil.showToast(activity, e.getMessage());
        Log.e("cwwww",e.getMessage());
        this.mSubscriberOnNextListener.onNext(null);
    }

    public void onNext(T t) {
        this.mSubscriberOnNextListener.onNext(t);
    }

    public void onCancelProgress() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }
}
