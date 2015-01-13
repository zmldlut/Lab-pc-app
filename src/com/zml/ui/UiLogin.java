package com.zml.ui;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zml.R;
import com.zml.base.BaseAuth;
import com.zml.base.BaseMessage;
import com.zml.base.BaseService;
import com.zml.base.BaseUi;
import com.zml.base.C;
import com.zml.model.Student;
import com.zml.service.NoticeService;

public class UiLogin extends BaseUi {

    private EditText mEditName;
    private EditText mEditPass;
    private CheckBox mCheckBox;
    private Button mBtnLogin;
    private SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.forward(UiIndex.class);
        if (BaseAuth.isLogin()) {
            this.forward(UiIndex.class);
        }

        setAbContentView(R.layout.ui_login);
        settings = getPreferences(Context.MODE_PRIVATE);
        init();
    }

    private void init() {

        mEditName = (EditText) this.findViewById(R.id.userName);
        mEditPass = (EditText) this.findViewById(R.id.userPwd);
        mCheckBox = (CheckBox) this.findViewById(R.id.login_check);
        mBtnLogin = (Button) this.findViewById(R.id.loginBtn);
        // remember checkbox
        if (settings.getBoolean("remember", false)) {
            mCheckBox.setChecked(true);
            mEditName.setText(settings.getString("username", ""));
            mEditPass.setText(settings.getString("password", ""));
        }
        mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                if (mCheckBox.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", mEditName.getText().toString());
                    editor.putString("password", mEditPass.getText().toString());
                } else {
                    editor.putBoolean("remember", false);
                    editor.putString("username", "");
                    editor.putString("password", "");
                }
                editor.commit();
            }
        });

        // login submit
        OnClickListener mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.loginBtn : 
                        Log.e("zml","zml");
                        doTaskLogin();
//                        forward(UiIndex.class);
                        break;
                }
            }
        };
        mBtnLogin.setOnClickListener(mOnClickListener);
    }


    private void doTaskLogin() {
        if (mEditName.length() > 0 && mEditPass.length() > 0) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("stdNum", mEditName.getText().toString());
            urlParams.put("password", mEditPass.getText().toString());
            try {
                this.doTaskAsync(C.task.login, C.api.login, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // async task callback methods
    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.login:
                Student student = null;
                // login logic
                try {
                    student = (Student) message.getResult("Student");
                    // login success
                    if (student.getStdNum() != null) {
                        BaseAuth.setstudent(student);
                        BaseAuth.setLogin(true);
                    // login fail
                    } else {
                        BaseAuth.setstudent(student); // set sid
                        BaseAuth.setLogin(false);
                        toast(this.getString(R.string.msg_loginfail));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                // login complete
                long startTime = app.getLong();
                long loginTime = System.currentTimeMillis() - startTime;
                Log.w("LoginTime", Long.toString(loginTime));
                // turn to index
                if (BaseAuth.isLogin()) {
                    // start service
                    BaseService.start(this, NoticeService.class);
                    // turn to index
                    forward(UiIndex.class);
                }
                break;
        }
    }

    @Override
    public void onNetworkError (int taskId) {
        super.onNetworkError(taskId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // other methods
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initTitle() {
        this.setTitleText(R.string.app_name);
        this.setTitleLayoutBackground(R.drawable.top_bg);
        this.setTitleTextMargin(10, 0, 0, 0);
        this.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
    }
}
