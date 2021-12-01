package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class GuidelinesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.guidelines);

        TextView guideline = findViewById(R.id.guideline);
        String guide = getIntent().getStringExtra("guide");
        String guidelines = getIntent().getStringExtra("guidelines");

        guideline.setText(guide);
        guideline.setText(guidelines);
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