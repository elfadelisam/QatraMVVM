package com.example.qatramvvm.data;

import com.example.qatramvvm.pojo.CasesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CasesInterface {
    @GET("cases")
    Call<List<CasesModel>> getCases();
}
