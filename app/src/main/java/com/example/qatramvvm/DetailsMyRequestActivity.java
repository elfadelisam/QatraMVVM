package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsMyRequestActivity extends AppCompatActivity {
    TextView txtBloodType, txtName, txtHospital, txtPhone, txtDesc, txtAge, txtDate, txtGender, txtNum, txtDonorName;
    String case_phone, case_id, donorName, donorPhone;
    LinearLayout linearDonor;
    Button contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_my_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtBloodType = findViewById(R.id.txtBloodType);
        txtName      = findViewById(R.id.name);
        txtHospital  = findViewById(R.id.hospital);
        txtPhone     = findViewById(R.id.phone);
        txtDesc      = findViewById(R.id.description);
        txtAge       = findViewById(R.id.age);
        txtDate      = findViewById(R.id.date);
        txtGender    = findViewById(R.id.gender);
        txtNum       = findViewById(R.id.number);
        txtDonorName = findViewById(R.id.donor_name);
        contact      = findViewById(R.id.contact);
        linearDonor  = findViewById(R.id.linearDonor);

        txtBloodType.setText(getIntent().getStringExtra("blood_type"));
        txtName.setText(getIntent().getStringExtra("case_name"));
        txtHospital.setText(getIntent().getStringExtra("hospital"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtDesc.setText(getIntent().getStringExtra("description"));
        txtAge.setText(getIntent().getStringExtra("age"));
        txtDate.setText(getIntent().getStringExtra("date"));
        txtGender.setText(getIntent().getStringExtra("gender"));
        txtNum.setText(getIntent().getStringExtra("num"));

        case_phone = getIntent().getStringExtra("phone");
        case_id = getIntent().getStringExtra("case_id");

        get_donor();

        contact.setOnClickListener(view -> {
            Intent phone= new Intent(Intent.ACTION_DIAL);
            phone.setData(Uri.parse("tel:"+donorPhone));
            if(phone.resolveActivity(getPackageManager())!= null)
                startActivity(phone);
        });
    }

    public void get_donor(){
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/get_donor.php?case_id="+case_id;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url_data ,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array= jsonObject.getJSONArray("donor");
                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject o =array.getJSONObject(i);
                            donorName = o.getString("donor_name");
                            donorPhone = o.getString("phone");
                            txtDonorName.setText(donorName);
                            if (!donorName.isEmpty()){
                                linearDonor.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsMyRequestActivity.this);
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
}