package com.example.qatramvvm.data;

import com.example.qatramvvm.pojo.CasesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasesClient {
    private static final String BASE_URL = "http://192.168.43.29:8000/api/";
    private CasesInterface casesInterface;
    private static CasesClient instance;

    public CasesClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        casesInterface = retrofit.create(CasesInterface.class);
    }

    public static CasesClient getInstance(){
        if(instance == null){
            instance = new CasesClient();
        }
        return instance;
    }

    public Call<List<CasesModel>> getCases(){
        return casesInterface.getCases();
    }
}
