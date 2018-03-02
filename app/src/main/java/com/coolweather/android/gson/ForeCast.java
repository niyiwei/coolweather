package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phd on 2018/3/2 13:52 13:53.
 */
public class ForeCast {
    public String date;

    @SerializedName(value = "tmp")
    public Temperature temperature;

    @SerializedName(value = "cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName(value = "txt_d")
        public String info;
    }
}
