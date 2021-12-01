package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MakeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        setTitle(R.string.make_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void viaSMS(View view) {
        startActivity(new Intent(MakeSelectionActivity.this, ResetPasswordViaSmsActivity.class));
    }

    public void viaMail(View view) {
        startActivity(new Intent(MakeSelectionActivity.this, ResetPasswordViaEmailActivity.class));
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