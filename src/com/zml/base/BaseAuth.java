package com.zml.base;

import com.zml.model.Student;

public class BaseAuth {

    static private Student student = Student.getInstance();
    static public boolean isLogin () {
        return student.getLogin();
    }

    static public void setLogin (Boolean status) {
        student.setLogin(status);
    }

    static public void setstudent (Student std) {
        student.setStdNum(std.getStdNum());
        student.setPassword(std.getPassword());
    }

    static public Student getstudent () {
        return student;
    }
}