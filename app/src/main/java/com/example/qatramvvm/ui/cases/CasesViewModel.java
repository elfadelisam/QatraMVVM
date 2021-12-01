package com.example.qatramvvm.ui.cases;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qatramvvm.data.CasesClient;
import com.example.qatramvvm.pojo.CasesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasesViewModel extends ViewModel {
    MutableLiveData<List<CasesModel>> casesMutableLiveData = new MutableLiveData<>();

    public void getCases(){
        CasesClient.getInstance().getCases().enqueue(new Callback<List<CasesModel>>() {
            @Override
            public void onResponse(Call<List<CasesModel>> call, Response<List<CasesModel>> response) {
                casesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CasesModel>> call, Throwable t) {

            }
        });
    }
}
