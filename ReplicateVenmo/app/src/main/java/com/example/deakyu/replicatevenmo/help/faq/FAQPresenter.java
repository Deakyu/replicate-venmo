package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQPresenter implements FAQContract.Presenter, FAQContract.Interactor.OnFinishedListener {

    private FAQContract.View view;
    private FAQContract.Interactor interactor;

    private List<Category> currentCategories;

    public FAQPresenter(FAQContract.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void bind(FAQContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void fetchCategoriesFromServer(int networkStatus) {
        if(networkStatus == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            if(view != null) view.onNetworkNotConnected("Internet Not Available!");
        } else {
            interactor.getCategories(this);
        }
    }

    @Override
    public void onTopicItemClicked(String topic, String description) {
        if(view != null) view.startFAQDescriptionActivity(topic, description);
    }

    @Override
    public void onFinished(List<Category> categories) {
        currentCategories = flattenCategories(categories);
        if(view != null) view.updateUiCategories(flattenCategories(categories));
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    public List<Category> getCachedCategories() { return currentCategories; }

    public List<Category> flattenCategories(List<Category> categories) {
        List<Topic> empty = new ArrayList<>();
        empty.add(new Topic(1, 1, "empty", "emtpy"));

        List<Category> flattened = new ArrayList<>();

        for(int i=0 ; i < categories.size() ; i++) {
            Category tmp = categories.get(i);
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
}
