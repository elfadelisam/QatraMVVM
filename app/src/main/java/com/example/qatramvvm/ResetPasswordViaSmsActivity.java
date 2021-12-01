package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordViaSmsActivity extends AppCompatActivity {
    EditText ed_phone;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_via_sms);

        setTitle(R.string.recovery_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_phone = findViewById(R.id.phone);


    }

    public void next(View view) {
        phone = ed_phone.getText().toString().trim();
        if(phone.length()!=10){
            Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else {
            String contact = phone;
            String url_data = "http://192.168.43.29:8081/bloodbank/admin/generate_code.php?contact="+contact;
            StringRequest stringRequest=new StringRequest(Request.Method.GET,
                    url_data ,
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent=new Intent(ResetPasswordViaSmsActivity.this,VerifyOTPActivity.class);
                                intent.putExtra("contact",ed_phone.getText().toString().trim());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(ResetPasswordViaSmsActivity.this,"عذراً رقم الهاتف غير صحيح أو لم يتم التسجيل به",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(ResetPasswordViaSmsActivity.this);
            requestQueue.add(stringRequest);
        }
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
}