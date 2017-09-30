package com.tt.rds.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.rds.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alpha Dog on 2017/9/30.
 */

public class DataQueryAdapter extends RecyclerView.Adapter <DataQueryAdapter.DataViewHolder>{
    Context mContext;

    public DataQueryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_data_query, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.render();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_code)
        TextView mTvCode;
        @BindView(R.id.tv_long)
        TextView mTvLong;
        @BindView(R.id.tv_about)
        TextView mTvAbout;
        @BindView(R.id.iv_dropdown)
        ImageView mIvDropdown;

        DataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        void render(){
            mIvImage.setBackgroundColor(Color.parseColor("#000000"));
            mTvName.setText("桥梁名称：收梁沟桥");
            mTvCode.setText("桥梁编码：C0019L28123216");
            mTvLong.setText("桥梁全长：48(m)");
            String base="采集相关：2017-3-1 15：30 张三 ";
            String type="未上报";
            SpannableString ss=new SpannableString(base+type);
            ss.setSpan(new ForegroundColorSpan(Color.RED),base.length(),(base+type).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvAbout.setText(ss);
        }
    }
}
