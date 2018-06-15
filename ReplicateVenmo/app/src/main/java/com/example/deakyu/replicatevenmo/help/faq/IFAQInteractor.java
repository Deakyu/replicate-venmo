package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.VenmoAPIService;

import java.util.List;

import retrofit2.Call;

public interface IFAQInteractor {
    Call<List<Category>> getCategories();
}
