/**
 * 
 */
package com.pula.star.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.utils.StaticStrings;

/**
 * 
 * 我的作品
 * 
 * @author Liangfei
 *
 */
public class MyWorkH5Activity extends BaseActivity {
    private SharedPreferences preference;

    private String userName;
    private String passWord;

    private Handler handler;
    private WebView webView;
//    private TextView titleView;
    private ProgressBar progressBar;

    private String url;

    private static final String MY_WORK_URL = "http://121.40.151.183:8080/pula-sys/app/image/icon?fp=logo.png&sub=notice&studentNo=%s";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        userName = preference.getString("USER_NAME", "");
        passWord = preference.getString("PASSWORD", "");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        url = String.format(MY_WORK_URL, userName);

        LinearLayout rootViewLayout = new LinearLayout(this);
        rootViewLayout.setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mywork_h5, null);
        
//        titleView = (TextView)view.findViewById(R.id.mywork_title);
//        titleView.setText(String.format("我的作品 : %s", userName));
        
        rootViewLayout.addView(view);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        webView = new WebView(this);
        rootViewLayout.addView(progressBar);
        rootViewLayout.addView(webView);

        setContentView(rootViewLayout);

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        addWebViewListener();

    }

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
    private void addWebViewListener() {
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
}
