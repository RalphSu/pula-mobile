package com.pula.star.activity;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.adapter.MyCourseListAdapter;
import com.pula.star.bean.MyCourseData;
import com.pula.star.bean.MyCourseJsonTools;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.MyCourseJsonUtils;
import com.pula.star.utils.RTPullListView;
import com.pula.star.utils.StaticStrings;

public class MyCourseActivity extends Activity {

	RTPullListView my_course_list;
	ProgressBar pb;
	private List<MyCourseJsonTools> list;
	private List<String> course_field_value;
	private MyCourseJsonTools tools;
	private List<MyCourseJsonTools> list_2;
	String mess;
	private MyCourseJsonUtils jj;
	RTPullListView list_2_2;
	private MyCourseListAdapter adapter;
	private SharedPreferences preference;
	private String userName;
	private String userNameInfo;
	private int weekday;
	private ImageView img_bottom;
	
	private String courseValidityPeriodDesc;
	private String courseSysCourseDesc;
	private String courseSpeCourseDesc;
	private String gongfangCourseDesc;
    private String memActCourseDesc;
    
    private int courseSysCoursePaidCnt = 0;
    private int courseSysCourseUsedCnt = 0;
    
	private int courseSpeCoursePaidCnt = 0;
	private int courseSpeCourseUsedCnt = 0;
	
	private int gongfangCoursePaidCnt = 0;
	private int gongfangCourseUsedCnt = 0;
	
    private int memActCoursePaidCnt = 0;
    private int memActCourseUsedCnt = 0;
    
    private int userLevel = 4;
    private String courseProgress = "0%";
    
    
    private MyCourseData courseData;
    private MyCourseData courseDataGet;
    
    private TextView userGrade;
    private TextView progress;
    private TextView courseTime;
	private String courseTimeString;
    private ProgressBar courseProgressBar;
    
	
    
    
    
	String[] week = new String[] { "每期一", "每期二", "每期三", "每期四", "每期五", "每期六",
			"每期日", };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.frame_my_course);

		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				MODE_PRIVATE);
		userName = preference.getString("USER_INFO_NO", "");
		userNameInfo = preference.getString("USER_INFO_NAME", "");
		
		
		
		
		
		my_course_list = (RTPullListView) findViewById(R.id.my_course_list);
		
		pb = (ProgressBar) findViewById(R.id.pb);
        img_bottom =(ImageView)findViewById(R.id.img_p);
        userGrade = (TextView)findViewById(R.id.grade_text);
        progress =  (TextView)findViewById(R.id.class_progress);
        courseTime = (TextView)findViewById(R.id.class_time_text);
        courseProgressBar =  (ProgressBar) findViewById(R.id.progressbar_sys_used);
        
        
        
		// 初始化
		initView();
		// 填值
		initVaule();
		// 监听
		initListener();
		
		setTitle("普拉星球 - 我的课程");
		// getDateFormServise();// 联网获取数据
		// 下拉刷新监听器
		/*
		 * my_course_list.setonRefreshListener(new OnRefreshListener() {
		 * 
		 * @Override // 重写pullListView的刷新方法 public void onRefresh() { //
		 * 本地数据加载太快，加一个线程，可以看到刷新的效果 Handler handler = new Handler() { public
		 * void handleMessage(Message msg) { getDateFormServise();//
		 * 刷新时始终返回服务器的第一页 }; }; handler.sendEmptyMessageDelayed(0, 1000); } });
		 */

		// 判断Intent的信息，加载到list内
		/*
		 * Intent intent = getIntent(); String info =
		 * intent.getStringExtra("info"); if (info != null) { PutData(info); }
		 */

		// 加载默认地区的数据 默认为北京，昌平区，购物
		/*
		 * Api_AllStore tools = new Api_AllStore(); String path = null; try {
		 * path = tools.getSingle("deal/find_deals?", 6); } catch (URIException
		 * e1) { e1.printStackTrace(); } encodeQuery = path; try { encodeQuery =
		 * URIUtil.encodeQuery(encodeQuery, "utf-8"); } catch (URIException e) {
		 * e.printStackTrace(); }
		 */
		new Thread() {
			public void run() {
				Log.i("username=", "" + userName);
				
				courseData = ClientApi.getMyCourseData(userName);
				
				if (courseData != null) {

					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = courseData;
					handler.sendMessage(msg);
				}else {
					
					String str = null;
					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = str;
					handler.sendMessage(msg);
				
				}
					
			};
		}.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
	public void initView() {
		// expandTabView=(ExpandTabView) findViewById(R.id.expandtab_view);
		// viewLeft = new LeftFilterView(this,
		// FilterDataSource.createPriceFilterItems());
		// viewMiddle = new MiddleFilterView(this);
		// viewRight = new RightFilterView(this,
		// FilterDataSource.createSortFilterItems());
	}

	private void initVaule() {
		/*
		 * mViewArray.add(viewLeft); mViewArray.add(viewMiddle);
		 * mViewArray.add(viewRight);
		 * 
		 * ArrayList<String> mTextArray = new ArrayList<String>();
		 * mTextArray.add("全城"); mTextArray.add("全部分类"); mTextArray.add("排序");
		 * 
		 * expandTabView.setValue(mTextArray, mViewArray);
		 * 
		 * expandTabView.setTitle("全城", 0); expandTabView.setTitle("全部分类", 1);
		 * expandTabView.setTitle("排序", 2);
		 */

	}

	private void initListener() {
		
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			my_course_list.onRefreshComplete();
			switch (msg.what) {
			case 2:
				courseDataGet = new MyCourseData();

				course_field_value = new ArrayList<String>();
				
				if (msg.obj != null) {
					courseDataGet = (MyCourseData)(msg.obj);
				

		
				courseSysCoursePaidCnt = courseDataGet.getPaidCount();
				courseSysCourseUsedCnt = courseDataGet.getUsedCount();
				courseSpeCoursePaidCnt = courseDataGet.getSpecialCourseCount();
				courseSpeCourseUsedCnt = courseDataGet.getUsedSpecialCourseCount();
				
				gongfangCoursePaidCnt = courseDataGet.getGongfangCount();			
				gongfangCourseUsedCnt = courseDataGet.getUsedGongFangCount();
				
				memActCoursePaidCnt = courseDataGet.getHuodongCount();
				
				memActCourseUsedCnt = courseDataGet.getUsedHuodongCount();
				
				courseValidityPeriodDesc = courseDataGet.getUpdateTime();
				
				course_field_value.add(courseValidityPeriodDesc);
				
				courseSysCourseDesc = "总课券"+courseSysCoursePaidCnt+"次,已使用"+courseSysCourseUsedCnt+"次";
				
				course_field_value.add(courseSysCourseDesc);
				
				userLevel = courseDataGet.getLevel();
				
				userLevel = (userLevel>5)? 5:userLevel;
				
				userGrade.setText("您的等级为:"+" "+userLevel+"级");
				
				if(courseSysCoursePaidCnt != 0)
				{	
					 NumberFormat numberFormat = NumberFormat.getInstance();  
					 numberFormat.setMaximumFractionDigits(0);  
					 courseProgress = numberFormat.format((float) courseSysCourseUsedCnt / (float) courseSysCoursePaidCnt * 100);  
					 
					 						
				}
				else
				{
					courseProgress = "0";
				}
				
				progress.setText(courseProgress+"%");
				
				courseProgressBar.setProgress(Integer.parseInt(courseProgress));
				
				
				courseSpeCourseDesc= "总课券"+courseSpeCoursePaidCnt+"次,已使用"+courseSpeCourseUsedCnt+"次";
				
				course_field_value.add(courseSpeCourseDesc);
				
				gongfangCourseDesc= "总课券"+gongfangCoursePaidCnt+"次,已使用"+gongfangCourseUsedCnt+"次";
				
				course_field_value.add(gongfangCourseDesc);
				
				memActCourseDesc = "总课券"+memActCoursePaidCnt+"次,已使用"+memActCourseUsedCnt+"次";
				
				course_field_value.add(memActCourseDesc);
				
				
				courseTimeString = courseDataGet.getCourseTime();
				courseTime.setText(courseTimeString);
				
				
				}
				else
				{
					course_field_value.add(" ");
					course_field_value.add(" ");
					course_field_value.add(" ");
					course_field_value.add(" ");
					course_field_value.add(" ");
				}
					
					
			
				pb.setVisibility(View.GONE);

				
				
				
				adapter = new MyCourseListAdapter(MyCourseActivity.this,course_field_value);

				my_course_list.setAdapter(adapter);
				// list列表点击事件
				img_bottom.setVisibility(View.VISIBLE);
				
				break;

			}
		}
	};

	
}
