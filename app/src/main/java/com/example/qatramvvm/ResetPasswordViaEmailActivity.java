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

public class ResetPasswordViaEmailActivity extends AppCompatActivity {
    EditText ed_email;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_via_email);

        setTitle(R.string.recovery_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_email = findViewById(R.id.email);
    }

    public void next(View view) {
        email = ed_email.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }
        else {
            String contact = email;
            String url_data = "http://192.168.43.29:8081/bloodbank/admin/forget_password.php?contact="+contact;
            StringRequest stringRequest=new StringRequest(Request.Method.GET,
                    url_data ,
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent=new Intent(ResetPasswordViaEmailActivity.this,VerifyOTPActivity.class);
                                intent.putExtra("contact",ed_email.getText().toString().trim());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(ResetPasswordViaEmailActivity.this,"عذراً البريد الإلكتروني غير صحيح أو لم يتم التسجيل به",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(ResetPasswordViaEmailActivity.this);
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