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
    
    private int userAge = 4;
    private String courseProgress = "0%";
    
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
		userName = preference.getString("USER_NAME", "");
		userNameInfo = preference.getString("USER_INFO_NAME", "");
		userAge = preference.getInt("USER_INFO_AGE", 4);
		
		
		
		my_course_list = (RTPullListView) findViewById(R.id.my_course_list);
		
		pb = (ProgressBar) findViewById(R.id.pb);
        img_bottom =(ImageView)findViewById(R.id.img_p);
        userGrade = (TextView)findViewById(R.id.grade_text);
        progress =  (TextView)findViewById(R.id.class_progress);
        courseTime = (TextView)findViewById(R.id.class_time_text);
        courseProgressBar =  (ProgressBar) findViewById(R.id.progressbar_sys_used);
    
        userAge = (userAge>5)? 5:userAge;
        
        userGrade.setText("您的等级为:"+" "+userAge+"级");
        
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
				InputStream is = HttpTools
						.getInputStream("http://121.40.151.183:8080/pula-sys/app/studentinterface/listTimeCourses?studentNo="
								+ userName);
				// InputStream is =
				// HttpTools.getInputStream("http://121.40.151.183:8080/pula-sys/app/studentinterface/listTimeCourses?studentNo=PD0D00002");

				Log.i("is=", "" + is);
				if (is != null) {

					String str = HttpTools.GetStringByInputStream(is);
					Log.i("str=", str);
					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = str;
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
		/*
		 * viewLeft.setOnSelectListener(new LeftFilterView.OnSelectListener() {
		 * 
		 * @Override public void getValue(String distance, String showText) {
		 * onRefresh(viewLeft, showText); } });
		 * 
		 * viewMiddle.setOnSelectListener(new
		 * MiddleFilterView.OnItemSelectListener() {
		 * 
		 * @Override public void getValue(String showText) {
		 * onRefresh(viewMiddle, showText);
		 * 
		 * } });
		 * 
		 * viewRight.setOnSelectListener(new RightFilterView.OnSelectListener()
		 * {
		 * 
		 * @Override public void getValue(String distance, String showText) {
		 * onRefresh(viewRight, showText); } });
		 */
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			my_course_list.onRefreshComplete();
			switch (msg.what) {
			case 2:
				jj = new MyCourseJsonUtils();
				list = new ArrayList<MyCourseJsonTools>();
				course_field_value = new ArrayList<String>();

				if (msg.obj != null) {
					Log.i("msg.obj =", "" + msg.obj.toString());
					list = jj.PutData(msg.obj.toString());
				}

		
				
				if (list.size() > 0) {
					
					for(int i=0; i < list.size();i++)
					{
						 courseSysCoursePaidCnt = courseSysCoursePaidCnt + list.get(i).getPaid_count();
						 courseSysCourseUsedCnt = courseSysCourseUsedCnt + list.get(i).getUsed_count();
						    
						 courseSpeCoursePaidCnt = courseSpeCoursePaidCnt + list.get(i).getSpec_count();
						 courseSpeCourseUsedCnt = courseSpeCourseUsedCnt + list.get(i).getUsed_spec_count();
							
						 gongfangCoursePaidCnt = gongfangCoursePaidCnt + list.get(i).getGongfang_count();
						 gongfangCourseUsedCnt = gongfangCourseUsedCnt + list.get(i).getUsed_gongfang_count();
							
						 memActCoursePaidCnt = memActCoursePaidCnt + list.get(i).getHuodong_count();
						 memActCourseUsedCnt = memActCourseUsedCnt + list.get(i).getUsed_huodong_count();
					}
					//course_field_value.add(userName);
					//course_field_value.add(userNameInfo);
					
					courseValidityPeriodDesc = list.get(0).getStart_time() + "至" + list.get(0).getEnd_time(); 
					course_field_value.add(courseValidityPeriodDesc);
					
					courseSysCourseDesc = "总课券"+courseSysCoursePaidCnt+"次,已使用"+courseSysCourseUsedCnt+"次";
					course_field_value.add(courseSysCourseDesc);
					
					if(list.get(0).getPaid_count() != 0)
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
					
					int weekday = Integer.parseInt(list.get(0).getStart_weekday());
					
					String duration_time = list.get(0).getDuration_minute();
					
					int hour;
					int min;
					
					hour = (Integer.parseInt(list.get(0).getStart_hour())*60 + Integer.parseInt(list.get(0).getStart_minute())+ Integer.parseInt(duration_time))/60;
					min = (Integer.parseInt(list.get(0).getStart_hour())*60 + Integer.parseInt(list.get(0).getStart_minute())+ Integer.parseInt(duration_time))%60;
					
					courseTimeString = "上课时间:"+ week[weekday%7]+ list.get(0).getStart_hour()+":"+list.get(0).getStart_minute()+"-"+hour+":"+min;
					courseTime.setText(courseTimeString);
					
					
					/*
					weekday = Integer.parseInt(list.get(0).getStart_weekday());
					course_field_value.add(week[weekday] + " "
							+ list.get(0).getStart_hour() + ":"
							+ list.get(0).getStart_minute());
							*/
					
				} else {

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
				
				 /*
				my_course_list
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
                              
								System.out.println(position);

								position = position - 1;

								MyCourseJsonTools to = list.get(position);
								Intent intent = new Intent(
										MyCourseActivity.this,
										CourseDetailH5Activity.class);

								intent.putExtra("courseNo", to.getCourse_no());
								// Log.i("course no  =", "" +
								// to.getCourse_no());
								// intent.putExtra("SearchId","1");
								intent.putExtra("name", to.getName());
								startActivity(intent);
							
							}
						});
                   */
				break;

			}
		}
	};

	
}
