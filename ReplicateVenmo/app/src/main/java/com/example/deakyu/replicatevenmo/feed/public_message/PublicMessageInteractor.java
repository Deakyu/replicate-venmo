package com.example.deakyu.replicatevenmo.feed.public_message;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class PublicMessageInteractor implements IPublicMessageInteractor {

    private VenmoAPIService service;

    public PublicMessageInteractor() {
        this.service = VenmoRetrofit.getInstance().getVenmoService();
    }

    @Override
    public Observable<List<Message>> getMessasge() {
        return service.getMessages().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable likeMessage(int id, Message message) {
        return service.likeMessage(id, message).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertComment(int id, Comment comment) {
        return service.insertComment(id, comment).subscribeOn(Schedulers.io());
    }
}
