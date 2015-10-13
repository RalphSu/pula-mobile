/**
 * 
 */
package com.pula.star;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;




import android.widget.Toast;

import com.pula.star.utils.WxUtils;
import com.pula.star.R;
import com.pula.star.activity.BaseActivity;
import com.pula.star.activity.CourseDetailH5Activity;
import com.pula.star.activity.changePasswordActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

//import com.tencent.mm.sdk.openapi.WXAppExtendObject;

import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;

//import com.tencent.mm.sdk.openapi.WXEmojiObject;

import com.tencent.mm.sdk.modelmsg.WXEmojiObject;

//import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;

//import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;

//import com.tencent.mm.sdk.openapi.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;

//import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;

//import com.tencent.mm.sdk.openapi.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
//import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendAuth;

/**
 * @author papa
 * 
 */
public class ShareDialog extends Dialog {

	private Context context;
	ImageButton btn_wxChat,btn_wxCircle,btn_qzone,btn_weibo,btn_browser;
	Button btn_cancel_share;

	private String title;// 分享标题
	private String wxContent;// 微信分享内容
	private String otherContent;// 其它分享内容
	private Bitmap icon;// 分享应用icon
	private String shareUrl;// 分享链接
	private IWXAPI msgApi;
	private static boolean wxInstall;


	private static final int EVENT_REQUEST_DJ_DATA = 1;
	private static final int EVENT_REQUEST_WX_DATA = 2;
	
	public ShareDialog(Context context,Bundle bundle) {
		super(context, R.style.MyWebDialog);		

		initData(context, bundle);
		initView();
		msgApi = WXAPIFactory.createWXAPI(context, null);
		
		if(isWXAppInstalledAndSupported(context, msgApi) == false)
		{
		  this.dismiss();
		}
		
	}

	private void initData(Context context, Bundle bundle) {
		this.context = context;
		if (bundle != null) {
			
			title = bundle.getString("title");
			wxContent = bundle.getString("wxContent");
			//icon = (Bitmap)bundle.getParcelable("icon");   
			icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
			shareUrl = bundle.getString("shareUrl");
			otherContent = bundle.getString("otherContent");
		} else {
			shareUrl = "http://www.zt-zoo.com";
			otherContent = "普拉星球";
			title = context.getResources().getString(R.string.app_name);
			wxContent = context.getResources().getString(R.string.app_desc);
			icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
		}
	}

	private static boolean  isWXAppInstalledAndSupported(Context context,IWXAPI api) {
		
		wxInstall = api.isWXAppInstalled() && api.isWXAppSupportAPI();
		
		if (!wxInstall) {
			
			Toast.makeText(context, "微信客户端未安装，请确认", Toast.LENGTH_SHORT).show();
		}
        return wxInstall;
	}

	
	@SuppressWarnings("deprecation")
	private void initView() {
		View v = LayoutInflater.from(context).inflate(
				R.layout.dialog_share, null);
		btn_wxChat = (ImageButton) v.findViewById(R.id.share_item_wxchat);
		btn_wxCircle = (ImageButton) v.findViewById(R.id.share_item_wxcircle);
		//btn_qzone = (ImageButton) v.findViewById(R.id.share_item_qzone);
		//btn_weibo = (ImageButton) v.findViewById(R.id.share_item_weibo);
		//btn_browser = (ImageButton) v.findViewById(R.id.share_item_browser);
		//btn_browser.setVisibility(isBrowser?View.VISIBLE:View.GONE);
		btn_cancel_share = (Button) v.findViewById(R.id.btn_cancel_share);
		
		
		btn_wxChat.setOnClickListener(new Button.OnClickListener(){
			

			@Override
			public void onClick(View v) {
			
					WxUtils.registWxApi(context);
					if (wxContent == null) {
						wxContent = otherContent;
					}
					WxUtils.sendWebPageWx(shareUrl, title, wxContent, icon, SendMessageToWX.Req.WXSceneSession);
			    	dismiss();
			}
			
		 }
		);
		btn_wxCircle.setOnClickListener(new Button.OnClickListener(){	
			
			@Override
			public void onClick(View v) {
			WxUtils.registWxApi(context);
			if (wxContent == null) {
				wxContent = otherContent;
			}
			WxUtils.sendWebPageWx(shareUrl, title, wxContent, icon, SendMessageToWX.Req.WXSceneTimeline);
			dismiss();
		}
			
		 });
		
		//btn_qzone.setOnClickListener(mAct.click);
		//btn_weibo.setOnClickListener(mAct.click);
		//btn_browser.setOnClickListener(mAct.click); 
	
		btn_cancel_share.setOnClickListener( new View.OnClickListener()
	        {
	           public void onClick(View v)

	           {	           
	        	   ShareDialog.this.dismiss();             

	           }   
	        }
	       );
	      
		
		setContentView(v);

		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		
		getWindow().setGravity(Gravity.BOTTOM);
	}

	
	
}
