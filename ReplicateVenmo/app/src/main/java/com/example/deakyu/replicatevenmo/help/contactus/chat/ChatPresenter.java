package com.example.deakyu.replicatevenmo.help.contactus.chat;

import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPresenter implements IChatPresenter {

    private IChatActivity view;
    private IChatInteractor interactor;
    private Chat inserted;

    public ChatPresenter(IChatInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void bind(IChatActivity view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void insertChat(Chat chat, int networkStatus) {
        if(networkStatus == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            if(view != null) {
                view.onNetworkNotConnected("Internet Not Available!");
            }
        } else {
            interactor.insertChat(chat).enqueue(new Callback<Chat>() {
                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {
                    if(view != null) {
                        inserted = response.body();
                        view.redirectToContactUsWithToast("Your chat has been recorded!");
                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
