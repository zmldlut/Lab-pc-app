package com.zml.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zml.util.AppUtil;

public class BaseHandler extends Handler {

    protected BaseUi ui;
    protected BaseFragment fragment;

    public BaseHandler (BaseUi ui) {
        this.ui = ui;
    }

    public BaseHandler (Looper looper) {
        super(looper);
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            int taskId;
            String result;
            switch (msg.what) {
                case BaseTask.TASK_COMPLETE:
                    ui.hideLoadBar();
                    taskId = msg.getData().getInt("task");
                    result = msg.getData().getString("data");
                    if (result != null) {
                        if(fragment != null) {
                            fragment.onTaskComplete(taskId, AppUtil.getMessage(result));
                        } else {
                            ui.onTaskComplete(taskId, AppUtil.getMessage(result));
                        }
                    } else if (!AppUtil.isEmptyInt(taskId)) {
                        if(fragment != null) {
                            fragment.onTaskComplete(taskId);
                        } else {
                            ui.onTaskComplete(taskId);
                        }
                        ui.onTaskComplete(taskId);
                    } else {
                        ui.toast(C.err.message);
                    }
                    break;
                case BaseTask.NETWORK_ERROR:
                    ui.hideLoadBar();
                    taskId = msg.getData().getInt("task");
                    ui.onNetworkError(taskId);
                    break;
                case BaseTask.SHOW_LOADBAR:
                    ui.showLoadBar();
                    break;
                case BaseTask.HIDE_LOADBAR:
                    ui.hideLoadBar();
                    break;
                case BaseTask.SHOW_TOAST:
                    ui.hideLoadBar();
                    result = msg.getData().getString("data");
                    ui.toast(result);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ui.toast(e.getMessage());
        }
    }
}