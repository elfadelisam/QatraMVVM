package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailsCase extends AppCompatActivity {
    SessionManager session;
    TextView txtBloodType, txtName, txtHospital, txtPhone, txtDesc, txtAge, txtDate, txtGender, txtNum;
    String phone, password, case_phone, case_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        HashMap<String, String> user = session.getUserDetails();
        phone = user.get(SessionManager.KEY_PHONE);
        password = user.get(SessionManager.KEY_PASSWORD);

        txtBloodType = findViewById(R.id.txtBloodType);
        txtName      = findViewById(R.id.name);
        txtHospital  = findViewById(R.id.hospital);
        txtPhone     = findViewById(R.id.phone);
        txtDesc      = findViewById(R.id.description);
        txtAge       = findViewById(R.id.age);
        txtDate      = findViewById(R.id.date);
        txtGender    = findViewById(R.id.gender);
        txtNum       = findViewById(R.id.number);

        txtBloodType.setText(getIntent().getStringExtra("blood_type"));
        txtName.setText(getIntent().getStringExtra("case_name"));
        txtHospital.setText(getIntent().getStringExtra("hospital"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtDesc.setText(getIntent().getStringExtra("description"));
        txtAge.setText(getIntent().getStringExtra("age"));
        txtDate.setText(getIntent().getStringExtra("date"));
        txtGender.setText(getIntent().getStringExtra("gender"));
        txtNum.setText(getIntent().getStringExtra("num"));

        case_phone = getIntent().getStringExtra("phone");
        case_id = getIntent().getStringExtra("case_id");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void accept_request(View view) {
        check_as_donor();
    }

    public void check_as_donor(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/check_as_adonor.php?phone="+phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //Toast.makeText(DetailsCase.this,"مسجل كمتبرع",Toast.LENGTH_SHORT).show();
                                check_donation_date();
                            }
                            else {
                                Toast.makeText(DetailsCase.this,"عذراً .. لم يتم تسجيل بياناتك كمتبرع",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsCase.this);
        requestQueue.add(stringRequest);
    }

    public void check_donation_date(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/check_donation_date.php?phone="+phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //Toast.makeText(DetailsCase.this,"تهانينا .. مستوفي الفترة الزمنية للتبرع",Toast.LENGTH_SHORT).show();
                                check_request_date();
                            }
                            else {
                                Toast.makeText(DetailsCase.this,"عذراً .. لم تكمل الفترة الزمنية للتبرع",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsCase.this);
        requestQueue.add(stringRequest);
    }

    public void check_request_date(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/check_request_date.php?phone="+case_phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //Toast.makeText(DetailsCase.this,"تهانينا ...",Toast.LENGTH_SHORT).show();
                                check_donation();
                            }
                            else {
                                Toast.makeText(DetailsCase.this,"عذراً .. لقد انقضت الفترة المحددة للتبرع",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsCase.this);
        requestQueue.add(stringRequest);
    }

    public void check_donation(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/check_donation.php?phone="+case_phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //Toast.makeText(DetailsCase.this,"تهانينا ...لم يتم التبرع مسبقاً",Toast.LENGTH_SHORT).show();
                                donation();
                            }
                            else {
                                Toast.makeText(DetailsCase.this,"عذراً .. تم التبرع مسبقاً",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsCase.this);
        requestQueue.add(stringRequest);
    }

    public void donation(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/donation.php?case_id="+case_id+"&phone="+phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(DetailsCase.this,"تهانينا .. تمت عملية التبرع بنجاح",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DetailsCase.this,"عذراً .. لم يتم التبرع",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsCase.this);
        requestQueue.add(stringRequest);
    }
}