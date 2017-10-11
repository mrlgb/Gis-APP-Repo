package com.tt.rds.app.activity.pointcollect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.adapter.ImagesAdapter;
import com.tt.rds.app.app.GPSApplication;
import com.tt.rds.app.bean.Picture;
import com.tt.rds.app.bean.PictureDao;
import com.tt.rds.app.bean.PointMarker;
import com.tt.rds.app.bean.PointMarkerDao;
import com.tt.rds.app.bean.PointType;
import com.tt.rds.app.bean.TtPoint;
import com.tt.rds.app.bean.TtPointDao;
import com.tt.rds.app.util.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;



public class MarkerActivity extends BaseSaveActivity{
    private static final String TAG = MarkerActivity.class.getSimpleName();

    final GPSApplication gpsGPSApplication = GPSApplication.getInstance();
    //----------Mine fields----------
    EditText edtName;
    EditText edtCode;
    Spinner spinAdminDiv;
    Spinner spinType;
    EditText edtLong;
    EditText edtLati;
    EditText edtAlti;
    EditText edtRemark;
    TextView spinner_error1 ;
    TextView spinner_error2 ;
    ImageButton btnCamera;

    private RecyclerView mRecyclerView;
    private ImagesAdapter imagesAdapter;
    private ScrollView scrollView;
    private ArrayList<File> photos = new ArrayList<>();
    private static final String PHOTOS_KEY = "easy_image_photos_list";
    private static final String PHOTOS_FOLDER = "PointMarker";
    private HashMap<String, String> photosName;
    private Location loc;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_point_maker;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);
        initToolbar();
        //------------------------------
        if(gpsGPSApplication.getRealtimeLoc()!=null)
            loc=new Location(gpsGPSApplication.getRealtimeLoc());
        initView();
        //------------------------------
        photosName = new HashMap<>();
        Nammu.init(this);

        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTOS_KEY);
        }

        setupRecycleView();

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

    private void setupRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        imagesAdapter = new ImagesAdapter(this, photos);
        imagesAdapter.setClickListener(new ImagesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                photos.remove(position);
                imagesAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(photos.size() - 1);
            }

        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(imagesAdapter);
    }

    private void initView() {
        edtName = (EditText) findViewById(R.id.point_marker_name);
        edtCode = (EditText) findViewById(R.id.point_marker_code);
        edtLong = (EditText) findViewById(R.id.point_longitude);
        edtLati = (EditText) findViewById(R.id.point_latitude);
        edtAlti = (EditText) findViewById(R.id.point_elevation);
        edtRemark = (EditText) findViewById(R.id.point_marker_remark);

        spinAdminDiv = (Spinner) findViewById(R.id.admin_divison_code);
        spinType = (Spinner) findViewById(R.id.point_marker_type);

        btnCamera = (ImageButton) findViewById(R.id.camera_button);
        btnCamera.setOnClickListener(takePhotoListener);

        scrollView =(ScrollView)findViewById(R.id.scrollView2);
        spinner_error1=(TextView) findViewById(R.id.spinner_error1);
        spinner_error2=(TextView) findViewById(R.id.spinner_error2);

        if(loc!=null){
            edtLati.setText(loc.getLatitude()+"");
            edtLong.setText(loc.getLongitude()+"");
            edtAlti.setText(loc.getAltitude()+"");
        }else
        {
            ToastUtil.showToast(this, "GPS信号弱，请重试！");
        }


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
                Snackbar.make(scrollView, "照片数大于5张！", Snackbar.LENGTH_SHORT).show();
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
            PointMarkerDao pMakerDao = gpsGPSApplication.getDbService().getPointMarkerDao();
            PictureDao pictureDao = gpsGPSApplication.getDbService().getPictureDao();
            TtPointDao ttPointDao= gpsGPSApplication.getDbService().getTtPointDao();

            PointType pType = new PointType((long) 9, "TYPE1", 1);
            //设置关联点
            TtPoint ttPoint = new TtPoint();
            //id
            ttPoint.setTtPointId(null);//id
            ttPoint.setGuid(java.util.UUID.randomUUID().toString()); //guid
            //name
            ttPoint.setName("TEST-TTPoint-10001");
            //point type
            ttPoint.setPTypeId(pType.getPTypeId());
            //CODE
            ttPoint.setCode("SC04001");

            ttPoint.setLat(loc.getLatitude());
            ttPoint.setLon(loc.getLongitude());
            ttPoint.setAlt(loc.getAltitude());
            ttPoint.setAdminCode("");//行政编码
            ttPointDao.insert(ttPoint);

            if(photosName.size()!=0){
                //照片
                for (Map.Entry<String, String> entry : photosName.entrySet()) {
                    Picture pic = new Picture();
                    pic.setPicId(null);//id
                    pic.setGuid(java.util.UUID.randomUUID().toString());//guid
                    pic.setName(TAG + entry.getKey());//name
                    pic.setPath(entry.getValue());//path
                    //增加pic
                    pictureDao.insertOrReplaceInTx(pic);
                }

            }else{
                // 创建Snackbar实例
                Snackbar.make(scrollView, "请至少拍1张照片", Snackbar.LENGTH_SHORT).show();
                return;
            }


            PointMarker pMarker = new PointMarker();
            //id
            pMarker.setPMarkerId(null);
            //name
            pMarker.setName("TEST-PointMarker-10001");
            //code
            pMarker.setCode(edtCode.getText().toString());
            //类别
            pMarker.setCatergory("标志点");
            //备注
            pMarker.setRemark("");


            //ttpoint
            pMarker.setTtPointId(ttPoint.getTtPointId());
            pMarker.setTtPoint(ttPoint);

            //增加标志点
            pMakerDao.insert(pMarker);

            //查询
//            Query<PointMarker> query = pMakerDao.queryBuilder().where(PointMarkerDao.Properties.Name.eq("TEST-PointMarker-10001")).build();
//            for (PointMarker pointMarker : query.list()) {
//                for(Picture pic :pointMarker.getTtPoint().getPictures())
//                Log.d(TAG, "onCreate: " + pic.getGuid()+pic.getPath());
//            }

            // 创建Snackbar实例
            Snackbar.make(scrollView, "标志点保存成功！", Snackbar.LENGTH_LONG).show();

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

        String typeCode =spinType.getSelectedItem().toString();
        if (typeCode.equals("数据类型")) {
            TextView errorText = (TextView)spinType.getSelectedView();
            errorText.setError("请选择标志点类型");
            return false;
        }

        String adminCode =spinAdminDiv.getSelectedItem().toString();
        if (adminCode.equals("行政等级")) {
            TextView errorText = (TextView)spinAdminDiv.getSelectedView();
            errorText.setError("请选择行政区划");
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

        for (int i = 0; i < returnedPhotos.size(); i++) {
//            ToastUtil.showToast(this, (photos.size() - 1) + "/" + returnedPhotos.get(i).getAbsolutePath());
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd--HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            photosName.put(formatter.format(curDate).toString(), returnedPhotos.get(i).getAbsolutePath());
        }

    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this);
        super.onDestroy();
    }
}
