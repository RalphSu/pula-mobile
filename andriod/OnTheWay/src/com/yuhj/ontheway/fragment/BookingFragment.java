package com.yuhj.ontheway.fragment;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.yuhj.ontheway.activity.BookingActivity;
import com.yuhj.ontheway.activity.CurriculumScheduleActivity;
import com.yuhj.ontheway.adapter.CalendarGridViewAdapter;
import com.yuhj.ontheway.bean.BookingData;
import com.yuhj.ontheway.clients.ClientApi;
import com.yuhj.ontheway.utils.StaticStrings;

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
//    private GestureDetector mGesture = null;

    public boolean onTouch(View v, MotionEvent event) {
//        return mGesture.onTouchEvent(event);
        return true;
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

//    private class GestureListener extends SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
//            try {
//                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                    return false;
//                // right to left swipe
//                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    setNextViewItem();
//                    return true;
//
//                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    setPrevViewItem();
//                    return true;
//                }
//            } catch (Exception e) {
//                // nothing
//            }
//            return false;
//        }
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            // ListView lv = getListView();
//            //得到当前选中的是第几个单元格
//            int pos = ((GridView)viewFlipper.getCurrentView()).pointToPosition((int) e.getX(), (int) e.getY());
//            LinearLayout txtDay = (LinearLayout) ((GridView)viewFlipper.getCurrentView()).findViewById(pos + 5000);
//            notifyCalendarDaySelected(txtDay);
//
//            Log.i("TEST", "onSingleTapUp -  pos=" + pos);
//
//            return false;
//        }
//    }

    /**
     * Called when a calendar day grid was clicked
     * @param clickedDayLayout
     */
    public void notifyCalendarDaySelected(LinearLayout clickedDayLayout) {
        if (clickedDayLayout != null && clickedDayLayout.getTag() != null) {
            DateTime date = (DateTime) clickedDayLayout.getTag();
            calSelected = date;

            gCurrentAdapter.setSelectedDate(calSelected);
            gCurrentAdapter.notifyDataSetChanged();

//            gPrevAdapter.setSelectedDate(calSelected);
//            gPrevAdapter.notifyDataSetChanged();

//            gNextAdapter.setSelectedDate(calSelected);
//            gNextAdapter.notifyDataSetChanged();
            
            // start detail activity
            Intent intent = new Intent(getActivity(), CurriculumScheduleActivity.class);

            intent.putExtra("calSelected", calSelected);
            startActivity(intent);
        }
    }
    
    public BookingFragment(Context context) {
        this.mContext = context;
    }

    // 基本变量
    private Context mContext = null;
    private GridView title_gView;
//    private GridView gPrevView;// 上一个月
    private GridView gCurrentView;// 当前月
    private GridView gNextView;// 下一个月
    private DateTime calCurrentMonthFirstDay = DateTime.now();// day 1 of current caleander's month
    private DateTime calSelected = DateTime.now(); // 选择的日历
    private CalendarGridViewAdapter gCurrentAdapter;
//    private CalendarGridViewAdapter gPrevAdapter;
    private CalendarGridViewAdapter gNextAdapter;
    // 顶部按钮
    private Button btnToday = null;
    private Button btnPrevMonth = null;
    private Button btnNextMonth = null;
    private RelativeLayout mainLayout;
    
    private SharedPreferences preference;
    
    private List<BookingData> bookingData = new ArrayList<BookingData>();

    class DownData extends AsyncTask<Void, Void, List<BookingData>> {

        @Override
        protected List<BookingData> doInBackground(Void... arg0) {
            String no = preference.getString("studentno", "");
            return ClientApi.getBookingList(no, "");
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
        preference = getActivity().getSharedPreferences(StaticStrings.PREFS_SETTINGS, Context.MODE_PRIVATE);
        new DownData().execute();

        generateContentView(inflater, container);


        slideLeftIn = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_right_out);

        slideLeftIn.setAnimationListener(animationListener);
        slideLeftOut.setAnimationListener(animationListener);
        slideRightIn.setAnimationListener(animationListener);
        slideRightOut.setAnimationListener(animationListener);

//        mGesture = new GestureDetector(this.mContext, new GestureListener());

        return mainLayout;
    }

    /** 底部菜单文字 **/
    String[] menu_toolbar_name_array;


    private View generateContentView(LayoutInflater inflater, ViewGroup container) {
        View bookingFragment = inflater.inflate(R.layout.booking_fragment, container, false);
        mainLayout = (RelativeLayout) bookingFragment.findViewById(R.id.bookingRelativelayout);

        LinearLayout layTopControls = (LinearLayout) bookingFragment.findViewById(R.id.bookingTopLinearlayout);
        generateTopButtons(layTopControls); // 生成顶部按钮 （上一月，下一月，当前月）

        setTitleGirdView();

        calCurrentMonthFirstDay = DateTime.now();// when create, by default is the current month

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
        
        btnPrevMonth = (Button)layTopControls.findViewById(R.id.bookingPrevMonthButton);
        btnPrevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrevViewItem();
            }
        });
        
        btnNextMonth = (Button)layTopControls.findViewById(R.id.bookingNextMonthButton);
        btnNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextViewItem();
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
        updateCalendarTitle();
        
        // pre month calendar
//        gPrevAdapter = new CalendarGridViewAdapter(this.getActivity(), calCurrentMonthFirstDay.minusMonths(1), this);
//        gPrevView = createCalendarView(R.id.prevMonthCalendarView, gPrevAdapter);

        // current month calendar 
        gCurrentAdapter = new CalendarGridViewAdapter(this.getActivity(), calCurrentMonthFirstDay, this);
        gCurrentView = createCalendarView(R.id.currentMonthCalendarView, gCurrentAdapter);

        // next month calendar
        gNextAdapter = new CalendarGridViewAdapter(this.getActivity(), calCurrentMonthFirstDay.plusMonths(1), this);
        gNextView = createCalendarView(R.id.nextMonthCalendarView, gNextAdapter);

//        gCurrentView.setOnTouchListener(this);
//        gPrevView.setOnTouchListener(this);
//        gNextView.setOnTouchListener(this);
    }

    @SuppressWarnings("deprecation")
    private GridView createCalendarView(int id, CalendarGridViewAdapter adapter) {
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

    private void setPrevViewItem() {
        calCurrentMonthFirstDay = calCurrentMonthFirstDay.minusMonths(1);
        viewFlipper.setInAnimation(slideLeftIn);
        viewFlipper.setOutAnimation(slideLeftOut);
        CreateGirdView();
    }

    private void setNextViewItem() {
        viewFlipper.setInAnimation(slideLeftIn);
        viewFlipper.setOutAnimation(slideLeftOut);
        calCurrentMonthFirstDay = calCurrentMonthFirstDay.plusMonths(1);
        CreateGirdView();
    }

    private void updateCalendarTitle() {
        String s = calCurrentMonthFirstDay.getYear() + "-" + calCurrentMonthFirstDay.getMonthOfYear();
        btnToday.setText(s);
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
