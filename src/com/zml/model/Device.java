package com.zml.model;

import com.zml.base.BaseModel;

public class Device extends BaseModel {
    
    // model columns
    public final static String COL_ID = "id";
    public final static String COL_NAME = "name";
    public final static String COL_TYPE = "type";
    public final static String COL_SERVICE_TAG = "service_tag";
    public final static String COL_STATUS = "status";
    
    private String id;
    private String name;
    private String type;
    private String service_tag;
    private String status;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getService_tag() {
        return service_tag;
    }
    public void setService_tag(String service_tag) {
        this.service_tag = service_tag;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}