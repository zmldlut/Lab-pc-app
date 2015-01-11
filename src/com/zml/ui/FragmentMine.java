package com.zml.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zml.R;
import com.zml.base.BaseFragment;
import com.zml.model.Student;

public class FragmentMine extends BaseFragment implements OnClickListener{

    private static String mPhone = "14747207814";
    private View mContentView = null;
    private RelativeLayout mUserLy, mReportLy, mPhoneLy, mAboutLy, mLogoutLy;
    private TextView mUserText;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_mine, null);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    void initView () {
        mUserText = (TextView) mContentView.findViewById(R.id.user_text);
        mUserText.setText(Student.getInstance().getStdNum());
        
        mUserLy = (RelativeLayout) mContentView.findViewById(R.id.user_layout);
        mReportLy = (RelativeLayout) mContentView.findViewById(R.id.report_layout);
        mPhoneLy = (RelativeLayout) mContentView.findViewById(R.id.phone_layout);
        mAboutLy = (RelativeLayout) mContentView.findViewById(R.id.about_layout);
        mLogoutLy = (RelativeLayout) mContentView.findViewById(R.id.logout_layout);
        
        mUserLy.setOnClickListener(this);
        mReportLy.setOnClickListener(this);
        mPhoneLy.setOnClickListener(this);
        mAboutLy.setOnClickListener(this);
        mLogoutLy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        Intent intent;
        switch (view.getId()) {
        case R.id.user_layout:
            forward(UiPassword.class);
            break;
        case R.id.report_layout:
            forward(UiReport.class);
            break;
        case R.id.phone_layout:
            intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mPhone));  
            startActivity(intent);
            break;
        case R.id.about_layout:
            forward(UiAbout.class);
            break;
        case R.id.logout_layout:
            exit();
            break;
        default:
            break;
        }
    }
    
    public void exit(){
        System.exit(0);
    } 
}
