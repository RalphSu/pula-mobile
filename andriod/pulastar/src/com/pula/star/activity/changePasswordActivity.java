package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;
import com.pula.star.utils.Validator;

public class changePasswordActivity extends BaseActivity {
	private EditText oldPwd, newPwd1, newPwd2;
	private String oldPwdValue, passWord1Value, passWord2Value;
	private Button doAction;
	private String studentNo;
	private SharedPreferences preference;
	
	

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

		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		
		studentNo = preference.getString("USER_NAME", "");
		
		doAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				oldPwdValue = oldPwd.getText().toString();
				passWord1Value = newPwd1.getText().toString();
				passWord2Value = newPwd2.getText().toString();

				if (oldPwdValue.equals("")) {
					Toast.makeText(changePasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
				} else if ((passWord1Value.equals("")) || (passWord2Value.equals(""))) {
					Toast.makeText(changePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
				} else {
					if (passWord2Value.equals(passWord1Value) == true) {
						if(Validator.isPassword(passWord2Value) == false)
						{	
						 Toast.makeText(changePasswordActivity.this, "密码格式不对，请设置长度为6-16位只包含字母和数字的密码", Toast.LENGTH_SHORT).show();
						}
						else
						{
						  new change_pwd_action().execute();	
						}
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
	
	class change_pwd_action extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {
			
			return ClientApi.changePwd(studentNo,oldPwdValue,passWord1Value);
		}

		@Override
		protected void onPostExecute(final Boolean result) {

			super.onPostExecute(result);
			if (result == true) {
			
				Toast.makeText(changePasswordActivity.this,"密码修改成功",Toast.LENGTH_LONG).show();
			
			} else {

				Toast.makeText(changePasswordActivity.this,"密码重置失败，请核对密码信息",Toast.LENGTH_LONG).show();
			}
		}

	}


}
