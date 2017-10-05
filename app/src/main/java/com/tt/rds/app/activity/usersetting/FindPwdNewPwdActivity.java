package com.tt.rds.app.activity.usersetting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tt.rds.app.R;

public class FindPwdNewPwdActivity extends AppCompatActivity {

    EditText et_pwd, et_pwd2;
    Button mFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd_new_pwd);
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
                FindPwdNewPwdActivity.this.onBackPressed();

            }
        });
    }
    private void initViews(){
        et_pwd=(EditText)findViewById(R.id.find_pwd_1);
        et_pwd2=(EditText)findViewById(R.id.find_pwd_2);
        mFinish=(Button)findViewById(R.id.find_finish);

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd1=et_pwd.getText().toString();
                String pwd2=et_pwd2.getText().toString();
                if(pwd1.equals(pwd2)){
                    //TODO 联网更新密码
                    Toast.makeText(FindPwdNewPwdActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(FindPwdNewPwdActivity.this,"两次输入密码不一致！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
