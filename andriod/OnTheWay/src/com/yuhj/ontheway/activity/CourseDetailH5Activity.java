/**
 * 
 */
package com.yuhj.ontheway.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.activity.buy.BuyCourseSubmitActivity;
import com.yuhj.ontheway.utils.StaticStrings;

/**
 * @author Liangfei
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class CourseDetailH5Activity extends BaseActivity {
    Handler handler;
    WebView webView;
    TextView textView;
    ProgressBar progressBar;
    private String url;
    private String name;

    private String SearchId;
    private String courseNo;

    private static final String COURSE_DETIAL_GET = "http://121.40.151.183:8080/pula-sys/app/timecourse/appshow?id=%s&no=%s";

    private SharedPreferences preference;
    private String userName;
    private String passWord;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        userName = preference.getString("USER_NAME", "");
        passWord = preference.getString("PASSWORD", "");

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SearchId = getIntent().getStringExtra("SearchId");
        courseNo = getIntent().getStringExtra("courseNo");

        if (SearchId == null) {
            SearchId = "";
        }

        if (courseNo == null) {
            courseNo = "";
        }

        url = String.format(COURSE_DETIAL_GET, SearchId, courseNo);

        Log.i("H5 URL =", url);

        name = getIntent().getStringExtra("name");
        LinearLayout rootViewLayout = new LinearLayout(this);
        rootViewLayout.setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_zhuanti_detail, null);
        rootViewLayout.addView(view);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        webView = new WebView(this);
        rootViewLayout.addView(progressBar);
        rootViewLayout.addView(webView);

        addBuyNowButton(rootViewLayout);

        setContentView(rootViewLayout);
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
                progressBar.setProgress(newProgress);
                progressBar.postInvalidate();
            }
        });
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
                Intent intent = new Intent(CourseDetailH5Activity.this, BuyCourseSubmitActivity.class);
                intent.putExtra("courseNo", courseNo);
                startActivity(intent);
            }
        });
    }

}
