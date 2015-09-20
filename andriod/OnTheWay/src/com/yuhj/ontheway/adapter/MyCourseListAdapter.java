package com.yuhj.ontheway.adapter;

import java.io.InputStream;
import java.util.List;

//import com.anjoyo.activity.MoreActivity;






import com.yuhj.ontheway.R;
import com.yuhj.ontheway.bean.MyCourseJsonTools;
import com.yuhj.ontheway.utils.AsyncImageLoader;
import com.yuhj.ontheway.utils.HttpTools;

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
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyCourseListAdapter extends BaseAdapter {
	private Context context;
	//private List<MyCourseJsonTools> list;
	private List<String> list;
	private LayoutInflater inflater;
	Bitmap bm;
	private AsyncImageLoader loder = new AsyncImageLoader();

	Boolean state;

	 
	String [] week = new String[] {"星期一","星期二","星期三","星期四","星期五","星期六","星期日",};
	String field[] = {"课程名称","创立时间","上课时间"}; 
	public MyCourseListAdapter(Context context, List<String> list) {
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
			convertView = inflater.inflate(R.layout.list_my_course_item, null);

			/*
			holder.course_name = (TextView)convertView.findViewById(R.id.tx_course_name);
			holder.branch_name = (TextView)convertView.findViewById(R.id.tx_branch_name);
			holder.duration_time = (TextView)convertView.findViewById(R.id.tx_course_duraion_name);
			holder.open_time = (TextView)convertView.findViewById(R.id.tx_course_open_time);
			holder.avaiable_period = (TextView)convertView.findViewById(R.id.tx_available_period_name);
			holder.teacher_pic = (ImageView) convertView.findViewById(R.id.img_p);
			
			holder.buy_used = (ProgressBar)convertView.findViewById(R.id.progressbar_buy_used);
			holder.huodong_used = (ProgressBar)convertView.findViewById(R.id.progressbar_huodong_used);
			holder.gongfang_used = (ProgressBar)convertView.findViewById(R.id.progressbar_gongfang_used);
			*/
			
			holder.course_field_name = (TextView)convertView.findViewById(R.id.course_info_field) ;
			holder.course_field_value = (TextView)convertView.findViewById(R.id.course_info_value);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		if(position % 2 == 1)
		{  
			Log.i("position=",""+ position);
			holder.course_field_name.setBackgroundColor(Color.parseColor("#CCFFCC"));
			holder.course_field_value.setBackgroundColor(Color.parseColor("#CCFFCC"));
		}
	
		if(position %2 == 0)
		{  
			Log.i("position=",""+ position);
			holder.course_field_name.setBackgroundColor(Color.parseColor("#FFFFFF"));
			holder.course_field_value.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
	
		holder.course_field_name.setText(field[position] );
        holder.course_field_value.setText(list.get(position));
        
        
		// 取值
        /*
		final MyCourseJsonTools tools = list.get(position);
	    
        
		Log.i("msg.obj =", "" + tools.getName());
			
		weekday = Integer.parseInt(tools.getStart_weekday());		
		holder.course_name.setText(tools.getName());
		holder.branch_name.setText(tools.getBranch_name());
		holder.avaiable_period.setText(tools.getStart_time() + "-" + tools.getEnd_time());
		holder.open_time.setText(tools.getStart_hour()+":"+tools.getStart_minute()+" "+  week[weekday]);
        holder.duration_time.setText(tools.getDuration_minute());		
		String path = tools.getTeacher_pic_url();
		holder.teacher_pic.setTag(path);
		

		holder.buy_used.setMax(tools.getPaid_count());
		holder.huodong_used.setMax(tools.getHuodong_count());
		holder.gongfang_used.setMax(tools.getGongfang_count());
		
		holder.buy_used.setProgress(tools.getUsed_count());
		holder.huodong_used.setProgress(tools.getUsed_huodong_count());
		holder.gongfang_used.setProgress(tools.getUsed_gongfang_count());
		*/
		
		
		
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
		//holder.teacher_pic.setImageResource(R.drawable.pula_logo_rec);
		
		return convertView;
	}

	public class ViewHolder {
		//private ImageView teacher_pic;
		//private TextView course_name,branch_name,duration_time,avaiable_period,open_time;
		//private ProgressBar buy_used,huodong_used,gongfang_used;
		  private TextView course_field_name,course_field_value;
	}
}
