/**
 * 
 */
package com.pula.star;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;

import com.pula.star.R;
import com.pula.star.activity.BaseActivity;


/**
 * @author papa
 * 
 */
public class ShareDialog extends Dialog {

	Context mContext;
	BaseActivity mAct;
	ImageButton btn_wxChat,btn_wxCircle,btn_qzone,btn_weibo,btn_browser;
	Button btn_cancel_share;
	boolean isBrowser;
	
	public ShareDialog(Context context,boolean isShowBrowser) {
		super(context, R.style.MyWebDialog);
		mContext = context;
		this.isBrowser = isShowBrowser;
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		View v = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_share, null);
		btn_wxChat = (ImageButton) v.findViewById(R.id.share_item_wxchat);
		btn_wxCircle = (ImageButton) v.findViewById(R.id.share_item_wxcircle);
		//btn_qzone = (ImageButton) v.findViewById(R.id.share_item_qzone);
		//btn_weibo = (ImageButton) v.findViewById(R.id.share_item_weibo);
		//btn_browser = (ImageButton) v.findViewById(R.id.share_item_browser);
		//btn_browser.setVisibility(isBrowser?View.VISIBLE:View.GONE);
		btn_cancel_share = (Button) v.findViewById(R.id.btn_cancel_share);
		
		/*
		btn_wxChat.setOnClickListener(new Button.OnClickListener(){
			
		 }
		);
		btn_wxCircle.setOnClickListener(new Button.OnClickListener(){
			
		 });
		
		//btn_qzone.setOnClickListener(mAct.click);
		//btn_weibo.setOnClickListener(mAct.click);
		//btn_browser.setOnClickListener(mAct.click);
		btn_cancel_share.setOnClickListener(new OnClickListener(){
			
		 });*/
		
		setContentView(v);

		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		
		getWindow().setGravity(Gravity.BOTTOM);
	}


}
