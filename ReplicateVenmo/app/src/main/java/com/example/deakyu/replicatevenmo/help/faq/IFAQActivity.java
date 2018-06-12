package com.example.deakyu.replicatevenmo.help.faq;

import java.util.List;

public interface IFAQActivity {
    void updateUiCategories(List<Category> categories);
    void startFAQDescriptionActivity(String topic, String description);
    void onNetworkNotConnected(String message);
}
