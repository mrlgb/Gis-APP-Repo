package com.tt.rds.app.activity.usersetting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.tt.rds.app.app.Application;
import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.app.Common;
import com.tt.rds.app.bean.PointType;
import com.tt.rds.app.bean.User;
import com.tt.rds.app.bean.UserDao;
import com.tt.rds.app.bean.UserPointType;
import com.tt.rds.app.bean.UserPointTypeDao;

import java.util.ArrayList;
import java.util.List;

public class PointSetActivity extends BaseSaveActivity {

    private ListView mListView;
    private List<CheckBox> checkpoints;
    private List<UserPointType> pointAll;
    private UserPointTypeDao userPointTypeDao;
    private UserDao userDao;
    private User current_user;

    final Application gpsApplication = Application.getInstance();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pointset;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);
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
                PointSetActivity.this.onBackPressed();

            }
        });
    }

    private void initViews(){
        mListView=(ListView)findViewById(R.id.pt_list);
        checkpoints=new ArrayList<CheckBox>();

        userPointTypeDao=gpsApplication.getDbService().getUserPointTypeDao();
        userDao = gpsApplication.getDbService().getUserDao();
        SharedPreferences sf=getSharedPreferences(Common.login_preference_name,MODE_PRIVATE);
        String cur_username=sf.getString(Common.current_user,"");
        current_user = userDao.queryBuilder().where(UserDao.Properties.User.eq(cur_username)).build().unique();
        pointAll = userPointTypeDao.queryBuilder().where(UserPointTypeDao.Properties.UserId.eq(current_user.getUserId())).build().list();

        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return pointAll.size();
        }

        @Override
        public Object getItem(int i) {
            return pointAll.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=getLayoutInflater().inflate(R.layout.checkbox_item,viewGroup,false);
                checkpoints.add((CheckBox)view);
            }
            CheckBox cb=(CheckBox)view;
            cb.setText(pointAll.get(i).getName());
            cb.setChecked(pointAll.get(i).getUsually()==1?true:false);
            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId=item.getItemId();
        if(itemId==R.id.save_activity){
            for(int i=0;i<pointAll.size();i++){
                if(checkpoints.get(i).isChecked()){
                    pointAll.get(i).setUsually(1);
                }
                else{
                    pointAll.get(i).setUsually(0);
                }
                userPointTypeDao.update(pointAll.get(i));
            }
            this.onBackPressed();
        }
        return true;
    }
}
