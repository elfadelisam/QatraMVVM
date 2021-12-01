package com.example.qatramvvm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Change_Password extends StringRequest {
    //private static final String data_url="https://qatradam.000webhostapp.com/admin/change_password.php";
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/change_password.php";
    private Map<String, String> MapData;

    public Change_Password(String ed_new_password, String ed_old_password, String phone, Response.Listener<String> listener) {
        super(Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("new_password", ed_new_password);
        MapData.put("old_password", ed_old_password);
        MapData.put("phone", phone);
    }

    @Override
    public Map<String, String> getParams() { return MapData; }
}
