/**
 * 
 */
package com.pula.star.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.adapter.BookingTimeSegmentAdapter;
import com.pula.star.adapter.CurriculumScheduleListAdapter;
import com.pula.star.adapter.MyBookingListAdapter;
import com.pula.star.adapter.MyCourseListAdapter;
import com.pula.star.adapter.NumberHelper;
import com.pula.star.bean.BookingData;
import com.pula.star.bean.CourseData;
import com.pula.star.bean.MyCourseJsonTools;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.MyCourseJsonUtils;
import com.pula.star.utils.RTPullListView;
import com.pula.star.utils.StaticStrings;

/**
 * 当日课程表
 * 
 */
public class CurriculumScheduleActivity extends BaseActivity {
	
	RTPullListView course_list;
	ProgressBar pb;
	private List<MyCourseJsonTools>list;	
	String date;
	DateTime selected;
	String mess;
	private MyCourseJsonUtils jj;

	private CurriculumScheduleListAdapter adapter;
	private SharedPreferences preference;
	private String userName;
	String [] course_time = new String[] {"09:00-10:30","13:00-14:00","14:30-15:30","16:00-17:00","19:00-20:00",};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.frame_curriculum_schedule);

		selected = (DateTime) getIntent().getSerializableExtra("calSelected");
		
        date = String.format(Locale.US, BookingTimeSegmentAdapter.DAY_KEY, selected.getYear(),
                selected.getMonthOfYear(), selected.getDayOfMonth());
        
        
        Log.e("CurriculumScheduleActivity",date);
        
        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        
		userName = preference.getString("USER_NAME", "USER_NAME");
		
		
		course_list = (RTPullListView) findViewById(R.id.course_list);
		
		pb = (ProgressBar) findViewById(R.id.pb);

		// 初始化
		initView();
		// 填值
		initVaule();
		// 监听
		initListener();
		setTitle("普拉星球 - 预约");
		new Thread() {
			public void run() {
				Log.i("username=","" + userName);
				/*
				InputStream is = HttpTools.getInputStream("http://121.40.151.183:8080/pula-sys/app/studentinterface/listTimeCourses?studentNo=" + userName);
				//InputStream is = HttpTools.getInputStream("http://121.40.151.183:8080/pula-sys/app/studentinterface/listTimeCourses?studentNo=PD0D00002");
				
				Log.i("is=", "" + is);
				
				if (is != null) {

					String str = HttpTools.GetStringByInputStream(is);
					Log.i("str=", str);
					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = str;
					handler.sendMessage(msg);
				}
				*/
				//String str="{\"message\":null,\"error\":false,\"data\":[{\"course\":{\"name\":\"系统课01级A\",\"id\":5,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT01A\",\"removed\":false,\"comments\":\"适合托班及小班前衔接\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"2.5~3\",\"durationMinute\":45,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"故事屋A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":11,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":8,\"usedHuodongCount\":2,\"no\":\"20150705005\",\"removed\":false,\"comments\":\"dd\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT01A\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":2,\"gongfangCount\":2,\"usedGongFangCount\":2,\"orderStatus\":0}],\"orderUsages\":[]},{\"course\":{\"name\":\"系统课01级B\",\"id\":8,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":200.0,\"no\":\"XT01B\",\"removed\":false,\"comments\":\"适合中班及受过系统课教学的孩子\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"4~5\",\"durationMinute\":90,\"createTime\":\"2015-09-08\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-08\",\"startHour\":0,\"branchName\":\"文峰\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":12,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":8,\"usedHuodongCount\":8,\"no\":\"20150705006\",\"removed\":false,\"comments\":\"8\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT01B\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":8,\"gongfangCount\":8,\"usedGongFangCount\":8,\"orderStatus\":0}],\"orderUsages\":[]},{\"course\":{\"name\":\"系统课03级A\",\"id\":7,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT03A\",\"removed\":false,\"comments\":\"适合小学1~3年纪\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"6~12\",\"durationMinute\":90,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"艺术与创作A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":14,\"enabled\":true,\"buyType\":1,\"paied\":122,\"paiedCount\":8,\"usedHuodongCount\":8,\"no\":\"20150705008\",\"removed\":false,\"comments\":\"8\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT03A\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":8,\"gongfangCount\":8,\"usedGongFangCount\":8,\"orderStatus\":0}],\"orderUsages\":[]},{\"course\":{\"name\":\"系统课02级A\",\"id\":6,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT02A\",\"removed\":false,\"comments\":\"适合小班及中班\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"3~4\",\"durationMinute\":90,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"艺术与快乐A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":13,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":12,\"usedHuodongCount\":12,\"no\":\"20150705007\",\"removed\":false,\"comments\":\"12\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT02A\",\"usedCount\":12,\"updateTime\":\"2015-09-12\",\"huodongCount\":12,\"gongfangCount\":12,\"usedGongFangCount\":12,\"orderStatus\":0}],\"orderUsages\":[]},{\"course\":{\"name\":\"2015暑期班\",\"id\":4,\"endTime\":\"2015-08-31\",\"startTime\":\"2015-07-01\",\"enabled\":true,\"price\":1080.0,\"no\":\"TS2015001\",\"removed\":false,\"comments\":\"1080元送5次乐创\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳月亮\",\"applicableAges\":\"1-8\",\"durationMinute\":90,\"createTime\":\"2015-08-27\",\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"updateTime\":\"2015-09-09\",\"startHour\":0,\"branchName\":\"文峰\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":8,\"enabled\":true,\"buyType\":1,\"paied\":1080,\"paiedCount\":8,\"usedHuodongCount\":0,\"no\":\"20150705001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"TS2015001\",\"usedCount\":8,\"updateTime\":\"2015-09-07\",\"huodongCount\":10,\"gongfangCount\":6,\"usedGongFangCount\":6,\"orderStatus\":1}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]}],\"no\":null,\"redirectTo\":null}";
				String str="{\"message\":null,\"error\":false,\"data\":[{\"course\":{\"name\":\"系统课01级A\",\"id\":5,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT01A\",\"removed\":false,\"comments\":\"适合托班及小班前衔接\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"2.5~3\",\"durationMinute\":45,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"故事屋A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":11,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":8,\"usedHuodongCount\":2,\"no\":\"20150705005\",\"removed\":false,\"comments\":\"dd\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT01A\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":2,\"gongfangCount\":2,\"usedGongFangCount\":2,\"orderStatus\":0}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]},{\"course\":{\"name\":\"系统课01级B\",\"id\":8,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":200.0,\"no\":\"XT01B\",\"removed\":false,\"comments\":\"适合中班及受过系统课教学的孩子\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"4~5\",\"durationMinute\":90,\"createTime\":\"2015-09-08\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-08\",\"startHour\":0,\"branchName\":\"文峰\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":12,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":8,\"usedHuodongCount\":8,\"no\":\"20150705006\",\"removed\":false,\"comments\":\"8\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT01B\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":8,\"gongfangCount\":8,\"usedGongFangCount\":8,\"orderStatus\":0}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]},{\"course\":{\"name\":\"系统课03级A\",\"id\":7,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT03A\",\"removed\":false,\"comments\":\"适合小学1~3年纪\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"6~12\",\"durationMinute\":90,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"艺术与创作A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":14,\"enabled\":true,\"buyType\":1,\"paied\":122,\"paiedCount\":8,\"usedHuodongCount\":8,\"no\":\"20150705008\",\"removed\":false,\"comments\":\"8\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT03A\",\"usedCount\":8,\"updateTime\":\"2015-09-12\",\"huodongCount\":8,\"gongfangCount\":8,\"usedGongFangCount\":8,\"orderStatus\":0}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]},{\"course\":{\"name\":\"系统课02级A\",\"id\":6,\"endTime\":\"2016-09-01\",\"startTime\":\"2015-09-01\",\"enabled\":true,\"price\":0.0,\"no\":\"XT02A\",\"removed\":false,\"comments\":\"适合小班及中班\",\"creator\":\"wenfeng\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳、月亮\",\"applicableAges\":\"3~4\",\"durationMinute\":90,\"createTime\":\"2015-09-07\",\"updator\":\"wenfeng\",\"updateTime\":\"2015-09-07\",\"startHour\":0,\"branchName\":\"艺术与快乐A\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":13,\"enabled\":true,\"buyType\":1,\"paied\":100,\"paiedCount\":12,\"usedHuodongCount\":12,\"no\":\"20150705007\",\"removed\":false,\"comments\":\"12\",\"creator\":\"wenfeng\",\"createTime\":\"2015-09-12\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"XT02A\",\"usedCount\":12,\"updateTime\":\"2015-09-12\",\"huodongCount\":12,\"gongfangCount\":12,\"usedGongFangCount\":12,\"orderStatus\":0}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]},{\"course\":{\"name\":\"2015暑期班\",\"id\":4,\"endTime\":\"2015-08-31\",\"startTime\":\"2015-07-01\",\"enabled\":true,\"price\":1080.0,\"no\":\"TS2015001\",\"removed\":false,\"comments\":\"1080元送5次乐创\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"startWeekDay\":0,\"maxStudentNum\":0,\"startMinute\":0,\"classRoomName\":\"太阳月亮\",\"applicableAges\":\"1-8\",\"durationMinute\":90,\"createTime\":\"2015-08-27\",\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"updateTime\":\"2015-09-09\",\"startHour\":0,\"branchName\":\"文峰\",\"courseType\":0},\"sourceType\":0,\"orders\":[{\"id\":8,\"enabled\":true,\"buyType\":1,\"paied\":1080,\"paiedCount\":8,\"usedHuodongCount\":0,\"no\":\"20150705001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":0,\"updator\":\"wenfeng\",\"courseNo\":\"TS2015001\",\"usedCount\":8,\"updateTime\":\"2015-09-07\",\"huodongCount\":10,\"gongfangCount\":6,\"usedGongFangCount\":6,\"orderStatus\":1}],\"orderUsages\":[{\"id\":3,\"enabled\":true,\"usedGongfangCount\":0,\"usedHuodongCount\":0,\"no\":\"PD0DB001\",\"removed\":false,\"comments\":\"0\",\"creator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"createTime\":\"2015-08-27\",\"studentNo\":\"PD0DB001\",\"usedCost\":3,\"updator\":\"ec1fb84f-b761-4cbf-b668-23813ab9f047\",\"orderNo\":\"20150705001\",\"courseNo\":\"TS2015001\",\"usedCount\":3,\"updateTime\":null}]}],\"no\":null,\"redirectTo\":null}";

				Message msg = Message.obtain();
				msg.what = 2;
				msg.obj = str;
				handler.sendMessage(msg);
			
				
			};
		}.start();
	}

    @Override
    public void onBackPressed() {

		Intent intent = new Intent(CurriculumScheduleActivity.this,MainActivity.class);
		startActivity(intent);
		
    }
    
	public void initView() {

	}

	private void initVaule() {
		

	}

	private void initListener() {
		
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			//course_list.onRefreshComplete();
			switch (msg.what) {
			case 2:
				jj = new MyCourseJsonUtils();
				list = new ArrayList<MyCourseJsonTools>();
				
				if(msg.obj != null)
				{
				 Log.i("msg.obj =", "" + msg.obj.toString()); 	
				 list = jj.PutData(msg.obj.toString());
				} 
				//list_2 = new ArrayList<JSONTools>();
				pb.setVisibility(View.GONE);
				
				adapter = new CurriculumScheduleListAdapter(CurriculumScheduleActivity.this, list);

				course_list.setAdapter(adapter);
				// list列表点击事件
				
				course_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								
								position = position - 1;
								
								MyCourseJsonTools to = list.get(position);
								
								
								Intent intent;
								
								if(userName.equals("USER_NAME") == true)
								{	
								  intent = new Intent(CurriculumScheduleActivity.this,BookingDialogActivity.class);
								}
								else
								{
								  intent = new Intent(CurriculumScheduleActivity.this, RegistedUserBookingDialogActivity.class);
								}
									
								intent.putExtra("courseName", to.getName());
								intent.putExtra("courseNo",to.getCourse_no());
								intent.putExtra("date",date);
								intent.putExtra("plan", course_time[position]);
								
								intent.putExtra("calSelected", selected);
								
								//Log.i("course no  =", "" + to.getCourse_no()); 
								//intent.putExtra("SearchId","1");
								intent.putExtra("name", to.getName());
								startActivity(intent);
							}
						}); 
				
				break;
		   		
			
		}
	  }
   };	
   
   
}
