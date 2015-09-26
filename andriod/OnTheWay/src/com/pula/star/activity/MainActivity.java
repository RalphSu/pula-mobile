package com.pula.star.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.pula.star.R;
import com.pula.star.fragment.BookingFragment;
import com.pula.star.fragment.CourseFragment;
import com.pula.star.fragment.HuodongFragment;
import com.pula.star.fragment.LoginFragment;

/**
 * @name MainActivity
 * @Descripation 这是普拉星球主界面的设计类<br>
 *               1、精选<br>
 *               2、专题<br>
 *               3、活动<br>
 *               4、我的<br>
 * @author 
 * @date 
 * @version 1.0
 */
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener, OnTouchListener, OnKeyListener, OnClickListener {
	private TextView title;
	private BookingFragment bookingFragment;
	private HuodongFragment huodongFragment;
	private CourseFragment courseFragment;
	private LoginFragment loginFragment;
	private ArrayList<Fragment> fragments;
	private RadioGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
        initViews();
        group = (RadioGroup) findViewById(R.id.main_tab_bar);
        group.setOnCheckedChangeListener(this);
        
        fragments = new ArrayList<Fragment>();
        
        fragments.add(huodongFragment);
        fragments.add(courseFragment);
        fragments.add(bookingFragment);
        fragments.add(loginFragment);
        
        if (getIntent().getIntExtra("FragmentType", 0) == 3) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_framelayout, fragments.get(3));
            transaction.commit();
            //title.setText("登录");
        } else {
            FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.main_framelayout, fragments.get(0));
			transaction.commit();
			//title.setText("预约");
        }

    }

    private void initViews() {
        bookingFragment = new BookingFragment(this);
        courseFragment = new CourseFragment();
        huodongFragment = new HuodongFragment();
        loginFragment = new LoginFragment();
        //title = (TextView) findViewById(R.id.main_title);
    }

    @Override
    public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	@Override
	public void onCheckedChanged(RadioGroup view, int checkId) {
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btnButton = null;
		for (int i = 0; i < childCount; i++) {
			btnButton = (RadioButton) group.getChildAt(i);
			if (btnButton.isChecked()) {
				checkedIndex = i;
				break;
			}
		}

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = null;
		switch (checkedIndex) {
		case 0:
			fragment = fragments.get(0);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			//title.setText("预约");
			break;
		case 1:
			fragment = fragments.get(1);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			//title.setText("课程");
			break;
		case 2:
		    fragment = fragments.get(2);
		    transaction.replace(R.id.main_framelayout, fragment);
		    transaction.commit();
		    //title.setText("活动");
			
			break;
		case 3:
		    fragment = fragments.get(3);
		    transaction.replace(R.id.main_framelayout, fragment);
		    transaction.commit();
		    //title.setText("登录");
		    break;
		case 4:
		    break;

		default:
			break;
		}

	}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
//        if (v.getId() == bookingFragment.getId()) {
//            return bookingFragment.onTouch(v, event);
//        }
        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        v.getId();
    }

}
