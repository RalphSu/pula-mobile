package com.pula.star.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.ShareDialog;
import com.pula.star.R;
import com.pula.star.activity.buy.BuyHuodongSubmitActivity;
import com.pula.star.utils.StaticStrings;

/**
 * @name HuodongDetailActivity
 * @Descripation 活动详情界面<br>
 * @author
 * @date 2014-10-25
 * @version 1.0
 * @param <ShareDialog>
 */
public class HuodongDetailActivity extends Activity {
    Handler handler;
    WebView webView;
    TextView textView;
    ProgressBar progressBar;
    private String url;
    private String name;
    private String noticeId;
    private Bundle bundle;
    private SharedPreferences preference;
    private String userName;
    private String passWord;
    private ImageButton btnShare;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        userName = preference.getString("USER_NAME", "");
        passWord = preference.getString("PASSWORD", "");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        	
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        
        bundle = new Bundle();
        bundle.putString("shareUrl", url);
        bundle.putString("title", "普拉星球");
        bundle.putString("wxContent",name+"活动");
        bundle.putString("otherContent","少儿艺术创造力研发中心");
        
        LinearLayout rootViewLayout = new LinearLayout(this);
        rootViewLayout.setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_huodong_detail, null);
        rootViewLayout.addView(view);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        
        webView = new WebView(this);
        rootViewLayout.addView(progressBar);
        rootViewLayout.addView(webView);
        
        addBuyNowButton(rootViewLayout);

        setContentView(rootViewLayout);
        btnShare = (ImageButton)findViewById(R.id.btn_share);
        
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 页面下载完毕,却不代表页面渲染完毕显示出来
                // WebChromeClient中progress==100时也是一样
                if (webView.getContentHeight() != 0) {
                    // 这个时候网页才显示
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 自身加载新链接,不做外部跳转
                view.loadUrl(url);
                return true;
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 这里将textView换成你的progress来设置进度
                // if (newProgress == 0) {
                // textView.setVisibility(View.VISIBLE);
                // progressBar.setVisibility(View.VISIBLE);
                // }
                progressBar.setProgress(newProgress);
                progressBar.postInvalidate();
                // if (newProgress == 100) {
                // textView.setVisibility(View.GONE);
                // progressBar.setVisibility(View.GONE);
                // }
            }
        });
        
        btnShare.setOnClickListener( new OnClickListener()
         {
            public void onClick(View v)

            {

            	ShareDialog shareDialog = new ShareDialog(HuodongDetailActivity.this, bundle);
    			shareDialog.show();             

            }   
         }
        );
       
        
        
    }

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
    private void addBuyNowButton(LinearLayout rootViewLayout) {
        // check login
        if (userName == null || passWord == null) {
            return;
        }
        // TODO check whether already bought the course?

        Button btn = new Button(this);
        btn.setText("马上购买!");
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(params);
        rootViewLayout.addView(btn);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // start buy activity
                Intent intent = new Intent(HuodongDetailActivity.this, BuyHuodongSubmitActivity.class);
                intent.putExtra("noticeId", noticeId);
                startActivity(intent);
            }
        });
    }

}