package com.tt.rds.app.activity.point;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.adapter.ImageAdapter;
import com.tt.rds.app.bean.InfoMaker;
import com.tt.rds.app.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class MarkerActivity extends BaseSaveActivity {
    private static final String TAG = MarkerActivity.class.getSimpleName();
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

    private RecyclerView mRecyclerView;
    //----------Mine fields----------

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;
    private String mImageFileLocation = "";
    private String GALLERY_LOCATION = "image gallery";
    private File mGalleryFolder;
    //----------Mine fields----------
    UUID makerUuid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_point_maker;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);

        initToolbar();

        //------------------------------
        edtName = (EditText) findViewById(R.id.point_marker_name);
        edtCode = (EditText) findViewById(R.id.point_marker_code);
        edtLong = (EditText) findViewById(R.id.point_longitude);
        edtLati = (EditText) findViewById(R.id.point_latitude);
        edtEleva = (EditText) findViewById(R.id.point_elevation);
        edtRemark = (EditText) findViewById(R.id.point_marker_remark);

        spinAdminDiv = (Spinner) findViewById(R.id.admin_divison_code);
        spinType = (Spinner) findViewById(R.id.point_marker_type);
        iBtnCamera = (ImageButton) findViewById(R.id.point_marker_camera);
        //------------------------------

        createImageGallery();

        initRecycleView();

        iBtnCamera.setOnClickListener(btnTakePhotoListener);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.point_marker_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerActivity.this.onBackPressed();
            }
        });
    }

    private void initRecycleView() {
        makerUuid=java.util.UUID.randomUUID();
        mRecyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter imageAdapter = new ImageAdapter(mGalleryFolder,makerUuid.toString());
        mRecyclerView.setAdapter(imageAdapter);
    }

    View.OnClickListener btnTakePhotoListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

            startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            // Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
            // Bundle extras = data.getExtras();
            // Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            // Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            // setReducedImageSize();
            RecyclerView.Adapter newImageAdapter = new ImageAdapter(mGalleryFolder,"");
            mRecyclerView.swapAdapter(newImageAdapter, false);

        }
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

    private void saveInfo2DB() {
        if (verifyInput()) {

            InfoMaker pMarker = new InfoMaker();
            pMarker.setName(edtName.getText().toString());
            pMarker.setCode(edtCode.getText().toString());
//            pMarker.setCategory(spinType.getSelectedItem().toString());
//            pMarker.setAdminCode(spinAdminDiv.getSelectedItem().toString());
            pMarker.setLongitude(23.001);
            pMarker.setLatitude(123.001);
            pMarker.setRemarks(edtRemark.getText().toString());

            ToastUtil.showToast(this, "save to db successful!");
        }
        MarkerActivity.this.onBackPressed();

    }

    //验证逻辑
    private boolean verifyInput() {

        String name = edtName.getText().toString();
        String code = edtCode.getText().toString();
        if (name.isEmpty()) {
            edtName.setError("请输入正确的名称");
            return false;
        }

        if (code.isEmpty()) {
            edtCode.setError("请选择正确的编码");
            return false;
        }

        return true;
    }

    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs();
        }

    }

    File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_"+makerUuid+"_";

        File image = File.createTempFile(imageFileName, ".jpg", mGalleryFolder);
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }

    void setReducedImageSize() {
        int targetImageViewWidth = mPhotoCapturedImageView.getWidth();
        int targetImageViewHeight = mPhotoCapturedImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth / targetImageViewWidth, cameraImageHeight / targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        mPhotoCapturedImageView.setImageBitmap(photoReducedSizeBitmp);


    }
}
