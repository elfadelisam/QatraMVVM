package com.example.qatramvvm;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add_Donor extends StringRequest {
    private static final String data_url="http://192.168.43.29:8081/bloodbank/admin/insert_donor.php";
    private Map<String, String> MapData;

    public Add_Donor(String ed_name, String ed_email, String ed_phone, String blood_type, String ed_address, String ed_age, String gender, String last_donation_date, String user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, data_url, listener, null);
        MapData = new HashMap<>();
        MapData.put("name", ed_name);
        MapData.put("email", ed_email);
        MapData.put("phone", ed_phone);
        MapData.put("blood_type", blood_type);
        MapData.put("address", ed_address);
        MapData.put("age", ed_age);
        MapData.put("gender", gender);
        MapData.put("last_donation_date", last_donation_date);
        MapData.put("user_id", user_id);
    }
    @Override
    public Map<String, String> getParams() { return MapData; }

}
