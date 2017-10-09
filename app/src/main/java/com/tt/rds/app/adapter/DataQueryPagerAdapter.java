package com.tt.rds.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2017/10/2.
 */

public class DataQueryPagerAdapter extends PagerAdapter {
    List<View> mViewList;
    Context mContext;

    public DataQueryPagerAdapter(Context context) {
        mContext = context;
        mViewList = new ArrayList<>();

        for (int i : new int[]{0, 1, 2, 3, 4}) {
            RecyclerView recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            recyclerView.setAdapter(new DataQueryAdapter(mContext,i));
            mViewList.add(recyclerView);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String str = "";
        switch (position) {
            case 0:
                str = "全部";
                break;
            case 1:
                str = "路线";
                break;
            case 2:
                str = "构造物";
                break;
            case 3:
                str = "沿线设施";
                break;
            case 4:
                str = "地名";
                break;
        }
        return str;
    }
}
