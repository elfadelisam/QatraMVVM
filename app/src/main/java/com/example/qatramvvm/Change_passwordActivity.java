package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Change_passwordActivity extends AppCompatActivity {
    EditText ed_old_password, ed_new_password;
    String old_password, new_password;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setTitle(R.string.change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_old_password = findViewById(R.id.old_password);
        ed_new_password= findViewById(R.id.new_password);
    }

    public void change_password(View view) {
        intialize();
        if(!validate()){

        }
        else {
            // Session class instance
            session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();
            final String phone = user.get(SessionManager.KEY_PHONE);
            final String password = user.get(SessionManager.KEY_PASSWORD);

            final ProgressDialog progressDialog = new ProgressDialog(Change_passwordActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("يتم تعديل البيانات ...");
            progressDialog.show();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            progressDialog.dismiss();
                            Toast.makeText(Change_passwordActivity.this, "تم تغيير كلمة المرور بنجاح .. يرجى إعادة تسجيل الدخول", Toast.LENGTH_LONG).show();
                            session.logoutUser();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Change_passwordActivity.this, "لم يتم تغيير كلمة المرور", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Change_Password change_password = new Change_Password(new_password, old_password, phone, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Change_passwordActivity.this);
            queue.add(change_password);
        }
    }

    public boolean validate(){
        boolean valid = true;
        if(old_password.isEmpty()){
            ed_old_password.setError("Please Enter Old Password");
            valid = false;
        }
        if(new_password.isEmpty()){
            ed_new_password.setError("Please Enter New Password");
            valid = false;
        }
        return valid;
    }

    public void intialize(){
        old_password = ed_old_password.getText().toString().trim();
        new_password = ed_new_password.getText().toString().trim();
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