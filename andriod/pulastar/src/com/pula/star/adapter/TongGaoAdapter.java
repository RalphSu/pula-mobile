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
import com.pula.star.bean.CourseData;
import com.pula.star.bean.TongGaoData;
import com.pula.star.utils.ImageCache;
import com.squareup.picasso.Picasso;


public class TongGaoAdapter extends BaseAdapter {
    private ArrayList<TongGaoData> data;
    private Context context;
    private LruCache<String, Bitmap> lruCache;

    String color[] = {"#00B2EE","#9A32CD","#006699","#CC3366","#A0522D","#8B814C","#FF7F24","#EE1289"};
    
    public void BindData(ArrayList<TongGaoData> data) {

        this.data = data;
    }

    public TongGaoAdapter(Context context) {
        this.context = context;
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
        ViewHoleder viewHoleder = null;
        if (view == null) {
            viewHoleder = new ViewHoleder();
            view = LayoutInflater.from(context).inflate(R.layout.tonggao_item, null);
            viewHoleder.imageView = (ImageView) view.findViewById(R.id.tonggao_imageView);
            view.setTag(viewHoleder);
        } else {
            viewHoleder = (ViewHoleder) view.getTag();
        }
        
        TongGaoData tongGao = data.get(position);

        String url = "http://121.40.151.183:8080/pula-sys/app/image/icon?sub=" + tongGao.getSub() + "&fp=" + tongGao.getImage();
        
        Picasso.with(context).load(url)
		.placeholder(R.drawable.deer).error(R.drawable.deer)
		.into( viewHoleder.imageView);
        return view;
    }

    private class ViewHoleder {
        public ImageView imageView;
        public TextView titleView;
        public TextView classRoomName;
        public TextView branchName;

    }

}
