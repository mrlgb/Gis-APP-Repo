package com.tt.rds.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tt.rds.app.R;
import com.tt.rds.app.util.DisplayUtility;

import java.io.File;
import java.util.List;


/**
 * Created by Jacek Kwiecień on 08.11.2016.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<File> imagesFiles;
    private int gridItemWidth;

    private OnItemClickListener clickListener=null;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public ImagesAdapter(Context context, List<File> imagesFiles) {
        this.context = context;
        this.imagesFiles = imagesFiles;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.view_image, parent, false));

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int screenWidth = DisplayUtility.getScreenWidth(context);
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(context)) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        gridItemWidth = screenWidth / numOfColumns;
        Picasso.with(context)
                .load(imagesFiles.get(position))
                .resize(gridItemWidth,0)
                .into(holder.imageView);

    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            //注意这里使用getTag方法获取position
            clickListener.onClick(v,(int)v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return imagesFiles.size();
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public ImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image_view);
            imageButton=itemView.findViewById(R.id.ibtn_delete);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(itemView, getAdapterPosition());
            }
        }

    }
}
