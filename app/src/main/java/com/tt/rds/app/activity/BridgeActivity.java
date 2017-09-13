package com.tt.rds.app.activity;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tt.rds.app.R;
import com.tt.rds.app.util.ToastUtil;

public class BridgeActivity extends AppCompatActivity {
    private static final String TAG = BridgeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bridge_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeActivity.this.onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId=item.getItemId();

        if(itemId==R.id.save_activity){

            ToastUtil.showToast(getApplicationContext(),TAG+"-save_activity");
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
