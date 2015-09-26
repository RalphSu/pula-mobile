package com.pula.star.adapter;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.bean.CourseData;
import com.pula.star.utils.ImageCache;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @name ZhuantiImageDetailsAdapter
 * @Descripation 图片专题的适配器<br>
 * @author 禹慧军
 * @date 2014-10-25
 * @version 1.0
 */
@Deprecated
public class CourseImageDetailsAdapter extends BaseAdapter {
	private ArrayList<CourseData> datas;
	private Context context;
	private LruCache<String, Bitmap> lruCache;
	private final static String IMAGEHOME = "http://img.117go.com/timg/p308/";
	private final static String AVATAR = "http://img.117go.com/demo27/img/ava66/";

	public void BindData(ArrayList<CourseData> datas) {
		this.datas = datas;
	}

	public CourseImageDetailsAdapter(Context context) {
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.course_detail_image_item, null);
			viewHolder.disCitys = (TextView) view
					.findViewById(R.id.zhuanti_address);
			viewHolder.favoriteCount = (TextView) view
					.findViewById(R.id.zhuanti_commentCount);
			viewHolder.image = (ImageView) view
					.findViewById(R.id.gridview_imageview);
			viewHolder.title = (TextView) view.findViewById(R.id.zhuanti_title);
			viewHolder.viewCount = (TextView) view
					.findViewById(R.id.zhuanti_likeCount);
			viewHolder.userCircleImageView = (CircleImageView) view
					.findViewById(R.id.user_circleImageView);
			viewHolder.content = (TextView) view
					.findViewById(R.id.text_content);
			viewHolder.publishdate = (TextView) view
					.findViewById(R.id.publish_date);
			viewHolder.user_name = (TextView) view.findViewById(R.id.user_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
//		CourseData jingXuanData = datas.get(position);
//		viewHolder.favoriteCount.setText(jingXuanData.getCmtCount());
//		viewHolder.title.setText(jingXuanData.getTitle());
//		viewHolder.viewCount.setText(jingXuanData.getViewCount());
//		if (jingXuanData.getForeword().length()>100) {
//			content = jingXuanData.getForeword().substring(0,100)+"...";
//			viewHolder.content.setText(content);
//		}else {
//			viewHolder.content.setText(content);
//		}
//		
//		viewHolder.publishdate.setText(jingXuanData.getPubdate());
//
//		StringBuffer stringBuffer = new StringBuffer();
//		
//		if (jingXuanData.getDispCities().length > 0) {
//			for (int i = 0; i < jingXuanData.getDispCities().length; i++) {
//				stringBuffer.append(jingXuanData.getDispCities()[i]).append(
//						"->");
//				if (i == jingXuanData.getDispCities().length) {
//					stringBuffer.append(jingXuanData.getDispCities()[i]);
//				}
//
//			}
//			viewHolder.disCitys.setText(jingXuanData.getDispCities()[0]); // stringBuffer.toString()
//		}
//		UserInfo userInfo = jingXuanData.getUserInfo();
//		viewHolder.user_name.setText(userInfo.getNickname());
//		viewHolder.image.setTag(IMAGEHOME + jingXuanData.getImage());
//		viewHolder.image.setImageResource(R.drawable.defaultcovers);
//		new ImageCache(context, lruCache, viewHolder.image, IMAGEHOME
//				+ jingXuanData.getImage(), "OnTheWay", 800, 400);
//		viewHolder.userCircleImageView.setTag(AVATAR + userInfo.getAvatar());
//		new ImageCache(context, lruCache, viewHolder.userCircleImageView,
//				AVATAR + userInfo.getAvatar(), "OnTheWay", 800, 400);
		return view;
	}

	/* 其中计算项高的方法是从staggeredGridView提供的sample中提出来的，就是根据位置得到一个随机的比例 */
	private final Random mRandom = new Random();
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private String content;

	private double getPositionRatio(final int position) {
		double ratio = sPositionHeightRatios.get(position, 0.0);
		if (ratio == 0) {
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
													// the width
	}

	private class ViewHolder {

		private CircleImageView userCircleImageView;
		public TextView pictureCount;
		public TextView favoriteCount;
		public TextView disCitys;
		public TextView viewCount;
		public ImageView image;
		public TextView title;
		public TextView content;
		public TextView publishdate;
		public TextView user_name;

	}

}
