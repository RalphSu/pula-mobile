package com.pula.star.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.pula.star.R;

/**
 * @author yangyu
 *  功能描述：欢迎界面Activity（Logo）
 */
public class WelcomeActivity extends BaseActivity {  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);  
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        // JodaTimeAndroid.init(this);
        initJoda();
        
        /**
		 * millisInFuture:从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
		 * countDownInterval:接收onTick(long)回调的间隔时间
		 */
       // AseoZdpAseo.initType(this, AseoZdpAseo.SCREEN_TYPE);
        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.welcome_image);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.welcome);
		linearLayout.startAnimation(animation);
        new CountDownTimer(5000, 1000) {  
            @Override  
            public void onTick(long millisUntilFinished) {  
            }  
  
            @Override  
            public void onFinish() {  
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);  
                startActivity(intent);  
                WelcomeActivity.this.finish();  
            }  
        }.start();  
    }

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
    private void initJoda() {
      //  System.setProperty("org.joda.time.DateTimeZone.Provider", 
       //         "com.yuhj.ontheway.utils.FastDateTimeZoneProvider");
    }  
  
}  
