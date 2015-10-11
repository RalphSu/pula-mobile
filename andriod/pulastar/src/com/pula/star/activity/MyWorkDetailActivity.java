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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.HttpTools;
import com.pula.star.utils.StaticStrings;
import com.squareup.picasso.Picasso;

public class MyWorkDetailActivity extends Activity {
	String imageUrl;
	String workEffectDate;
	String comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.mywork_detail);

		imageUrl = getIntent().getStringExtra("imgUrl");
		workEffectDate = getIntent().getStringExtra("date");
		comments = getIntent().getStringExtra("comments");

		ImageView show_waterfall_image = (ImageView) findViewById(R.id.show_waterfall_image);
		TextView show_waterfall_text01 = (TextView) findViewById(R.id.show_waterfall_text01); // for
																								// date
		TextView show_waterfall_text02 = (TextView) findViewById(R.id.show_waterfall_text03); // for
																								// comments

		if (imageUrl != null) {
			Picasso.with(MyWorkDetailActivity.this).load(imageUrl)
					.placeholder(R.drawable.defaultcovers)
					.error(R.drawable.defaultcovers).into(show_waterfall_image);
		}

		if (workEffectDate != null) {
			show_waterfall_text01.setText(workEffectDate);
		}

		if (comments != null) {
			show_waterfall_text02.setText(comments);

			Typeface tf = Typeface
					.createFromAsset(getAssets(), "fonts/wwt.ttf");
			// show_waterfall_text01.setTypeface(tf);//设置字体
			show_waterfall_text02.setTypeface(tf);// 设置字体
			show_waterfall_text02.getPaint().setFakeBoldText(true);
		}
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
