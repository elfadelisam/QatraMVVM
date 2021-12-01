package com.example.qatramvvm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XMan on 3/24/2020.
 */

public class Update_Profile extends StringRequest {
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/update_profile.php";
    private Map<String, String> MapData;

    public Update_Profile(String ed_name, String ed_phone, String ed_email, String ed_address, String pre_phone, String pre_password, Response.Listener<String> listener) {
        super(Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("name", ed_name);
        MapData.put("phone", ed_phone);
        MapData.put("email", ed_email);
        MapData.put("address", ed_address);
        MapData.put("pre_phone", pre_phone);
        MapData.put("pre_password", pre_password);
    }
    @Override
    public Map<String, String> getParams() { return MapData; }
}
