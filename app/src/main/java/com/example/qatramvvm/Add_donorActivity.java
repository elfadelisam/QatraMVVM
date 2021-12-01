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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.qatramvvm.data.DonorInterface;
import com.example.qatramvvm.ui.donors.DonorsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_donorActivity extends AppCompatActivity {

    public String blood_type, gender;
    SessionManager session;
    String[] blood_types ={"الرجاء إختيار الفصيلة","A+","A-","B+","B-","AB+","AB-","O+","O-"};
    EditText ed_name, ed_email, ed_phone, ed_address, ed_age;
    TextView txt_last_donation_date;
    String name, email, phone, address, age, last_donation_date;
    RadioButton male, female;
    RadioGroup radioGroup;
    final Calendar c= Calendar.getInstance();
    final int year=c.get(Calendar.YEAR);
    final int month=c.get(Calendar.MONTH);
    final int day=c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);
        setTitle(R.string.add_donor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner();

        ed_name                = findViewById(R.id.name);
        ed_phone               = findViewById(R.id.phone);
        ed_email               = findViewById(R.id.email);
        ed_address             = findViewById(R.id.address);
        ed_age                 = findViewById(R.id.age);
        txt_last_donation_date = findViewById(R.id.txt_last_donation_date);
        radioGroup             = findViewById(R.id.gender);
        male                   = findViewById(R.id.male);
        female                 = findViewById(R.id.female);

        txt_last_donation_date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(Add_donorActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        monthOfYear = monthOfYear + 1;
                        txt_last_donation_date.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    },year,month,day);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            datePickerDialog.setTitle("Select date");
            datePickerDialog.show();
        });
    }

    public void add_donor(View view) {
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
        initialize();
        if(!validate()){

        }
        else {
            session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
            HashMap<String, String> userdata = session.getUserData();
            String user_id = userdata.get(SessionManager.KEY_USER_ID);

            final ProgressDialog progressDialog = new ProgressDialog(Add_donorActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("يتم تسجيل البيانات ...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.29:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DonorInterface donorInterface = retrofit.create(DonorInterface.class);

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    progressDialog.dismiss();
                    if (success) {
                        Toast.makeText(Add_donorActivity.this, "تم تسجيل البيانات بنجاح", Toast.LENGTH_LONG).show();
                        FragmentManager fm = getSupportFragmentManager();
                        DonorsFragment donorsFragment = new DonorsFragment();
                        fm.beginTransaction().replace(R.id.nav_host_fragment,donorsFragment).commit();
                    } else {
                        Toast.makeText(Add_donorActivity.this, "لم يتم تسجيل البيانات", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            Add_Donor add_donor = new Add_Donor(name, email, phone, blood_type, address, age, gender, last_donation_date, user_id, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Add_donorActivity.this);
            queue.add(add_donor);
        }

    }

    public boolean validate(){
        boolean valid = true;
        if(address.isEmpty()){
            ed_address.setError("Please Enter address");
            valid = false;
        }
        if(phone.isEmpty() || phone.length()<10){
            ed_phone.setError("Please Enter valid phone number");
            valid = false;
        }
        if(last_donation_date.isEmpty()){
            txt_last_donation_date.setError("Please Enter a date");
            valid = false;
        }
        if(age.isEmpty()){
            ed_age.setError("Please Enter age");
            valid = false;
        }
        return valid;
    }

    public void initialize(){
        name               = ed_name.getText().toString().trim();
        phone              = ed_phone.getText().toString().trim();
        email              = ed_email.getText().toString().trim();
        address            = ed_address.getText().toString().trim();
        age                = ed_age.getText().toString().trim();
        last_donation_date = txt_last_donation_date.getText().toString().trim();
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

    public <ViewGroup> void spinner(){
        Spinner spinner= (Spinner) findViewById(R.id.type);
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
}