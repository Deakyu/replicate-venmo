package com.example.deakyu.replicatevenmo;

import com.example.deakyu.replicatevenmo.help.faq.Category;
import com.example.deakyu.replicatevenmo.help.faq.FAQActivity;
import com.example.deakyu.replicatevenmo.help.faq.FAQInteractor;
import com.example.deakyu.replicatevenmo.help.faq.FAQPresenter;
import com.example.deakyu.replicatevenmo.help.faq.Topic;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FAQPresenterUnitTest {

    @Mock FAQInteractor interactor;
    @Mock FAQActivity view;
    FAQPresenter presenter;

    List<Topic> mockedTopic;
    List<Category> mockedCategory;

    @Before
    public void setup() {
        presenter = new FAQPresenter(interactor);
        presenter.bind(view);

        mockedTopic = new ArrayList<>();
        mockedCategory = new ArrayList<>();
        mockedTopic.add(new Topic(1, 1, "topic", "desc"));
        mockedCategory.add(new Category(1, "category", mockedTopic));
    }

    @Test
    public void shouldUpdateViewWithCategoriesFromServerWithWIFI() {
        presenter.fetchCategoriesFromServer(NetworkUtil.NETWORK_STAUS_WIFI);
        presenter.onFinished(mockedCategory);
        Mockito.verify(view).updateUiCategories(mockedCategory);
    }

    @Test
    public void shouldUpdateViewWithCategoriesFromServerWithData() {
        presenter.fetchCategoriesFromServer(NetworkUtil.NETWORK_STATUS_MOBILE);
        presenter.onFinished(mockedCategory);
        Mockito.verify(view).updateUiCategories(mockedCategory);
    }

    @Test
    public void shouldShowNetworkErrorWhenNetworkNotConnected() {
        presenter.fetchCategoriesFromServer(NetworkUtil.NETWORK_STATUS_NOT_CONNECTED);
        Mockito.verify(view).onNetworkNotConnected("Internet Not Available!");
    }

    @Test
    public void shouldStartFAQDescriptionActivityOnTopicItemClicked() {
        presenter.onTopicItemClicked("topic", "description");
        Mockito.verify(view).startFAQDescriptionActivity("topic", "description");
    }

}
