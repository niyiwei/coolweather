package com.coolweather.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 发送请求工具类
 * 2018年3月1日21:59:06.
 */
public class HttpUtil {
    /**
     * 发送Http请求
     * @param address 请求地址
     * @param callback 回调函数
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
