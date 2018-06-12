package com.example.deakyu.replicatevenmo.help.faq;

import java.util.List;

import retrofit2.Call;

public interface IFAQInteractor {
    Call<List<Category>> getCategories();
}
