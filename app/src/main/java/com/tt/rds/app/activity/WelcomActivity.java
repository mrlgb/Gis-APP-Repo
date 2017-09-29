package com.tt.rds.app.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.tt.rds.app.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomActivity extends AppCompatActivity {

    ViewFlipper mViewFlipper;
    private List<View> mDotList;
    private int currentPos;
    private int oldPos;

    private static final int AUTO = 0x01;
    DisplayMetrics dm;
    int wd_width;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO:
                    if(currentPos==mDotList.size()-1){
                        startMainActivity();
                    }
                    else {
                        showNext();
                        sendAutoMsg();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        initViews();
    }

    private void initViews(){
        dm=getResources().getDisplayMetrics();
        wd_width=dm.widthPixels;

        oldPos=0;
        currentPos=0;

        mViewFlipper=(ViewFlipper)findViewById(R.id.welcome_flipper);

        mDotList=new ArrayList<>();
        mDotList.add(findViewById(R.id.welcom_dot1));
        mDotList.add(findViewById(R.id.welcom_dot2));
        mDotList.add(findViewById(R.id.welcom_dot3));
        //初始时，将第一个圆点设为被选中状态
        mDotList.get(currentPos).setBackgroundResource(R.drawable.dot_focused);
        sendAutoMsg();

    }

    private void showNext(){
        mViewFlipper.setInAnimation(this,R.anim.slide_in_right);
        mViewFlipper.setOutAnimation(this,R.anim.slide_out_left);
        mViewFlipper.showNext();
        setDotsBackgroud();
    }

    private void sendAutoMsg(){
        Message msgs = new Message();
        msgs.what = AUTO;
        mHandler.sendMessageDelayed(msgs, 2000);
    }

    private void setDotsBackgroud(){
        oldPos=currentPos;
        currentPos=mViewFlipper.getDisplayedChild();
        mDotList.get(oldPos).setBackgroundResource(R.drawable.dot_normal);
        mDotList.get(currentPos).setBackgroundResource(R.drawable.dot_focused);
    }

    public void onJumpToMain(View v){
        startMainActivity();
    }

    private void startMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
