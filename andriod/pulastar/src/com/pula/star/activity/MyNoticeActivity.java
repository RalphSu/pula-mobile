/**
 * 
 */
package com.pula.star.activity;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.adapter.MyNoticeListAdapter;
import com.pula.star.bean.HuoDongData;
import com.pula.star.bean.MyNoticeData;
import com.pula.star.bean.UserHuoDongData;
import com.pula.star.clients.ClientApi;
import com.pula.star.utils.StaticStrings;

/**
 * @author Liangfei
 *
 */
public class MyNoticeActivity extends BaseActivity {

    private ListView myNoticeList;
    private SharedPreferences preference;
    private String userNo;
    private String passWord;
    private MyNoticeListAdapter myNoticeAdapter;

    // private RelativeLayout loadRelativeLayout;
    private RelativeLayout dataLinearLayout;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        preference = getSharedPreferences(StaticStrings.PREFS_SETTINGS, MODE_PRIVATE);
        userNo = preference.getString("USER_INFO_NO", "");
        passWord = preference.getString("USER_INFO_PASSWORD", "");
        
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_mynotice, null);

        dataLinearLayout = (RelativeLayout) view.findViewById(R.id.myNoticeLinearlayout);

        new DownData().execute();
        
        myNoticeAdapter = new MyNoticeListAdapter(this, userNo, passWord);
        myNoticeList = (ListView) view.findViewById(R.id.myNoticeListview);
       
        setTitle("普拉星球 - 我的活动");
        setContentView(view);
    }

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		}
	}
	
    class DownData extends AsyncTask<Void, Void, List<MyNoticeData>> {

        @Override
        protected List<MyNoticeData> doInBackground(Void... arg0) {
        	
            List<UserHuoDongData> huodongs = ClientApi.getUserHuoDongList(userNo);

            List<MyNoticeData> notices = new ArrayList<MyNoticeData>();
            
            if(huodongs != null)
            { 	
             for (UserHuoDongData data : huodongs) {
            	 
                MyNoticeData notice = new MyNoticeData();
                notice.setNoticeId(data.getId());
                notice.setBuyCount(data.getBookingCount());
                notice.setNoticeName(data.getTitle());
                notice.setBuyDay(DateTime.parse(data.getCreateTime()));
                notice.setNoticeDay(DateTime.parse("2015-10-01")); 
                notice.setUrlS(data.getUrlS());
                notice.setImageUrl(data.getImageUrl());
                notices.add(notice);
             }
            }
            return notices;
        }

        @Override
        protected void onPostExecute(final List<MyNoticeData> result) {

            super.onPostExecute(result);
            if (result != null) {
                myNoticeAdapter.BindData(result);
                myNoticeList.setAdapter(myNoticeAdapter);
                myNoticeAdapter.notifyDataSetChanged();
                // loadRelativeLayout.setVisibility(View.GONE);
                dataLinearLayout.setVisibility(View.VISIBLE);
                
                // listView.setOnItemClickListener(new OnItemClickListener() {
                //
                // @Override
                // public void onItemClick(AdapterView<?> arg0, View arg1, int
                // position, long arg3) {
                // Intent intent = new Intent(getActivity(),
                // HuodongDetailActivity.class);
                // intent.putExtra("id", result.get(position).getId());
                // intent.putExtra("url", result.get(position).getUrlS());
                // intent.putExtra("name", result.get(position).getTitle());
                // startActivity(intent);
                // }
                // });
            } else {
                // loadRelativeLayout.setVisibility(View.GONE);
                Toast.makeText(MyNoticeActivity.this, "网络异常,请检查", 1).show();
            }
        }

    }

}
