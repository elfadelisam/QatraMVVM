package com.example.qatramvvm.ui.donors;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.qatramvvm.R;
import com.example.qatramvvm.pojo.DonorsModel;

import java.util.ArrayList;
import java.util.List;

public class DonorsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private DonorsAdapter adapter;
    private List<DonorsModel> donorsModelList;
    public SwipeRefreshLayout swipeRefreshLayout;
    DonorsViewModel donorsViewModel;

    public DonorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View donors = inflater.inflate(R.layout.fragment_donors, container, false);

        setHasOptionsMenu(true);

        donorsViewModel = new ViewModelProvider(this).get(DonorsViewModel.class);
        donorsViewModel.getDonors();

        donorsModelList = new ArrayList<>();
        recyclerView = donors.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = donors.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(DonorsFragment.this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        donorsViewModel.donorsMutableLiveData.observe(getViewLifecycleOwner(), donorsModels -> {
            adapter = new DonorsAdapter(donorsModels, getContext());
            recyclerView.setAdapter(adapter);
        });


        return donors;
    }

    @Override
    public void onRefresh() {
        donorsModelList.clear();
        //loadRecyclerViewData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

}