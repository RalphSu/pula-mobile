package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class changePasswordActivity extends BaseActivity {
	private EditText oldPwd, newPwd1, newPwd2;
	private String oldPwdValue, passWord1Value, passWord2Value;
	private Button doAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		this.requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.change_pwd);

		doAction = (Button) findViewById(R.id.btn_enter);
		oldPwd = (EditText) findViewById(R.id.et_old_mima);
		newPwd1 = (EditText) findViewById(R.id.et_mima_1);
		newPwd2 = (EditText) findViewById(R.id.et_mima_2);

		doAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				oldPwdValue = oldPwd.getText().toString();
				passWord1Value = newPwd1.getText().toString();
				passWord2Value = newPwd2.getText().toString();

				if (oldPwdValue.equals("")) {
					Toast.makeText(changePasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
				} else if ((passWord1Value.equals("")) || (passWord2Value.equals(""))) {
					Toast.makeText(changePasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
				} else {
					if (passWord2Value.equals(passWord1Value) == true) {
						Toast.makeText(changePasswordActivity.this, "密码输入一致，准备修改", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(changePasswordActivity.this, "密码输入不一致，请修改", Toast.LENGTH_SHORT).show();
					}

				}

			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}

}
