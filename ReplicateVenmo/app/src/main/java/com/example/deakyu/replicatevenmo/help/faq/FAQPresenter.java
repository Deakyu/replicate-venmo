package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.ArrayList;
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
    public void getCategoriesFromServer(int networkStatus) {
        if(networkStatus == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            if(view != null) {
                view.onNetworkNotConnected("Internet Not Available!");
            }
        } else {
            interactor.getCategories().enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if(view != null) {
                        currentCategories = response.body();
                        view.updateUiCategories(getCurrentCategories());
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public List<Category> getCurrentCategories() {
        List<Topic> empty = new ArrayList<>();
        empty.add(new Topic(1, 1, "empty", "emtpy"));

        List<Category> flattened = new ArrayList<>();

        for(int i=0 ; i < currentCategories.size() ; i++) {
            Category tmp = currentCategories.get(i);
            List<Topic> topics = tmp.getTopics();
            flattened.add(new Category(tmp.getId(), tmp.getCategory(), empty));

            for(int j=0 ; j < topics.size() ; j++) {
                List<Topic> secondTmp = new ArrayList<>();
                secondTmp.add(topics.get(j));
                flattened.add(new Category(tmp.getId(), tmp.getCategory(), secondTmp));
            }

        }
        return flattened;
    }

    @Override
    public void onTopicItemClicked(String topic, String description) {
        if(view != null) view.startFAQDescriptionActivity(topic, description);
    }
}
