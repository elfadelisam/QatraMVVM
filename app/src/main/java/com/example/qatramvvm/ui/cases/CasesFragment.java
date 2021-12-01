package com.example.qatramvvm.ui.cases;

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
import com.example.qatramvvm.SessionManager;

public class CasesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private CasesAdapter adapter;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager session;
    CasesViewModel casesViewModel;

    public CasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cases= inflater.inflate(R.layout.fragment_cases, container, false);

        setHasOptionsMenu(true);

        casesViewModel = new ViewModelProvider(this).get(CasesViewModel.class);
        casesViewModel.getCases();

        recyclerView = cases.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = cases.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(CasesFragment.this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        casesViewModel.casesMutableLiveData.observe(getViewLifecycleOwner(), casesModels -> {
            adapter = new CasesAdapter(casesModels, getContext());
            recyclerView.setAdapter(adapter);
        });

        return cases;
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

    @Override
    public void onRefresh() {
        //casesModelList.clear();
        //loadRecyclerViewData();
    }
}