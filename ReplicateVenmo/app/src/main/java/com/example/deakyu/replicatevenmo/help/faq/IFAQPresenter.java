package com.example.deakyu.replicatevenmo.help.faq;

import java.util.List;

public interface IFAQPresenter {
    void bind(IFAQActivity view);
    void unbind();
    void getCategoriesFromServer(int networkStatus);
    void onTopicItemClicked(String topic, String description);
    List<Category> getCurrentCategories();
}
