package com.pula.star.fragment;


import java.util.ArrayList;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pula.star.R;
import com.pula.star.activity.CourseDetailH5Activity;
import com.pula.star.adapter.CourseAdapter;
import com.pula.star.adapter.TongGaoAdapter;
import com.pula.star.bean.CourseData;
import com.pula.star.bean.TongGaoData;
import com.pula.star.bean.UserInfoData;
import com.pula.star.clients.ClientApi;
import com.pula.star.fragment.CourseFragment.Downdata;
import com.pula.star.utils.LoadingAinm;

public class TongGaoFragment extends Fragment {
 
	    private ListView image_listview;
	    private TongGaoAdapter image_adaAdapter;
	    private RelativeLayout loadRelativeLayout;
	    private LinearLayout dataLinearLayout;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.tonggao_fragment, container, false);
	        LoadingAinm.ininLodingView(view);
	        initViews(view);
	        new Downdata().execute();
	        return view;
	    }

	    private void initViews(View view) {
	        image_listview = (ListView) view.findViewById(R.id.tonggao_listview);
	        image_adaAdapter = new TongGaoAdapter(getActivity());
	        loadRelativeLayout = (RelativeLayout) view.findViewById(R.id.lodingRelativeLayout);
	        dataLinearLayout = (LinearLayout) view.findViewById(R.id.courseLinearlayout);
	    }

	    class Downdata extends AsyncTask<Void, Void, ArrayList<TongGaoData>> {

	        @Override
	        protected ArrayList<TongGaoData> doInBackground(Void... arg0) {
	            return ClientApi.getTongGaoDatas();
	        }

	        @Override
	        protected void onPostExecute(final ArrayList<TongGaoData> result) {
	            super.onPostExecute(result);
	            if (result != null) {
	                image_adaAdapter.BindData(result);
	                image_listview.setAdapter(image_adaAdapter);
	                image_adaAdapter.notifyDataSetChanged();
	                dataLinearLayout.setVisibility(View.VISIBLE);
	                loadRelativeLayout.setVisibility(View.GONE);
	            } else {
	                loadRelativeLayout.setVisibility(View.GONE);
	                Toast.makeText(getActivity(), "网络异常", 0).show();
	            }
	        }

	    }

}
 