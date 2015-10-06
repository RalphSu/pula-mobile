package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.pula.star.activity.BookingDialogActivity.enter_action;
import com.pula.star.clients.ClientApi;
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
					new reset_action().execute();
					
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
	
	
	class reset_action extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {
			
			return ClientApi.resetPwd(mobilePhoneValue);
		}

		@Override
		protected void onPostExecute(final Boolean result) {

			super.onPostExecute(result);
			if (result == true) {

			
				Toast.makeText(resetPasswordActivity.this,"密码重置成功",Toast.LENGTH_LONG).show();
			
			} else {

				Toast.makeText(resetPasswordActivity.this,"密码重置失败，请核对手机号码信息",Toast.LENGTH_LONG).show();
			}
		}

	}

	

}
