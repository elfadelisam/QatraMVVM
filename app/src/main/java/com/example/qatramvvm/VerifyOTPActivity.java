package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.goodiebag.pinview.Pinview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class VerifyOTPActivity extends AppCompatActivity {
    private TextView textView, timer, re_send_otp;
    private static final long time = 20000;
    CountDownTimer countDownTimer;
    private long time_left = time;
    private Boolean timerRunning;
    private String contact, verify_code;
    private Pinview pinview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        setTitle(R.string.code_verification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView    = findViewById(R.id.textView);
        timer       = findViewById(R.id.timer);
        re_send_otp = findViewById(R.id.re_send_otp);
        textView.setText(getIntent().getStringExtra("contact"));
        contact = textView.getText().toString();

        startTimer();

        Get_Verify_code();

        re_send_otp.setOnClickListener(view -> {
            if(timerRunning){
                re_send_otp.setClickable(false);
                re_send_otp.setTextColor(getResources().getColor(R.color.gray));
            }else {
                startTimer();
                re_send_otp.setClickable(false);
                re_send_otp.setTextColor(getResources().getColor(R.color.gray));
                update_code();
                Get_Verify_code();
            }
        });

        updateCountDownText();

        pinview = (Pinview)findViewById(R.id.pinview);
        pinview.setPinViewEventListener((pinview, fromUser) -> {
            //Make api calls here or what not
        });
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(time_left, 1000) {
            @Override
            public void onTick(long l) {
                time_left = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                re_send_otp.setClickable(true);
                re_send_otp.setTextColor(Color.BLACK);
                time_left = time;
                updateCountDownText();
            }
        }.start();

        timerRunning = true;
    }

    public void update_code() {
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/generate_code.php?contact="+contact;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            /*Intent intent=new Intent(ResetPasswordViaSmsActivity.this,VerifyOTPActivity.class);
                            intent.putExtra("contact",ed_phone.getText().toString().trim());
                            startActivity(intent);*/
                            Toast.makeText(VerifyOTPActivity.this, "تم ارسال رمز جديد", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(VerifyOTPActivity.this,"فشل ارسال رمز تحقق جديد",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                });
        RequestQueue requestQueue = Volley.newRequestQueue(VerifyOTPActivity.this);
        requestQueue.add(stringRequest);

    }

    private void updateCountDownText(){
        int minutes = (int)(time_left / 1000) / 60;
        int seconds = (int)(time_left / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
    }

    public void verify(View view) {
        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        boolean ch = cic.isConnecting();
        if(ch){
            //showCustomDialog();
            //Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            if(pinview.getValue().equals("")){
                Toast.makeText(this, "رجاءً قم بكتابة رمز التحقق", Toast.LENGTH_SHORT).show();
            }
            else {
                if(pinview.getValue().equals(verify_code)){
                    Intent intent=new Intent(VerifyOTPActivity.this, Reset_passwordActivity.class);
                    intent.putExtra("contact",textView.getText().toString().trim());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(VerifyOTPActivity.this, "رمز التحقق الذي أدخلته غير صحيح", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void Get_Verify_code(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/get_code.php?contact="+contact;

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data,
                response -> {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray array= jsonObject.getJSONArray("code");

                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject o =array.getJSONObject(i);
                            verify_code = o.getString("code");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    /*private void showCustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTPActivity.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/
}