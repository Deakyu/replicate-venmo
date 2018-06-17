package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FAQPresenter implements FAQContract.Presenter, FAQContract.Interactor.OnFinishedListener{

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
    public void unbind() { this.view = null; }

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
        currentCategories = categories;
        if(view != null) view.updateUiCategories(categories);
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    public List<Category> getCachedCategories() { return currentCategories; }

}
