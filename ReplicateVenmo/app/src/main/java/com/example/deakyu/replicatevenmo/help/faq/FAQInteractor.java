package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import java.util.List;

import retrofit2.Call;

public class FAQInteractor implements IFAQInteractor {

    private VenmoAPIService service;

    public FAQInteractor(VenmoAPIService service) {
        this.service = service;
    }

    @Override
    public Call<List<Category>> getCategories() {
        return service.getCategories();
    }

}
