package com.zml.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ImgeListAdapter extends BaseAdapter {

    private Context mContext;
    //xml杞琕iew瀵硅薄
    private LayoutInflater mInflater;
    //鍗曡鐨勫竷灞�
    private int mResource;
    //鍒楄〃灞曠幇鐨勬暟鎹�
    private List mData;
    //Map涓殑key
    private String[] mFrom;
    //view鐨刬d
    private int[] mTo;
//    private AbImageDownloadQueue mAbImageDownloadQueue = null;
    
    public ImgeListAdapter(Context context, List data,
            int resource, String[] from, int[] to){
        this.mContext = context;
        this.mData = data;
        this.mResource = resource;
        this.mFrom = from;
        this.mTo = to;
        //鐢ㄤ簬灏唜ml杞负View
        this.mInflater = LayoutInflater.from(mContext);
//        this.mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        if(convertView == null){
             //浣跨敤鑷畾涔夌殑list_items浣滀负Layout
             convertView = mInflater.inflate(mResource, parent, false);
             //鍑忓皯findView鐨勬鏁�
             holder = new ViewHolder();
             //鍒濆鍖栧竷灞�腑鐨勫厓绱�
             holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0])) ;
             holder.itemsTitle = ((TextView) convertView.findViewById(mTo[1]));
             holder.itemsText = ((TextView) convertView.findViewById(mTo[2]));
             convertView.setTag(holder);
        }else{
             holder = (ViewHolder) convertView.getTag();
        }
      //鑾峰彇璇ヨ鐨勬暟鎹�
        final Map<String, Object>  obj = (Map<String, Object>)mData.get(position);
        holder.itemsIcon.setBackgroundResource((Integer)obj.get("itemsIcon"));
        holder.itemsTitle.setText((String)obj.get("itemsTitle"));
        holder.itemsText.setText((String)obj.get("itemsText"));
//        holder.itemsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //showInfo();
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        ImageView itemsIcon;
        TextView itemsTitle;
        TextView itemsText;
        ImageButton itemsBtn;
    }
}
