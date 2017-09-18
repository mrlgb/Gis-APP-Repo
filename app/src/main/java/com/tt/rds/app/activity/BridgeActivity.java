package com.tt.rds.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.tt.rds.app.R;
import com.tt.rds.app.bean.InfoBridge;
import com.tt.rds.app.util.ToastUtil;


public class BridgeActivity extends BaseSaveActivity {
    private static final String TAG = BridgeActivity.class.getSimpleName();
    //----------Mine fields----------
    EditText edtBridgeName;
    EditText edtBridgeNo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bridge;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

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

        //------------------------------
        edtBridgeName = (EditText) findViewById(R.id.Edt_BridgeName);
        edtBridgeNo = (EditText) findViewById(R.id.Edt_BridgeNo);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.save_activity) {
            saveBridgeInfo2DB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //保存逻辑
    private void saveBridgeInfo2DB() {
        if (verifyInput()) {
            InfoBridge bridge = new InfoBridge();
            bridge.setName(edtBridgeName.getText().toString());
            bridge.setNoID(edtBridgeNo.getText().toString());
            ToastUtil.showToast(getApplicationContext(), TAG + "-save!");
        }
    }

    //验证逻辑
    private boolean verifyInput() {
        return true;
    }
}
