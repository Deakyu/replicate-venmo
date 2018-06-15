package com.example.deakyu.replicatevenmo.help.contactus.chat;

import retrofit2.Call;

public interface ChatContract {
    interface View {
        void onNetworkNotConnected(String message);
        void redirectToContactUsWithToast(String message);
    }

    interface Presenter {
        void bind(ChatContract.View view);
        void unbind();
        void insertChat(Chat chat, int networkStatus);
    }

    interface Interactor {

        interface OnFinishedListener {
            void onFinished(Chat chat);
            void onFailure(Throwable t);
        }

        void insertChat(OnFinishedListener onFinishedListener, Chat chat);
    }
}
