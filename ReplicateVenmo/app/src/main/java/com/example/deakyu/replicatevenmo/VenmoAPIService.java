package com.example.deakyu.replicatevenmo;

import com.example.deakyu.replicatevenmo.notification.Notification;
import com.example.deakyu.replicatevenmo.notification.RemoteNotification;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface VenmoAPIService {
    @GET("notifications")
    Observable<List<Notification>> getNotifications();
}
