package com.tt.rds.app.activity.point;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.adapter.ImagesAdapter;
import com.tt.rds.app.app.Application;
import com.tt.rds.app.bean.PoMaker;
import com.tt.rds.app.bean.PoMakerDao;
import com.tt.rds.app.bean.PointType;
import com.tt.rds.app.bean.TtPoint;
import com.tt.rds.app.bean.User;
import com.tt.rds.app.bean.UserDao;
import com.tt.rds.app.db.DBService;
import com.tt.rds.app.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;


public class MarkerActivity extends BaseSaveActivity {
    private static final String TAG = MarkerActivity.class.getSimpleName();

    final Application gpsApplication = Application.getInstance();
    //----------Mine fields----------
    EditText edtName;
    EditText edtCode;
    Spinner spinAdminDiv;
    Spinner spinType;
    EditText edtLong;
    EditText edtLati;
    EditText edtEleva;
    EditText edtRemark;
    ImageButton btnCamera;

    private RecyclerView mRecyclerView;
    private ImagesAdapter imagesAdapter;
    private ArrayList<File> photos = new ArrayList<>();
    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private static final String PHOTOS_FOLDER = "EasyImage sample";
    private HashMap<Integer,String> photosName;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_point_maker;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);
        initToolbar();
        //------------------------------
        initView();
        //------------------------------
        photosName =new HashMap<>();
        Nammu.init(this);

        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTOS_KEY);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        imagesAdapter = new ImagesAdapter(this, photos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(imagesAdapter);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                @Override
                public void permissionRefused() {
                    finish();
                }
            });
        }

        EasyImage.configuration(this)
                .setImagesFolderName(PHOTOS_FOLDER)
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(true);

        checkGalleryAppAvailability();

    }

    private void initView() {
        edtName = (EditText) findViewById(R.id.point_marker_name);
        edtCode = (EditText) findViewById(R.id.point_marker_code);
        edtLong = (EditText) findViewById(R.id.point_longitude);
        edtLati = (EditText) findViewById(R.id.point_latitude);
        edtEleva = (EditText) findViewById(R.id.point_elevation);
        edtRemark = (EditText) findViewById(R.id.point_marker_remark);

        spinAdminDiv = (Spinner) findViewById(R.id.admin_divison_code);
        spinType = (Spinner) findViewById(R.id.point_marker_type);

        btnCamera = (ImageButton) findViewById(R.id.camera_button);
        btnCamera.setOnClickListener(takePhotoListener);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    private void checkGalleryAppAvailability() {
        if (!EasyImage.canDeviceHandleGallery(this)) {
            //Device has no app that handles gallery intent
//            galleryButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    View.OnClickListener takePhotoListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (photos.size() > 4) {
                ToastUtil.showToast(MarkerActivity.this,"照片数大于5张！");
            } else
                EasyImage.openCamera(MarkerActivity.this, 0);
        }
    };


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

            PoMaker pMarker = new PoMaker();
            //name
            pMarker.setName(edtName.getText().toString());
            //code
            pMarker.setCode(edtCode.getText().toString());
//            pMarker.setCategory(spinType.getSelectedItem().toString());
//            pMarker.setAdminCode(spinAdminDiv.getSelectedItem().toString());

            PointType pointType =new PointType((long)1,"TYPE1",1);

            TtPoint ttPoint=new TtPoint();
            //id
            ttPoint.setId((long)100001);
            //name
            ttPoint.setName("TEST-10001");
            //point type
            ttPoint.setPointType(pointType);
            ttPoint.setPointTypeId(pointType.getId());

            //ttpoint
            pMarker.setPointId(ttPoint.getId());
            pMarker.setTtPoint(ttPoint);

            PoMakerDao poMakerDao=gpsApplication.getDbService().getPoMakerDao();
            poMakerDao.insert(pMarker);

            List<PoMaker> list = poMakerDao.queryBuilder()
                    .where(PoMakerDao.Properties.Id.between(0, 13)).limit(5).build().list();
            for (int i = 0; i < list.size(); i++) {
                Log.d(TAG, "PoMaker: [" +i+ "]="+list.get(i).getName());
            }

//            pMarker.set(23.001);
//            pMarker.setLatitude(123.001);
//            pMarker.setRemarks(edtRemark.getText().toString());
//
//            for (int i = 0; i < photosName.size(); i++) {
//                pMarker.getPicName().add(photosName.get(i));
//
//                ToastUtil.showToast(this, "save to db successful!");
//            }


        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(MarkerActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        photos.addAll(returnedPhotos);
        imagesAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(photos.size() - 1);

        for(int i = 0;i < returnedPhotos.size(); i ++){
            ToastUtil.showToast(this, (photos.size()-1)+"/"+returnedPhotos.get(i).getAbsolutePath());
            photosName.put(photos.size()-1,returnedPhotos.get(i).getAbsolutePath());
        }

    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }
}
