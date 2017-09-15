package com.tt.rds.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tt.rds.app.R;

public class DataQueryActivity extends AppCompatActivity {

    Button bt_filter;
    DrawerLayout dl_dq;
    LinearLayout ll_filter_dq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataquery_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataQueryActivity.this.onBackPressed();

            }
        });

        dl_dq=(DrawerLayout)findViewById(R.id.drawer_data_query);
        bt_filter=(Button)findViewById(R.id.bt_filter_dq);
        ll_filter_dq=(LinearLayout)findViewById(R.id.ll_right_dq);

        DisplayMetrics dm=getResources().getDisplayMetrics();
        int wd_width=dm.widthPixels;
        DrawerLayout.LayoutParams layparams=(DrawerLayout.LayoutParams)ll_filter_dq.getLayoutParams();
        layparams.width=wd_width*3/4;
        ll_filter_dq.setLayoutParams(layparams);

        bt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dl_dq.isDrawerOpen(GravityCompat.END)){
                    dl_dq.openDrawer(GravityCompat.END);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data_query,menu);
        return true;
    }
}
