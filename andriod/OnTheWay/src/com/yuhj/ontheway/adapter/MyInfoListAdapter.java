package com.yuhj.ontheway.adapter;

import java.io.InputStream;
import java.util.List;

import com.yuhj.ontheway.R;
//import com.yuhj.ontheway.bean.MyCourseJsonTools;
import com.yuhj.ontheway.bean.BookingData;
import com.yuhj.ontheway.clients.ClientApi;
import com.yuhj.ontheway.utils.AsyncImageLoader;
import com.yuhj.ontheway.utils.HttpTools;

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

public class MyInfoListAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
	Bitmap bm;
	private AsyncImageLoader loder = new AsyncImageLoader();

	Boolean state;
    
	String field[] = {"编号","姓名","性别","家长姓名","联系电话","家庭地址","学员卡号"};
	
 
	public MyInfoListAdapter(Context context, List<String> list) {
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
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_my_info_item, null);
            holder.usr_info_field_name = (TextView)convertView.findViewById(R.id.user_info_field);
			holder.user_info_value = (TextView)convertView.findViewById(R.id.user_info_value);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

	
        holder.usr_info_field_name.setText(field[position] );
		
    	// 取值
		final String value  = list.get(position);
		if(value != null)
		{	
		 holder.user_info_value.setText(value);
		 holder.user_info_value.setVisibility(View.VISIBLE);
		}

		
		return convertView;
	}

	public class ViewHolder {
		private TextView usr_info_field_name, user_info_value;
	}
}
