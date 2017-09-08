package cn.edu.hfuu.gis.gisapp.activity;

import android.animation.Animator;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import cn.edu.hfuu.gis.gisapp.R;

public class SearchActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);

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
