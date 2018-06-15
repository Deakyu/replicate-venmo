package com.example.deakyu.replicatevenmo.help.contactus.chat;

import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPresenter implements ChatContract.Presenter, ChatContract.Interactor.OnFinishedListener {

    private ChatContract.View view;
    private ChatContract.Interactor interactor;
    private Chat inserted;

    public ChatPresenter(ChatContract.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void bind(ChatContract.View view) {
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
            interactor.insertChat(this, chat);
        }
    }

    @Override
    public void onFinished(Chat chat) {
        if(view != null) {
            inserted = chat;
            view.redirectToContactUsWithToast("Your chat has been recorded!");
        }
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
