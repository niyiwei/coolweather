package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phd on 2018/3/2 11:38.
 */

public class Basic {
    @SerializedName(value = "city")
    public String cityName;

    @SerializedName(value = "id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
