package com.example.deakyu.replicatevenmo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class NotificationActivity extends AppCompatActivity implements NotificationItemClickListener{

    @Override
    public void onItemClick(View view, int pos) {
        // Item Clicked - update notification through server
        boolean read = currentNotifications.get(pos).isRead();
        currentNotifications.get(pos).setRead(!read);
        view.findViewById(R.id.check_image).setVisibility(currentNotifications.get(pos).isRead() ? View.VISIBLE : View.GONE);

        Notification curNotif = currentNotifications.get(pos);
        int id = curNotif.getId();
        updateNotificationOnServer(id, curNotif);
    }

    private Toolbar toolbar;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private NotificationViewModel notificationViewModel;
    private List<Notification> currentNotifications;
    private NotificationStorageUtil storageUtil;

    private RecyclerView recyclerView;
    private NotificationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        storageUtil = new NotificationStorageUtil(getApplicationContext());
        notificationViewModel = new NotificationViewModel(new NotificationInteractorImpl(), AndroidSchedulers.mainThread());

        setRecyclerView();

        registerNetworkListener();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        unregisterReceiver(networkListener);
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.notification_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new NotificationListAdapter(this);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getNotificationsFromStorage() {
        currentNotifications = storageUtil.loadNotifications();
        adapter.setNotifications(currentNotifications);
    }

    // region ViewModel Methods
    private void getNotificationsFromServer() {
        subscriptions.add(notificationViewModel.getNotifications().subscribe(new Observer<List<Notification>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                currentNotifications = storageUtil.loadNotifications();
                adapter.setNotifications(currentNotifications);
            }

            @Override
            public void onNext(List<Notification> remoteNotification) {
                currentNotifications = remoteNotification;
                storageUtil.storeNotifications(remoteNotification);
                adapter.setNotifications(currentNotifications);
            }
        }));
    }

    private void updateNotificationOnServer(int id, Notification notification) {
        subscriptions.add(notificationViewModel.updateNotification(id, notification).subscribe(new Observer<Notification>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { e.printStackTrace();}

            @Override
            public void onNext(Notification notification) {
                storageUtil.storeNotifications(currentNotifications);
            }
        }));
    }
    // endregion

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
                Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
                subscriptions.unsubscribe();
                getNotificationsFromStorage();
            } else { // Connection restored
                Toast.makeText(getApplicationContext(), "Connection Restored", Toast.LENGTH_SHORT).show();
                getNotificationsFromServer();
            }
        }
    };

    private void registerNetworkListener() {
        IntentFilter filter = new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkListener, filter);
    }
    // endregion
}
