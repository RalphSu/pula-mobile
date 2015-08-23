package com.yuhj.ontheway.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.adapter.CalendarGridViewAdapter;
import com.yuhj.ontheway.adapter.NumberHelper;
import com.yuhj.ontheway.bean.BookingData;
import com.yuhj.ontheway.clients.ClientApi;

/**
 * @name JingXuanFragment
 * @Descripation 精选界面的设计<br>
 *		1、<br>
 *		2、<br>
 *      3、<br>
 * @author
 * @date 2014-10-23
 * @version 1.0
 */
public class BookingFragment extends Fragment implements OnTouchListener {

    //判断手势用
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //动画
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private ViewFlipper viewFlipper;
    private GestureDetector mGesture = null;

    public boolean onTouch(View v, MotionEvent event) {
        return mGesture.onTouchEvent(event);
    }

    private AnimationListener animationListener=new AnimationListener() {
        public void onAnimationStart(Animation animation) {
        }
        
        public void onAnimationRepeat(Animation animation) {
        }
        
        public void onAnimationEnd(Animation animation) {
            //当动画完成后调用
            CreateGirdView();
        }
    };

    private class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                    setNextViewItem();
                    //CreateGirdView();
                    return true;

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                    viewFlipper.showPrevious();
                    setPrevViewItem();
                    //CreateGirdView();
                    return true;

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // ListView lv = getListView();
            //得到当前选中的是第几个单元格
            int pos = ((GridView)viewFlipper.getCurrentView()).pointToPosition((int) e.getX(), (int) e.getY());
            LinearLayout txtDay = (LinearLayout) ((GridView)viewFlipper.getCurrentView()).findViewById(pos + 5000);
            notifyCalendarDaySelected(txtDay);

            Log.i("TEST", "onSingleTapUp -  pos=" + pos);

            return false;
        }
    }

    /**
     * Called when a calendar day grid was clicked
     * @param clickedDayLayout
     */
    public void notifyCalendarDaySelected(LinearLayout clickedDayLayout) {
        if (clickedDayLayout != null && clickedDayLayout.getTag() != null) {
            Date date = (Date) clickedDayLayout.getTag();
            calSelected.setTime(date);

            gCurrentAdapter.setSelectedDate(calSelected);
            gCurrentAdapter.notifyDataSetChanged();

            gPrevAdapter.setSelectedDate(calSelected);
            gPrevAdapter.notifyDataSetChanged();

            gNextAdapter.setSelectedDate(calSelected);
            gNextAdapter.notifyDataSetChanged();
            
        }
    }
    
    public BookingFragment(Context context) {
        this.mContext = context;
    }

    // 基本变量
    private Context mContext = null;
    private GridView title_gView;
    private GridView gPrevView;// 上一个月
    private GridView gCurrentView;// 当前月
    private GridView gNextView;// 下一个月
    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历
    private Calendar calToday = Calendar.getInstance(); // 今日
    private CalendarGridViewAdapter gCurrentAdapter;
    private CalendarGridViewAdapter gPrevAdapter;
    private CalendarGridViewAdapter gNextAdapter;
    // 顶部按钮
    private Button btnToday = null;
    private RelativeLayout mainLayout;
    
    private List<BookingData> bookingData = new ArrayList<BookingData>();
    
    class DownData extends AsyncTask<Void, Void, List<BookingData>> {

    @Override
        protected List<BookingData> doInBackground(Void... arg0) {
            return ClientApi.getBookingList("", "");
        }

        @Override
        protected void onPostExecute(final List<BookingData> result) {
            super.onPostExecute(result);
            if (result != null) {
                bookingData = result;
            } else {
                Toast.makeText(BookingFragment.this.getActivity(), "网络异常,请检查", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new DownData().execute();

        generateContentView(inflater, container);
        UpdateStartDateForMonth();

        slideLeftIn = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_right_out);

        slideLeftIn.setAnimationListener(animationListener);
        slideLeftOut.setAnimationListener(animationListener);
        slideRightIn.setAnimationListener(animationListener);
        slideRightOut.setAnimationListener(animationListener);

        mGesture = new GestureDetector(this.mContext, new GestureListener());

        return mainLayout;
    }

    //
    private int iMonthViewCurrentMonth = 0; // 当前视图月
    private int iMonthViewCurrentYear = 0; // 当前视图年
    private int iFirstDayOfWeek = Calendar.MONDAY;
    /** 底部菜单文字 **/
    String[] menu_toolbar_name_array;


    private View generateContentView(LayoutInflater inflater, ViewGroup container) {
        View bookingFragment = inflater.inflate(R.layout.booking_fragment, container, false);
        mainLayout = (RelativeLayout) bookingFragment.findViewById(R.id.bookingRelativelayout);

        LinearLayout layTopControls = (LinearLayout) bookingFragment.findViewById(R.id.bookingTopLinearlayout);
        generateTopButtons(layTopControls); // 生成顶部按钮 （上一月，下一月，当前月）

        setTitleGirdView();

        calStartDate = getCalendarStartDate();
        viewFlipper = (ViewFlipper) mainLayout.findViewById(R.id.bookingViewFlipper);
        CreateGirdView();

        LinearLayout br = (LinearLayout) mainLayout.findViewById(R.id.bookingContentLinearlayout);
        br.setBackgroundColor(getResources().getColor(R.color.calendar_background));

        return mainLayout;
    }


    // 生成顶部按钮
    // 参数：布局
    private void generateTopButtons(LinearLayout layTopControls) {
        btnToday = (Button)layTopControls.findViewById(R.id.bookingTodayButton);
        // 创建一个当前月按钮（中间的按钮）
        btnToday.setBackgroundResource(Color.TRANSPARENT);

        // 当前月的点击事件的监听
        btnToday.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                setToDayViewItem();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void setTitleGirdView() {

        title_gView = (GridView) mainLayout.findViewById(R.id.bookingTitleGridView);
        title_gView.setVerticalSpacing(0);// 垂直间隔
        title_gView.setHorizontalSpacing(0);// 水平间隔
        TitleGridAdapter titleAdapter = new TitleGridAdapter(this.getActivity());
        title_gView.setAdapter(titleAdapter);// 设置菜单Adapter
        title_gView.setBackgroundColor(getResources().getColor(R.color.calendar_background));

        WindowManager windowManager = this.getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        title_gView.setPadding(x, 0, 0, 0);// 居中
    }

    private void CreateGirdView() {

        Calendar prevCalendar = Calendar.getInstance(); // 临时
        Calendar currentCalendar = Calendar.getInstance(); // 临时
        Calendar nextCalendar = Calendar.getInstance(); // 临时
        prevCalendar.setTime(calStartDate.getTime());
        currentCalendar.setTime(calStartDate.getTime());
        nextCalendar.setTime(calStartDate.getTime());

        // pre month
        gPrevAdapter = new CalendarGridViewAdapter(this.getActivity(), prevCalendar, this);
        prevCalendar.add(Calendar.MONTH, -1);
        gPrevView = createCalendarView(prevCalendar, R.id.prevMonthCalendarView, gPrevAdapter);

        gCurrentAdapter = new CalendarGridViewAdapter(this.getActivity(), currentCalendar, this);
        gCurrentView = createCalendarView(prevCalendar, R.id.currentMonthCalendarView, gCurrentAdapter);

        nextCalendar.add(Calendar.MONTH, 1);
        gNextAdapter = new CalendarGridViewAdapter(this.getActivity(), nextCalendar, this);
        gNextView = createCalendarView(prevCalendar, R.id.nextMonthCalendarView, gNextAdapter);

        gCurrentView.setOnTouchListener(this);
        gPrevView.setOnTouchListener(this);
        gNextView.setOnTouchListener(this);

    }

    @SuppressWarnings("deprecation")
    private GridView createCalendarView(Calendar tempSelected1, int id, CalendarGridViewAdapter adapter) {
        GridView gview = (GridView)viewFlipper.findViewById(id);
        
        gview.setAdapter(adapter);// 设置菜单Adapter
        gview.setBackgroundColor(getResources().getColor(R.color.calendar_background));
        
        WindowManager windowManager = ((Activity)mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        gview.setPadding(x, 0, 0, 0);// 居中
        return gview;
    }

    // 上一个月
    private void setPrevViewItem() {
        iMonthViewCurrentMonth--;// 当前选择月--
        // 如果当前月为负数的话显示上一年
        if (iMonthViewCurrentMonth == -1) {
            iMonthViewCurrentMonth = 11;
            iMonthViewCurrentYear--;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1); // 设置日为当月1日
        calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth); // 设置月
        calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear); // 设置年
    }

    // 当月
    private void setToDayViewItem() {
        calSelected.setTimeInMillis(calToday.getTimeInMillis());
        calSelected.setFirstDayOfWeek(iFirstDayOfWeek);
        calStartDate.setTimeInMillis(calToday.getTimeInMillis());
        calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
    }

    // 下一个月
    private void setNextViewItem() {
        iMonthViewCurrentMonth++;
        if (iMonthViewCurrentMonth == 12) {
            iMonthViewCurrentMonth = 0;
            iMonthViewCurrentYear++;
        }
        calStartDate.set(Calendar.DAY_OF_MONTH, 1);
        calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
        calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
    }

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
        iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年

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

        String s = calStartDate.get(Calendar.YEAR)
                + "-"
                + NumberHelper.LeftPad_Tow_Zero(calStartDate
                        .get(Calendar.MONTH) + 1);
        btnToday.setText(s);
    }

    private Calendar getCalendarStartDate() {
        calToday.setTimeInMillis(System.currentTimeMillis());
        calToday.setFirstDayOfWeek(iFirstDayOfWeek);

        if (calSelected.getTimeInMillis() == 0) {
            calStartDate.setTimeInMillis(System.currentTimeMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        } else {
            calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
            calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
        }

        return calStartDate;
    }

    public List<BookingData> getBookingData() {
        return bookingData;
    }

    public void setBookingData(List<BookingData> bookingData) {
        this.bookingData = bookingData;
    }


    /**
     * 
     * @author Liangfei
     *
     */
    public class TitleGridAdapter extends BaseAdapter {

        int[] titles = new int[] { R.string.Sun, R.string.Mon, R.string.Tue,
                R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat };

        private Activity activity;

        // construct
        public TitleGridAdapter(Activity a) {
            activity = a;
        }

        public int getCount() {
            return titles.length;
        }

        public Object getItem(int position) {
            return titles[position];
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout iv = new LinearLayout(activity);
            TextView txtDay = new TextView(activity);
            txtDay.setFocusable(false);
            txtDay.setBackgroundColor(Color.TRANSPARENT);
            iv.setOrientation(1);

            txtDay.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

            int i = (Integer) getItem(position);

            txtDay.setTextColor(Color.WHITE);
            Resources res = getResources();

            if (i == R.string.Sat) {
                // 周六
                txtDay.setBackgroundColor(res.getColor(R.color.title_text_6));
            } else if (i == R.string.Sun) {
                // 周日
                txtDay.setBackgroundColor(res.getColor(R.color.title_text_7));
            } else {
            }

            txtDay.setText((Integer) getItem(position));

            iv.addView(txtDay, lp);

            return iv;
        }
    }


    
}
