package com.coolweather.android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 选择区域碎片
 * Created by phd on 2018/3/2 09:34.
 */
public class ChooseAreaFragment extends Fragment {
    /**
     * 等级：省份.
     */
    public static final int LEVEL_PROVINCE = 0;
    /**
     * 等级：城市.
     */
    public static final int LEVEL_CITY = 1;
    /**
     * 等级：县.
     */
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /***省份列表.*/
    private List<Province> provinceList;
    /***城市列表.*/
    private List<City> cityList;
    /***县列表.*/
    private List<County> countyList;
    /**
     * 选中的省份.
     */
    private Province selectedProvince;
    /**
     * 选中的城市.
     */
    private City selectedCity;
    /**
     * 当前的级别.
     */
    private int currentLevel;
    /**请求查询类型：省份.*/
    private final static String QUERY_TYPE_PROVINCE = "province";
    /**请求查询类型：城市.*/
    private final static String QUERY_TYPE_CITY = "city";
    /**请求查询类型：县.*/
    private final static String QUERY_TYPE_COUNTY = "county";
    /**查询省市县数据地址.*/
    private final static String ADDRESS_URL = "http://guolin.tech/api/china";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置列表选中事件
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (currentLevel == LEVEL_PROVINCE) {
                selectedProvince = provinceList.get(position);
                Log.i("ChooseAreaFragment","当前选择的省:"+selectedProvince+",当前选择的市:"+selectedCity+",position="+position+",id="+id);
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                selectedCity = cityList.get(position);
                Log.i("ChooseAreaFragment","当前选择的省:"+selectedProvince+",当前选择的市:"+selectedCity+",position="+position+",id="+id);
                queryCounties();
            }else if (currentLevel == LEVEL_COUNTY){
                String weatherId = countyList.get(position).getWeatherId();
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("weather_id", weatherId);
                startActivity(intent);
                getActivity().finish();
            }
        });
        // 设置返回按钮点击事件
        backButton.setOnClickListener((v) -> {
            if (currentLevel == LEVEL_COUNTY) {
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                queryProvinces();
            }
        });
        queryProvinces();
    }

    /**查询全国所有的省.*/
    private void queryProvinces(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0){
            dataList.clear();
            for (Province data: provinceList){
                dataList.add(data.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            queryFromServer(ADDRESS_URL, QUERY_TYPE_PROVINCE);
        }
    }

    /**查询选中省内所有的城市，优先查询数据库.*/
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0){
            dataList.clear();
            for (City data: cityList){
                dataList.add(data.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            queryFromServer(ADDRESS_URL.concat("/").concat(String.valueOf(selectedProvince.getId())), QUERY_TYPE_CITY);
        }
    }

    /**查询选中省内所有的城市，优先查询数据库.*/
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0){
            dataList.clear();
            for (County data: countyList){
                dataList.add(data.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else{
            queryFromServer(ADDRESS_URL.concat("/").concat(String.valueOf(selectedProvince.getId()))
                    .concat("/").concat(String.valueOf(selectedCity.getCityCode())), QUERY_TYPE_COUNTY);
        }
    }
    /**
     * 根据传入的地址到服务器查询省市县数据
     *
     * @param address 请求地址
     * @param type    请求类型.
     */
    private void queryFromServer(final String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.i("ChooseAreaFragment","address="+address+",type="+type+",查询返回数据:"+responseText);
                if (!handResponseTextByType(responseText, type)){
                    showLoadFailureTips();
                    return;
                }
                handleLoadSuccess(type);
            }
            @Override
            public void onFailure(Call call, IOException e) {
                showLoadFailureTips();
            }
        });
    }

    /**
     * 根据查询类型处理查询返回数据
     * @param responseText 查询成功返回的数据
     * @param type 查询类型
     * @return boolean. 成功：true，失败：false
     */
    private boolean handResponseTextByType(final String responseText, final String type){
        if (QUERY_TYPE_PROVINCE.equals(type)){
            return Utility.handleProvinceResponse(responseText);
        }else if(QUERY_TYPE_CITY.equals(type)){
            return Utility.handleCityResponse(responseText, selectedProvince.getId());
        }else if(QUERY_TYPE_COUNTY.equals(type)){
            return Utility.handleCountyResponse(responseText, selectedCity.getId());
        }
        return false;
    }

    /**加载成功处理.*/
    private void handleLoadSuccess(final String type){
        getActivity().runOnUiThread(()->{
            closeProgressDialog();
            if (QUERY_TYPE_PROVINCE.equals(type)){
                queryProvinces();
            }else if(QUERY_TYPE_CITY.equals(type)){
                queryCities();
            }else if(QUERY_TYPE_COUNTY.equals(type)){
                queryCounties();
            }
        });
    }
    /**展示加载失败提示.*/
    private void showLoadFailureTips(){
        //通过runOnUiThread()方法回到主线程
        getActivity().runOnUiThread(()->{
            closeProgressDialog();
            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * 显示进度条对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载中...");
            //设置进度条对话框无法关闭
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /***关闭进度对话框.*/
    private void closeProgressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        progressDialog.dismiss();
    }
}