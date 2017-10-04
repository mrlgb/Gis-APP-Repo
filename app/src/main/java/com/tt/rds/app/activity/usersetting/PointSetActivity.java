package com.tt.rds.app.activity.usersetting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.tt.rds.app.MainApplication;
import com.tt.rds.app.R;
import com.tt.rds.app.activity.BaseSaveActivity;
import com.tt.rds.app.bean.PointType;

import java.util.ArrayList;
import java.util.List;

public class PointSetActivity extends BaseSaveActivity {

    private ListView mListView;
    private List<CheckBox> checkpoints;
    private List<PointType> pointAll;
    private List<String> points;
    private int[] pointTypes;

    final MainApplication gpsApplication = MainApplication.getInstance();

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
        points=new ArrayList<String>();
        checkpoints=new ArrayList<CheckBox>();

        pointAll=gpsApplication.getUsualPoints();
        pointTypes = new int[pointAll.size()];

        for(int i=0;i<pointAll.size();i++){
            points.add(pointAll.get(i).getName());
            pointTypes[i]=pointAll.get(i).getUsually();
        }
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return points.size();
        }

        @Override
        public Object getItem(int i) {
            return points.get(i);
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
            cb.setText(points.get(i));
            cb.setChecked(pointTypes[i]==1?true:false);
            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId=item.getItemId();
        if(itemId==R.id.save_activity){
            gpsApplication.setUsualPoints(getPointTypeLists());
            this.onBackPressed();
        }
        return true;
    }

    private int[] getPointTypeLists(){
        for(int i=0;i<pointTypes.length;i++){
            if(checkpoints.get(i).isChecked()){
                pointTypes[i]=1;
            }
            else{
                pointTypes[i]=0;
            }
        }
        return pointTypes;
    }
}
