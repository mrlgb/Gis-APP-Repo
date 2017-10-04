package com.tt.rds.app.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tt.rds.app.R;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by nigelhenshaw on 25/06/2015.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private File imagesFile;
    private String  filter;



    public ImageAdapter(File folderFile,String filterStr ) {
        imagesFile = folderFile;
        filter=filterStr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_images_relative_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File imageFile = imagesFile.listFiles(new myFileFilter())[position];
        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.getImageView().setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        if(imagesFile.listFiles()!=null)
            return imagesFile.listFiles().length>5?5:imagesFile.listFiles().length;
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageGalleryView);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public class myFileFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            String filename = pathname.getName().toLowerCase();
            if(filename.contains("filter")){
                return false;
            }else{
                return true;
            }
        }
    }
}
