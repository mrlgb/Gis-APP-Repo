package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tt.rds.app.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
                AboutActivity.this.onBackPressed();

            }
        });
    }

    private void initViews(){

    }

    public void onCheckUpdate(View v){
        //TODO 检查更新
    }

    public void onShareApp(View v){
        Intent intent = new Intent(AboutActivity.this, ShareActivity.class);
        startActivity(intent);
    }
}
