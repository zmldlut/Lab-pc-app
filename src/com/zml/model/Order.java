package com.zml.model;

import com.zml.base.BaseModel;

public class Order extends BaseModel {
    
    // model columns
    public final static String COL_ID = "id";
    public final static String COL_DEVICE_ID = "device_id";
    public final static String COL_ORDER_TIME = "order_time";
    public final static String COL_START_TIME = "start_time";
    public final static String COL_END_TIME = "end_time";
    public final static String COL_STATUS = "status";
    public final static String COL_STDNUM = "stdNum";

    private String id;
    private String device_id;
    private String order_time;
    private String start_time;
    private String end_time;
    private String status;
    private String stdNum;
    public String getStdNum() {
        return stdNum;
    }
    public void setStdNum(String stdNum) {
        this.stdNum = stdNum;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDevice_id() {
        return device_id;
    }
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
    public String getOrder_time() {
        return order_time;
    }
    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    

}