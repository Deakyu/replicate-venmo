package com.example.deakyu.replicatevenmo.notification;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class NotificationViewModel {

    private NotificationInteractor interactor;
    private Scheduler scheduler;

    public NotificationViewModel(NotificationInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }

    public Observable<List<Notification>> getNotifications() {
        return interactor.getNotifications().observeOn(scheduler);
    }

    public Observable<Notification> updateNotification(int id, Notification notification) {
        return interactor.updateNotification(id, notification);
    }

}
