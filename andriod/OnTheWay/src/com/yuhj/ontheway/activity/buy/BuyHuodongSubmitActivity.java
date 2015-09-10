/**
 * 
 */
package com.yuhj.ontheway.activity.buy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yuhj.ontheway.R;
import com.yuhj.ontheway.activity.BaseActivity;

/**
 * @author Liangfei
 *
 */
public class BuyHuodongSubmitActivity extends BaseActivity {
    private String noticeId;
    private String studentNo;
    private int cost;

    // pay result code
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_buycoursesubmit, null);
        TextView tipView = (TextView) view.findViewById(R.id.buycourse_tip);
        tipView.setText("活动购买马上开通，敬请期待！");
    }
}
