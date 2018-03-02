package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phd on 2018/3/2 13:49.
 */
public class Suggestion {

    @SerializedName(value = "comf")
    public Comfort comfort;

    @SerializedName(value = "cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash {
        @SerializedName("txt")
        public String info;
    }

    public class Sport {
        @SerializedName("txt")
        public String info;
    }
}
