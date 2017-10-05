package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tt.rds.app.R;

public class FindPwdVerCodeActivity extends AppCompatActivity {

    private final int UPDATE_TIME=0x0541;
    TextView mShowCode;
    EditText mCode;
    Button mVerify;
    int mTime;
    String mContent;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TIME:
                    mTime--;
                    if(mTime>0) {
                        mShowCode.setText(mTime + mContent);
                        mShowCode.setTextColor(getResources().getColor(R.color.grey));
                        mShowCode.setEnabled(false);
                        sendEmptyMessageDelayed(UPDATE_TIME,1000);
                    }
                    else {
                        mShowCode.setText("获取验证码");
                        mShowCode.setTextColor(getResources().getColor(R.color.black));
                        mShowCode.setEnabled(true);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd_ver_code);
        initToolBar();
        initViews();
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPwdVerCodeActivity.this.onBackPressed();

            }
        });
    }

    private void initViews(){
        mCode = (EditText)findViewById(R.id.login_vercode);
        mShowCode = (TextView) findViewById(R.id.login_show_vercode);
        mVerify = (Button) findViewById(R.id.find_verify);
        mTime = 60;
        mContent="s后重新获取";
        mHandler.sendEmptyMessageDelayed(UPDATE_TIME,1000);

        mShowCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发送验证码
                mTime = 60;
                mHandler.sendEmptyMessageDelayed(UPDATE_TIME,1000);
            }
        });

        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 验证输入验证码是否正确
                Intent intent = new Intent(FindPwdVerCodeActivity.this,FindPwdNewPwdActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
