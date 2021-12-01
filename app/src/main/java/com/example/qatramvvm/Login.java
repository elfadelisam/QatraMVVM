package com.example.qatramvvm;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends StringRequest {
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/login.php";

    private Map<String, String> MapData;

    public Login(String ed_phone, String ed_password, Response.Listener<String> listener) {
        super(Request.Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("phone", ed_phone);
        MapData.put("password", ed_password);
    }

    @Override
    public Map<String, String> getParams() { return MapData; }
}
