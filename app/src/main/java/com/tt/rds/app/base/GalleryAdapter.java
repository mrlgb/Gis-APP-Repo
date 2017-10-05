
package com.tt.rds.app.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.ArrayList;


public class GalleryAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<String> urls;

	public ArrayList<String> getUrls() {
		return urls;
	}


	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}


	public GalleryAdapter(Context context, ArrayList<String> urls) {
		this.context = context;
		this.urls = urls;
	}


	public int getCount() {
		return urls.size();
	}

	
	public Object getItem(int arg0) {
		return urls.get(arg0);
	}


	public long getItemId(int arg0) {
		return 0;
	}

	public void addItem(String s) {
		urls.add(s);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		LogUtils.d("position :" + position % urls.size());
		if(urls.get(position % urls.size())==null){
			ProgressBar pro = new ProgressBar(context);
			pro.setLayoutParams(new Gallery.LayoutParams(150, 200));
			return pro;
//			return LayoutInflater.from(context).inflate(R.layout.gallery_progressbar,null);
		}else{
			final ImageView imageView = new ImageView(context);
			String url = urls.get(position % urls.size());

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 3 ;
			
			Bitmap bmp = BitmapFactory.decodeFile(url,options);
			imageView.setImageBitmap(bmp);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(300, 400));
			return imageView;
		}
	}

}