package com.example.common_lib.info;

public class ServerInfo {

    //120.27.11.67:6211  http://192.168.0.113:6211/
    private static final String SERVER_IP = "http://192.168.0.111:6211/";//服务器ip
    private static final String SERVER_PROJECT = SERVER_IP + "dy_wisdom/";//服务器ip

    public static String getAppDownloadAddress(String address) {
        return SERVER_PROJECT + address;
    }

    public static String getServerAddress(String address) {
        return SERVER_PROJECT + address;
    }

    /**
     * 得到图片地址
     *
     * @param imageName
     * @return
     */
    public static String getImageAddress(String imageName) {
        //http://47.94.1.12:8080/groupimage/6c155734-04c9-47d2-a298-5608185c2394.png
        return SERVER_PROJECT + "" + imageName;
    }


}
