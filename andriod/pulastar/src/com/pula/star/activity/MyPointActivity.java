package com.pula.star.activity;

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

import com.pula.star.R;
import com.pula.star.adapter.MyPointListAdapter;
import com.pula.star.bean.MyPoints;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.RTPullListView;
import com.pula.star.utils.StaticStrings;

public class MyPointActivity extends Activity {
	
	RTPullListView my_booking_list;
	ProgressBar pb;
	
	private ArrayList<MyPoints> point_list;
	private ArrayList<MyPoints> point_list_rcvd;

	private MyPointListAdapter adapter;
	private SharedPreferences preference;
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.frame_my_booking);

        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		userName = preference.getString("USER_NAME", "");
			
		my_booking_list = (RTPullListView) findViewById(R.id.my_course_list);
		pb = (ProgressBar) findViewById(R.id.pb);
	
		new Thread() {
			public void run() {
				Log.i("username=","" + userName);
				
				List<MyPoints> booking_list_send = ClientApi.getMyPointList(userName);
				//List<BookingData> booking_list_send = ClientApi.getBookingList("", "");
				if (booking_list_send != null) {
					
					
					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = booking_list_send;
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

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			my_booking_list.onRefreshComplete();
			switch (msg.what) {
			case 2:
				
				point_list_rcvd = new ArrayList<MyPoints>();
				
				if(msg.obj != null)
				{	
				 Log.i("msg.obj != null","DONE");	
				 point_list_rcvd = (ArrayList<MyPoints>)msg.obj;
				} 
				
				
				
				pb.setVisibility(View.GONE);
				
				adapter = new MyPointListAdapter(MyPointActivity.this, point_list_rcvd);

				my_booking_list.setAdapter(adapter);
				// list列表点击事件
				
				my_booking_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								
								/*
								position = position - 1;
								MyCourseJsonTools to = list.get(position);
								Intent intent = new Intent(MyBookingActivity.this,CourseDetailH5Activity.class);
								
								intent.putExtra("SearchId",to.getCourse_no());
								intent.putExtra("name", to.getName());
								startActivity(intent); */
							}
						}); 
				
				break;
		   		
			
		}
	  }
   };	
   
   
}

