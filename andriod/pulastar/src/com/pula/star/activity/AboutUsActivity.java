package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pula.star.utils.phoneInfoUtils;
import com.pula.star.R;
//import com.dongji.market.helper.AConstDefine;
//import com.dongji.market.helper.DJMarketUtils;
//import com.dongji.market.receiver.CommonReceiver;

/**
 * 关于界面
 * @author wenli
 *
 */
public class AboutUsActivity extends Activity {
	private TextView mPublishDateTV, mVersionInfoTV;
	private ImageView mTopLogo;
	//private CommonReceiver receiver;
	private ImageView mShareUs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		initView();
		
	}


	private void initView() {
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		mPublishDateTV = (TextView) findViewById(R.id.publish_date);
		mVersionInfoTV = (TextView) findViewById(R.id.version_info);
		mPublishDateTV.setText(R.string.publish_date_value);
		mVersionInfoTV.setText(getVersionName());
		//mTopLogo = (ImageView) findViewById(R.id.topLogo);
		

		if (!phoneInfoUtils.isPhone(this)||phoneInfoUtils.getPhysicalSize(this)<6) {
			return;
		}
		
		int screenHeight = phoneInfoUtils.getScreenSize(this).heightPixels;
		int screenWidth = phoneInfoUtils.getScreenSize(this).widthPixels;
		LinearLayout.LayoutParams linearParams;
		int actualHeight;
		int actualWidth;
		int leftMargin;
		int rightMargin;
		
		actualHeight = (int) (screenHeight * 0.042);
		actualWidth = actualHeight;
		leftMargin = (int) (screenWidth * 0.022);
		/*
		linearParams = (LayoutParams) mTopLogo.getLayoutParams();
		linearParams.width = actualWidth;
		linearParams.height = actualHeight;
		linearParams.leftMargin = leftMargin;
		mTopLogo.setLayoutParams(linearParams);
		*/
		actualHeight = (int) (screenHeight * 0.03);
		actualWidth = (int) (actualHeight * 0.692);
		rightMargin = (int) (screenWidth * 0.03);
		linearParams = (LayoutParams) mShareUs.getLayoutParams();
		linearParams.width = actualWidth;
		linearParams.height = actualHeight;
		linearParams.rightMargin = rightMargin;
		mShareUs.setLayoutParams(linearParams);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
	public String getVersionName() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
