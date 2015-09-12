/**
 * 
 */
package com.yuhj.ontheway.adapter;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.bean.MyNoticeData;

/**
 * @author Liangfei
 *
 */
public class MyNoticeListAdapter extends BaseAdapter {

    private String userName;
    private String password;

    private List<MyNoticeData> myNotices = new ArrayList<MyNoticeData>();

    private final LayoutInflater inflater;

    public MyNoticeListAdapter(Context context, String userName, String passWord) {
        this.userName = userName;
        this.password = passWord;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myNotices.size();
    }

    @Override
    public MyNoticeData getItem(int position) {
        return myNotices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.mynotice_item, null);

            holder.noticeName = (TextView) convertView.findViewById(R.id.mynotice_name);
            holder.noticeDate = (TextView) convertView.findViewById(R.id.mynotice_date);
            holder.buyCount = (TextView) convertView.findViewById(R.id.mynotice_count);
//            holder.buyDate = (TextView) convertView.findViewById(R.id.mynotice_buydate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MyNoticeData notice = myNotices.get(position);

        holder.noticeName.setText(notice.getNoticeName());
        holder.noticeDate.setText(notice.getNoticeDay() == null ? "" : formatter.print(notice.getNoticeDay()));
        holder.buyCount.setText("预约人数: " + notice.getBuyCount());
//        holder.buyDate.setText(notice.getBuyDay() == null ? "" : formatter.print(notice.getBuyDay()));

        return convertView;

    }
    private static final DateTimeFormatter formatter = DateTimeFormat.mediumDate();

    private static class ViewHolder {
//        ImageView noticeImage;
        TextView noticeName;
        TextView noticeDate;
        TextView buyCount;
//        TextView buyDate;
    }

    public void BindData(List<MyNoticeData> result) {
        this.myNotices = result;
    }

}
