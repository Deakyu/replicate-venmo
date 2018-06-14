package com.example.deakyu.replicatevenmo.feed.public_message;

import java.util.List;

import rx.Completable;
import rx.Observable;

public interface IPublicMessageInteractor {
    Observable<List<Message>> getMessasge();
    Completable likeMessage(int id, Message message);
    Completable insertComment(int id, Comment comment);
}
