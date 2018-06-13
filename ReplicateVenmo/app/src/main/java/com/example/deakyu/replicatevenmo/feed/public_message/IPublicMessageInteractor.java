package com.example.deakyu.replicatevenmo.feed.public_message;

import java.util.List;

import rx.Observable;

public interface IPublicMessageInteractor {
    Observable<List<Message>> getMessasge();
}
