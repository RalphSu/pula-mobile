package com.pula.star.adapter;

import java.io.InputStream;
import java.util.List;

import com.pula.star.R;
import com.pula.star.bean.MyPoints;
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

public class MyPointListAdapter extends BaseAdapter {
	private Context context;
	private List<MyPoints> list;
	private LayoutInflater inflater;
	Bitmap bm;
	private AsyncImageLoader loder = new AsyncImageLoader();

	Boolean state;

	 
	public MyPointListAdapter(Context context, List<MyPoints> list) {
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
			convertView = inflater.inflate(R.layout.list_my_point_item, null);
	
			holder.createdTime = (TextView)convertView.findViewById(R.id.tx_create_time_name);
			holder.points = (TextView)convertView.findViewById(R.id.tx_point_name);
			holder.type = (TextView)convertView.findViewById(R.id.tx_point_type_name);
			holder.comments = (TextView)convertView.findViewById(R.id.tx_comments_name);			
			holder.teacher_pic = (ImageView) convertView.findViewById(R.id.img_p);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 取值
		final MyPoints tools = list.get(position);
		// 赋值
        
	
		if(tools.getPoints() != null)
		{	
		 holder.points.setText(tools.getPoints());
		 
		}
	
		if(tools.getType() != null)
		{	
		 holder.type.setText(tools.getType());
		 
		}
		
		if(tools.getComments() != null)
		{	
		 holder.comments.setText(tools.getComments());
		 
		}
		
		if(tools.getCreatedTime() != null)
		{	
		 holder.createdTime.setText(tools.getCreatedTime());	
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
		private TextView createdTime,points,type,comments;
		
	}
}
