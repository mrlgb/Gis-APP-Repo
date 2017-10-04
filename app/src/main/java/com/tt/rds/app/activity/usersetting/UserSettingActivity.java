package com.tt.rds.app.activity.usersetting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.app.Common;
import com.tt.rds.app.bean.AppBitmap;
import com.tt.rds.app.bean.UserInfo;

import java.io.File;

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private final MainApplication gpsApplication=MainApplication.getInstance();
    private final int GENDER_MALE=0x0041;
    private final int GENDER_FEMALE=0x0042;

    ImageView mf_head;
    ConstraintLayout us_head;
    UserInfo userInfo;
    LinearLayout us_anony,us_phone,us_email,us_gender,us_addr,us_signature;
    TextView mf_anony,mf_phone,mf_email,mf_gender,mf_addr,mf_signature,us_capture,us_gallery;
    PopupWindow pws;
    Uri capture_uri;
    String filePath;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GENDER_MALE:
                    mf_gender.setText("男");
                    userInfo.setGender("男");
                    gpsApplication.updateUserLoginInfo(userInfo);
                    break;
                case GENDER_FEMALE:
                    mf_gender.setText("女");
                    userInfo.setGender("女");
                    gpsApplication.updateUserLoginInfo(userInfo);
                    break;
                //TODO 联网更新到服务端，并发送event/广播给MainActivity更新头像等信息
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        initToolBar();
        initViews();

    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSettingActivity.this.onBackPressed();

            }
        });
    }

    private void initViews() {

        us_head=(ConstraintLayout)findViewById(R.id.us_header);
        us_anony=(LinearLayout)findViewById(R.id.us_anony);
        us_phone=(LinearLayout)findViewById(R.id.us_phone);
        us_email=(LinearLayout)findViewById(R.id.us_email);
        us_gender=(LinearLayout)findViewById(R.id.us_gender);
        us_addr=(LinearLayout)findViewById(R.id.us_addr);
        us_signature=(LinearLayout)findViewById(R.id.us_signature);

        mf_head=(ImageView) findViewById(R.id.mf_header);
        mf_anony=(TextView) findViewById(R.id.mf_anony);
        mf_phone=(TextView)findViewById(R.id.mf_phone);
        mf_email=(TextView)findViewById(R.id.mf_email);
        mf_gender=(TextView)findViewById(R.id.mf_gender);
        mf_addr=(TextView)findViewById(R.id.mf_addr);
        mf_signature=(TextView)findViewById(R.id.mf_signature);

        View view = getLayoutInflater().inflate(R.layout.dialog_select,null);
        pws=new PopupWindow(view,300,100);
        configPopupWindow();
        us_capture=(TextView)view.findViewById(R.id.us_camera);
        us_gallery=(TextView)view.findViewById(R.id.us_gallery);

        userInfo=new UserInfo();
        SharedPreferences sf = getSharedPreferences(Common.login_preference_name,MODE_PRIVATE);
        String currentUser=sf.getString(Common.current_user,"");
        filePath= Environment.getExternalStorageDirectory().getPath()
                + "/GPSLogger/Headers/header_" + currentUser+".jpg";
        capture_uri=Uri.fromFile(new File(filePath));

        File pic_f=new File(filePath);
        if(pic_f.exists()){
            Bitmap bitmap=BitmapFactory.decodeFile(filePath);
            mf_head.setImageBitmap(AppBitmap.getRoundBitmap(bitmap));
        }

        userInfo=gpsApplication.getUserLoginInfo(currentUser);
        mf_anony.setText(userInfo.getAnonymous().equals("")?"未填写":userInfo.getAnonymous());
        mf_phone.setText(userInfo.getPhone().equals("")?"未填写":userInfo.getPhone());
        mf_email.setText(userInfo.getEmail().equals("")?"未填写":userInfo.getEmail());
        mf_gender.setText(userInfo.getGender().equals("")?"未填写":userInfo.getGender());
        mf_addr.setText(userInfo.getAddress().equals("")?"未填写":userInfo.getAddress());
        mf_signature.setText(userInfo.getSignature().equals("")?"未填写":userInfo.getSignature());

        us_head.setOnClickListener(this);
        us_anony.setOnClickListener(this);
        us_phone.setOnClickListener(this);
        us_email.setOnClickListener(this);
        us_gender.setOnClickListener(this);
        us_addr.setOnClickListener(this);
        us_signature.setOnClickListener(this);
        us_capture.setOnClickListener(this);
        us_gallery.setOnClickListener(this);

    }

    private void configPopupWindow(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        int wd_width=dm.widthPixels;
        pws.setWidth(wd_width*3/4);
        pws.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置可以获取焦点
        pws.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        pws.setOutsideTouchable(true);
        // 更新popupwindow的状态
        pws.update();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.us_header:
                pws.showAtLocation(us_head, Gravity.TOP,0,getResources().getDisplayMetrics().heightPixels/4);
                break;
            case R.id.us_anony:
                startAlterActivity(Common.MODIFY_ANONYMOUS);
                break;
            case R.id.us_phone:
                startAlterActivity(Common.MODIFY_PHONE);
                break;
            case R.id.us_email:
                startAlterActivity(Common.MODIFY_EMAIL);
                break;
            case R.id.us_addr:
                startAlterActivity(Common.MODIFY_ADDR);
                break;
            case R.id.us_signature:
                startAlterActivity(Common.MODIFY_SIGNATURE);
                break;
            case R.id.us_camera:
                pws.dismiss();
                Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentc.putExtra(MediaStore.EXTRA_OUTPUT,capture_uri);
                startActivityForResult(intentc,Common.CAMERA_CAPTURE);
                break;
            case R.id.us_gallery:
                pws.dismiss();
                Intent intentg = new Intent(Intent.ACTION_PICK, null);
                intentg.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentg, Common.GALLERY_SELECT);
                break;
            case R.id.us_gender:
                String[] items=new String[]{"男","女"};
                int g_index=0;
                if(userInfo.getGender().equals("女")){
                    g_index=1;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(this)
                        //将items里的内容作为列表项显示在对话框中，index表示默认被选中的列表项id
                        .setSingleChoiceItems(items, g_index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    mHandler.sendEmptyMessage(GENDER_MALE);
                                }
                                else if(which==1){
                                    mHandler.sendEmptyMessage(GENDER_FEMALE);
                                }
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;

        }
    }

    private void startAlterActivity(int mode){
        Intent intent=new Intent(UserSettingActivity.this,USModifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("launch_mode", mode);
        intent.putExtra("mode",bundle);
        startActivityForResult(intent,Common.MODIFY_INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Common.MODIFY_INFO){
            String content = data.getBundleExtra("result").getString("content");
            switch (resultCode){
                case Common.MODIFY_ANONYMOUS:
                    mf_anony.setText(content);
                    userInfo.setAnonymous(content);
                    break;
                case Common.MODIFY_PHONE:
                    mf_phone.setText(content);
                    userInfo.setPhone(content);
                    break;
                case Common.MODIFY_EMAIL:
                    mf_email.setText(content);
                    userInfo.setEmail(content);
                    break;
                case Common.MODIFY_ADDR:
                    mf_addr.setText(content);
                    userInfo.setAddress(content);
                    break;
                case Common.MODIFY_SIGNATURE:
                    mf_signature.setText(content);
                    userInfo.setSignature(content);
                    break;
            }
            gpsApplication.updateUserLoginInfo(userInfo);
        }
        else if(requestCode == Common.CAMERA_CAPTURE && resultCode==RESULT_OK){
            if (data != null)
            {
                if(data.hasExtra("data")){
                    Bitmap bitmap = data.getParcelableExtra("data");
                    mf_head.setImageBitmap(AppBitmap.getRoundBitmap(bitmap));
                    AppBitmap.saveBitmap(this,bitmap,capture_uri.getPath());
                }
            }
            else
            {
                Bitmap bitmap = BitmapFactory.decodeFile(capture_uri.getPath());
                mf_head.setImageBitmap(AppBitmap.getRoundBitmap(bitmap));
            }
        }
        else if(requestCode == Common.GALLERY_SELECT && resultCode==RESULT_OK){
            Bitmap bitmap = AppBitmap.getBitmapFromContentUri(data.getData(), this);
            mf_head.setImageBitmap(AppBitmap.getRoundBitmap(bitmap));
            Log.d("BitmapDebug",capture_uri.getPath());
            AppBitmap.saveBitmap(this,bitmap,capture_uri.getPath());
        }


        //TODO 联网更新到服务端，并发送event/广播给MainActivity更新头像等信息
    }
}
