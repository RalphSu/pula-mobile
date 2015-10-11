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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.pula.star.R;
import com.pula.star.adapter.MyPointListAdapter;
import com.pula.star.bean.BookingData;
import com.pula.star.bean.MyPoints;
import com.pula.star.bean.MyWorkData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.RTPullListView;
import com.pula.star.utils.StaticStrings;
import com.pula.star.adapter.ShowGridAdapter;


public class MyWorkActivity extends Activity {
	
	//RTPullListView my_booking_list;
	//ProgressBar pb;
	private ArrayList<MyWorkData> work_list;
	private ArrayList<MyWorkData> work_list_rcvd;
	
	//private LayoutInflater inflater;
	//private View view;
	private GridView grid;
	private Display display;
	private SharedPreferences preference;
	private String userName;
	
	private int itemWidth;
	private int column_count = 2;// 显示列数
	private int page_count = 16;// 每次加载15张图片
	private int current_page = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.mywork);

        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		userName = preference.getString("USER_NAME", "");
			
	 
		display = this.getWindowManager().getDefaultDisplay();
		itemWidth = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
		
		
		
		//pb = (ProgressBar) findViewById(R.id.pb);
	
		new Thread() {
			public void run() {
				
				
				List<MyWorkData> booking_list_send = ClientApi.getMyWorkList(userName);
			
				if (booking_list_send != null) {
										
					Message msg = Message.obtain();
					msg.what = 2;
					msg.obj = booking_list_send;
					handler.sendMessage(msg);
				}
			};
		}.start(); 
		
		//initGrid();
		
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
			
			switch (msg.what) {
			case 2:
				
				work_list_rcvd = new ArrayList<MyWorkData>();
				
				if(msg.obj != null)
				{	
				
				 work_list_rcvd = (ArrayList<MyWorkData>)msg.obj;
				} 
										
				initGrid();
				break;
		   		
				
		}
	  }
   };	
   
   private void initGrid(){
		
		grid = (GridView)findViewById(R.id.tab_a_grid);
		int height = display.getHeight() / 3;
		
		ShowGridAdapter adapter = new ShowGridAdapter(work_list_rcvd,MyWorkActivity.this,height);
		grid.setAdapter(adapter);
	}
   
   
}

