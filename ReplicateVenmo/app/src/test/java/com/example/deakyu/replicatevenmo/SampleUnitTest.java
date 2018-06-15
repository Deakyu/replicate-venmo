package com.example.deakyu.replicatevenmo;


import com.example.deakyu.replicatevenmo.help.faq.Category;
import com.example.deakyu.replicatevenmo.help.faq.FAQContract;
import com.example.deakyu.replicatevenmo.help.faq.FAQPresenter;
import com.example.deakyu.replicatevenmo.help.faq.Topic;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleUnitTest {

    @Mock FAQContract.View view;
    @Mock FAQContract.Interactor interactor;
    List<Category> mockedCategoryList;
    List<Topic> mockedTopicList;
    FAQPresenter presenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        presenter = new FAQPresenter(interactor);
        presenter.bind(view);

        mockedCategoryList = new ArrayList<>();
        mockedTopicList = new ArrayList<>();
        mockedTopicList.add(new Topic(1, 1, "topic", "description"));
        mockedCategoryList.add(new Category(1, "c1", mockedTopicList));
    }

    @Test
    public void shouldUpdateUIWithCategoryWhenSuccessNetwork() {
        presenter.fetchCategoriesFromServer(NetworkUtil.NETWORK_STAUS_WIFI);
        interactor.getCategories(presenter);
        presenter.onFinished(mockedCategoryList);
        verify(view).updateUiCategories(presenter.flattenCategories(mockedCategoryList));
    }

}
