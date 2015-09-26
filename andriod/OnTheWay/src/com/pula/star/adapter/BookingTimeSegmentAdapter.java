package com.pula.star.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pula.star.R;

/**
 * 
 * @author Liangfei
 *
 */
public class BookingTimeSegmentAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private Context context;
    
    private final Map<String, Map<String, String>> courses;
    public static final String SEG5 = "19:00-20:00";
    public static final String SEG4 = "16:00-17:30";
    public static final String SEG3 = "14:30-15:30";
    public static final String SEG2 = "13:00-14:00";
    public static final String SEG1 = "09:00-10:30";
    public static final String DAY_KEY = "%4d-%02d-%02d";
    
    private String today;

    // private LruCache<String, Bitmap> lruCache;

    public BookingTimeSegmentAdapter(Context context, Map<String, Map<String, String>> courses, String day) {
        this.context = context;
//        lruCache = ImageCache.GetLruCache(context);
        data = new ArrayList<String>();
        data.add(SEG1);
        data.add(SEG2);
        data.add(SEG3);
        data.add(SEG4);
        data.add(SEG5);
        
        this.courses = courses;
        this.today = day;
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
            viewHoleder.contentView = (TextView) view.findViewById(R.id.bookingDay_item_course);
            view.setTag(viewHoleder);
        } else {
            viewHoleder = (ViewHoleder) view.getTag();
        }
        String zhuantiData = data.get(position);
//        viewHoleder.imageView.setImageResource(R.drawable.pula_logo_circle);
        viewHoleder.titleView.setText(zhuantiData);
        if (position % 2 == 0) {
            viewHoleder.titleView.setBackgroundColor(0xFFEC8B);
        } else {
            viewHoleder.titleView.setBackgroundColor(0xFFC125);
        }

        if (courses.containsKey(today)) {
            Map<String, String> segs = courses.get(today);
            if (segs.containsKey(zhuantiData)) {
                viewHoleder.titleView.setBackgroundColor(0X66cccc);
                viewHoleder.contentView.setText(segs.get(zhuantiData));
            }
        }

        view.setTag(viewHoleder);
        
        return view;
    }

    private class ViewHoleder {
        public ImageView imageView;
        public TextView titleView;
        public TextView contentView;

    }

}
