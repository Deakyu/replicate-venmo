package com.example.deakyu.replicatevenmo.help.contactus.chat;

public interface IChatActivity {
    void onNetworkNotConnected(String message);
    void redirectToContactUsWithToast(String message);
}
