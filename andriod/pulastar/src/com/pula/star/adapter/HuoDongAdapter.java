package com.pula.star.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.bean.HuoDongData;
import com.pula.star.utils.ImageCache;
import com.squareup.picasso.Picasso;

/**
 * @name ZhuantiAdapter
 * @Descripation 专题<br>
 * @author 禹慧军
 * @date 2014-10-24
 * @version 1.0
 */
public class HuoDongAdapter extends BaseAdapter {
	private ArrayList<HuoDongData> data;
	private Context context;
	private LruCache<String,Bitmap> lruCache;
	String color[] = {"#B22222","#8B2500","#B03060","#00B2EE","#A0522D","#8B814C","#FF7F24","#EE1289","#9A32CD","#006699","#CC3366"};
	
	public void BindData(ArrayList<HuoDongData> data){
		this.data = data;
	}
	
	public HuoDongAdapter(Context context){
		this.context=context;
		lruCache = ImageCache.GetLruCache(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHoleder viewHoleder =null;
		if (view==null) {
            viewHoleder = new ViewHoleder();
            view = LayoutInflater.from(context).inflate(R.layout.huodong_item, null);
            viewHoleder.imageView = (ImageView) view.findViewById(R.id.huodong_imageView);
            viewHoleder.textView = (TextView) view.findViewById(R.id.huodong_item_title);
            viewHoleder.dateView = (TextView) view.findViewById(R.id.huodong_item_date);
			view.setTag(viewHoleder);
		}else {
			viewHoleder = (ViewHoleder) view.getTag();
		}
		
        HuoDongData huoDongData = data.get(position);
        viewHoleder.textView.setText(huoDongData.getTitle());
        viewHoleder.textView.setTextColor(Color.parseColor(color[position%11]));
//		viewHoleder.imageView.setImageResource(R.drawable.defaultcovers);
//		viewHoleder.imageView.setTag(huoDongData.getImage());
//		new ImageCache(context, lruCache, viewHoleder.imageView, huoDongData.getIamge(),"OnTheWay",800, 400);

				
		Picasso.with(context).load(huoDongData.getImage())
				.placeholder(R.drawable.deer).error(R.drawable.deer)
				.into( viewHoleder.imageView);
		
		return view;
	}
	
	private class ViewHoleder{
		public ImageView imageView;
		public TextView textView;
		public TextView dateView;
		
	}

}
