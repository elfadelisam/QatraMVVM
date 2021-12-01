package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText ed_phone,ed_password;
    private String phone, password;
    CheckBox rememberMe;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.app_name);

        ed_phone    =  findViewById(R.id.phone);
        ed_password =  findViewById(R.id.password);
        rememberMe  =  findViewById(R.id.remember_me);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_REMEMBER_ME);
        if(sessionManager.checkRememberMe()){
            final HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetails();
            ed_phone.setText(rememberMeDetails.get(SessionManager.KEY_REMEMBERME_PHONE));
            ed_password.setText(rememberMeDetails.get(SessionManager.KEY_REMEMBERME_PASSWORD));
            rememberMe.setChecked(true);
        }
    }

    public void createAccount(View view) {
        Intent intent=new Intent(this,RegistrationActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.signUp),"transition_signUp");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
        startActivity(intent, options.toBundle());
    }

    public void login(View view) {
        initialize();
        if(rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_REMEMBER_ME);
            sessionManager.createRememberMeSession(phone, password);
        }
        else{
            SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_REMEMBER_ME);
            sessionManager.removeRememberMeSession();
        }
        if(!validate()){

        }
        else
        {
            session = new SessionManager(LoginActivity.this, SessionManager.SESSION_USER_SESSION);
            final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("يتم تسجيل الدخول ...");
            progressDialog.show();

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        session.createLoginSession(phone,password);
                        progressDialog.dismiss();
                        GetData();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"لم يتم تسجيل الدخول",Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            };
            Login login = new Login(phone, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(login);
        }
    }

    public void GetData(){
        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        String phone = user.get(SessionManager.KEY_PHONE);
        String password = user.get(SessionManager.KEY_PASSWORD);

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "http://192.168.43.29:8081/bloodbank/admin/profile.php?phone=" + phone + "&password=" + password,
                response -> {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray array= jsonObject.getJSONArray("profiles");

                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject o =array.getJSONObject(i);
                            String user_id = o.getString("user_id");
                            String name = o.getString("name");
                            String phone1 = o.getString("phone");
                            String email = o.getString("email");
                            String address = o.getString("address");
                            String group = o.getString("blood_type");
                            session.createUserDataSession(user_id, name, phone1, email, address, group);
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

    public boolean validate(){
        boolean valid = true;
        if(phone.isEmpty() || phone.length()<10){
            ed_phone.setError("Please Enter valid phone number");
            valid = false;
        }
        if(password.isEmpty()){
            ed_password.setError("Please Enter valid password");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        phone    = ed_phone.getText().toString().trim();
        password = ed_password.getText().toString().trim();
    }

    public void forget_password(View view) {
        startActivity(new Intent(LoginActivity.this, MakeSelectionActivity.class));
    }
}