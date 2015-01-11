package com.zml.base;

import java.util.HashMap;

import com.zml.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment {
    protected BaseApp app;
    protected BaseHandler handler;
    protected BaseTaskPool taskPool;
    protected boolean showLoadBar = false;
    protected boolean showDebugMsg = true;

    protected BaseUi myActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.myActivity = (BaseUi)this.getActivity();
        this.handler = new BaseHandler(myActivity);
        this.taskPool = new BaseTaskPool(myActivity);
        this.app = (BaseApp) myActivity.getApplicationContext();
    }

    public void toast (String msg) {
        Toast.makeText(myActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLoadBar () {
        myActivity.findViewById(R.id.main_load_bar).setVisibility(View.VISIBLE);
        myActivity.findViewById(R.id.main_load_bar).bringToFront();
        showLoadBar = true;
    }

    public void hideLoadBar () {
        if (showLoadBar) {
            myActivity.findViewById(R.id.main_load_bar).setVisibility(View.GONE);
            showLoadBar = false;
        }
    }

    public void sendMessage (int what, int taskId, String data) {
        Bundle b = new Bundle();
        b.putInt("task", taskId);
        b.putString("data", data);
        Message m = new Message();
        m.what = what;
        m.setData(b);
        handler.sendMessage(m);
    }

    public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//      showLoadBar();
        taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
            @Override
            public void onComplete (String httpResult) {
                sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
            }
            @Override
            public void onError (String error) {
                sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
            }
        }, 0);
    }
    
    public void forward (Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    public void forward (Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        this.startActivity(intent);
    }

  public void onTaskComplete (int taskId, BaseMessage message) {
      
  }

  public void onTaskComplete (int taskId) {
      
  }

  public void onNetworkError (int taskId) {
      toast(C.err.network);
  }
}
