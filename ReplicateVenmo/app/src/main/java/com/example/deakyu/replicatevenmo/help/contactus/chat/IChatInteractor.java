package com.example.deakyu.replicatevenmo.help.contactus.chat;

import retrofit2.Call;

public interface IChatInteractor {
    Call<Chat> insertChat(Chat chat);
}
