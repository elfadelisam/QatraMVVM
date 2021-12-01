package com.example.qatramvvm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Registration extends StringRequest {
    //private static final String data_url="https://qatradam.000webhostapp.com/admin/registration.php";
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/registration.php";
    private Map<String, String> MapData;

    public Registration(String ed_name, String ed_phone, String ed_password, String ed_email, String ed_address, String blood_type, Response.Listener<String> listener) {
        super(Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("name", ed_name);
        MapData.put("phone", ed_phone);
        MapData.put("password", ed_password);
        MapData.put("email", ed_email);
        MapData.put("address", ed_address);
        MapData.put("blood_type", blood_type);
    }
    @Override
    public Map<String, String> getParams() { return MapData; }
}
