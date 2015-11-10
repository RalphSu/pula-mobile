package com.pula.star.activity.buy;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.activity.BaseActivity;
import com.pula.star.activity.LoginWelcomeActivity;
import com.pula.star.pay.wechat.Constants;
import com.pula.star.pay.wechat.MD5;
import com.pula.star.pay.wechat.Util;
import com.pula.star.utils.StaticStrings;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/** 购买课程 **/

public class BuyCourseActivity extends BaseActivity {

	private String userInfoNo;
	private SharedPreferences preference;
	private TextView buycourse_nouse;
	private TextView buycourse_total_payable;
	private ImageView buycourse_reduction;
	private TextView buycourse_num;
	private ImageView buycourse_plus_selected;
	private RelativeLayout rela_wechat;
	private ImageView wechat_image;
	private Bundle bundle;
	private Button payNowButton;
	private TextView coursesName;

	// 子线程更新UI
	private Handler handler;
	private int courseStatus;
	private int price;
	private String id;
	private String courseName;
	private String courseNo;

	private int bonus_id;
	// 微信支付
	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	TextView show;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	private TextView buycoursePrice;
	private ProgressDialog dialog;
	private String xmlstring;
	private String type;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		setContentView(R.layout.activity_buycourse);
		init();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				super.handleMessage(msg);
				String message = (String) msg.obj;

				if (message == "zhifu") {
					try {
						/*
						 * Thread.sleep(100);
						 */
						genPayReq();
						
						if (dialog != null) {
							dialog.dismiss();
						}
						
						sendPayReq();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		};
		/* 微信支付代码 */
		req = new PayReq();
		sb = new StringBuffer();
		msgApi.registerApp(Constants.APP_ID);

	}

	private void init() {
		rela_wechat = (RelativeLayout) findViewById(R.id.rela_wechat);
		wechat_image = (ImageView) findViewById(R.id.wechat_image);
		buycourse_total_payable = (TextView) findViewById(R.id.buycourse_total_payable);
		payNowButton = (Button) findViewById(R.id.btn_buycourse);
		coursesName = (TextView) findViewById(R.id.buycourse_context);

		preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS,
				MODE_PRIVATE);
		userInfoNo = preference.getString("USER_INFO_NO", "USER_INFO_NO");

		id = getIntent().getExtras().getString("id");
		courseStatus = getIntent().getIntExtra("status", 1);
		price = getIntent().getIntExtra("price", 1000);
		courseName = getIntent().getExtras().getString("courseName");
		courseNo = getIntent().getExtras().getString("courseNo");

		coursesName.setText(courseName);
		buycourse_total_payable.setText(String.valueOf(price));

		/*
		 * if (courseStatus==1) { buycourse_nouse.setText("有可用"); }
		 */

		Intent intent = this.getIntent(); // 获取已有的intent对象
		bundle = intent.getExtras(); // 获取intent里面的bundle对象

		payNowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (userInfoNo.equals("USER_INFO_NO")) {

					Intent intent = new Intent(BuyCourseActivity.this,
							LoginWelcomeActivity.class);

					startActivity(intent);
				} else {

					if (bundle != null && price >= 0) {

						try {
							GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
							getPrepayId.execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

	}

	/* 微信支付代码 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		Log.e("orion", sb.toString());
		// 将得到的xml结果转码。否则订单描述存在汉字时 签名出错。
		try {
			return new String(sb.toString().getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {

		

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(BuyCourseActivity.this,
					getString(R.string.app_tip),
					getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
		
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

			resultunifiedorder = result;

			Message message = Message.obtain();
			String send = "zhifu";
			message.obj = send;
			handler.sendMessage(message);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");

			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();
		// String out_trade_no = "timecourse" + id;
		// String out_trade_no = Long.toString(System.currentTimeMillis()) +
		// "timecourse"+ id;
		//String out_trade_no = new SimpleDateFormat("yyyyMMddHHmmssSS").format(System.currentTimeMillis());
		
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
		String out_trade_no = userInfoNo + "ZZ" + date;

		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("attach", userInfoNo + "@tc@"+ courseNo));
			packageParams.add(new BasicNameValuePair("body", courseName));
			packageParams
					.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://121.40.151.183:8080/pula-sys/app/weixinpay/wechatPayNotify"));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					out_trade_no));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			// packageParams.add(new BasicNameValuePair("total_fee",
			// Integer.toString(price)));
			packageParams.add(new BasicNameValuePair("total_fee", Integer
					.toString(price*100)));

			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			
		
			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return xmlstring;
		}

	}

	private void genPayReq() {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		Log.e("orion", signParams.toString());

	}

	private void sendPayReq() {

		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
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
