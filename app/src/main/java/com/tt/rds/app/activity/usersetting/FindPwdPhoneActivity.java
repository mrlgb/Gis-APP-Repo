package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tt.rds.app.R;

public class FindPwdPhoneActivity extends AppCompatActivity {

    EditText mPhone;
    Button mNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd_phone);

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
                FindPwdPhoneActivity.this.onBackPressed();

            }
        });
    }

    private void initViews(){
        mNext=(Button) findViewById(R.id.find_next);
        mPhone=(EditText)findViewById(R.id.find_phone);

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    mNext.setEnabled(true);
                    mNext.setBackground(getResources().getDrawable(R.drawable.bg_rectangle_solid_blue));
                }
                else
                {
                    mNext.setEnabled(false);
                    mNext.setBackground(getResources().getDrawable(R.drawable.bg_rectangle_solid_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发送验证码

                Intent intent = new Intent(FindPwdPhoneActivity.this,FindPwdVerCodeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
