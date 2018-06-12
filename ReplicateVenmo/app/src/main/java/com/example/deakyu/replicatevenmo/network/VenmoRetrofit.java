package com.example.deakyu.replicatevenmo.network;

import com.example.deakyu.replicatevenmo.VenmoAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VenmoRetrofit {
    private static VenmoRetrofit instance = null;
    public static final String BASE_URL = "http://5b1727a7f5c9b700145511cc.mockapi.io/facevenmodata/v1/";

    private VenmoAPIService service;

    public static VenmoRetrofit getInstance() {
        if(instance == null) {
            synchronized (VenmoRetrofit.class) {
                if(instance == null) {
                    instance = new VenmoRetrofit();
                }
            }
        }
        return instance;
    }

    private VenmoRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(VenmoAPIService.class);
    }

    public VenmoAPIService getVenmoService() {
        return this.service;
    }
}
