package com.tt.rds.app.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tt.rds.app.R;
import com.tt.rds.app.util.ToastUtil;

public class Bridge2Activity extends BaseSaveActivity {
    private static final String TAG = Bridge2Activity.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bridge2;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bridge2_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bridge2Activity.this.onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.save_activity) {
            ToastUtil.showToast(getApplicationContext(), TAG + "-save!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
