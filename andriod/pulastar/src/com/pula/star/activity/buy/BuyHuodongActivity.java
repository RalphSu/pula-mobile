package com.pula.star.activity.buy;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.Toast;


import com.pula.star.activity.BaseActivity;
import com.pula.star.pay.wechat.Constants;
import com.pula.star.pay.wechat.MD5;
import com.pula.star.pay.wechat.Util;
import com.pula.star.R;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**购买课程 **/

public class BuyHuodongActivity extends BaseActivity{

	private TextView buynotice_nouse;
	private TextView buynotice_total_payable;
	private ImageView buynotice_reduction;
	private TextView buynotice_num;
	private ImageView buynotice_plus_selected;
	private RelativeLayout rela_wechat;
	private ImageView wechat_image;
	
	private Bundle bundle;
	private Button payNowButton;
	
	private TextView noticesName;
	private Intent intent;
	private String bonusPrice=0+"";
	private Boolean flag = true;
	private boolean payway = true,haveUseBonus=false;
	// 子线程更新UI
	private Handler handler;
	private String totalprice = null;
	static double i = 0;
	/** 红包是否可用 */
	private int noticeStatus;
	private int price;
	private String noticeName;
	
	private int bonus_id;
	// 微信支付
	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	TextView show;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;//
	private TextView buynoticePrice;
	private String xmlstring;
	private String type;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		setContentView(R.layout.activity_buyhuodong);
		init();
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				String message = (String) msg.obj;
				// 数量增加
				/*
				if (message == "addprice") {
					if (order != null) {
						
						i = Double.valueOf(order.good.good_price);
					} else {
						i = Double.valueOf(bundle.getString("price"));
					}
					totalprice = String.valueOf(add(count, i));
					buynotice_total_payable.setText(sub(Double.valueOf(totalprice),Double.valueOf(bonusPrice))+"");
					buynoticePrice.setText(""+Double.valueOf(totalprice));
				}
				// 数量减少
				else if (message == "price") {
					if (order != null) {

						i = Double.parseDouble(order.good.good_price);
					} else {
						i = Double.parseDouble(bundle.getString("price"));
					}
					totalprice = String.valueOf(add(count, i));
					buynotice_total_payable.setText(sub(Double.valueOf(totalprice),Double.valueOf(bonusPrice))+"");
					buynoticePrice.setText(""+Double.parseDouble(totalprice));

				} else if (message == "zhifu") {
					try {
						Thread.sleep(1000);
						genPayReq();
						sendPayReq();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				*/
			}
			

		};
		/* 微信支付代码 */
		req = new PayReq();
		sb = new StringBuffer();
		msgApi.registerApp(Constants.APP_ID);
     
	}

	/* double类型乘积* */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	private void init() {
		rela_wechat = (RelativeLayout) findViewById(R.id.rela_wechat);
		wechat_image = (ImageView) findViewById(R.id.wechat_image);
		buynotice_total_payable = (TextView) findViewById(R.id.buynotice_total_payable);
		payNowButton = (Button) findViewById(R.id.btn_buynotice);
		noticesName = (TextView) findViewById(R.id.buynotice_context);
		
		noticeStatus = getIntent().getIntExtra("status",1);
		price = getIntent().getIntExtra("price",1000);
		noticeName = getIntent().getExtras().getString("name");
		
		noticesName.setText(noticeName);
		buynotice_total_payable.setText(String.valueOf(price));
		
		/*
		if (noticeStatus==1) {
			buynotice_nouse.setText("有可用");
		}
		*/
		
		Intent intent = this.getIntent(); // 获取已有的intent对象
		bundle = intent.getExtras(); // 获取intent里面的bundle对象
		
		payNowButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // start buy activity
                Intent intent = new Intent(BuyHuodongActivity.this, BuyHuodongSubmitActivity.class);               
                startActivity(intent);
            }
        });

	}

	
	public void onClick(View v) {
		/*
		Intent intent = null;
		Message message = Message.obtain();
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.rela_buynotice_nouse:
			if (noticeStatus == 1) {
				intent = new Intent(this, MynoticesActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("bonus", order.bonuslist);
				bundle.putInt("status", 1);
				intent.putExtras(bundle);
				startActivityForResult(intent, 110);
			}
			break;
		case R.id.buynotice_reduction:
			if (count > 1) {
				count -= 1;
				buynotice_num.setText("" + count);
				String str_temps = "price";
				message.obj = str_temps;
				handler.sendMessage(message);
			}
			break;
		case R.id.buynotice_plus_selected:
			count += 1;
			buynotice_num.setText("" + count);
			String str_temp = "addprice";
			message.obj = str_temp;
			handler.sendMessage(message);
			break;

		case R.id.rela_wechat:
			wechat_image
					.setImageResource(R.drawable.my_shopping_cart_choice_icon);
			alipay_image
					.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
			payway = false;
			break;
		case R.id.rela_alipay:
			alipay_image
					.setImageResource(R.drawable.my_shopping_cart_choice_icon);
			wechat_image
					.setImageResource(R.drawable.my_shopping_cart_not_selected_icon);
			payway = true;
			break;
		case R.id.btn_buynotice:
			if(flag == false){
				return;
			}else{
			if (order != null&&Double.valueOf(totalprice)>0) {
				
				if (payway == true) {
					orderModel.flowOrderDone(bonus_id, order.seller.seller_phone, order.seller.seller_address,
							"支付宝", "", order.seller.seller_id,
							order.seller.seller_name, order.good.good_id, "1", "1", count+"",0);
				}else if (payway == false) {
					orderModel.flowOrderDone(bonus_id, order.seller.seller_phone, order.seller.seller_address,
							"微信", "", order.seller.seller_id,
							order.seller.seller_name, order.good.good_id, "2", "1", count+"", 0);
				}
			} else if (bundle != null&&Double.valueOf(totalprice)>0) {
				String name = bundle.getString("notice_name");
				String subject = bundle.getString("notice_brief");
				String number = bundle.getString("order_sn");
				if (payway == true) {
					if (totalprice == null) {// 支付宝支付
						totalprice = bundle.getString("price");
					}
					intent = new Intent(this, PayDemoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("price", totalprice);
					bundle.putString("name", name);
					bundle.putString("subject", subject);
					bundle.putString("number", number);
					intent.putExtras(bundle);
					startActivity(intent);
				} else if (payway == false) {// 微信支付
					try {
						GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
						getPrepayId.execute();
					} catch (Exception e) {

					}
				}

			}else if (Double.valueOf(totalprice)<=0) {

				orderModel.flowOrderDone(bonus_id, order.seller.seller_phone, order.seller.seller_address,
						"", "", order.seller.seller_id,
						order.seller.seller_name, order.good.good_id, "", "1", count+"", 0);
				
			}

		}
			flag = false;
		}
		*/
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

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(BuyHuodongActivity.this,
					getString(R.string.app_tip),
					getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
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
			//String entity = genProductArgs();
			String entity = "test";
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

	/*
	private String genProductArgs() {
		
		StringBuffer xml = new StringBuffer();
		String price;
		String body = null;
		String out_trade_no = null;
		if (order != null) {
			if (totalprice == null) {

				totalprice = order.good.good_price;

			}
			double i = Double.parseDouble(totalprice);
			int m = (int) (i * 100);
			price = String.valueOf(m);
			body  =order.good.good_name;
			out_trade_no= orderModel.info.order_sn;
			SharedPreferences sp = BuynoticeActivity.this.getSharedPreferences(
					"number", Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("number", out_trade_no);
			edit.commit();
		} else {
			if (totalprice == null) {

				totalprice = bundle.getString("price");
			}
			double i = Double.parseDouble(totalprice);
			int m = (int) (i * 100);
			price = String.valueOf(m);
			body =bundle.getString("notice_name");
			out_trade_no=bundle.getString("order_sn");
			SharedPreferences sp = BuynoticeActivity.this.getSharedPreferences(
					"number", Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("number", out_trade_no);
			edit.commit();
		}
		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body",body
					));
			packageParams
					.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://121.40.35.3/test"));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					out_trade_no));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", price));
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
*/
	
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data!=null) {
			if (requestCode == 110) {
				buynotice_nouse.setText(data.getStringExtra("bonusName"));
				intent = new Intent("com.funmi.lehuitong");
				intent.putExtra("bounce", buynotice_nouse.getText().toString());
				sendBroadcast(intent);
				bonus_id =Integer.parseInt(data.getStringExtra("bonusId"));
				bonusPrice=data.getStringExtra("bonusPrice");
				totalprice=String.valueOf(sub(Double.valueOf(totalprice),Double.valueOf(bonusPrice)));
				buynotice_total_payable.setText(totalprice);
				if(Double.parseDouble(totalprice)==0){
					
					wechat_image.setVisibility(View.GONE);
				}
//				else if (haveUseBonus==false) {
//					totalprice=String.valueOf(sub(Double.valueOf(totalprice),Double.valueOf(bonusPrice)));
//					haveUseBonus=true;
//					buynotice_total_payable.setText(totalprice);
//					
//				}
			}else if(requestCode == 200){
				/*
				orderModel = new KtvAndnoticesOrderModel(this);
				orderModel.addResponseListener(this);
				orderModel.getOrderDetail(bundle.getString("order_id"));
				*/
			}
		}
	}
	/**double类型相减*/
	 public static double sub(double v1, double v2)  
	    {  
	    	double number;
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	        if (b1.subtract(b2).doubleValue()>0) {
				
	        	number= b1.subtract(b2).doubleValue();  
			}else {
				number= 0; 
			}
	        return number;
	    }  
	  
		/*
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
	
		if (url.endsWith(ProtocolConst.FLOW_TICKET_DONE)) {
			if (payway == true&&Double.valueOf(totalprice)>0) {
				if (totalprice == null&&Double.valueOf(totalprice)>0) {// 支付宝支付
					totalprice = order.good.good_price;
				}
				try{
				intent = new Intent(this, PayDemoActivity.class);
				intent.putExtra("type", "Buynotice");
				Log.i("111111111", orderModel.info.order_sn);
				Log.i("222", order.order_sn);
				Bundle bundle = new Bundle();
				bundle.putString("price", totalprice);
				bundle.putString("name", order.good.good_name);
				bundle.putString("subject", order.good.good_brief);
				bundle.putString("number", orderModel.info.order_sn);
				intent.putExtras(bundle);
				startActivity(intent);
				
				finish();
				}catch(Exception e){
					e.printStackTrace();
				}
			} else if (payway == false&&Double.valueOf(totalprice)>0) {// 微信支付
				try {
					GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
						getPrepayId.execute();
						
				} catch (Exception e) {
						
				}
			}else if(Double.valueOf(totalprice)<=0){
				PayModel model= new PayModel(this);
				model.paydone(orderModel.info.order_sn);
				Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this,LehuitongMainActivity.class));
				finish();
			}
		}
		if(url.endsWith(ProtocolConst.ORDER_TICKET_DETAIL)){
			Log.i("noticeStatus", ""+noticeStatus);
			if (orderModel.ktvOrderList.get(0).order_status.equals("1")) {
				buynotice_nouse.setText("有可用");
			}else{
				buynotice_nouse.setText("无可用");
			}
			if(orderModel.ktvOrderList.get(0).bonus_name.equals("")){
				buynotice_nouse.setText("无可用");
			}else{
				buynotice_nouse.setText(orderModel.ktvOrderList.get(0).bonus_name);
			}
		}
		
	}
	*/
	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			}
		}
}
