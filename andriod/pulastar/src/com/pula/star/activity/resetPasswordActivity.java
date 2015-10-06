package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.utils.Validator;

public class resetPasswordActivity extends BaseActivity {

	private EditText mobilePhone;
	private String mobilePhoneValue;
	private Button doAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		this.requestWindowFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.reset_pwd);

		doAction = (Button) findViewById(R.id.btn_enter);
		mobilePhone = (EditText) findViewById(R.id.et_zh);
		mobilePhone.setInputType(InputType.TYPE_CLASS_NUMBER);

		doAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mobilePhoneValue = mobilePhone.getText().toString();

				if (mobilePhoneValue.equals("")) {

					Toast.makeText(resetPasswordActivity.this, "请输入电话号码",
							Toast.LENGTH_SHORT).show();

				} else if (Validator.isMobile(mobilePhoneValue) == false) {
					Toast.makeText(resetPasswordActivity.this, "请输入正确的电话号码",
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(resetPasswordActivity.this, "密码重置",
							Toast.LENGTH_SHORT).show();
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

}
