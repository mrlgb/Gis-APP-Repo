package com.tt.rds.app.activity.point;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.bean.InfoPointMarker;
import com.tt.rds.app.util.ToastUtil;


public class PointMarkerActivity extends BaseSaveActivity {
    private static final String TAG = PointMarkerActivity.class.getSimpleName();
    //----------Mine fields----------
    EditText edtName;
    EditText edtCode;
    Spinner spinAdminDiv;
    Spinner spinType;
    EditText edtLong;
    EditText edtLati;
    EditText edtEleva;
    EditText edtRemark;
    ImageButton iBtnCamera;

    TextInputLayout input_name;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_point_maker;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.point_marker_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointMarkerActivity.this.onBackPressed();
            }
        });

        //------------------------------
        edtName = (EditText) findViewById(R.id.point_marker_name);
        edtCode = (EditText) findViewById(R.id.point_marker_code);
        edtLong = (EditText) findViewById(R.id.point_longitude);
        edtLati= (EditText) findViewById(R.id.point_latitude);
        edtEleva= (EditText) findViewById(R.id.point_elevation);
        edtRemark = (EditText) findViewById(R.id.point_marker_remark);

        spinAdminDiv = (Spinner) findViewById(R.id.admin_divison_code);
        spinType = (Spinner) findViewById(R.id.point_marker_type);
        iBtnCamera= (ImageButton) findViewById(R.id.point_marker_camera);

        input_name= (TextInputLayout) findViewById(R.id.input_name);
        //------------------------------
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

            InfoPointMarker pMarker = new InfoPointMarker();
            pMarker.setName(edtName.getText().toString());
            pMarker.setCode(edtCode.getText().toString());
            pMarker.setType(spinType.getSelectedItemPosition());
            pMarker.setAdminDiv(spinAdminDiv.getSelectedItemPosition());
            pMarker.setLongitude(23.001);
            pMarker.setLatitude(123.001);
            pMarker.setElevation(345.001);
            pMarker.setRemarks(edtRemark.getText().toString());


        }
    }

    //验证逻辑
    private boolean verifyInput() {
        boolean result1;
        boolean result2 = true;

        String name = edtName.getText().toString();

        if (name.isEmpty()) {
            input_name.setError("请输入正确的名称");
            result1 = false;
        } else{
            input_name.setErrorEnabled(false);
            result1 = true;
        }


//        String code = edtCode.getText().toString();
//
//        if (code == null) {
//            // We set the error message
//            edtCode.setError("请输入正确的编码");
//            result2 = false;
//        } else
//            result2 = true;

        return result1 && result2;
    }
}
