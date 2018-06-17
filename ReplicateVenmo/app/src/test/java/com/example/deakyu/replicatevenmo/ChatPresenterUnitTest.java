package com.example.deakyu.replicatevenmo;

import com.example.deakyu.replicatevenmo.help.contactus.chat.Chat;
import com.example.deakyu.replicatevenmo.help.contactus.chat.ChatInteractor;
import com.example.deakyu.replicatevenmo.help.contactus.chat.ChatPresenter;
import com.example.deakyu.replicatevenmo.help.contactus.chat.ChatWithUsActivity;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatPresenterUnitTest {

    @Mock ChatWithUsActivity view;
    @Mock ChatInteractor interactor;
    ChatPresenter presenter;
    Chat mockedChat;

    @Before
    public void setup() {
        presenter = new ChatPresenter(interactor);
        presenter.bind(view);
        mockedChat = new Chat("issue", "description");
    }

    @Test
    public void shouldShowNetworkErrorWhenNetworkNotConnected() {
        presenter.insertChat(mockedChat, NetworkUtil.NETWORK_STATUS_NOT_CONNECTED);
        Mockito.verify(view).onNetworkNotConnected("Internet Not Available!");
    }

    @Test
    public void shouldRedirectToContactUsWithToastAfterSavingChatToServerOnData() {
        presenter.insertChat(mockedChat, NetworkUtil.NETWORK_STATUS_MOBILE);
        presenter.onFinished(mockedChat);
        Mockito.verify(view).redirectToContactUsWithToast("Your chat has been recorded!");
    }

    @Test
    public void shouldRedirectToContactUsWithToastAfterSavingChatToServerOnWIFI() {
        presenter.insertChat(mockedChat, NetworkUtil.NETWORK_STAUS_WIFI);
        presenter.onFinished(mockedChat);
        Mockito.verify(view).redirectToContactUsWithToast("Your chat has been recorded!");
    }
}