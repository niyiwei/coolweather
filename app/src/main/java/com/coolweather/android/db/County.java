package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/***
 * 县
 * 2018年3月1日21:50:18.
 */
public class County extends DataSupport {
    /**
     * 县ID.
     */
    private int id;
    /**
     * 县名称.
     */
    private String countyName;
    /**
     * 县的天气ID.
     */
    private String weatherId;
    /**
     * 城市ID.
     */
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", countyName='" + countyName + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
