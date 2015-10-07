package com.pula.star.adapter;

import java.io.InputStream;
import java.util.List;

//import com.anjoyo.activity.MoreActivity;







import com.pula.star.R;
import com.pula.star.bean.MyCourseJsonTools;
import com.pula.star.utils.AsyncImageLoader;
import com.pula.star.utils.HttpTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CurriculumScheduleListAdapter extends BaseAdapter {
	private Context context;
	private List<MyCourseJsonTools> list;
	private LayoutInflater inflater;
	Bitmap bm;
	private AsyncImageLoader loder = new AsyncImageLoader();

	Boolean state;

	 
	String [] week = new String[] {"星期一","星期二","星期三","星期四","星期五","星期六","星期日",};
	 
    String [] course_time = new String[] {"09:00 - 10:30","13:00 - 14:00","14:30 - 15:30","16:00 - 17:00","19:00 - 20:00",};

    String [] duration_time = new String[] {"90","60","60","60","60",};
    
	public CurriculumScheduleListAdapter(Context context, List<MyCourseJsonTools> list) {
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
			convertView = inflater.inflate(R.layout.list_course_item, null);
            if(position % 2 == 0)
            {
               convertView.setBackgroundColor(Color.parseColor("#99CC66"));
            }
            else
            {
               convertView.setBackgroundColor(Color.parseColor("#B4EEB4"));
            }
            
			holder.course_name = (TextView)convertView.findViewById(R.id.tx_course_name);
			//holder.branch_name = (TextView)convertView.findViewById(R.id.tx_branch_name);
			holder.duration_time = (TextView)convertView.findViewById(R.id.tx_course_duraion_name);
			holder.open_time = (TextView)convertView.findViewById(R.id.tx_course_open_time);
			//holder.avaiable_period = (TextView)convertView.findViewById(R.id.tx_available_period_name);
			holder.teacher_pic = (ImageView) convertView.findViewById(R.id.img_p);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 取值
		final MyCourseJsonTools tools = list.get(position);
		// 赋值
        
		Log.i("msg.obj =", "" + tools.getName());
		Log.i("Positioon =","" + position);
			
		weekday = Integer.parseInt(tools.getStart_weekday());
		
		holder.course_name.setText(tools.getName());
		//holder.branch_name.setText(tools.getBranch_name());
		//holder.avaiable_period.setText(tools.getStart_time() + "-" + tools.getEnd_time());
		holder.open_time.setText(course_time[position]);
        holder.duration_time.setText(duration_time[position]);		
		
        String path = tools.getTeacher_pic_url();
		holder.teacher_pic.setTag(path);
		
		/*
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
		holder.teacher_pic.setImageResource(R.drawable.deer);
		
		return convertView;
	}

	public class ViewHolder {
		private ImageView teacher_pic;
		private TextView course_name,branch_name,duration_time,avaiable_period,open_time;
	}
}
