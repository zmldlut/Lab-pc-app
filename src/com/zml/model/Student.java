package com.zml.model;

import com.zml.base.BaseModel;

public class Student extends BaseModel {
    
    public final static String COL_CARDID = "cardID";
    public final static String COL_NAME = "name";
    public final static String COL_PASSWORD = "password";
    public final static String COL_STDNUM = "stdNum";
 // default is no login
    private boolean isLogin = false;
    
    private String cardID = null;
    private String email = null;
    private String grade_id = null;
    private String is_here = null;
    
    private String major_id = null;
    private String name = null;
    private String password = null;
    private String phone = null;
    private String QQ = null;
    private String stdnum = null;
    public boolean getLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getIs_here() {
        return is_here;
    }

    public void setIs_here(String is_here) {
        this.is_here = is_here;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String qQ) {
        QQ = qQ;
    }

    public String getStdnum() {
        return stdnum;
    }

    public void setStdnum(String stdnum) {
        this.stdnum = stdnum;
    }

    // single instance for login
    static private Student customer = null;
    public static Student getCustomer() {
        return customer;
    }

    static public Student getInstance () {
        if (Student.customer == null) {
            Student.customer = new Student();
        }
        return Student.customer;
    }

    public static void setCustomer(Student customer) {
        Student.customer = customer;
    }
}