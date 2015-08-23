package com.yuhj.ontheway.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.fragment.BookingFragment;

public class CalendarGridViewAdapter extends BaseAdapter {

    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历

    public void setSelectedDate(Calendar cal) {
        calSelected = cal;
    }

    private Calendar calToday = Calendar.getInstance(); // 今日
    private int iMonthViewCurrentMonth = 0; // 当前视图月

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }

    ArrayList<java.util.Date> titles;

    private ArrayList<java.util.Date> getDates() {

        UpdateStartDateForMonth();

        ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

        for (int i = 1; i <= 42; i++) {
            alArrayList.add(calStartDate.getTime());
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alArrayList;
    }

    private Activity activity;
    Resources resources;

    // construct
    public CalendarGridViewAdapter(Activity a, Calendar cal) {
        calStartDate = cal;
        activity = a;
        resources = activity.getResources();
        titles = getDates();
    }

    public CalendarGridViewAdapter(Activity a) {
        activity = a;
        resources = activity.getResources();
    }

    private BookingFragment bookingFragment;

    public CalendarGridViewAdapter(Activity activity2, Calendar currentCalendar, BookingFragment bookingFragment) {
        this(activity2, currentCalendar);
        this.bookingFragment = bookingFragment;
    }

    public int getCount() {
        return titles.size();
    }

    public Object getItem(int position) {
        return titles.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout iv = new LinearLayout(activity);
        iv.setId(position + 5000);
        LinearLayout imageLayout = new LinearLayout(activity);
        imageLayout.setOrientation(0);
        iv.setGravity(Gravity.CENTER);
        iv.setOrientation(1);
        iv.setBackgroundColor(resources.getColor(R.color.white));

        Date myDate = (Date) getItem(position);
        Calendar calCalendar = Calendar.getInstance();
        calCalendar.setTime(myDate);

        final int iMonth = calCalendar.get(Calendar.MONTH);
        final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);

        // 判断周六周日
        iv.setBackgroundColor(resources.getColor(R.color.white));
        if (iDay == 7) {
            // 周六
            iv.setBackgroundColor(resources.getColor(R.color.text_6));
        } else if (iDay == 1) {
            // 周日
            iv.setBackgroundColor(resources.getColor(R.color.text_7));
        } else {

        }
        // 判断周六周日结束

        TextView txtToDay = new TextView(activity);// 日本老黄历
        txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
        txtToDay.setTextSize(9);
        if (equalsDate(calToday.getTime(), myDate)) {
            // 当前日期
            iv.setBackgroundColor(resources.getColor(R.color.event_center));
            txtToDay.setText("TODAY!");
        }

        // 设置背景颜色
        if (equalsDate(calSelected.getTime(), myDate)) {
            // 选择的
            iv.setBackgroundColor(resources.getColor(R.color.selection));
        } else {
            if (equalsDate(calToday.getTime(), myDate)) {
                // 当前日期
                iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
            }
        }

        // highlight booking date
        if (bookingFragment.getBookingData() != null) {
            // TODO highlight the booking event day
//            iv.setBackgroundColor(resources.getColor(R.color.BookingDay));
        }

        // 设置背景颜色结束

        // 日期开始
        TextView txtDay = new TextView(activity);// 日期
        txtDay.setGravity(Gravity.CENTER_HORIZONTAL);

        // 判断是否是当前月
        if (iMonth == iMonthViewCurrentMonth) {
            txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
            txtDay.setTextColor(resources.getColor(R.color.Text));
        } else {
            txtDay.setTextColor(resources.getColor(R.color.noMonth));
            txtToDay.setTextColor(resources.getColor(R.color.noMonth));
        }

        int day = myDate.getDate(); // 日期
        txtDay.setText(String.valueOf(day));
        txtDay.setId(position + 500);
        iv.setTag(myDate);

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

    @SuppressWarnings("deprecation")
    private Boolean equalsDate(Date date1, Date date2) {

        if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }

    }

}
