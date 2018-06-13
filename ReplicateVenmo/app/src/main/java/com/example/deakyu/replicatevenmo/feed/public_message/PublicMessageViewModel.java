package com.example.deakyu.replicatevenmo.feed.public_message;

import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.Scheduler;

public class PublicMessageViewModel {
    private IPublicMessageInteractor interactor;
    private Scheduler scheduler;

    public PublicMessageViewModel(IPublicMessageInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }

    public Observable<List<Message>> getMessages() {
        return interactor.getMessasge().observeOn(scheduler);
    }

    public Completable likedMessage(int id, Message message) {
        return interactor.likeMessage(id, message).observeOn(scheduler);
    }
}
