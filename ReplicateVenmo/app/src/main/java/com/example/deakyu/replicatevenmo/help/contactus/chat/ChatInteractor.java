package com.example.deakyu.replicatevenmo.help.contactus.chat;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatInteractor implements ChatContract.Interactor {

    private VenmoAPIService service;

    public ChatInteractor() {
        this.service = VenmoRetrofit.getInstance().getVenmoService();
    }

    @Override
    public void insertChat(OnFinishedListener onFinishedListener, Chat chat) {
        service.insertChat(chat).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                onFinishedListener.onFinished(chat);
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
