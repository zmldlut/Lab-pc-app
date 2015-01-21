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

public class UiPassword extends BaseUi {
    
    private EditText mOldPass, mNewPass, mConfigPass;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.ui_password);
        mOldPass = (EditText) findViewById(R.id.old_pass_ext);
        mNewPass = (EditText) findViewById(R.id.new_pass_ext);
        mConfigPass = (EditText) findViewById(R.id.config_pass_ext);

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initTitle() {
        this.setTitleText(R.string.title_ui_password);
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
        if (mOldPass.length() > 0 && mNewPass.length() > 0 && mConfigPass.length() > 0) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("pass.stdNum", Student.getInstance().getStdnum());
            urlParams.put("pass.oldPass", mOldPass.getText().toString());
            urlParams.put("pass.newPass", mNewPass.getText().toString());
            urlParams.put("pass.configPass", mConfigPass.getText().toString());
            try {
                doTaskAsync(C.task.password, C.api.password, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.password:
                toast("密码修改成功成功！");
                finish();
                break;
        }
    }
}
