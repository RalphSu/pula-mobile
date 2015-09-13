/**
 * 
 */
package com.yuhj.ontheway.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.adapter.BookingTimeSegmentAdapter;
import com.yuhj.ontheway.adapter.NumberHelper;

/**
 * 预约信息对话页面
 * 分登录和未登录两种情况处理
 * 
 */

public class BookingActivity extends BaseActivity {

    private ListView list;

    @Override
    @SuppressLint("InflateParams")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_booking, null);

        Calendar selected = (Calendar) getIntent().getSerializableExtra("calSelected");
        View bookingRelative = view.findViewById(R.id.bookingDay);
        TextView title = (TextView) bookingRelative.findViewById(R.id.bookingDate);
        String s = selected.get(Calendar.YEAR) + "-" + NumberHelper.LeftPad_Tow_Zero(selected.get(Calendar.MONTH))
                + NumberHelper.LeftPad_Tow_Zero(selected.get(Calendar.DATE));
        
        
        Log.e("BookingActivity",s);
               
        list = (ListView) bookingRelative.findViewById(R.id.bookingList);
        BookingTimeSegmentAdapter adapter = new BookingTimeSegmentAdapter(this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        
        
        
    }
}
