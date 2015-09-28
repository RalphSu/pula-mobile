package com.pula.star.activity;

import java.util.Calendar;

import org.joda.time.DateTime;

import com.pula.star.R;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistedUserBookingDialogActivity extends BaseActivity {

	private String userNameValue;

	private SharedPreferences sp;

	String date;
	DateTime selected;
	String plan;

	private String course_name;

	private String course_booking_time;

	private String student_name;
	private String student_parent_name;
	private String mobile_phone;

	private SharedPreferences preference;

	TextView booking_course_name;

	TextView booking_course_time;

	TextView booking_user_no;

	ImageView btn_enter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.registed_user_booking_dialog);

		selected = (DateTime) getIntent().getSerializableExtra("calSelected");

		date = (String) getIntent().getStringExtra("date");

		plan = (String) getIntent().getStringExtra("plan");

		course_name = (String) getIntent().getStringExtra("courseName");

		sp = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);

		userNameValue = sp.getString("USER_NAME", "USER_NAME");

		btn_enter = (ImageView) findViewById(R.id.btn_enter);

		booking_user_no = (TextView) findViewById(R.id.tv_user_no);
		booking_course_name = (TextView) findViewById(R.id.tv_course_name);
		booking_course_time = (TextView) findViewById(R.id.tv_booking_time_name);

		booking_course_name.setText("预约课程:" + course_name);
		booking_course_time.setText("预约时间:" + date + " " + plan);

		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				MODE_PRIVATE);

		student_name = preference.getString("USER_INFO_NAME", "");
		student_parent_name = preference.getString("USER_INFO_PARENT_NAME", "");
		mobile_phone = preference.getString("USER_INFO_MOBILE", "");

		// 分为已经注册的用户和未注册用户
		if (userNameValue.equals("USER_NAME") != true) {
			booking_user_no.setText("用户编号:" + userNameValue);

			btn_enter.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					new enter_action().execute();

				}
			});

		}

	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(RegistedUserBookingDialogActivity.this,
				CurriculumScheduleActivity.class);
		intent.putExtra("calSelected", selected);
		
		
		startActivity(intent);

	}

	class enter_action extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {

			return ClientApi.audition_create(student_name,mobile_phone,student_parent_name,plan,course_name);

			//return true;
		}

		@Override
		protected void onPostExecute(final Boolean result) {

			super.onPostExecute(result);
			if (result == true) {

				// Toast.makeText(BookingDialogActivity.this,"预约成功",
				// Toast.LENGTH_LONG).show();

				showCustomMessageOK("预约请求已经提交", "请等待短信通知");

			} else {

				showCustomMessage("预约请求失败", "请核实用户信息");
			}
		}

	}

	/**
	 * it will show the OK/CANCEL dialog like iphone, make sure no keyboard is
	 * visible
	 * 
	 * @param pTitle
	 *            title for dialog
	 * @param pMsg
	 *            msg for body
	 */
	private void showCustomMessage(String pTitle, final String pMsg) {
		final Dialog lDialog = new Dialog(
				RegistedUserBookingDialogActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar);

		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.r_okcanceldialogview);

		((TextView) lDialog.findViewById(R.id.dialog_title)).setText(pTitle);
		((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
		// ((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
		((Button) lDialog.findViewById(R.id.cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks
						// CANCEL
						// lDialog.dismiss();
						Intent intent = new Intent(
								RegistedUserBookingDialogActivity.this,
								CurriculumScheduleActivity.class);
						intent.putExtra("calSelected", selected);
						startActivity(intent);

					}
				});
		((Button) lDialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks OK

						lDialog.dismiss();
					}
				});
		lDialog.show();

	}

	/**
	 * it will show the OK dialog like iphone, make sure no keyboard is visible
	 * 
	 * @param pTitle
	 *            title for dialog
	 * @param pMsg
	 *            msg for body
	 */
	private void showCustomMessageOK(String pTitle, final String pMsg) {
		final Dialog lDialog = new Dialog(
				RegistedUserBookingDialogActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.r_okdialogview);
		((TextView) lDialog.findViewById(R.id.dialog_title)).setText(pTitle);
		((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
		// ((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
		((Button) lDialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// write your code to do things after users clicks OK
						// lDialog.dismiss();
						Intent intent = new Intent(
								RegistedUserBookingDialogActivity.this,
								CurriculumScheduleActivity.class);
						intent.putExtra("calSelected", selected);
						startActivity(intent);
					}
				});
		lDialog.show();

	}

}
