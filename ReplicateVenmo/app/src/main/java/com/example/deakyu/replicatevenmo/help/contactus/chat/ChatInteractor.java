package com.example.deakyu.replicatevenmo.help.contactus.chat;

import com.example.deakyu.replicatevenmo.VenmoAPIService;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import retrofit2.Call;

public class ChatInteractor implements IChatInteractor {

    private VenmoAPIService service;

    public ChatInteractor() {
        this.service = VenmoRetrofit.getInstance().getVenmoService();
    }

    @Override
    public Call<Chat> insertChat(Chat chat) {
        return service.insertChat(chat);
    }
}
