package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.common.ConstantValue;

public class USModifyActivity extends BaseSaveActivity
{

    EditText et_input;
    TextView toolbar_title;
    int current_mode;
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
                USModifyActivity.this.onBackPressed();

            }
        });
    }

    private void initViews() {
        et_input=(EditText)findViewById(R.id.us_new_info);
        toolbar_title=(TextView)findViewById(R.id.us_title);

        Bundle bundleIn=getIntent().getBundleExtra("mode");
        current_mode=bundleIn.getInt("launch_mode");

        switch (current_mode){
            case ConstantValue.MODIFY_ANONYMOUS:
                et_input.setHint("请输入您的昵称");
                toolbar_title.setText("更改昵称");
                break;
            case ConstantValue.MODIFY_PHONE:
                et_input.setHint("请输入您的手机号");
                et_input.setInputType(InputType.TYPE_CLASS_PHONE);
                Drawable src=getResources().getDrawable(R.drawable.et_drawable_86);
                src.setBounds(0,0,80,50);
                et_input.setCompoundDrawables(src,null,null,null);
                toolbar_title.setText("联系方式");
                break;
            case ConstantValue.MODIFY_EMAIL:
                et_input.setHint("请输入您的邮箱");
                toolbar_title.setText("邮箱");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_activity){
            Intent intent =new Intent();
            Bundle bundle=new Bundle();
            bundle.putString("content",et_input.getText().toString());
            intent.putExtra("result",bundle);
            setResult(current_mode,intent);
            finish();
        }
        return true;
    }
}
