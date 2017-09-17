package com.tt.rds.app.activity;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tt.rds.app.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{

    private static final String TAG = SearchActivity.class.getSimpleName();
    private CoordinatorLayout mCoordinatorLayout;
    private LinearLayout mll_filter;
    private DrawerLayout mDrawer;
    private Button mbt_filter;
    private Button mbt_nolimit,mbt_progm,mbt_plan;
    private Button mbt_road,mbt_bridge,mbt_tunnel,mbt_ferry,mbt_platform,mbt_mark;
    private List<View> bt_dataTypes;
    private List<View> bt_markTypes;
    private final int BT_NOLIMIT_INDEX=0;
    private final int BT_PROGM_INDEX=1;
    private final int BT_PLAN_INDEX=2;
    private final int BT_ROAD_INDEX=0;
    private final int BT_BRIDGE_INDEX=1;
    private final int BT_TUNNEL_INDEX=2;
    private final int BT_FERRY_INDEX=3;
    private final int BT_PLATFORM_INDEX=4;
    private final int BT_MARK_INDEX=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setOnClickListerForFilter();

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.onBackPressed();
            }
        });

        //set size of slide right
        DisplayMetrics dm=getResources().getDisplayMetrics();
        int wd_width=dm.widthPixels;
        DrawerLayout.LayoutParams layparams=(DrawerLayout.LayoutParams)mll_filter.getLayoutParams();
        layparams.width=wd_width*5/6;
        mll_filter.setLayoutParams(layparams);

    }

    private void initViews(){
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_search);
        mll_filter = (LinearLayout)findViewById(R.id.ll_right_search);
        mbt_filter = (Button)findViewById(R.id.bt_filter_search);
        //数据类型
        mbt_nolimit = (Button)findViewById(R.id.bt_sf_nolimit);
        mbt_progm = (Button)findViewById(R.id.bt_sf_progm);
        mbt_plan = (Button)findViewById(R.id.bt_sf_plan);
        //路线/点位
        mbt_road = (Button)findViewById(R.id.bt_sf_road);
        mbt_bridge = (Button)findViewById(R.id.bt_sf_bridge);
        mbt_tunnel = (Button)findViewById(R.id.bt_sf_tunnel);
        mbt_ferry = (Button)findViewById(R.id.bt_sf_ferry);
        mbt_platform = (Button)findViewById(R.id.bt_sf_platform);
        mbt_mark = (Button)findViewById(R.id.bt_sf_mark);

        bt_dataTypes=new ArrayList<>();
        bt_dataTypes.add(mbt_nolimit);
        bt_dataTypes.add(mbt_progm);
        bt_dataTypes.add(mbt_plan);

        bt_markTypes=new ArrayList<>();
        bt_markTypes.add(mbt_road);
        bt_markTypes.add(mbt_bridge);
        bt_markTypes.add(mbt_tunnel);
        bt_markTypes.add(mbt_ferry);
        bt_markTypes.add(mbt_platform);
        bt_markTypes.add(mbt_mark);

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);

                Log.d(TAG,"button clickable is "+mbt_nolimit.isClickable());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setVisibleForMarkButtons(0);
                setVisibleForDataButtons(0);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void setOnClickListerForFilter(){
        //drawerlayout打开侧滑监听
        mbt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mDrawer.isDrawerOpen(GravityCompat.END)){
                    mDrawer.openDrawer(GravityCompat.END);
                }
            }
        });
        //不限
        mbt_nolimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click nolimit button");
                if(mbt_progm.getVisibility()==View.GONE){
                    setVisibleForDataButtons(BT_NOLIMIT_INDEX);
                }
                else{
                    setUnvisibleForDataButtons(BT_NOLIMIT_INDEX);
                }

            }
        });
        //规划
        mbt_progm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click progm button");
                if(mbt_plan.getVisibility()==View.GONE){
                    setVisibleForDataButtons(BT_PROGM_INDEX);
                }
                else{
                    setUnvisibleForDataButtons(BT_PROGM_INDEX);
                }

            }
        });

        //计划
        mbt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click plan button");
                if(mbt_progm.getVisibility()==View.GONE){
                    setVisibleForDataButtons(BT_PLAN_INDEX);
                }
                else{
                    setUnvisibleForDataButtons(BT_PLAN_INDEX);
                }

            }
        });


        //路段
        mbt_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click road button");
                if(mbt_mark.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_ROAD_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_ROAD_INDEX);
                }
            }
        });

        //桥梁
        mbt_bridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click bridge button");
                if(mbt_mark.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_BRIDGE_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_BRIDGE_INDEX);
                }
            }
        });

        //隧道
        mbt_tunnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click tunnel button");
                if(mbt_mark.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_TUNNEL_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_TUNNEL_INDEX);
                }
            }
        });

        //渡口
        mbt_ferry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click ferry button");
                if(mbt_mark.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_FERRY_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_FERRY_INDEX);
                }
            }
        });
        //客运站点
        mbt_platform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click platform button");
                if(mbt_mark.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_PLATFORM_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_PLATFORM_INDEX);
                }
            }
        });
        //标志点
        mbt_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click mark button");
                if(mbt_road.getVisibility()==View.GONE){
                    setVisibleForMarkButtons(BT_MARK_INDEX);
                }
                else{
                    setUnvisibleForMarkButtons(BT_MARK_INDEX);
                }
            }
        });

    }

    private void setVisibleForDataButtons(int index){
        for(int i=0;i<bt_dataTypes.size();i++){
            bt_dataTypes.get(i).setVisibility(View.VISIBLE);
        }
        bt_dataTypes.get(index).setBackgroundColor(getResources().getColor(R.color.whitesmoke));
    }

    private void setUnvisibleForDataButtons(int index){
        for(int i=0;i<bt_dataTypes.size();i++){
            bt_dataTypes.get(i).setVisibility(View.GONE);
        }
        bt_dataTypes.get(index).setVisibility(View.VISIBLE);
        bt_dataTypes.get(index).setBackgroundColor(getResources().getColor(R.color.orange));

    }
    private void setVisibleForMarkButtons(int index){
        for(int i=0;i<bt_markTypes.size();i++){
            bt_markTypes.get(i).setVisibility(View.VISIBLE);
        }
        bt_markTypes.get(index).setBackgroundColor(getResources().getColor(R.color.whitesmoke));
    }

    private void setUnvisibleForMarkButtons(int index){
        for(int i=0;i<bt_markTypes.size();i++){
            bt_markTypes.get(i).setVisibility(View.GONE);
        }
        bt_markTypes.get(index).setVisibility(View.VISIBLE);
        bt_markTypes.get(index).setBackgroundColor(getResources().getColor(R.color.orange));

    }
    /**
     * animate the views if we close the activity
     */
    @Override
    public void onBackPressed() {
//        for (int i = mRowContainer.getChildCount() - 1; i > 0; i--)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }



}
