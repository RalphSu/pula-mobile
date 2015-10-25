package com.pula.star.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;
import com.pula.star.utils.getAge;

public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText userName, password;
	private SharedPreferences sp;
	private boolean loginStatus = false;
	private String userNameValue, passwordValue;

	private String user_info_name;// 用户姓名
	private String user_info_parent_name; // 家长姓名
	private String user_info_phone; // 用户电话号码
	private int user_info_age; // 用户年龄
	private String user_info_birth; // 用户的生日 YYYY-MM-DD

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Boolean result = (Boolean) msg.obj;
				if (result == true) {
					new get_user_info().execute();
				}
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		this.requestWindowFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.logo);

		loginButton = (Button) findViewById(R.id.btn_login);
		userName = (EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		sp = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				Context.MODE_PRIVATE);
		
		
		userName.setText(sp.getString("USER_INFO_NO",""));
     	password.setText(sp.getString("USER_INFO_PASSWORD",""));

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				userNameValue = userName.getText().toString();
				passwordValue = password.getText().toString();

				if ((userNameValue.equals("") == true)
						&& (passwordValue.equals("") == true)) {
					
					Toast.makeText(LoginActivity.this, "请输入用户名与密码",
							Toast.LENGTH_LONG).show();
					
				} else {

					new loging_action(handler).execute();

				}
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
	}

	class loging_action extends AsyncTask<Void, Void, Boolean> {
		Handler mHandler;

		public loging_action(Handler handler) {

			this.mHandler = handler;
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			return ClientApi.getLoginStatus(userNameValue, passwordValue);
		}

		@Override
		protected void onPostExecute(final Boolean result) {

			super.onPostExecute(result);

			if (result == true) {

				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG)
						.show();

				Editor editor = sp.edit();
				editor.putString("USER_INFO_NO", userNameValue);
				editor.putString("USER_INFO_PASSWORD", passwordValue);
				editor.putBoolean("USER_LOGIN_STATUS", true);
				editor.commit();

				Message msg = mHandler.obtainMessage();

				msg.what = 1;
				msg.obj = result;
				mHandler.sendMessage(msg);

			} else {

				Editor editor = sp.edit();

				editor.clear();
				editor.commit();

				Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录",
						Toast.LENGTH_LONG).show();

			}
		}

	}

	class get_user_info extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {

			UserInfoData userInfo = new UserInfoData();

			userInfo = ClientApi.getUserInfoData(userNameValue, passwordValue);

			if (userInfo != null) {
				user_info_name = userInfo.getName();
				user_info_parent_name = userInfo.getParentName();
				user_info_phone = userInfo.getMobile();

				if (userInfo.getBirthday() != 0) {
					user_info_birth = new java.text.SimpleDateFormat(
							"yyyy-MM-dd").format(new java.util.Date(userInfo
							.getBirthday()));
				}

				if (user_info_birth != null) {
					user_info_age = getAge.getUserAge(user_info_birth);
				} else {
					user_info_age = 4; // 默认就是四岁吧
				}

				Log.i("user_info_age=", "" + user_info_age);
				return true;
			} else

			{

				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean result) {

			super.onPostExecute(result);
			if (result == true) {

				Editor editor = sp.edit();
				editor.putString("USER_INFO_NAME", user_info_name);
				editor.putString("USER_INFO_PARENT_NAME", user_info_parent_name);
				editor.putString("USER_INFO_MOBILE", user_info_phone);
				editor.putInt("USER_INFO_AGE", user_info_age);

				editor.commit();

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);

				startActivity(intent);

			}

		}

	}

}
