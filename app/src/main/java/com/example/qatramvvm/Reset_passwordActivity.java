package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Reset_passwordActivity extends AppCompatActivity {
    EditText ed_new_password, ed_confirm_password;
    String new_password, confirm_password, contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setTitle(R.string.reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_new_password     = findViewById(R.id.new_password);
        ed_confirm_password = findViewById(R.id.confirm_password);
        contact             = getIntent().getStringExtra("contact");
    }

    public void reset_password(View view) {
        initialize();
        if(!validate()){

        }
        else {
            if(!new_password.equals(confirm_password))
            {
                Toast.makeText(this, "عذراً كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
            }
            else{
                final ProgressDialog progressDialog = new ProgressDialog(Reset_passwordActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("يتم تعيين كلمة المرور ...");
                progressDialog.show();

                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(Reset_passwordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Reset_passwordActivity.this, "لم يتم تغيير كلمة المرور", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };
                Reset_Password reset_password = new Reset_Password(new_password, contact, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Reset_passwordActivity.this);
                queue.add(reset_password);
            }
        }
    }

    public boolean validate(){
        boolean valid = true;
        if(confirm_password.isEmpty()){
            ed_confirm_password.setError("Please Enter Old Password");
            valid = false;
        }
        if(new_password.isEmpty()){
            ed_new_password.setError("Please Enter New Password");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        new_password     = ed_new_password.getText().toString().trim();
        confirm_password = ed_confirm_password.getText().toString().trim();
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