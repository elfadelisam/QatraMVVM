package com.example.qatramvvm.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.qatramvvm.ui.donors.DonorsAdapter;
import com.example.qatramvvm.R;

public class HomeActivity extends AppCompatActivity {
    HomeViewModel homeViewModel;
    DonorsAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getDonors();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        homeViewModel.donorMutableLiveData.observe(this, donorsModels -> {
            adapter = new DonorsAdapter(donorsModels, getApplicationContext());
            recyclerView.setAdapter(adapter);
        });

    }
}