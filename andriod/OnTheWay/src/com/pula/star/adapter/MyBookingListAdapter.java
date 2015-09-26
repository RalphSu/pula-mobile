package com.pula.star.adapter;

import java.io.InputStream;
import java.util.List;

import com.pula.star.R;
//import com.yuhj.ontheway.bean.MyCourseJsonTools;
import com.pula.star.bean.BookingData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.AsyncImageLoader;
import com.pula.star.utils.HttpTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBookingListAdapter extends BaseAdapter {
	private Context context;
	private List<BookingData> list;
	private LayoutInflater inflater;
	Bitmap bm;
	private AsyncImageLoader loder = new AsyncImageLoader();

	Boolean state;

	 
	public MyBookingListAdapter(Context context, List<BookingData> list) {
		this.context = context;
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		final ViewHolder holder;
		final int weekday;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_my_booking_item, null);
	
			holder.branch_name = (TextView)convertView.findViewById(R.id.tx_branch_name);
			holder.plan1 = (TextView)convertView.findViewById(R.id.tx_booking_plan_name1);
			//holder.plan2 = (TextView)convertView.findViewById(R.id.tx_booking_plan_name2);
			//holder.plan3 = (TextView)convertView.findViewById(R.id.tx_booking_plan_name3);
			//holder.plan4 = (TextView)convertView.findViewById(R.id.tx_booking_plan_name4);
			//holder.plan5 = (TextView)convertView.findViewById(R.id.tx_booking_plan_name5);
			
			holder.teacher_pic = (ImageView) convertView.findViewById(R.id.img_p);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 取值
		final BookingData tools = list.get(position);
		// 赋值
        
		Log.i("msg.obj =", "" + tools.getBranchName());
	
		if(tools.getPlan1() != null)
		{	
		 holder.plan1.setText(tools.getPlan1());
		 holder.plan1.setVisibility(View.VISIBLE);
		}
		/*
		if(tools.getPlan2() != null)
		{	
		 holder.plan2.setText(tools.getPlan2());
		 holder.plan2.setVisibility(View.VISIBLE);
		}
		
		if(tools.getPlan3() != null)
		{	
		 holder.plan3.setText(tools.getPlan3());
		 holder.plan3.setVisibility(View.VISIBLE);
		}
		
		if(tools.getPlan4() != null)
		{	
		 holder.plan4.setText(tools.getPlan4());	
		 holder.plan4.setVisibility(View.VISIBLE);
		}
	
		if(tools.getPlan5() != null)
		{	
		 holder.plan5.setText(tools.getPlan5());
		 holder.plan5.setVisibility(View.VISIBLE);
		}
		*/
		
		if(tools.getBranchName() != null)
		{			
		 holder.branch_name.setText(tools.getBranchName());
		}
		/*
		String path = tools.getTeacher_pic_url();
			
		holder.teacher_pic.setTag(path);
		
	
		Bitmap bitmap = loder.loadImage(path,
				new AsyncImageLoader.ImageCallBack() {

					public void imageLoaded(String path, Bitmap bitmap) {
						ImageView imgv = (ImageView) parent
								.findViewWithTag(path);
						if (imgv != null && bitmap != null) {
							imgv.setImageBitmap(bitmap);
						}
					}
				});

		if (bitmap != null) {
			
			holder.teacher_pic.setScaleType(ImageView.ScaleType.FIT_XY);
			holder.teacher_pic.setImageBitmap(bitmap);

		} else {
			
			holder.teacher_pic.setImageResource(R.drawable.pula_logo_circle);
		}
		*/
		holder.teacher_pic.setImageResource(R.drawable.pula_logo_circle);
		
		return convertView;
	}

	public class ViewHolder {
		private ImageView teacher_pic;		
		private TextView plan1,plan2,plan3,plan4,plan5,branch_name;
	}
}
