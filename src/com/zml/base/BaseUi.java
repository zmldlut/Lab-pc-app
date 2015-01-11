package com.zml.base;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.zml.R;
import com.zml.ui.UiAbout;
import com.zml.ui.UiIndex;
import com.zml.util.AppUtil;

public class BaseUi extends AbActivity {

    protected BaseApp app;
    protected BaseHandler handler;
    protected BaseTaskPool taskPool;
    protected boolean showLoadBar = false;
    protected boolean showDebugMsg = true;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // debug memory
        initTitle();
        debugMemory("onCreate");
        // async task handler
        this.handler = new BaseHandler(this);
        // init task pool
        this.taskPool = new BaseTaskPool(this);
        // init application
        this.app = (BaseApp) this.getApplicationContext();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // util method
    public void toast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void overlay (Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    public void overlay (Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
    }

    public void forward (Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();
    }

    public void forward (Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        this.startActivity(intent);
        this.finish();
    }

    public void forward (String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        this.startActivity(intent);
    }

    public void forward (String action, Bundle params) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtras(params);
        this.startActivity(intent);
    }

    public Context getContext () {
        return this;
    }

    public BaseHandler getHandler () {
        return this.handler;
    }

    public void setHandler (BaseHandler handler) {
        this.handler = handler;
    }

    public LayoutInflater getLayout () {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getLayout (int layoutId) {
        return getLayout().inflate(layoutId, null);
    }

    public View getLayout (int layoutId, int itemId) {
        return getLayout(layoutId).findViewById(itemId);
    }

    public BaseTaskPool getTaskPool () {
        return this.taskPool;
    }

    public void showLoadBar () {
        this.findViewById(R.id.main_load_bar).setVisibility(View.VISIBLE);
        this.findViewById(R.id.main_load_bar).bringToFront();
        showLoadBar = true;
    }

    public void hideLoadBar () {
        if (showLoadBar) {
            this.findViewById(R.id.main_load_bar).setVisibility(View.GONE);
            showLoadBar = false;
        }
    }

    public void openDialog(Bundle params) {
        new BaseDialog(this, params).show();
    }

    public void loadImage (final String url) {
        taskPool.addTask(0, new BaseTask(){
            @Override
            public void onComplete(){
//                AppCache.getCachedImage(getContext(), url);
                sendMessage(BaseTask.LOAD_IMAGE);
            }
        }, 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // logic method
    
    public void doFinish () {
        this.finish();
    }

    public void doLogout () {
        BaseAuth.setLogin(false);
    }

    public void sendMessage (int what) {
        Message m = new Message();
        m.what = what;
        handler.sendMessage(m);
    }

    public void sendMessage (int what, String data) {
        Bundle b = new Bundle();
        b.putString("data", data);
        Message m = new Message();
        m.what = what;
        m.setData(b);
        handler.sendMessage(m);
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

    public void doTaskAsync (int taskId, int delayTime) {
        showLoadBar();
        taskPool.addTask(taskId, new BaseTask(){
            @Override
            public void onComplete () {
                sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
            }
            @Override
            public void onError (String error) {
                sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
            }
        }, delayTime);
    }

    public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
        showLoadBar();
        taskPool.addTask(taskId, baseTask, delayTime);
    }

    public void doTaskAsync (int taskId, String taskUrl) {
        showLoadBar();
        taskPool.addTask(taskId, taskUrl, new BaseTask(){
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

    public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//        showLoadBar();
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

    public void onTaskComplete (int taskId, BaseMessage message) {
        
    }

    public void onTaskComplete (int taskId) {
        
    }

    public void onNetworkError (int taskId) {
        toast(C.err.network);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // debug method
    public void debugMemory (String tag) {
        if (this.showDebugMsg) {
            Log.w(this.getClass().getSimpleName(), tag+":"+AppUtil.getUsedMemory());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // common classes
    public class BitmapViewBinder implements ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if ((view instanceof ImageView) & (data instanceof Bitmap)) {
                ImageView iv = (ImageView) view;
                Bitmap bm = (Bitmap) data;
                iv.setImageBitmap(bm);
                return true;
            }
            return false;
        }
    }

    public void initTitle() {
        this.setTitleText(R.string.app_name);
        this.setTitleLayoutBackground(R.drawable.top_bg);
        this.setTitleTextMargin(10, 0, 0, 0);
        this.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
        clearRightView();
        View rightViewMore = mInflater.inflate(R.layout.more_btn, null);
        View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
        addRightView(rightViewApp);
        addRightView(rightViewMore);
        
        Button about = (Button)rightViewMore.findViewById(R.id.moreBtn);
        Button appBtn = (Button)rightViewApp.findViewById(R.id.appBtn);
        appBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseUi.this,UiIndex.class); 
                startActivity(intent);
            }
         });
        about.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseUi.this, UiAbout.class); 
                startActivity(intent);
            }
         });
    }
}