package com.example.deakyu.replicatevenmo.help.contactus.chat;

public interface IChatPresenter {
    void bind(IChatActivity view);
    void unbind();
    void insertChat(Chat chat, int networkStatus);
}
