package com.example.deakyu.replicatevenmo.notification;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.deakyu.replicatevenmo.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NotificationStorageUtil {

    private final String TAG = BuildConfig.APPLICATION_ID+NotificationStorageUtil.class.getSimpleName();
    private SharedPreferences preferences;
    private Context context;

    public NotificationStorageUtil(Context context) {
        this.context = context;
    }

    public void storeNotifications(List<Notification> notifications) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notifications);
        editor.putString("notifications", json);
        editor.apply();
    }

    public List<Notification> loadNotifications() {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("notifications", null);
        Type type = new TypeToken<List<Notification>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void clearCache() {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
