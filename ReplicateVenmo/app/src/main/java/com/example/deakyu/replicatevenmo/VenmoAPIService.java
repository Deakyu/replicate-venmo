package com.example.deakyu.replicatevenmo;

import com.example.deakyu.replicatevenmo.notification.Notification;
import com.example.deakyu.replicatevenmo.notification.RemoteNotification;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface VenmoAPIService {
    @GET("notifications")
    Observable<List<Notification>> getNotifications();

    @PUT("notifications/{id}")
    Observable<Notification> updateNotification(@Path("id") int id, @Body Notification notification);
}
