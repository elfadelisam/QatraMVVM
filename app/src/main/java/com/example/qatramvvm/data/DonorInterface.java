package com.example.qatramvvm.data;

import com.example.qatramvvm.pojo.DonorsModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DonorInterface {
    @GET("donors")
    Call<List<DonorsModel>> getDonors();

    @POST("donors/create")
    Call<DonorsModel> insertDonor(@Body DonorsModel donorsModel);
}
