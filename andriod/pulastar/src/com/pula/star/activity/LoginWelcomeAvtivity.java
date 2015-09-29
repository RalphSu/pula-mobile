package com.pula.star.activity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;

public class LoginWelcomeAvtivity extends Activity {
    private SharedPreferences preference;
    private ImageView img_pula_logo,img_my_info,img_my_notice, img_my_course, img_my_booking, img_my_point;
	
    private String userName;
    private String passWord;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        preference=getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        userName=preference.getString("USER_NAME", "");
        passWord=preference.getString("PASSWORD", "");

		setContentView(R.layout.frame_user_page);

		img_my_info = (ImageView) findViewById(R.id.img_my_info);
		img_pula_logo = (ImageView) findViewById(R.id.img_pula_logo);
		
        img_my_notice = (ImageView) findViewById(R.id.img_my_notice);
        img_my_course = (ImageView) findViewById(R.id.img_my_course);
        img_my_booking = (ImageView) findViewById(R.id.img_my_booking);
        img_my_point = (ImageView) findViewById(R.id.img_my_work);

        
        img_my_info.setImageResource(R.drawable.my_info);
        img_pula_logo.setImageResource(R.drawable.pula_logo_welocme_pic);
        img_my_notice.setImageResource(R.drawable.my_notice);   
        img_my_course.setImageResource(R.drawable.my_course);  
        img_my_booking.setImageResource(R.drawable.my_booking);   
        img_my_point.setImageResource(R.drawable.my_work);  
        
        
        img_my_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                Intent intent_my_info = new Intent(LoginWelcomeAvtivity.this, UserInfoActivity.class);
	            startActivity(intent_my_info);

			}
		});
        
        img_pula_logo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                Intent intent_my_info = new Intent(LoginWelcomeAvtivity.this, AboutUsActivity.class);
	            startActivity(intent_my_info);

			}
		});
        
        img_my_notice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                Intent intent_my_info = new Intent(LoginWelcomeAvtivity.this, MyNoticeActivity.class);
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
                Intent intent_my_point = new Intent(LoginWelcomeAvtivity.this, MyWorkH5Activity.class);
	            startActivity(intent_my_point);
			}
		});
        
      //  new Thread(runnable).start();
	}
    
	/*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            Bundle data = msg.getData();
            StringBuffer buffer = new StringBuffer();

            buffer.append("编号： ").append(data.getString("No")).append("\n");
            buffer.append("姓名： ").append(data.getString("Name")).append("\n");
            buffer.append("生日： ").append(data.getString("Birthday")).append("\n");
            buffer.append("家长： ").append(data.getString("parentName")).append("\n");
            buffer.append("电话： ").append(data.getString("mobile")).append("\n");

            TextView textView = (TextView) findViewById(R.id.user_info_text);
            textView.setText(buffer.toString());

        }
    };
	
    private static final DateTimeFormatter formatter = DateTimeFormat.fullDate();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            UserInfoData userInfo = new UserInfoData();

            userInfo = ClientApi.getUserInfoData(userName, passWord);
            Message msg = new Message();
            Bundle data = new Bundle();

            if (userInfo != null) {
                data.putString("No", userInfo.getNo());
                data.putString("Name", userInfo.getName());
                data.putInt("Points", userInfo.getPoints());
                String birthdayString = "";
                if (userInfo.getBirthday() != 0) {
                    DateTime bday = new DateTime(userInfo.getBirthday());
                    birthdayString = String.format("%4d-%02d-%02d", bday.getYear(), bday.getMonthOfYear(), bday.getDayOfMonth());
                }
                data.putString("Birthday", birthdayString);
                data.putString("parentName", userInfo.getParentName());
                data.putString("mobile", userInfo.getMobile());

            } else {
                data.putString("No", " ");
                data.putString("Name", " ");
                data.putInt("Points", 0);
                data.putInt("Birthday", 0);
                data.putString("parentName", " ");
                data.putInt("Phone", 0);
            }
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
  */
}
