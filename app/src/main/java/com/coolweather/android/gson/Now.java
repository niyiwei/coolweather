package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phd on 2018/3/2 13:47.
 */
public class Now {
    @SerializedName(value = "tmp")
    public String temperature;

    @SerializedName(value = "cond")
    public More more;

    public class More {
        @SerializedName(value = "txt")
        public String info;
    }
}
