package com.zml.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zml.R;
import com.zml.base.BaseMessage;
import com.zml.base.BaseUi;
import com.zml.base.C;
import com.zml.model.Student;

public class UiReport extends BaseUi {

    private EditText mReportExt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.ui_report);
        mReportExt = (EditText) this.findViewById(R.id.ext_report);

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initTitle() {
        this.setTitleText(R.string.report);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
        this.setTitleTextMargin(10, 0, 0, 0);
        this.setLogoLine(R.drawable.line);

        clearRightView();
        View rightViewMore = mInflater.inflate(R.layout.submit_btn, null);
        addRightView(rightViewMore);
        Button about = (Button)rightViewMore.findViewById(R.id.submit_btn);
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doTaskSubmit();
            }
         });
    }
    
    private void doTaskSubmit() {
        if (mReportExt.length() > 0) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("report.stdNum", Student.getInstance().getStdnum());
            urlParams.put("report.report", mReportExt.getText().toString());
            try {
                doTaskAsync(C.task.report, C.api.report, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.report:
                toast("您的意见已经反馈成功！");
                finish();
                break;
        }
    }
}
