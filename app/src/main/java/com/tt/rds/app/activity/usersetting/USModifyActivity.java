package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.bean.UserInfo;
import com.tt.rds.app.common.ConstantValue;

public class USModifyActivity extends BaseSaveActivity
{

    private final MainApplication gpsApplication=MainApplication.getInstance();

    EditText et_input;
    TextView toolbar_title;
    int current_mode;
    UserInfo userInfo;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_usmodify;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);


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
                setResultForUS();

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResultForUS();
    }

    private void initViews() {
        et_input=(EditText)findViewById(R.id.us_new_info);
        toolbar_title=(TextView)findViewById(R.id.us_title);

        SharedPreferences sf = getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
        String currentUser=sf.getString(ConstantValue.current_user,"");

        userInfo=gpsApplication.getUserLoginInfo(currentUser);

        Bundle bundleIn=getIntent().getBundleExtra("mode");
        current_mode=bundleIn.getInt("launch_mode");

        switch (current_mode){
            case ConstantValue.MODIFY_ANONYMOUS:
                et_input.setHint("请输入您的昵称");
                et_input.setText(userInfo.getAnonymous());
                toolbar_title.setText("更改昵称");
                break;
            case ConstantValue.MODIFY_PHONE:
                et_input.setHint("请输入您的手机号");
                et_input.setText(userInfo.getPhone());
                et_input.setInputType(InputType.TYPE_CLASS_PHONE);
                Drawable src=getResources().getDrawable(R.drawable.et_drawable_86);
                src.setBounds(0,0,120,80);
                et_input.setCompoundDrawables(src,null,null,null);
                toolbar_title.setText("联系方式");
                break;
            case ConstantValue.MODIFY_EMAIL:
                et_input.setHint("请输入您的邮箱");
                et_input.setText(userInfo.getEmail());
                toolbar_title.setText("邮箱");
                break;
            case ConstantValue.MODIFY_ADDR:
                et_input.setHint("请输入您的所在地区");
                et_input.setText(userInfo.getAddress());
                toolbar_title.setText("所在地区");
                break;
            case ConstantValue.MODIFY_SIGNATURE:
                et_input.setHint("记录工作和生活的点点滴滴");
                et_input.setText(userInfo.getSignature());
                et_input.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        getResources().getDisplayMetrics().heightPixels/3));
                toolbar_title.setText("个性签名");
                break;
        }
    }

    private void setResultForUS(){
        Intent intent =new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("content",et_input.getText().toString());
        intent.putExtra("result",bundle);
        setResult(current_mode,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_activity){
            setResultForUS();
        }
        return true;
    }
}
