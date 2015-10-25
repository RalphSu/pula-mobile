package com.pula.star.activity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;

public class LoginWelcomeActivity extends Activity {
	private SharedPreferences preference;
	private Button login;
	private Button logOut;
	private Button forgetPwd;
	private Button changePwd;
	private boolean loginStatus = false;
	private String userNameValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		setContentView(R.layout.frame_user_page);

		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				MODE_PRIVATE);

		login = (Button) findViewById(R.id.log_in_button);
		logOut = (Button) findViewById(R.id.log_out_button);
		forgetPwd = (Button) findViewById(R.id.forget_pwd_button);
		changePwd = (Button) findViewById(R.id.change_pwd_button);

		loginStatus = preference.getBoolean("USER_LOGIN_STATUS", false);

		if (loginStatus == true) {
			login.setVisibility(View.GONE);
			logOut.setVisibility(View.VISIBLE);
			forgetPwd.setVisibility(View.GONE);
			changePwd.setVisibility(View.VISIBLE);
		} else {
			login.setVisibility(View.VISIBLE);
			logOut.setVisibility(View.GONE);
			forgetPwd.setVisibility(View.VISIBLE);
			changePwd.setVisibility(View.GONE);
		}

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentLogin = new Intent(LoginWelcomeActivity.this,
						LoginActivity.class);
				startActivity(intentLogin);

			}
		});

		logOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Editor editor = preference.edit();
				editor.clear();
				editor.commit();

				Intent intentLogOut = new Intent(
						LoginWelcomeActivity.this, MainActivity.class);
				startActivity(intentLogOut);

			}
		});

		forgetPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentForgetPwd = new Intent(LoginWelcomeActivity.this,
						resetPasswordActivity.class);
				startActivity(intentForgetPwd);

			}
		});

		changePwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentChangePwd = new Intent(LoginWelcomeActivity.this,
						changePasswordActivity.class);
				startActivity(intentChangePwd);

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

}
