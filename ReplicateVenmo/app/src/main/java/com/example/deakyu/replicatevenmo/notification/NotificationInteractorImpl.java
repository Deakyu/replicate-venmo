package com.example.deakyu.replicatevenmo.notification;

import com.example.deakyu.replicatevenmo.VenmoAPIService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

public class NotificationInteractorImpl implements NotificationInteractor {

    private VenmoAPIService service;

    public NotificationInteractorImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://5b1727a7f5c9b700145511cc.mockapi.io/facevenmodata/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(VenmoAPIService.class);
    }

    @Override
    public Observable<List<Notification>> getNotifications() {
        return service.getNotifications().subscribeOn(Schedulers.io());
    }
}
