package com.tt.rds.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tt.rds.app.R;
import com.tt.rds.app.util.ToastUtil;

/**
 * Created by WK on 2017/9/18.
 */

public class TunnelActivity extends  BaseSaveActivity {

    private static final String TAG = TunnelActivity.class.getSimpleName();
    //----------Mine fields----------
//    EditText edtBridgeName;
//    EditText edtBridgeNo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tunnel;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tunner_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TunnelActivity.this.onBackPressed();
            }
        });

        //------------------------------
//        edtBridgeName = (EditText) findViewById(R.id.Edt_BridgeName);
//        edtBridgeNo = (EditText) findViewById(R.id.Edt_BridgeNo);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.save_activity) {
            saveInfo2DB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //保存逻辑
    private void saveInfo2DB() {
        if (verifyInput()) {
//            InfoBridge bridge = new InfoBridge();
//            bridge.setName(edtBridgeName.getText().toString());
//            bridge.setNoID(edtBridgeNo.getText().toString());
            ToastUtil.showToast(getApplicationContext(), TAG + "-save!");
        }
    }

    //验证逻辑
    private boolean verifyInput() {
        return true;
    }
}