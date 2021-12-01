package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsDonors extends AppCompatActivity {
    SessionManager session;
    TextView txtBloodType, txtName, txtEmail, txtPhone, txtAddress, txtAge, txtLastDonationDate, txtGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donors_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtBloodType = findViewById(R.id.txtBloodType);
        txtName      = findViewById(R.id.name);
        txtEmail     = findViewById(R.id.email);
        txtPhone     = findViewById(R.id.phone);
        txtAddress   = findViewById(R.id.address);
        txtAge       = findViewById(R.id.age);
        txtGender    = findViewById(R.id.gender);
        txtLastDonationDate = findViewById(R.id.txt_last_donation_date);

        txtBloodType.setText(getIntent().getStringExtra("blood_type"));
        txtName.setText(getIntent().getStringExtra("donor_name"));
        txtEmail.setText(getIntent().getStringExtra("email"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtAddress.setText(getIntent().getStringExtra("address"));
        txtAge.setText(getIntent().getStringExtra("age"));
        txtLastDonationDate.setText(getIntent().getStringExtra("last_donation_date"));
        txtGender.setText(getIntent().getStringExtra("gender"));
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