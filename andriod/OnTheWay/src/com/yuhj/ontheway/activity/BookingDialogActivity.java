package com.yuhj.ontheway.activity;

import java.util.Calendar;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.clients.ClientApi;
import com.yuhj.ontheway.utils.StaticStrings;

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
import android.widget.TextView;
import android.widget.Toast;

public class BookingDialogActivity extends BaseActivity
{

    private EditText name, age, phone;

    private String nameValue,ageValue,phoneValue;
    
    private String userNameValue;
    
    private String course_name;
    
    private String course_booking_time;
    
	private Button btn_enter;
	
	private SharedPreferences sp;
	
	String date;
	Calendar selected;
	String plan;
	
	TextView booking_course_name;
	
	TextView booking_course_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.booking_dialog);
		
		selected = (Calendar) getIntent().getSerializableExtra("calSelected");
		
		date = (String)getIntent().getStringExtra("date");
		
		plan = (String)getIntent().getStringExtra("plan");
			
		course_name = (String)getIntent().getStringExtra("courseName");
		
		
		
		sp = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		
		userNameValue = sp.getString("USER_NAME", "USER_NAME");
		
		name = (EditText) findViewById(R.id.et_name);
		age = (EditText) findViewById(R.id.et_age);
		phone = (EditText) findViewById(R.id.et_phone);
		btn_enter = (Button)findViewById(R.id.btn_enter);
		booking_course_name = (TextView)findViewById(R.id.tv_course_name);
		booking_course_time = (TextView)findViewById(R.id.tv_booking_time_name);
		
		booking_course_name.setText("预约课程:"+ course_name);
		booking_course_time.setText("预约时间:"+ date + " " + plan );
		
		//分为已经注册的用户和未注册用户
		if(userNameValue.equals("USER_NAME") == true)
		  {
			
			btn_enter.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					nameValue = name.getText().toString();
				    ageValue = age.getText().toString();
				    phoneValue = phone.getText().toString();
				    System.out.println(nameValue);
				    System.out.println(ageValue);
				    System.out.println(phoneValue);
				    
				    Log.i("nameValue =", "" + nameValue);
				    Log.i("ageValue =", "" + ageValue);
				    Log.i("phoneValue =", "" + phoneValue);
				    
				    new enter_action().execute();
				    				  
				}
			});
			
		  }
		else
		{	
			Log.i("username value=","" + userNameValue);
			Log.i("date value=","" + date);
			Log.i("plan value=","" + plan);
			
		}
		
		
		
	}	
	
	   @Override
	    public void onBackPressed() {

			Intent intent = new Intent(BookingDialogActivity.this,CurriculumScheduleActivity.class);
			intent.putExtra("calSelected", selected);
			startActivity(intent);
			
			startActivity(intent);
			
	    }
	   
	 class enter_action extends AsyncTask<Void,Void,Boolean> {

	        @Override
	        protected Boolean doInBackground(Void... arg0) {
	            //return ClientApi.audition_create(nameValue,ageValue,phoneValue);
	        	if((nameValue.equals("") == true)||(ageValue.equals("") == true)||(phoneValue.equals("") == true))
	        	{
	        	 return false;	
	        	}
	        		
	        	return true;
	        }

   
	        @Override
	        protected void onPostExecute(final Boolean result) {

	            super.onPostExecute(result);
	            if (result == true) {
	    
	            	
	            	//Toast.makeText(BookingDialogActivity.this,"预约成功", Toast.LENGTH_LONG).show();
	            	
	            	showCustomMessageOK("预约请求已经提交","请等待短信通知"); 
	            					
	            } else {
	            	
	            	showCustomMessage("预约请求失败","请核实用户信息");	
	            }
	        }

 } 
	
	 
	 class audition_create_action extends AsyncTask<Void,Void,Boolean> {

	        @Override
	        protected Boolean doInBackground(Void... arg0) {
	            //return ClientApi.audition_create(nameValue,ageValue,phoneValue);
	        	if((userNameValue.equals("") == true)||(date.equals("") == true)||(plan.equals("") == true))
	        	{
	        	 return false;	
	        	}
	        		
	        	return true;
	        }


	        @Override
	        protected void onPostExecute(final Boolean result) {

	            super.onPostExecute(result);
	            if (result == true) {
	    
	            	
	            	//Toast.makeText(BookingDialogActivity.this,"预约成功", Toast.LENGTH_LONG).show();
	            	
	            	showCustomMessageOK("预约请求已经提交","请等待短信通知"); 
	            	
	            	//show_customer_ok();
					//Intent intent = new Intent(LoginFragment.this.getActivity(),LoginWelcomeAvtivity.class);

					//startActivity(intent);
					
	            } else {
	            	//show_custoemr_msg();
	            	
	            	showCustomMessage("预约请求失败","请核实用户信息");	
	            }
	        }

} 
	 
	 /**
		 * it will show the OK/CANCEL dialog like iphone, make sure no keyboard is visible
		 * 
		 * @param pTitle
		 *            title for dialog
		 * @param pMsg
		 *            msg for body
		 */
		private void showCustomMessage(String pTitle, final String pMsg) {
			final Dialog lDialog = new Dialog(BookingDialogActivity.this,
					android.R.style.Theme_Translucent_NoTitleBar);
			
			lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			lDialog.setContentView(R.layout.r_okcanceldialogview);
			
			((TextView) lDialog.findViewById(R.id.dialog_title)).setText(pTitle);
			((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
			//((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
			((Button) lDialog.findViewById(R.id.cancel))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// write your code to do things after users clicks CANCEL
							//lDialog.dismiss();
							Intent intent = new Intent(BookingDialogActivity.this,CurriculumScheduleActivity.class);
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
			final Dialog lDialog = new Dialog(BookingDialogActivity.this,
					android.R.style.Theme_Translucent_NoTitleBar);
			lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			lDialog.setContentView(R.layout.r_okdialogview);
			((TextView) lDialog.findViewById(R.id.dialog_title)).setText(pTitle);
			((TextView) lDialog.findViewById(R.id.dialog_message)).setText(pMsg);
			//((Button) lDialog.findViewById(R.id.ok)).setText("Ok");
			((Button) lDialog.findViewById(R.id.ok))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// write your code to do things after users clicks OK
							//lDialog.dismiss();
							Intent intent = new Intent(BookingDialogActivity.this,CurriculumScheduleActivity.class);
							intent.putExtra("calSelected", selected);
							startActivity(intent);
						}
					});
	 		lDialog.show();

		}
			
			
}



