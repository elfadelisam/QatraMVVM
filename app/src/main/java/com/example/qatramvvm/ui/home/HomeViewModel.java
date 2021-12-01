package com.example.qatramvvm.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qatramvvm.data.DonorsClient;
import com.example.qatramvvm.pojo.DonorsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    MutableLiveData<List<DonorsModel>> donorMutableLiveData = new MutableLiveData<>();

    public void getDonors(){
        DonorsClient.getInstance().getDonors().enqueue(new Callback<List<DonorsModel>>() {
            @Override
            public void onResponse(Call<List<DonorsModel>> call, Response<List<DonorsModel>> response) {
                donorMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DonorsModel>> call, Throwable t) {

            }
        });
    }
}
