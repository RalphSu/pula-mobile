package com.pula.star.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.ShareDialog;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.StaticStrings;
import com.squareup.picasso.Picasso;

public class MyWorkDetailActivity extends Activity {
	String imageUrl;
	String no;
	String shareUrl;
	String workEffectDate;
	String comments;
	int rate;
    private Bundle bundle;
    private ImageButton btnShare;
    String student_name;
    private SharedPreferences preference;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mywork_detail);
		
		  preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
		  student_name = preference.getString("USER_INFO_NAME", "");
	        
	        
		imageUrl = getIntent().getStringExtra("imgUrl");
		shareUrl = "http://121.40.151.183:8080/pula-sys/app/timecoursework/appshow?no="+getIntent().getStringExtra("no");
		workEffectDate = getIntent().getStringExtra("date");
		comments = getIntent().getStringExtra("comments");
        rate = getIntent().getIntExtra("rate",1);
        
        bundle = new Bundle();
        bundle.putString("shareUrl", shareUrl);
        bundle.putString("title", "普拉星球");
        bundle.putString("wxContent",student_name + "小朋友作品");
        bundle.putString("otherContent","少儿艺术创造力研发中心");
        
        
		ImageView show_waterfall_image = (ImageView) findViewById(R.id.show_waterfall_image);
		TextView show_waterfall_text01 = (TextView) findViewById(R.id.show_waterfall_text01); // for
																								// date
		btnShare = (ImageButton)findViewById(R.id.btn_share);
		 
		ImageView star_image = (ImageView)findViewById(R.id.star_image);
		
		if (imageUrl != null) {
			Picasso.with(MyWorkDetailActivity.this).load(imageUrl)
					.placeholder(R.drawable.defaultcovers)
					.error(R.drawable.defaultcovers).into(show_waterfall_image);
		}

		if (workEffectDate != null) {
			show_waterfall_text01.setText(workEffectDate);
		}

		
		if (rate == 0)
			rate = 1;

		rate = rate % 5;
		
		String image_name;

		image_name = "star" + rate;

		Resources resources = getResources();

		int indentify = resources.getIdentifier(getPackageName()
				+ ":drawable/" + image_name, null, null);

		if (indentify > 0) {
			star_image.setImageResource(indentify);
		}
		
		  btnShare.setOnClickListener( new OnClickListener()
	         {
	            public void onClick(View v)

	            {

	            	ShareDialog shareDialog = new ShareDialog(MyWorkDetailActivity.this, bundle);
	    			shareDialog.show();             

	            }   
	         }
	        );
		/*
		if (comments != null) {
			show_waterfall_text02.setText(comments);

			Typeface tf = Typeface
					.createFromAsset(getAssets(), "fonts/wwt.ttf");
			// show_waterfall_text01.setTypeface(tf);//设置字体
			show_waterfall_text02.setTypeface(tf);// 设置字体
			show_waterfall_text02.getPaint().setFakeBoldText(true);
		}
		*/
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
