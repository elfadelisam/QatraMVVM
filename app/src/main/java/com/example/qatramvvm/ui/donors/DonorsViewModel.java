package com.example.qatramvvm.ui.donors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qatramvvm.data.DonorsClient;
import com.example.qatramvvm.pojo.DonorsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorsViewModel extends ViewModel {
    MutableLiveData<List<DonorsModel>> donorsMutableLiveData = new MutableLiveData<>();

    public void getDonors(){
        DonorsClient.getInstance().getDonors().enqueue(new Callback<List<DonorsModel>>() {
            @Override
            public void onResponse(Call<List<DonorsModel>> call, Response<List<DonorsModel>> response) {
                donorsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DonorsModel>> call, Throwable t) {

            }
        });
    }
}
