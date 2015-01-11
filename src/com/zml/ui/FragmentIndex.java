package com.zml.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;

import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.view.AbPullToRefreshListView;
import com.zml.R;
import com.zml.adapter.ImgeListAdapter;
import com.zml.base.BaseFragment;
import com.zml.base.BaseMessage;
import com.zml.base.C;
import com.zml.model.Device;

public class FragmentIndex extends BaseFragment {

//    private static int mPageCount = 10;

    private List<Map<String, Object>> mData, tmpData;
    private AbPullToRefreshListView mPullToRefreshListView;
    private Spinner mDeviceType, mStatus, mTime;
    private ImgeListAdapter myListViewAdapter;
    private ArrayList<Device> devices = null;
    
//    private int mCurrentPage = 0;
    private View mContentView = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_index, null);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.handler.setFragment(this);
        initView();
        doAscTask();
    }

    private void initView() {
        mDeviceType = (Spinner) mContentView.findViewById(R.id.device_group);
        mTime = (Spinner) mContentView.findViewById(R.id.time_group);
        mStatus = (Spinner) mContentView.findViewById(R.id.status_group);
        mDeviceType.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mTime.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mStatus.setOnItemSelectedListener(new MyOnItemSelectedListener());

        mData = new ArrayList<Map<String, Object>>();
        tmpData = new ArrayList<Map<String, Object>>();
        mPullToRefreshListView = (AbPullToRefreshListView)mContentView.findViewById(R.id.mListView);
        myListViewAdapter = new ImgeListAdapter(this.getActivity(), mData,R.layout.list_items,
                new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
                        R.id.itemsTitle,R.id.itemsText });
        mPullToRefreshListView.setAdapter(myListViewAdapter);
        mPullToRefreshListView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                if( mStatus.getSelectedItemId() == 2 ) {
                    toast("您选的时间段，该设备已经被预约！");
                    return;
                }
                Device device = devices.get(position - 1);
                Bundle bundle = new Bundle();
                bundle.putString(Device.COL_ID, device.getId());
                bundle.putString(Device.COL_NAME, device.getName());
                bundle.putString(Device.COL_TYPE, device.getType());
                bundle.putString(Device.COL_SERVICE_TAG, device.getService_tag());
                bundle.putString(Device.COL_STATUS, device.getStatus());
                bundle.putInt("datetime", (int) (mTime.getSelectedItemId() + 1));
                forward(UiOrder.class, bundle);
            }
        });

        AbHttpItem item1 = new AbHttpItem();
        item1.callback = new AbHttpCallback() {
            @Override
            public void update() {
                mData.clear();
                if(tmpData!=null && tmpData.size()>0){
                    mData.addAll(tmpData);
                    tmpData.clear();
                }
                mPullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void get() {
                try {
//                    mCurrentPage = 1;
                    doAscTask();
                } catch (Exception e) {
                }
          };
        };

        mPullToRefreshListView.setRefreshItem(item1);
    }

    public class MyOnItemSelectedListener implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO Auto-generated method stub
            arg0.setVisibility(View.VISIBLE);
            doAscTask();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            
        }
        
    }

    private void doAscTask() {
        HashMap<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("type", mDeviceType.getSelectedItem()+"");
        urlParams.put("status", mStatus.getSelectedItemId()+"");
        urlParams.put("datetime", (mTime.getSelectedItemId()+1)+"");
        try {
            doTaskAsync(C.task.device_list, C.api.device_list, urlParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
            case C.task.device_list:
                try {
                    devices = (ArrayList<Device>) message.getResultList("Device");
                    tmpData.clear();
                    Map<String, Object> map = null;
                    if (devices != null) {
                        for(Device device : devices) {
                            map = new HashMap<String, Object>();
                            map.put("itemsIcon",R.drawable.ic_launcher);
                            map.put("itemsTitle", device.getName());
                            map.put("itemsText", device.getType());
                            tmpData.add(map);
                        }
                    } else {
                        tmpData.clear();
                    }
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void update() {
        mData.clear();
        if(tmpData!=null && tmpData.size()>0){
            mData.addAll(tmpData);
            tmpData.clear();
        }
        mPullToRefreshListView.onRefreshComplete();
    }
}
