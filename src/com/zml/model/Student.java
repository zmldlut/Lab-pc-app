package com.zml.model;

import com.zml.base.BaseModel;

public class Student extends BaseModel {
    
    // model columns
    public final static String COL_STDNUM = "stdNum";
    public final static String COL_PASSWORD = "password";
    public final static String COL_CARDID = "cardID";
    public final static String COL_NAME = "name";
    
    private String stdNum;
    private String password;
    private String cardID;
    private String name;

    // default is no login
    private boolean isLogin = false;
    
    // single instance for login
    static private Student customer = null;
    
    static public Student getInstance () {
        if (Student.customer == null) {
            Student.customer = new Student();
        }
        return Student.customer;
    }

    public Boolean getLogin () {
        return this.isLogin;
    }
    
    public void setLogin (boolean isLogin) {
        this.isLogin = isLogin;
    }
    public String getStdNum() {
        return stdNum;
    }

    public void setStdNum(String stdNum) {
        this.stdNum = stdNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}