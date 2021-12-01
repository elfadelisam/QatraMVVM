package com.example.qatramvvm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qatramvvm.R;
import com.example.qatramvvm.SessionManager;
import com.example.qatramvvm.pojo.CasesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private CaseAdapter adapter;
    private List<CasesModel> casesModelList;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager session;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View group = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

        recyclerView = group.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = group.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(HomeFragment.this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        casesModelList = new ArrayList<>();
        loadRecyclerViewData();

        return group;
    }

    private void loadRecyclerViewData() {
        session = new SessionManager(getContext(), SessionManager.SESSION_USER_SESSION);
        session.checkLogin();
        HashMap<String, String> userdata = session.getUserData();
        String type = userdata.get(SessionManager.KEY_GROUP);
        final String URL_DATA="http://192.168.43.29:8081/bloodbank/admin/case.php?type="+type;
        //final String URL_DATA="https://qatradam.000webhostapp.com/admin/case.php?type="+type;
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("cases");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                CasesModel casesModel = new CasesModel(
                                        o.getString("case_id"),
                                        o.getString("case_name"),
                                        o.getString("hospital"),
                                        o.getString("blood_type"),
                                        o.getString("phone"),
                                        o.getString("description"),
                                        o.getString("age"),
                                        o.getString("gender"),
                                        o.getString("date"),
                                        o.getString("num")
                                );
                                casesModelList.add(casesModel);
                            }
                            adapter = new CaseAdapter(casesModelList, getContext());
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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
        casesModelList.clear();
        loadRecyclerViewData();
    }
}
