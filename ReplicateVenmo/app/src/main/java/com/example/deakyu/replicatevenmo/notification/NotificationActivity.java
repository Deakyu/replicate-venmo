package com.example.deakyu.replicatevenmo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private NotificationViewModel notificationViewModel;
    private List<Notification> currentNotifications;
    private NotificationStorageUtil storageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        registerNetworkListener();

        storageUtil = new NotificationStorageUtil(getApplicationContext());
        notificationViewModel = new NotificationViewModel(new NotificationInteractorImpl(), AndroidSchedulers.mainThread());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isInternetAvailable()) {
            getNotificationsFromServer();
        } else {
            getNotificationsFromStorage();
            Toast.makeText(NotificationActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        unregisterReceiver(networkListener);
    }

    private void getNotificationsFromStorage() {
        currentNotifications = storageUtil.loadNotifications();
    }

    private void getNotificationsFromServer() {
        subscriptions.add(notificationViewModel.getNotifications().subscribe(new Observer<List<Notification>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(NotificationActivity.this, "Error getting data from server", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onNext(List<Notification> remoteNotification) {
                currentNotifications = remoteNotification;
                storageUtil.storeNotifications(remoteNotification);
            }
        }));
    }

    // region Network Checking Methods
    private boolean isInternetAvailable() {
        int status = NetworkUtil.getConnectivityStatusString(getApplicationContext());
        if(status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) return false;
        return true;
    }

    private BroadcastReceiver networkListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = NetworkUtil.getConnectivityStatusString(context);
            if(status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) { // Connection Lost
                Toast.makeText(NotificationActivity.this, "Connection Loast", Toast.LENGTH_SHORT).show();
            } else { // Connection restored
                Toast.makeText(NotificationActivity.this, "Connection Restored", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void registerNetworkListener() {
        IntentFilter filter = new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkListener, filter);
    }
    // endregion
}
