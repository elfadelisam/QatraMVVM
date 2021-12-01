package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qatramvvm.pojo.CasesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRequestsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    String phone, password;
    private MyRequestsAdapter adapter;
    private List<CasesModel> casesModelList;
    public SwipeRefreshLayout swipeRefreshLayout;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        //session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        phone = user.get(SessionManager.KEY_PHONE);
        password = user.get(SessionManager.KEY_PASSWORD);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(MyRequestsActivity.this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        casesModelList = new ArrayList<>();
        loadRecyclerViewData();

    }

    private void loadRecyclerViewData() {
        String url_data = "http://192.168.43.29:8081/bloodbank/admin/my_requests.php?phone="+phone;
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url_data,
                response -> {
                    swipeRefreshLayout.setRefreshing(false);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("my_requests");
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
                        adapter = new MyRequestsAdapter(casesModelList, MyRequestsActivity.this);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                error -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MyRequestsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(MyRequestsActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        casesModelList.clear();
        loadRecyclerViewData();
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