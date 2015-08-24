/**
 * 
 */
package com.yuhj.ontheway.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.adapter.BookingTimeSegmentAdapter;
import com.yuhj.ontheway.adapter.NumberHelper;

/**
 * 预约的页面
 * 
 * @author Liangfei
 *
 */
public class BookingActivity extends BaseActivity {

    private ListView list;

    @Override
    @SuppressLint("InflateParams")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_booking, null);

        Calendar selected = (Calendar) getIntent().getSerializableExtra("calSelected");
        View bookingRelative = view.findViewById(R.id.bookingDay);
        TextView title = (TextView) bookingRelative.findViewById(R.id.bookingDate);
        String s = selected.get(Calendar.YEAR) + "-" + NumberHelper.LeftPad_Tow_Zero(selected.get(Calendar.MONTH))
                + NumberHelper.LeftPad_Tow_Zero(selected.get(Calendar.DATE));
        title.setText(s);

        list = (ListView) bookingRelative.findViewById(R.id.bookingList);
        BookingTimeSegmentAdapter adapter = new BookingTimeSegmentAdapter(this);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
