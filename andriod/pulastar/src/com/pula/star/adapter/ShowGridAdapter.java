package com.pula.star.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pula.star.activity.MyWorkDetailActivity;
import com.pula.star.activity.MyWorkH5Activity;
import com.pula.star.bean.MyWorkData;
import com.squareup.picasso.Picasso;
import com.pula.star.R;

public class ShowGridAdapter extends BaseAdapter {

	private List<MyWorkData> list;
	private Context context;
	private LayoutInflater inflater;
	private int height;
	private int rate;
	private static String imageUrl;

	public ShowGridAdapter(List<MyWorkData> list, Context context, int height) {
		// TODO 自动生成的构造函数存根

		this.context = context;
		this.list = list;
		this.height = height;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.show_waterfallitem, null);

		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, height);
		view.setLayoutParams(param);
		final MyWorkData workData = list.get(arg0);
		ImageView show_waterfall_image = (ImageView) view
				.findViewById(R.id.show_waterfall_image);
		TextView show_waterfall_text01 = (TextView) view
				.findViewById(R.id.show_waterfall_text01); // for date
		/*
		 * TextView show_waterfall_text02 = (TextView) view
		 * .findViewById(R.id.show_waterfall_text03); //for comments
		 */
		ImageView rate_image = (ImageView) view.findViewById(R.id.star_image);

		imageUrl = "http://121.40.151.183:8080/pula-sys/app/timecoursework/icon?fp="
				+ workData.getFileId() + "&id=" + workData.getIconId();

		Picasso.with(context).load(imageUrl)
				.placeholder(R.drawable.white_cover)
				.error(R.drawable.white_cover).into(show_waterfall_image);

		show_waterfall_text01.setText(workData.getWorkEffectDate());
		// show_waterfall_text02.setText(workData.getComments());

		// work_rate.setRating(workData.getRate()%5);
		rate = workData.getRate();
		
		if (rate == 0)
			rate = 1;

		rate = rate % 5;
		
		String image_name;

		image_name = "star" + rate;

		Resources resources = context.getResources();

		int indentify = resources.getIdentifier(context.getPackageName()
				+ ":drawable/" + image_name, null, null);

		if (indentify > 0) {
			rate_image.setImageResource(indentify);
		}

		// Typeface tf = Typeface.createFromAsset (context.getAssets(),
		// "fonts/wwt.ttf" );
		// show_waterfall_text01.setTypeface(tf);//设置字体
		// show_waterfall_text02.setTypeface(tf);//设置字体
		// show_waterfall_text02.getPaint().setFakeBoldText(true);

		show_waterfall_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(context, MyWorkDetailActivity.class);

				intent.putExtra(
						"imgUrl",
						"http://121.40.151.183:8080/pula-sys/app/timecoursework/icon?fp="
								+ workData.getFileId() + "&id="
								+ workData.getIconId());
				intent.putExtra("date", workData.getWorkEffectDate());
				intent.putExtra("rate", workData.getRate());
				intent.putExtra("comments", workData.getComments());

				context.startActivity(intent);

				/*
				 * Intent intent = new Intent();
				 * intent.setData(Uri.parse(workData.getUrl()));
				 * intent.setAction(Intent.ACTION_VIEW);
				 * context.startActivity(intent); // 启动浏览器
				 */

			}
		});

		return view;
	}

}
