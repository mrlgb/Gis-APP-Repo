package com.tt.rds.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tt.rds.app.R;
import com.tt.rds.app.activity.DataQueryDetailActivity;
import com.tt.rds.app.app.GPSApplication;
import com.tt.rds.app.bean.PointMarker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2017/10/2.
 */

public class DataQueryAdapter extends RecyclerView.Adapter<DataQueryAdapter.ViewHolder> {
    private Context mContext;
    private int mType;
    List<PointMarker> mDataPoints, mResultDataPoint;
//    private String[] pointType = {"Point_Bridge", "Point_Tunnel", "Point_Ferry", "Point_Culvert", "Point_Town", "Point_Village", "Point_StandardVillage", "Point_School", "Point_Sign"};

    public DataQueryAdapter(Context context, int type) {
        mContext = context;
        mType = type;
        mResultDataPoint = new ArrayList<>();
        mDataPoints = GPSApplication.getInstance().getDbService().getPointMarkerDao().loadAll();
        if(mDataPoints!=null)
        switch (mType) {
            case 0:
                mResultDataPoint.addAll(mDataPoints);
                break;
            case 2:
                for (PointMarker bean : mDataPoints) {
                    if ("0123".contains(bean.getTtPointId().toString()))
                        mResultDataPoint.add(bean);
                }
                break;
            case 3:
                for(PointMarker bean : mDataPoints) {
                    if ("4567".contains(bean.getTtPointId().toString()))
                        mResultDataPoint.add(bean);
                }
                break;
            case 4:
                for(PointMarker bean : mDataPoints) {
                    if ("89".contains(bean.getTtPointId().toString()))
                        mResultDataPoint.add(bean);
                }
                break;
        }
    }

    @Override
    public DataQueryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_data_query, parent, false));
    }

    @Override
    public void onBindViewHolder(DataQueryAdapter.ViewHolder holder, int position) {
        if (mType != 1 && !mResultDataPoint.isEmpty())
            holder.render(mContext, mResultDataPoint.get(position));
    }

    @Override
    public int getItemCount() {
        if (mResultDataPoint == null)
            return 0;
        return mResultDataPoint.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivdropdown;
        TextView tvabout;
        TextView tvlong;
        TextView tvcode;
        TextView tvname;
        ImageView ivimg;

        public ViewHolder(View itemView) {
            super(itemView);
            ivdropdown = (ImageView) itemView.findViewById(R.id.iv_dropdown);
            tvabout = (TextView) itemView.findViewById(R.id.tv_about);
            tvlong = (TextView) itemView.findViewById(R.id.tv_long);
            tvcode = (TextView) itemView.findViewById(R.id.tv_code);
            tvname = (TextView) itemView.findViewById(R.id.tv_name);
            ivimg = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        void render(final Context context, final PointMarker bean) {
            if(bean.getTtPoint()!=null)
            switch (bean.getTtPoint().getPTypeId().intValue()) {
                case 0:
                    tvlong.setText("桥梁全长：");
                    tvcode.setText("桥梁编码：" + bean.getCode());
                    tvname.setText("桥梁名称：" + bean.getName());
                    break;
                case 1:
                    tvlong.setText("隧道长度：");
                    tvcode.setText("隧道编码：" + bean.getCode());
                    tvname.setText("隧道名称：" + bean.getName());
                    break;
                case 2:
                    tvlong.setText("渡口类型：" + bean.getCatergory());
                    tvcode.setText("渡口编码：" + bean.getCode());
                    tvname.setText("渡口名称：" + bean.getName());
                    break;
                case 3:
                    tvlong.setText("涵洞跨径：");
                    tvcode.setText("涵洞编码：" + bean.getCode());
                    tvname.setText("涵洞名称：" + bean.getName());
                    break;
                case 4:
                    tvlong.setText("乡镇行政区：" + bean.getTtPoint().getAdminCode());
                    tvcode.setText("乡镇编码：" + bean.getCode());
                    tvname.setText("乡镇名称：" + bean.getName());
                    break;
                case 5:
                    tvlong.setText("建制村行政区划：" + bean.getTtPoint().getAdminCode());
                    tvcode.setText("建制村编码：" + bean.getCode());
                    tvname.setText("建制村名称：" + bean.getName());
                    break;
                case 6:
                    tvlong.setText("自然村行政区划：" + bean.getTtPoint().getAdminCode());
                    tvcode.setText("自然村编码：" + bean.getCode());
                    tvname.setText("自然村名称：" + bean.getName());
                    break;
                case 7:
                    tvlong.setText("学校类别：" + bean.getCatergory());
                    tvcode.setText("学校编码：" + bean.getCode());
                    tvname.setText("学校名称：" + bean.getName());
                    break;
                case 8:
                    tvlong.setText("标志标牌类型：" + bean.getCatergory());
                    tvcode.setText("标志标牌编码：" + bean.getCode());
                    tvname.setText("标志标牌类型：" + bean.getName());
                    break;
                case 9:
                    tvlong.setText("标志点类型：" + bean.getCatergory());
                    tvcode.setText("标志点编码：" + bean.getCode());
                    tvname.setText("标志点名称：" + bean.getName());
                    break;
            }

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DataQueryDetailActivity.class);
                    intent.putExtra("typeId",bean.getTtPointId());
                    intent.putExtra("id", bean.getPMarkerId());
                    context.startActivity(intent);
                }
            });

            Bitmap bitmap = null;
            try
            {
                File file = new File(bean.getTtPoint().getPictures().get(0).getPath());
                if(file.exists())
                {
                    bitmap = BitmapFactory.decodeFile(bean.getTtPoint().getPictures().get(0).getPath());
                    ivimg.setImageBitmap(bitmap);
                }
            } catch (Exception e)
            {
                // TODO: handle exception
            }
            String name = String.format("采集相关: %s ", bean.getUserId());
            String now = 0 == 0 ? "未上报" : "已上报";
            SpannableString ss = new SpannableString(name + now);
            if (0 == 0)
                ss.setSpan(new ForegroundColorSpan(Color.RED), name.length(), (name + now).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            else
                ss.setSpan(new ForegroundColorSpan(Color.GREEN), name.length(), (name + now).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tvabout.setText(ss);
            ivdropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view=LayoutInflater.from(context).inflate(R.layout.popup_data_query,null);
                    final PopupWindow popupWindow=new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                    popupWindow.setTouchable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popupWindow.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                    popupWindow.showAsDropDown(ivdropdown);
                }
            });
        }
    }
}
