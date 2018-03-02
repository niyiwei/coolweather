package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 解析和处理返回数据工具类
 * 2018年3月1日22:02:45.
 */
public class Utility {

    /**
     * 处理查询省份返回数据
     *
     * @param response 返回数据
     * @return Boolean.
     */
    public static boolean handleProvinceResponse(final String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONArray allProvince = new JSONArray(response);
            JSONObject jsonObject = null;
            Province saveData = null;
            for (int i = 0; i < allProvince.length(); i++) {
                jsonObject = allProvince.getJSONObject(i);
                saveData = new Province();
                saveData.setProvinceName(jsonObject.getString("name"));
                saveData.setProvinceCode(jsonObject.getInt("id"));
                saveData.save();
            }
            return true;
        } catch (JSONException e) {
            Log.e("Utility", "handleProvinceResponse exception||" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 处理查询城市返回数据
     *
     * @param response 返回数据
     * @return Boolean.
     */
    public static boolean handleCityResponse(final String response, final int provinceId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONArray allCity = new JSONArray(response);
            JSONObject jsonObject = null;
            City saveData = null;
            for (int i = 0; i < allCity.length(); i++) {
                jsonObject = allCity.getJSONObject(i);
                saveData = new City();
                saveData.setCityName(jsonObject.getString("name"));
                saveData.setCityCode(jsonObject.getInt("id"));
                saveData.setProvinceId(provinceId);
                saveData.save();
            }
            return true;
        } catch (JSONException e) {
            Log.e("Utility", "handleCityResponse exception||" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理查询县返回数据
     *
     * @param response 返回数据
     * @return Boolean.
     */
    public static boolean handleCountyResponse(final String response, final int cityId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONArray allProvince = new JSONArray(response);
            JSONObject jsonObject = null;
            County saveData = null;
            for (int i = 0; i < allProvince.length(); i++) {
                jsonObject = allProvince.getJSONObject(i);
                saveData = new County();
                saveData.setCountyName(jsonObject.getString("name"));
                saveData.setWeatherId(jsonObject.getString("weather_id"));
                saveData.setCityId(cityId);
                saveData.save();
            }
            return true;
        } catch (JSONException e) {
            Log.e("Utility", "handleCountyResponse exception||" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static Weather handleWeatherResponse(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.get(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            Log.e("Utility", "handleWeatherResponse exception||" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}