package com.example.deakyu.replicatevenmo.help.faq;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FAQInteractor implements FAQContract.Interactor{

    private VenmoAPIService service;

    public FAQInteractor() {
        this.service = VenmoRetrofit.getInstance().getVenmoService();
    }

    @Override
    public void getCategories(OnFinishedListener onFinishedListener) {
        service.getCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        onFinishedListener.onFinished(flatten(categories));
                    }
                });
    }

    private List<Category> flatten(List<Category> categories) {
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
