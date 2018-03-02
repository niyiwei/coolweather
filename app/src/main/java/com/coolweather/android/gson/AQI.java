package com.coolweather.android.gson;

/**
 * Created by phd on 2018/3/2 13:43.
 */
public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
