package com.yuhj.ontheway.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.adapter.List_3Adapter;
import com.yuhj.ontheway.utils.StaticStrings;
import com.yuhj.ontheway.activity.MyCourseActivity;
import com.yuhj.ontheway.activity.UserInfoActivity;


public class LoginWelcomeAvtivity extends Activity {
	SharedPreferences preference;
	private ImageView img_my_info,img_my_course,img_my_booking,img_my_point;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.frame_user_page);
		
		img_my_info=(ImageView) findViewById(R.id.img_my_info);
		img_my_course=(ImageView) findViewById(R.id.img_my_course);
		img_my_booking=(ImageView) findViewById(R.id.img_my_booking);
		img_my_point=(ImageView) findViewById(R.id.img_my_point);
		
		img_my_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_info = new Intent(LoginWelcomeAvtivity.this, UserInfoActivity.class);
	            startActivity(intent_my_info);

			}
		});
		
		img_my_course.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_course = new Intent(LoginWelcomeAvtivity.this, MyCourseActivity.class);
	            startActivity(intent_my_course);

			}
		});
		
		img_my_booking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_booking = new Intent(LoginWelcomeAvtivity.this, MyBookingActivity.class);
				
				//Intent intent_my_booking = new Intent(LoginWelcomeAvtivity.this, MainActivity.class);				 
	            startActivity(intent_my_booking);
			}
		});
		
		img_my_point.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_point = new Intent(LoginWelcomeAvtivity.this, MyPointActivity.class);
	            startActivity(intent_my_point);
			}
		});
	}
	
	
}
