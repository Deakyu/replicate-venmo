package com.example.deakyu.replicatevenmo;

import com.example.deakyu.replicatevenmo.feed.public_message.Message;
import com.example.deakyu.replicatevenmo.help.contactus.chat.Chat;
import com.example.deakyu.replicatevenmo.help.faq.Category;
import com.example.deakyu.replicatevenmo.notification.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Completable;
import rx.Observable;

public interface VenmoAPIService {
    @GET("notifications")
    Observable<List<Notification>> getNotifications();

    @PUT("notifications/{id}")
    Observable<Notification> updateNotification(@Path("id") int id, @Body Notification notification);

    @GET("faqs")
    Call<List<Category>> getCategories();

    @POST("chats")
    Call<Chat> insertChat(@Body Chat chat);

    @GET("messages")
    Observable<List<Message>> getMessages();

    @PUT("messages/{id}")
    Completable likeMessage(@Path("id") int id, @Body Message message);
}
