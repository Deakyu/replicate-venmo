package com.example.deakyu.replicatevenmo.help.faq;

import java.util.List;

import rx.Observable;

public interface FAQContract {

    interface View {
        void updateUiCategories(List<Category> categories);
        void startFAQDescriptionActivity(String topic, String description);
        void onNetworkNotConnected(String message);
    }

    interface Presenter {
        void bind(FAQContract.View view);
        void unbind();
        void fetchCategoriesFromServer(int networkStatus);
        void onTopicItemClicked(String topic, String description);
        List<Category> getCachedCategories();
    }

    interface Interactor {
        interface OnFinishedListener {
            void onFinished(List<Category> categories);
            void onFailure(Throwable t);
        }
        void getCategories(OnFinishedListener onFinishedListener);
    }
}
