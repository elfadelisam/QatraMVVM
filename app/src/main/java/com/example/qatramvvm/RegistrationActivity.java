package com.example.qatramvvm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    String[] blood_types ={"الرجاء إختيار الفصيلة","A+","A-","B+","B-","AB+","AB-","O+","O-"};
    EditText ed_name, ed_phone, ed_password, ed_email, ed_address;
    String name, phone, password, email, address;
    public String blood_type;
    Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle(R.string.create_account);

        ed_name     =  findViewById(R.id.name);
        ed_phone    =  findViewById(R.id.phone);
        ed_password =  findViewById(R.id.password);
        ed_email    =  findViewById(R.id.email);
        ed_address  =  findViewById(R.id.address);

        spinner();
    }

    public <ViewGroup> void spinner(){
        Spinner spinner = findViewById(R.id.group);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.color_spinner_layout,blood_types){
            public View getView(int position, View convertView, android.view.ViewGroup parent){
                TextView tv=(TextView)super.getView(position, convertView, parent);
                return tv;
            }
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent){
                TextView tv=(TextView)super.getView(position, convertView, parent);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(14);
                return tv;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                blood_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void registration(View view) {
        initialize();
        if(!validate()){

        }
        else {
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        create_account();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "يوجد خطأ .. أو رقم الهاتف مسجل به مسبقاً", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Registration registration = new Registration(name, phone, password, email, address, blood_type, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
            queue.add(registration);
        }
    }

    private void create_account(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View create_account = getLayoutInflater().inflate(R.layout.create_account,null);
        btn_ok = create_account.findViewById(R.id.btn_ok);
        alert.setView(create_account);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_ok.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        });
        alertDialog.show();
    }

    public void login(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login),"transition_login");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this,pairs);
        startActivity(intent, options.toBundle());
    }

    public boolean validate(){
        boolean valid = true;
        if(name.isEmpty() || name.length()<3){
            ed_name.setError("name at least 3 characters");
            valid = false;
        }
        if(phone.isEmpty() || phone.length()<10){
            ed_phone.setError("Please Enter Valid Phone Number");
            valid = false;
        }
        if(password.isEmpty()){
            ed_password.setError("Please Enter Password");
            valid = false;
        }
        if(address.isEmpty()){
            ed_address.setError("Please Enter Address");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        name     = ed_name.getText().toString().trim();
        phone    = ed_phone.getText().toString().trim();
        password = ed_password.getText().toString().trim();
        email    = ed_email.getText().toString().trim();
        address  = ed_address.getText().toString().trim();
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