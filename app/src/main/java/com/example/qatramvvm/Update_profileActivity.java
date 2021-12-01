package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Update_profileActivity extends AppCompatActivity {

    EditText ed_name, ed_phone, ed_email, ed_address;
    TextView username, phoneNo;
    String name, phone, email, address;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        setTitle(R.string.update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        //session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String phone = user.get(SessionManager.KEY_PHONE);

        HashMap<String, String> userdata = session.getUserData();

        ed_name     = findViewById(R.id.name);
        ed_phone    = findViewById(R.id.phone);
        ed_email    = findViewById(R.id.email);
        ed_address  = findViewById(R.id.address);

        username    = findViewById(R.id.user_name);
        phoneNo     = findViewById(R.id.phone_no);

        ed_name.setText(userdata.get(SessionManager.KEY_NAME));
        ed_phone.setText(phone);
        ed_email.setText(userdata.get(SessionManager.KEY_EMAIL));
        ed_address.setText(userdata.get(SessionManager.KEY_ADDRESS));

        username.setText(userdata.get(SessionManager.KEY_NAME));
        phoneNo.setText(phone);

    }

    public void updateProfile(View view) {
        initialized();
        if(!validate()){

        }
        else {
            session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
            //session.checkLogin();
            HashMap<String, String> user = session.getUserDetails();
            String pre_phone = user.get(SessionManager.KEY_PHONE);
            String pre_password = user.get(SessionManager.KEY_PASSWORD);

            HashMap<String, String> userdata = session.getUserData();
            String user_id = userdata.get(SessionManager.KEY_USER_ID);
            String group = userdata.get(SessionManager.KEY_GROUP);

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(Update_profileActivity.this, "تم تعديل البيانات بنجاح !", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Update_profileActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Update_profileActivity.this, "عذرا .. لم يتم تعديل البيانات", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Update_Profile update_profile = new Update_Profile(name, phone, email, address, pre_phone, pre_password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Update_profileActivity.this);
            queue.add(update_profile);
            session.createUserDataSession(user_id, name, phone, email, address, group);
        }
    }

    public boolean validate(){
        boolean valid = true;
        if(name.isEmpty() || name.length()<2){
            ed_name.setError("name at least 2 characters");
            valid = false;
        }
        if(phone.isEmpty() || phone.length()<10){
            ed_phone.setError("Please Enter Valid Phone Number");
            valid = false;
        }
        if(address.isEmpty()){
            ed_address.setError("Please Enter Address");
            valid = false;
        }
        return valid;
    }

    public void initialized(){
        name    = ed_name.getText().toString().trim();
        phone   = ed_phone.getText().toString().trim();
        email   = ed_email.getText().toString().trim();
        address = ed_address.getText().toString().trim();
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