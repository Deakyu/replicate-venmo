package com.example.deakyu.replicatevenmo.help.faq;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQPresenter implements IFAQPresenter {

    private IFAQActivity view;
    private IFAQInteractor interactor;
    private List<Category> currentCategories;

    public FAQPresenter(IFAQInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void bind(IFAQActivity view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void getCategoriesFromServer() {
        interactor.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(view != null) {
                    currentCategories = response.body();
                    view.updateUiCategories(currentCategories);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public List<Category> getCurrentCategories() {
        return currentCategories;
    }
}
