package com.example.deakyu.replicatevenmo;


import com.example.deakyu.replicatevenmo.help.faq.Category;
import com.example.deakyu.replicatevenmo.help.faq.FAQActivity;
import com.example.deakyu.replicatevenmo.help.faq.FAQInteractor;
import com.example.deakyu.replicatevenmo.help.faq.FAQPresenter;
import com.example.deakyu.replicatevenmo.help.faq.Topic;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;
import com.example.deakyu.replicatevenmo.network.VenmoRetrofit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleUnitTest {

    @Mock Call<List<Category>> mockedCall;
    @Mock FAQActivity view;
    @Mock FAQInteractor interactor;
    FAQPresenter presenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        presenter = new FAQPresenter(interactor);
        presenter.bind(view);
    }

    @Test
    public void shouldUpdateUIWithCategoryWhenSuccessNetwork() {
        List<Category> mockedCategoryList = new ArrayList<>();
        List<Topic> mockedTopicList = new ArrayList<>();
        mockedTopicList.add(new Topic(1, 1, "topic", "description"));
        mockedCategoryList.add(new Category(1, "c1", mockedTopicList));
        presenter.getCategoriesFromServer(NetworkUtil.NETWORK_STAUS_WIFI);
        when(interactor.getCategories()).thenReturn(mockedCall);
        verify(view).updateUiCategories(mockedCategoryList);
    }

}
