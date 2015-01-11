package com.zml.ui;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zml.R;
import com.zml.base.BaseMessage;
import com.zml.base.BaseUi;
import com.zml.base.C;
import com.zml.model.Device;
import com.zml.model.Student;
import com.zml.util.TimeUtil;

public class UiOrder extends BaseUi {

    private TextView mTvUser, mDeviceID, mDeviceName, mDeviceType, mDeviceServiceTag, mDeviceStatus, mOrderStartTime, mOrderStopTime;
    private Device mDevice;
    private String startTime, stopTime;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.ui_order);
        init();
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        mDevice = new Device();
        mDevice.setId(bundle.getString(Device.COL_ID));
        mDevice.setName(bundle.getString(Device.COL_NAME));
        mDevice.setType(bundle.getString(Device.COL_TYPE));
        mDevice.setService_tag(bundle.getString(Device.COL_SERVICE_TAG));
        mDevice.setStatus(bundle.getString(Device.COL_STATUS));
        
        mTvUser = (TextView) findViewById(R.id.tx_user);
        mTvUser.setText(Student.getInstance().getStdNum());
        mDeviceID = (TextView) findViewById(R.id.tx_id);
        mDeviceID.setText(mDeviceID.getText() + mDevice.getId());
        mDeviceName = (TextView) findViewById(R.id.tx_name);
        mDeviceName.setText(mDeviceName.getText() + mDevice.getName());
        mDeviceType = (TextView) findViewById(R.id.tx_type);
        mDeviceType.setText(mDeviceType.getText() + mDevice.getType());
        mDeviceServiceTag = (TextView) findViewById(R.id.tx_service_tg);
        mDeviceServiceTag.setText(mDeviceServiceTag.getText() + mDevice.getService_tag());
        mDeviceStatus = (TextView) findViewById(R.id.tx_status);
        mDeviceStatus.setText(mDeviceStatus.getText() + mDevice.getStatus());
        mOrderStartTime = (TextView) findViewById(R.id.tx_start_time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = formatter.format(TimeUtil.getOderTime(bundle.getInt("datetime")));
        stopTime = formatter.format(TimeUtil.addHour(TimeUtil.getOderTime(bundle.getInt("datetime")), 3));
        mOrderStartTime.setText(mOrderStartTime.getText() + startTime);
        mOrderStopTime = (TextView) findViewById(R.id.tx_stop_time);
        mOrderStopTime.setText(mOrderStopTime.getText() + stopTime);
    }
    
    @Override
    public void initTitle() {
        this.setTitleText(R.string.title_ui_order);
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
        if (mDevice != null) {
            HashMap<String, String> urlParams = new HashMap<String, String>();
            urlParams.put("stdNum", Student.getInstance().getStdNum());
            urlParams.put("device_id", mDevice.getId());
            urlParams.put("start", startTime);
            urlParams.put("stop", stopTime);
            try {
                doTaskAsync(C.task.order, C.api.order, urlParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.order:
                toast("您的订单已经被提交！");
                finish();
                break;
        }
    }
}