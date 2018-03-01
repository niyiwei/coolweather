package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

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
    public static boolean hanldeProvinceResponse(final String response) {
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
    public static boolean hanldeCityResponse(final String response, final int provinceId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONArray allProvince = new JSONArray(response);
            JSONObject jsonObject = null;
            City saveData = null;
            for (int i = 0; i < allProvince.length(); i++) {
                jsonObject = allProvince.getJSONObject(i);
                saveData = new City();
                saveData.setCityName(jsonObject.getString("name"));
                saveData.setCityCode(jsonObject.getInt("id"));
                saveData.setProvinceId(provinceId);
                saveData.save();
            }
            return true;
        } catch (JSONException e) {
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
    public static boolean hanldeCountyResponse(final String response, final int cityId) {
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
            e.printStackTrace();
        }
        return false;
    }
}