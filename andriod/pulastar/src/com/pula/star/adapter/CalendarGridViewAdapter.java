package com.pula.star.adapter;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.fragment.BookingFragment;

public class CalendarGridViewAdapter extends BaseAdapter {

    private DateTime calStartDate = DateTime.now();// start day of current calendar

    private DateTime daySelected = DateTime.now().millisOfDay().setCopy(0); // selected day

    public void setSelectedDate(DateTime cal) {
        daySelected = cal;
    }

    private DateTime today = DateTime.now(); // 今日
    private int currentMonth = 0; // 当前视图月

    private void updateStartDateForCalendar() {
        calStartDate = calStartDate.dayOfMonth().setCopy(1).millisOfDay().setCopy(0);
        currentMonth = calStartDate.getMonthOfYear();// 得到当前日历显示的月

        // first column is Sunday
        int monthStartDayOfWeek = calStartDate.getDayOfWeek();
        if (monthStartDayOfWeek != 7) {
            calStartDate = calStartDate.plusDays(-monthStartDayOfWeek);
        }
    }

    private ArrayList<DateTime> titles;
    private Activity activity;
    private Resources resources;
    private BookingFragment bookingFragment;

    private ArrayList<DateTime> getDates() {

        updateStartDateForCalendar();

        ArrayList<DateTime> alArrayList = new ArrayList<DateTime>();
        // a calendar contains 6 * 7 tiles
        for (int i = 0; i <= 41; i++) {
            alArrayList.add(calStartDate.plusDays(i));
        }

        return alArrayList;
    }

    // construct
    private CalendarGridViewAdapter(Activity a, DateTime cal) {
        calStartDate = cal;
        activity = a;
        resources = activity.getResources();
        titles = getDates();
    }

    public CalendarGridViewAdapter(Activity activity2, DateTime currentCalendar, BookingFragment bookingFragment) {
        this(activity2, currentCalendar);
        this.bookingFragment = bookingFragment;
    }

    public int getCount() {
        return titles.size();
    }

    public DateTime getItem(int position) {
        return titles.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout iv = new LinearLayout(activity);
        iv.setId(position + 5000); //just to make a special id that not-conflict with auto-generated ids
        LinearLayout imageLayout = new LinearLayout(activity);
        imageLayout.setOrientation(0);
        iv.setGravity(Gravity.CENTER);
        iv.setOrientation(1);
        iv.setBackgroundColor(resources.getColor(R.color.white));

        DateTime tileDate = getItem(position);

        // set background for Saturday and Sunday
        iv.setBackgroundColor(resources.getColor(R.color.white));
        int dayOfWeek = tileDate.getDayOfWeek();
        if (dayOfWeek == DateTimeConstants.SATURDAY) {
            iv.setBackgroundColor(resources.getColor(R.color.text_6));
        } else if (dayOfWeek == DateTimeConstants.SUNDAY) {
            iv.setBackgroundColor(resources.getColor(R.color.text_7));
        } else {
            // normal day
        }

        TextView txtToDay = new TextView(activity);// 日本老黄历
        txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
        txtToDay.setTextSize(9);
        if (today.getDayOfYear() == tileDate.getDayOfYear()) {
            iv.setBackgroundColor(resources.getColor(R.color.event_center));
            txtToDay.setText("今天");
        }

        // 设置背景颜色
        if (daySelected.equals(tileDate)) {
            // 选择的
            iv.setBackgroundColor(resources.getColor(R.color.selection));
        } else {
            if (today.getDayOfYear() == tileDate.getDayOfYear()) {
                // 当前日期
                iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
            }
        }
        // 设置背景颜色结束

        // 日期开始
        TextView txtDay = new TextView(activity);// 日期
        txtDay.setGravity(Gravity.CENTER_HORIZONTAL);

        // check if current month : to fill the calendar, some adjacent days of next/prev month would be added
        if (tileDate.getMonthOfYear() == currentMonth) {
            txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
            txtDay.setTextColor(resources.getColor(R.color.Text));
        } else {
            txtDay.setTextColor(resources.getColor(R.color.noMonth));
            txtToDay.setTextColor(resources.getColor(R.color.noMonth));
        }

        int day = tileDate.getDayOfMonth(); // 日期
        txtDay.setText(String.valueOf(day));
        txtDay.setId(position + 500);
        iv.setTag(tileDate);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        iv.addView(txtDay, lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        iv.addView(txtToDay, lp1);
        // 日期结束
        iv.setOnClickListener(view_listener);

        return iv;
    }

    private OnClickListener view_listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (bookingFragment != null) {
                bookingFragment.notifyCalendarDaySelected((LinearLayout) v);
            }
            Log.i(v.getTag().toString(), String.format("Calendar grid view id %s, %s clicked!", v.getId(), v.getTag().toString()));
        }
    };

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
