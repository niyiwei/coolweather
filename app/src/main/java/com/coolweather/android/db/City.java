package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * 城市类
 * 2018年3月1日21:47:39.
 */
public class City extends DataSupport {
    /**
     * 城市ID.
     */
    private int id;
    /**
     * 城市名称.
     */
    private String cityName;
    /**
     * 城市代码.
     */
    private String cityCode;
    /**
     * 省份ID.
     */
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
}