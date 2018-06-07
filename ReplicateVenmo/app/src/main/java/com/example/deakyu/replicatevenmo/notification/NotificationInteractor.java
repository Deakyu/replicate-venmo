package com.example.deakyu.replicatevenmo.notification;

import java.util.List;

import rx.Observable;

public interface NotificationInteractor {

    Observable<List<Notification>> getNotifications();

    Observable<Notification> updateNotification(int id, Notification notification);

}
