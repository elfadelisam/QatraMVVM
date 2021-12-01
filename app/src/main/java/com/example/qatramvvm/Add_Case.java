package com.example.qatramvvm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add_Case extends StringRequest {
    //private static final String data_url="https://qatradam.000webhostapp.com/admin/insert_case.php";
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/insert_case.php";
    private Map<String, String> MapData;

    public Add_Case(String ed_name, String ed_desc, String ed_phone, String blood_type, String ed_hospital, String ed_age, String gender, String date, String ed_num, Response.Listener<String> listener) {
        super(Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("name", ed_name);
        MapData.put("description", ed_desc);
        MapData.put("phone", ed_phone);
        MapData.put("blood_type", blood_type);
        MapData.put("hospital", ed_hospital);
        MapData.put("age", ed_age);
        MapData.put("gender", gender);
        MapData.put("date", date);
        MapData.put("num", ed_num);
    }
    @Override
    public Map<String, String> getParams() { return MapData; }
}
