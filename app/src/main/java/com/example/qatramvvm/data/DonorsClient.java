package com.example.qatramvvm.data;

import com.example.qatramvvm.pojo.DonorsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonorsClient {
    private static final String BASE_URL = "http://192.168.43.29:8000/api/";
    private DonorInterface donorInterface;
    private static DonorsClient instance;

    public DonorsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        donorInterface = retrofit.create(DonorInterface.class);
    }

    public static DonorsClient getInstance(){
        if(instance == null){
            instance = new DonorsClient();
        }
        return instance;
    }

    public Call<List<DonorsModel>> getDonors(){
        return donorInterface.getDonors();
    }

}
