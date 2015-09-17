package com.yuhj.ontheway.activity;

import java.io.InputStream;
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
import android.widget.ProgressBar;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.adapter.MyCourseListAdapter;
import com.yuhj.ontheway.bean.MyCourseJsonTools;
import com.yuhj.ontheway.clients.ClientApi;
import com.yuhj.ontheway.utils.HttpTools;
import com.yuhj.ontheway.utils.MyCourseJsonUtils;
import com.yuhj.ontheway.utils.RTPullListView;
import com.yuhj.ontheway.utils.StaticStrings;

public class MyCourseActivity extends Activity {
	
	RTPullListView my_course_list;
	ProgressBar pb;
	private List<MyCourseJsonTools>list;
	private MyCourseJsonTools tools;	
	private List<MyCourseJsonTools>list_2;
	String mess;
	private MyCourseJsonUtils jj;
	RTPullListView list_2_2;
	private MyCourseListAdapter adapter;
	private SharedPreferences preference;
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.frame_my_course);

        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		userName = preference.getString("USER_NAME", "");
		
		
		my_course_list = (RTPullListView) findViewById(R.id.my_course_list);
		pb = (ProgressBar) findViewById(R.id.pb);

		// 初始化
		initView();
		// 填值
		initVaule();
		// 监听
		initListener();

		
		//getDateFormServise();// 联网获取数据
		// 下拉刷新监听器
		/*
		my_course_list.setonRefreshListener(new OnRefreshListener() {
			@Override
			// 重写pullListView的刷新方法
			public void onRefresh() {
				// 本地数据加载太快，加一个线程，可以看到刷新的效果
				Handler handler = new Handler() {
					public void handleMessage(Message msg) {
						getDateFormServise();// 刷新时始终返回服务器的第一页
					};
				};
				handler.sendEmptyMessageDelayed(0, 1000);
			}
		});
        */
		
		// 判断Intent的信息，加载到list内
		/*
		Intent intent = getIntent();
		String info = intent.getStringExtra("info");
		if (info != null) {
			PutData(info);
		}
		*/
		
		// 加载默认地区的数据 默认为北京，昌平区，购物
		/*
		Api_AllStore tools = new Api_AllStore();
		String path = null;
		try {
			path = tools.getSingle("deal/find_deals?", 6);
		} catch (URIException e1) {
			e1.printStackTrace();
		}
		encodeQuery = path;
		try {
			encodeQuery = URIUtil.encodeQuery(encodeQuery, "utf-8");
		} catch (URIException e) {
			e.printStackTrace();
		}
		*/
		new Thread() {
			public void run() {
				Log.i("username=","" + userName);
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
			};
		}.start();
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
				
				if(msg.obj != null)
				{
				 Log.i("msg.obj =", "" + msg.obj.toString()); 	
				 list = jj.PutData(msg.obj.toString());
				} 
				//list_2 = new ArrayList<JSONTools>();
				pb.setVisibility(View.GONE);
				
				adapter = new MyCourseListAdapter(MyCourseActivity.this, list);

				my_course_list.setAdapter(adapter);
				// list列表点击事件
				
				my_course_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								
							 
								
								System.out.println(position);
								
								position = position - 1;
								
								MyCourseJsonTools to = list.get(position);
								Intent intent = new Intent(MyCourseActivity.this,CourseDetailH5Activity.class);
								
								intent.putExtra("courseNo",to.getCourse_no());
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
   
   /*
	public List<JSONTools> parseJson(String content){
		
		
		list=new ArrayList<JSONTools>();
		
		JSONObject obj;
		try {
			obj = new JSONObject(content);
			JSONArray arr=obj.getJSONArray("records");
			Log.i("arr=", arr+"");
			for(int i=0;i<arr.length();i++){
				tools=new JSONTools();
				
				JSONObject json=arr.getJSONObject(i);
				
				tools.setStart_weekday(json.getString("startWeekDay"));
				tools.setStart_minute(json.getString("startMinute"));
				tools.setStart_hour(json.getString("startHour"));
				tools.setStart_time(json.getString("startTime"));
				tools.setEnd_time(json.getString("endTime"));
				tools.setName(json.getString("name"));
				tools.setBranch_name(json.getString("branchName"));
				tools.setDuration_minute(json.getString("duration_minute"));
				tools.setCourse_no(json.optString("course_no"));
				
				list.add(tools);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
		return list;
	}
*/

   /*
	public void PutData(String str) {
		list = new ArrayList<JSONTools>();
		JSONObject obj;
		try {
			obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("records");
			Log.i("arr=", arr + "");
			for (int i = 0; i < arr.length(); i++) {
				tools = new JSONTools();
				JSONObject json = arr.getJSONObject(i);
				
				tools.setStart_weekday(json.getString("startWeekDay"));
				tools.setStart_minute(json.getString("startMinute"));
				tools.setStart_hour(json.getString("startHour"));
				tools.setStart_time(json.getString("startTime"));
				tools.setEnd_time(json.getString("endTime"));
				tools.setName(json.getString("name"));
				tools.setBranch_name(json.getString("branchName"));
				list.add(tools);
			}
			pb.setVisibility(View.GONE);
	
			adapter = new List_2_2Adapter(MyCourseActivity.this, list);
			my_course_list.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
*/
   /*
	private void getDateFormServise() {
	

		new Thread() {
			@Override
			public void run() {
				String result = null;
				try {
			
					result = ClientApi
							.getDataByUrl("http://121.40.151.183:8080/pula-sys/app/timecourseorder/list?pageIndex=1&condition.studentNo=PD0D00002&_json=1");

					
					Log.i("result=", result);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				Message msg = Message.obtain();
				msg.what = 6;
				msg.obj = result;
				Log.i("", "##############" + result);
				handler.sendMessage(msg);
			}
		}.start();
	} */
}

