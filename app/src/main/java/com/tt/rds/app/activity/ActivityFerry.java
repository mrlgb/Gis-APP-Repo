package com.tt.rds.app.activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.tt.rds.app.R;

public class ActivityFerry extends BaseSaveActivity  {
    private static final String TAG = ActivityFerry.class.getSimpleName();

    protected int getLayoutResId() {
        return R.layout.activity_ferry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getLayoutResId();
        if (layoutResId == 0) {
            finish();
        }

        setContentView(layoutResId);
        initActivity(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ferryactivity_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityFerry.this.onBackPressed();
            }
        });

    }

    protected void initActivity(Bundle savedInstanceState) {
    }

    /**
     * animate the views if we close the activity
     */
    public void onBackPressed() {
//        for (int i = mRowContainer.getChildCount() - 1; i > 0; i--)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }


}
