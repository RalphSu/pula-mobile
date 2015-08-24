package com.yuhj.ontheway.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.utils.ImageCache;

/**
 * 
 * @author Liangfei
 *
 */
public class BookingTimeSegmentAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private Context context;

    // private LruCache<String, Bitmap> lruCache;

    public BookingTimeSegmentAdapter(Context context) {
        this.context = context;
//        lruCache = ImageCache.GetLruCache(context);
        data = new ArrayList<String>();
        data.add("09:00 - 10:30");
        data.add("13:00 - 14:00");
        data.add("14:30 - 15:30");
        data.add("16:00 - 17:00");
        data.add("19:00 - 20:00");
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHoleder viewHoleder = null;
        if (view == null) {
            viewHoleder = new ViewHoleder();
            view = LayoutInflater.from(context).inflate(R.layout.booking_day_item, null);
            viewHoleder.imageView = (ImageView) view.findViewById(R.id.bookingDay_imageView);
            viewHoleder.titleView = (TextView) view.findViewById(R.id.bookingDay_item_title);
            view.setTag(viewHoleder);
        } else {
            viewHoleder = (ViewHoleder) view.getTag();
        }
        String zhuantiData = data.get(position);
        viewHoleder.imageView.setImageResource(R.drawable.pula_logo_circle);
        viewHoleder.titleView.setText(zhuantiData);

        view.setTag(viewHoleder);
        return view;
    }

    private class ViewHoleder {
        public ImageView imageView;
        public TextView titleView;

    }

}
