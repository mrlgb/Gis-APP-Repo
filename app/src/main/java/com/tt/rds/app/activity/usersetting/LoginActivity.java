package com.tt.rds.app.activity.usersetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.common.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button mLogin;
    TextView mForget;
    EditText et_user,et_pwd;
    CheckBox mRemember;
    ImageButton mClear,mExpand,mCollapse;
    PopupWindow pw;
    ListView mListView;
    List<Map<String,Object>> allusers;
    List<String> usernames;
    List<String> passwords;

    final MainApplication gpsApplication = MainApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

    }

    private void initViews(){

        mLogin=(Button)findViewById(R.id.login_bt);
        et_user=(EditText) findViewById(R.id.login_et_user);
        et_pwd=(EditText) findViewById(R.id.login_et_password);
        mForget=(TextView) findViewById(R.id.login_forget);
        mRemember=(CheckBox) findViewById(R.id.login_remember);
        mClear=(ImageButton)findViewById(R.id.login_clear);
        mExpand=(ImageButton)findViewById(R.id.login_expand);
        mCollapse=(ImageButton)findViewById(R.id.login_collapse);

        View view = getLayoutInflater().inflate(R.layout.popup_window_layout,null);
        mListView=(ListView)view.findViewById(R.id.popup_listview);
        getAllUserInfo();
        pw=new PopupWindow(view,300,100);
        configPopupWindow();
        mClear.setOnClickListener(this);
        mForget.setOnClickListener(this);
        mExpand.setOnClickListener(this);
        mCollapse.setOnClickListener(this);
        mLogin.setOnClickListener(this);

        //Clear visibility setting
        et_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                    mClear.setVisibility(View.VISIBLE);
                }
                else {
                    mClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mExpand.setVisibility(View.VISIBLE);
                mCollapse.setVisibility(View.INVISIBLE);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pw.dismiss();
                et_user.setText(usernames.get(i));
                et_pwd.setText(passwords.get(i));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_forget:
                Intent intent = new Intent(LoginActivity.this,FindPwdPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.login_clear:
                et_user.setText("");
                break;
            case R.id.login_expand:
                popupUsers();
                break;
            case R.id.login_collapse:
                collapseUsers();
                break;
            case R.id.login_bt:
                if(!checkETInput()){
                    //input error
                }
                else if(verifyUserInfo()){
                    //if verify passed
                    upateUserInfoToDB();
                    setLoginStatePreference();
                    finish();
                }
                break;

        }
    }

    //get user info from db
    private void getAllUserInfo(){
        allusers = gpsApplication.getUserLoginInfo();

        if(allusers.size()==0){
            mExpand.setVisibility(View.GONE);
            mCollapse.setVisibility(View.GONE);
        }
        else{
            usernames=new ArrayList<>();
            passwords= new ArrayList<>();
            for(int i=0;i<allusers.size();i++)
            {
                String name=(allusers.get(i)).get("user").toString();
                String pwd=(allusers.get(i)).get("password").toString();
                usernames.add(name);
                passwords.add(pwd);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.list_item,usernames);
            mListView.setAdapter(adapter);

        }
    }

    //configure pw display
    private void configPopupWindow(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        int wd_width=dm.widthPixels;

        pw.setWidth(wd_width-70);
        pw.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        // 设置背景颜色
        pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_rectangle_whitesmoke));
        // 设置可以获取焦点
        pw.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        pw.setOutsideTouchable(true);
        // 更新popupwindow的状态
        pw.update();
    }

    //popup window
    private void popupUsers(){
        mExpand.setVisibility(View.GONE);
        mCollapse.setVisibility(View.VISIBLE);
        pw.showAsDropDown(et_user,0,10);
    }

    //dismiss window
    private void collapseUsers(){
        pw.dismiss();
    }

    //add userinfo to local database
    private void upateUserInfoToDB(){
        String[] userinfo=new String[]{
                et_user.getText().toString(),
                et_pwd.getText().toString()
        };
        if(!mRemember.isChecked()){
            userinfo[1]="";
        }
        gpsApplication.updateUserLoginInfo(userinfo);
    }

    //TODO 联网验证身份
    private boolean verifyUserInfo(){

        return true;

    }

    //check if input is null
    private boolean checkETInput(){
        if(et_user.getText().toString().equals("") || et_pwd.getText().toString().equals("")){
            Toast.makeText(this,"用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Set login_state to 1 (已登录状态)，设置当前用户名
    private void setLoginStatePreference(){
        SharedPreferences sf = getSharedPreferences(ConstantValue.login_preference_name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt(ConstantValue.login_state,1);
        editor.putString(ConstantValue.current_user,et_user.getText().toString());
        editor.commit();
    }


}
