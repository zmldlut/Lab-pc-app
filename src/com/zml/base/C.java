package com.zml.base;

public final class C {

    public static final class code {
        public static final int login               = 1000;
        public static final int device_list         = 1001;
        public static final int order_list          = 1002;
        public static final int report              = 1003;
        public static final int password            = 1004;
        public static final int order               = 1005;
        
        public static final int logout  = 1001;
        public static final int notice  = 1002;
        public static final int error   = 1003;
    }

    public static final class api {
        public static final String base             = "http://192.168.9.69:8080";

        public static final String login            = "/Lab-demo/pc_app/pc_app_login";
        public static final String device_list      = "/Lab-demo/pc_app/pc_app_device_list";
        public static final String order_list       = "/Lab-demo/pc_app/pc_app_order_list";
        public static final String report           = "/Lab-demo/pc_app/pc_app_report";
        public static final String password         = "/Lab-demo/pc_app/pc_app_password";
        public static final String order            = "/Lab-demo/pc_app/pc_app_order";
        
        
        public static final String logout           = "/Lab-demo/pc_app/pc_app_logout";
        public static final String notice           = "/Lab-demo/pc_app/pc_app_notice";
    }

    public static final class task {
        public static final int login               = 1000;
        public static final int device_list         = 1001;
        public static final int order_list          = 1002;
        public static final int report              = 1003;
        public static final int password            = 1004;
        public static final int order               = 1005;
        
        
        public static final int logout              = 1003;
        public static final int notice              = 1004;
    }

    public static final class err {
        public static final String network          = "网络错误";
        public static final String message          = "消息错误";
        public static final String jsonFormat       = "消息格式错误";
    }
}

//.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\bin;