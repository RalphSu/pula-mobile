package com.pula.star.fragment;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.activity.AboutUsActivity;
import com.pula.star.activity.BookingDialogActivity;
import com.pula.star.activity.LoginWelcomeActivity;
import com.pula.star.activity.MyCourseActivity;
import com.pula.star.activity.MyNoticeActivity;
import com.pula.star.activity.MyWorkActivity;
import com.pula.star.activity.UserInfoActivity;
import com.pula.star.activity.changePasswordActivity;
import com.pula.star.activity.resetPasswordActivity;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.getAge;

public class LoginFragment extends Fragment {

	private TextView userName; // 用户姓名
	private String userNameValue;// 用户姓名
	private LinearLayout userInfoLinearLayout;
	private RelativeLayout bookingRelativeLayout;
	private RelativeLayout aboutUsRelativeLayout;
	private Button aboutUsButton;
	private Button bookingButton;
	private SharedPreferences sp;
	private boolean loginStatus = false;// 登录状态
	private ImageView imgMyInfo, imgMyNotice, imgMyCourse, imgMyWork,
			imgEnterNext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.login, container, false);
		// 获得实例对象
		sp = this.getActivity().getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);

		userName = (TextView) view.findViewById(R.id.user_name);
		userInfoLinearLayout = (LinearLayout) view.findViewById(R.id.user_info);
		bookingRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.booking);
		aboutUsRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.aboutUs);

		imgEnterNext = (ImageView) view.findViewById(R.id.enter_next);
		imgMyInfo = (ImageView) view.findViewById(R.id.img_my_info);
		imgMyNotice = (ImageView) view.findViewById(R.id.img_my_notice);
		imgMyCourse = (ImageView) view.findViewById(R.id.img_my_course);
		imgMyWork = (ImageView) view.findViewById(R.id.img_my_work);

		aboutUsButton = (Button) view.findViewById(R.id.aboutUs_button);
		bookingButton = (Button) view.findViewById(R.id.booking_button);

		loginStatus = (boolean) sp.getBoolean("USER_LOGIN_STATUS", false);

		userNameValue = (String) sp.getString("USER_INFO_NAME", "未登录");

		if ((loginStatus == true) && (userNameValue.equals("未登录") == false)) {
			userName.setText(userNameValue);
			userInfoLinearLayout.setVisibility(View.VISIBLE);
			bookingRelativeLayout.setVisibility(View.GONE);
			aboutUsRelativeLayout.setVisibility(View.VISIBLE);
		} else {
			userName.setText(userNameValue);
			userInfoLinearLayout.setVisibility(View.GONE);
			bookingRelativeLayout.setVisibility(View.VISIBLE);
			aboutUsRelativeLayout.setVisibility(View.VISIBLE);

		}

		imgMyInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_info = new Intent(LoginFragment.this
						.getActivity(), UserInfoActivity.class);
				startActivity(intent_my_info);

			}
		});

		imgMyNotice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_info = new Intent(LoginFragment.this
						.getActivity(), MyNoticeActivity.class);
				startActivity(intent_my_info);

			}
		});

		imgMyCourse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_course = new Intent(LoginFragment.this
						.getActivity(), MyCourseActivity.class);
				startActivity(intent_my_course);

			}
		});

		imgMyWork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_point = new Intent(LoginFragment.this
						.getActivity(), MyWorkActivity.class);
				startActivity(intent_my_point);
			}
		});

		imgEnterNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_my_point = new Intent(LoginFragment.this
						.getActivity(), LoginWelcomeActivity.class);
				startActivity(intent_my_point);
			}
		});

		aboutUsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginFragment.this.getActivity(),
						AboutUsActivity.class);

				startActivity(intent);
			}
		});

		bookingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginFragment.this.getActivity(),
						BookingDialogActivity.class);

				startActivity(intent);

			}
		});
		return view;
	}	
}
