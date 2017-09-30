package com.tt.rds.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2017/9/30.
 */

public class DataQueryPageAdapter extends PagerAdapter {
    Context mContext;
    List<View> lv;

    public DataQueryPageAdapter(Context context) {
        mContext = context;
        lv=new ArrayList<>();
        for(int i:new int[]{0,1,2}) {
            RecyclerView recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new DataQueryAdapter(mContext));
            lv.add(recyclerView);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(lv.get(position));
        return lv.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(lv.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence str="";
        switch (position){
            case 0:str="全部";break;
            case 1:str="点位";break;
            case 2:str="路段";break;
        }

        return str;
    }
}
