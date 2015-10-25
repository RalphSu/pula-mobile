package com.pula.star.activity;

import java.util.ArrayList;

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.adapter.MyInfoListAdapter;
import com.pula.star.bean.BookingData;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.RTPullListView;
import com.pula.star.utils.StaticStrings;

public class UserInfoActivity extends Activity {

	private SharedPreferences preference;
	private String userName;
	private String passWord;
	private MyInfoListAdapter adapter;
	private ArrayList<String> info_list;
	private ArrayList<BookingData> booking_list;
	private String value;
	private Button change_pwd_button;
	private Button exit_login_button;

	RTPullListView my_info_list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_info);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		setTitle("普拉星球 - 我的信息");
		my_info_list = (RTPullListView) findViewById(R.id.my_info_list);
		
		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				MODE_PRIVATE);
		userName = preference.getString("USER_INFO_NO", "");
		passWord = preference.getString("USER_INFO_PASSWORD", "");

		Log.i("preference=", "" + userName);

		Log.i("preference=", "" + passWord);

		if ((userName == null) || (passWord == null)) {
			Toast.makeText(UserInfoActivity.this, "请重新登录", Toast.LENGTH_LONG)
					.show();
			Intent main = new Intent(UserInfoActivity.this, MainActivity.class);
			startActivity(main);
		}

		
		new Thread(runnable).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			my_info_list.onRefreshComplete();
			Bundle data = msg.getData();

			/*
			 * StringBuffer buffer = new StringBuffer();
			 * buffer.append("用户编号： ").
			 * append(data.getString("No")).append("\n");
			 * buffer.append("用户姓名： ")
			 * .append(data.getString("Name")).append("\n");
			 * buffer.append("用户性别： "
			 * ).append(data.getString("genderName")).append("\n");
			 * buffer.append
			 * ("用户生日： ").append(data.getString("Birthday")).append("\n");
			 * buffer
			 * .append("家长姓名： ").append(data.getString("parentName")).append
			 * ("\n");
			 * buffer.append("联系电话： ").append(data.getString("mobile")).append
			 * ("\n");
			 * buffer.append("家庭地址：").append(data.getString("Address")).append
			 * ("\n");
			 * buffer.append("学员卡号：").append(data.getString("BarCode")).append
			 * ("\n"); adapter = new MyInfoListAdapter(UserInfoActivity.this,
			 * data); lv.setAdapter(adapter);
			 */
			update_list_view(data);

		}
	};

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
					birthdayString = String.format("%4d-%02d-%02d",
							bday.getYear(), bday.getMonthOfYear(),
							bday.getDayOfMonth());
				}
				data.putString("Birthday", birthdayString);
				data.putString("parentName", userInfo.getParentName());
				data.putString("mobile", userInfo.getMobile());
				data.putString("genderName", userInfo.getGenderName());
				data.putString("Address", userInfo.getAddress());
				data.putString("BarCode", userInfo.getBarCode());
				data.putInt("Phone", userInfo.getPhone());

			} else {
				data.putString("No", " ");
				data.putString("Name", " ");
				data.putInt("Points", 0);
				data.putString("Birthday", "");
				data.putString("parentName", " ");
				data.putString("mobile", " ");
				data.putString("genderName", " ");
				data.putString("Address", "");
				data.putString("BarCode", "");
				data.putInt("Phone", 0);
			}
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	public void update_list_view(Bundle data) {

		info_list = new ArrayList<String>();

		value = new String();

		value = data.getString("No");
		info_list.add(value);

		value = data.getString("Name");
		info_list.add(value);

		value = data.getString("genderName");
		info_list.add(value);

		value = data.getString("parentName");
		info_list.add(value);

		value = data.getString("mobile");
		info_list.add(value);

		value = data.getString("Address");
		info_list.add(value);

		ListAdapter adapter = new MyInfoListAdapter(this, info_list);

		my_info_list.setAdapter(adapter);
	}

}
