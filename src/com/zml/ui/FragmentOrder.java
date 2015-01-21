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

import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.view.AbPullToRefreshListView;
import com.zml.R;
import com.zml.adapter.ImgeListAdapter;
import com.zml.base.BaseFragment;
import com.zml.base.BaseMessage;
import com.zml.base.C;
import com.zml.model.Order;
import com.zml.model.Student;

public class FragmentOrder extends BaseFragment {

//    private static int mPageCount = 10;

    private List<Map<String, Object>> mData, tmpData;
    private AbPullToRefreshListView mPullToRefreshListView = null;
//    private int mCurrentPage = 0;
    private View mContentView = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_order, null);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.handler.setFragment(this);
        initView();
        doAscTaskLogin();
    }

    private void initView() {
        mData = new ArrayList<Map<String, Object>>();
        tmpData = new ArrayList<Map<String, Object>>();

        mPullToRefreshListView = (AbPullToRefreshListView)mContentView.findViewById(R.id.order_listView);
        ImgeListAdapter myListViewAdapter = new ImgeListAdapter(this.getActivity(), mData,R.layout.list_items,
                new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
                        R.id.itemsTitle,R.id.itemsText });
        mPullToRefreshListView.setAdapter(myListViewAdapter);
        mPullToRefreshListView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
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
                    doAscTaskLogin();
                } catch (Exception e) {
                }
          };
        };

        mPullToRefreshListView.setRefreshItem(item1);
    }

    private void doAscTaskLogin() {
        HashMap<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("stdNum", Student.getInstance().getStdnum());
//        urlParams.put("page", mCurrentPage+"");
//        urlParams.put("page_count", mPageCount+"");
//        Log.e("zml",mDeviceType.getSelectedItemId()+"");
        try {
            this.doTaskAsync(C.task.order_list, C.api.order_list, urlParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskComplete(int taskId, BaseMessage message) {
        super.onTaskComplete(taskId, message);
        switch (taskId) {
        case C.task.order_list:
            ArrayList<Order> orders = null;
            try {
                orders = (ArrayList<Order>) message.getResultList("Order");
                tmpData.clear();
                Map<String, Object> map = null;
                if (orders != null) {
                    for(Order order : orders) {
                        map = new HashMap<String, Object>();
                        map.put("itemsIcon",R.drawable.ic_launcher);
                        map.put("itemsTitle", order.getDevice_id()
                                );
                        map.put("itemsText", order.getOrder_time());
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
    
    private void update() {
        mData.clear();
        if(tmpData!=null && tmpData.size()>0){
            mData.addAll(tmpData);
            tmpData.clear();
        }
        mPullToRefreshListView.onRefreshComplete();
    }
}
