package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsCases extends AppCompatActivity {
    TextView txtBloodType, txtName, txtHospital, txtPhone, txtDesc, txtAge, txtDate, txtGender, txtNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases_details);

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

        txtBloodType.setText(getIntent().getStringExtra("blood_type"));
        txtName.setText(getIntent().getStringExtra("case_name"));
        txtHospital.setText(getIntent().getStringExtra("hospital"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtDesc.setText(getIntent().getStringExtra("description"));
        txtAge.setText(getIntent().getStringExtra("age"));
        txtDate.setText(getIntent().getStringExtra("date"));
        txtGender.setText(getIntent().getStringExtra("gender"));
        txtNum.setText(getIntent().getStringExtra("num"));
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