package com.tt.rds.app.activity;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.tt.rds.app.R;

public class SearchActivity extends AppCompatActivity {

    //add test commit
    private CoordinatorLayout mCoordinatorLayout;
    private LinearLayout mll_filter;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_search);
        mll_filter = (LinearLayout)findViewById(R.id.nav_right);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("查询");
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
        layparams.width=wd_width*3/4;
        mll_filter.setLayoutParams(layparams);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_query,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId=item.getItemId();

        if(itemId==R.id.query_filter){
            if(!mDrawer.isDrawerOpen(GravityCompat.END)){
                mDrawer.openDrawer(GravityCompat.END);
            }
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
