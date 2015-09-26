/**
 * 
 */
package com.pula.star.activity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.pula.star.R;
import com.pula.star.adapter.BookingTimeSegmentAdapter;

/**
 * 预约的页面
 * 
 * @author Liangfei
 *
 */
public class BookingActivity extends BaseActivity {

    private ListView list;
    private String day;

    private static Map<String, Map<String, String>> courses = new HashMap<String, Map<String, String>>();
    static {
        DateTime now = DateTime.now();
        for (int i = 0; i < 60; i++) {
            DateTime dd = now.plusDays(i);
            
            String key = String.format(Locale.US, BookingTimeSegmentAdapter.DAY_KEY, dd.getYear(),
                    dd.getMonthOfYear(), dd.getDayOfMonth());

            Map<String, String> segCourses = new HashMap<String, String>();
            segCourses.put(BookingTimeSegmentAdapter.SEG1, "油画");
            segCourses.put(BookingTimeSegmentAdapter.SEG2, "");
            segCourses.put(BookingTimeSegmentAdapter.SEG3, "油画");
            segCourses.put(BookingTimeSegmentAdapter.SEG4, "油画");
            segCourses.put(BookingTimeSegmentAdapter.SEG5, "");
            
            courses.put(key, segCourses);
        }
    }

    @Override
    @SuppressLint("InflateParams")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_booking, null);

        DateTime selected = (DateTime) getIntent().getSerializableExtra("calSelected");
        day = String.format(Locale.US, BookingTimeSegmentAdapter.DAY_KEY, selected.getYear(),
                selected.getMonthOfYear(), selected.getDayOfMonth());
        this.setTitle(String.format("预订听课  %s", day));
        View bookingRelative = view.findViewById(R.id.bookingDay);

        setContentView(view);

        list = (ListView) bookingRelative.findViewById(R.id.bookingList);
        BookingTimeSegmentAdapter adapter = new BookingTimeSegmentAdapter(this, courses, day);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
