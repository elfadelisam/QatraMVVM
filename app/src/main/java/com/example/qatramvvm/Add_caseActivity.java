package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.qatramvvm.ui.cases.CasesFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class Add_caseActivity extends AppCompatActivity {
    public String blood_type, gender;
    SessionManager session;
    String[] blood_types ={"الرجاء إختيار الفصيلة","A+","A-","B+","B-","AB+","AB-","O+","O-"};
    EditText ed_name, ed_desc, ed_phone, ed_hospital, ed_age, ed_num;
    TextView txt_date;
    String name, phone, description, hospital, age, date, num;
    RadioButton male, female;
    RadioGroup radioGroup;
    final Calendar c= Calendar.getInstance();
    final int year=c.get(Calendar.YEAR);
    final int month=c.get(Calendar.MONTH);
    final int day=c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        setTitle(R.string.add_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        //session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String phone = user.get(SessionManager.KEY_PHONE);
        String password = user.get(SessionManager.KEY_PASSWORD);

        spinner();

        ed_name = findViewById(R.id.name);
        ed_phone = findViewById(R.id.phone);
        ed_desc = findViewById(R.id.description);
        ed_hospital= findViewById(R.id.hospital);
        ed_age = findViewById(R.id.age);
        ed_num = findViewById(R.id.number);
        radioGroup = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        txt_date= findViewById(R.id.vol_date);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Add_caseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear+1;
                                txt_date.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                            }
                        },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.setTitle("Select date");
                datePickerDialog.show();
            }
        });

    }

    public <ViewGroup> void spinner(){
        Spinner spinner= findViewById(R.id.type);
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

    public void add_case(View view) {
        intialize();
        if(!validate()){

        }
        else
        {
            final ProgressDialog progressDialog=new ProgressDialog(Add_caseActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("يتم تسجيل البيانات ...");
            progressDialog.show();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            progressDialog.dismiss();
                            Toast.makeText(Add_caseActivity.this, "تم تسجيل البيانات بنجاح", Toast.LENGTH_LONG).show();
                            FragmentManager fm = getSupportFragmentManager();
                            CasesFragment casesFragment = new CasesFragment();
                            fm.beginTransaction().replace(R.id.nav_host_fragment,casesFragment).commit();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Add_caseActivity.this, "لم يتم تسجيل البيانات ", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Add_Case add_case = new Add_Case(name, description, phone, blood_type, hospital, age, gender, date, num, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Add_caseActivity.this);
            queue.add(add_case);
        }
    }

    public boolean validate(){
        boolean valid = true;
        if(name.isEmpty() || name.length()<2){
            ed_name.setError("name at least 2 characters");
            valid = false;
        }
        if(phone.isEmpty() || phone.length()<10){
            ed_phone.setError("please enter valid phone number");
            valid = false;
        }
        if(date.isEmpty()){
            txt_date.setError("please enter a date");
            valid = false;
        }
        if(hospital.isEmpty()){
            ed_hospital.setError("please enter hospital");
            valid = false;
        }
        return valid;
    }

    public void intialize(){
        name = ed_name.getText().toString().trim();
        phone = ed_phone.getText().toString().trim();
        description = ed_desc.getText().toString().trim();
        hospital = ed_hospital.getText().toString().trim();
        age = ed_age.getText().toString().trim();
        date = txt_date.getText().toString().trim();
        num = ed_num.getText().toString().trim();

        if(male.isChecked())
        {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            male = findViewById(selectedId);
            gender = (String) male.getText();
        }
        else {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            female = findViewById(selectedId);
            gender = (String) female.getText();
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