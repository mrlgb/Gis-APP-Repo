package com.tt.rds.app.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tt.rds.app.R;
import com.tt.rds.app.adapter.DataQueryPageAdapter;

public class DataQueryActivity extends AppCompatActivity {
    //add test commit
    private static final String TAG = DataQueryActivity.class.getSimpleName();

    Button bt_filter;
    DrawerLayout dl_dq;
    LinearLayout ll_filter_dq;
    Button mbt_df_commit,mbt_df_notcommit;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataquery_main);

        initViews();
        setOnClickListerForFilter();

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

        DisplayMetrics dm=getResources().getDisplayMetrics();
        int wd_width=dm.widthPixels;
        DrawerLayout.LayoutParams layparams=(DrawerLayout.LayoutParams)ll_filter_dq.getLayoutParams();
        layparams.width=wd_width*4/5;
        ll_filter_dq.setLayoutParams(layparams);


    }

    private void initViews() {
        mTabLayout= (TabLayout) findViewById(R.id.tab_data_query);
        dl_dq=(DrawerLayout)findViewById(R.id.drawer_data_query);
        mViewPager= (ViewPager) findViewById(R.id.pager_data_query);
        bt_filter=(Button)findViewById(R.id.bt_filter_dq);
        ll_filter_dq=(LinearLayout)findViewById(R.id.ll_right_dq);

        mbt_df_commit=(Button)findViewById(R.id.bt_df_commit);
        mbt_df_notcommit=(Button)findViewById(R.id.bt_df_notcommit);

        dl_dq.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mbt_df_commit.setVisibility(View.VISIBLE);
                mbt_df_notcommit.setVisibility(View.VISIBLE);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        initData();

    }

    private void initData(){
        mViewPager.setAdapter(new DataQueryPageAdapter(this));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setOnClickListerForFilter() {
        //drawerlayout打开侧滑监听
        bt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dl_dq.isDrawerOpen(GravityCompat.END)){
                    dl_dq.openDrawer(GravityCompat.END);
                }

            }
        });

        //已上报
        mbt_df_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mbt_df_notcommit.getVisibility()==View.GONE)
                {
                    mbt_df_notcommit.setVisibility(View.VISIBLE);
                    mbt_df_commit.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
                }
                else{
                    mbt_df_notcommit.setVisibility(View.GONE);
                    mbt_df_commit.setBackgroundColor(getResources().getColor(R.color.orange));
                }

            }
        });

        //未上报
        mbt_df_notcommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mbt_df_commit.getVisibility()==View.GONE)
                {
                    mbt_df_commit.setVisibility(View.VISIBLE);
                    mbt_df_notcommit.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
                }
                else{
                    mbt_df_commit.setVisibility(View.GONE);
                    mbt_df_notcommit.setBackgroundColor(getResources().getColor(R.color.orange));
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
