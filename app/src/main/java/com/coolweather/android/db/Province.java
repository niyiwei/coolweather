package com.coolweather.android.db;

/**
 * 省份类
 * 2018年3月1日21:45:40.
 */
public class Province {
    /**
     * 省份ID.
     */
    private int id;
    /**
     * 省份名称.
     */
    private String provinceName;
    /**
     * 省份代码.
     */
    private String provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                '}';
    }
}
