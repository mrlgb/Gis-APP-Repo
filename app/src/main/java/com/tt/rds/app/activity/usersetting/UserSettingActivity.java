package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.bean.UserInfo;
import com.tt.rds.app.common.ConstantValue;

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private final MainApplication gpsApplication=MainApplication.getInstance();
    ImageView mf_head;
    ConstraintLayout us_head;
    UserInfo userInfo;
    LinearLayout us_anony,us_phone,us_email,us_gender,us_addr,us_signature;
    TextView mf_anony,mf_phone,mf_email,mf_gender,mf_addr,mf_signature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
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
                UserSettingActivity.this.onBackPressed();

            }
        });
    }

    private void initViews() {

        us_head=(ConstraintLayout)findViewById(R.id.us_header);
        us_anony=(LinearLayout)findViewById(R.id.us_anony);
        us_phone=(LinearLayout)findViewById(R.id.us_phone);
        us_email=(LinearLayout)findViewById(R.id.us_email);
        us_gender=(LinearLayout)findViewById(R.id.us_gender);
        us_addr=(LinearLayout)findViewById(R.id.us_addr);
        us_signature=(LinearLayout)findViewById(R.id.us_signature);

        mf_head=(ImageView) findViewById(R.id.mf_header);
        mf_anony=(TextView) findViewById(R.id.mf_anony);
        mf_phone=(TextView)findViewById(R.id.mf_phone);
        mf_email=(TextView)findViewById(R.id.mf_email);
        mf_gender=(TextView)findViewById(R.id.mf_gender);
        mf_addr=(TextView)findViewById(R.id.mf_addr);
        mf_signature=(TextView)findViewById(R.id.mf_signature);

        userInfo=new UserInfo();
        SharedPreferences sf = getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
        String currentUser=sf.getString(ConstantValue.current_user,"");

        userInfo=gpsApplication.getUserLoginInfo(currentUser);
        mf_anony.setText(userInfo.getAnonymous().equals("")?"未填写":userInfo.getAnonymous());
        mf_phone.setText(userInfo.getPhone().equals("")?"未填写":userInfo.getPhone());
        mf_email.setText(userInfo.getEmail().equals("")?"未填写":userInfo.getEmail());
        mf_gender.setText(userInfo.getGender().equals("")?"未填写":userInfo.getGender());
        mf_addr.setText(userInfo.getAddress().equals("")?"未填写":userInfo.getAddress());
        mf_signature.setText(userInfo.getSignature().equals("")?"未填写":userInfo.getSignature());

        us_head.setOnClickListener(this);
        us_anony.setOnClickListener(this);
        us_phone.setOnClickListener(this);
        us_email.setOnClickListener(this);
        us_gender.setOnClickListener(this);
        us_addr.setOnClickListener(this);
        us_signature.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.us_anony:
                startAlterActivity(ConstantValue.MODIFY_ANONYMOUS);
                break;
            case R.id.us_phone:
                startAlterActivity(ConstantValue.MODIFY_PHONE);
                break;
            case R.id.us_email:
                startAlterActivity(ConstantValue.MODIFY_EMAIL);
                break;

        }
    }

    private void startAlterActivity(int mode){
        Intent intent=new Intent(UserSettingActivity.this,USModifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("launch_mode", mode);
        intent.putExtra("mode",bundle);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content = data.getBundleExtra("result").getString("content");
        switch (resultCode){
            case ConstantValue.MODIFY_ANONYMOUS:
                mf_anony.setText(content);
                userInfo.setAnonymous(content);
                break;
            case ConstantValue.MODIFY_PHONE:
                mf_phone.setText(content);
                userInfo.setPhone(content);
                break;
            case ConstantValue.MODIFY_EMAIL:
                mf_email.setText(content);
                userInfo.setEmail(content);
                break;

        }
        gpsApplication.updateUserLoginInfo(userInfo);
        //TODO 联网更新到服务端
    }
}
