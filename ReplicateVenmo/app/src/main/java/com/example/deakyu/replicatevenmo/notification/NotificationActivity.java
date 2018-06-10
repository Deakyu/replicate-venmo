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
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.FuncN;
import rx.observables.AsyncOnSubscribe;
import rx.subscriptions.CompositeSubscription;

public class NotificationActivity extends AppCompatActivity implements NotificationItemClickListener{

    private List<Notification> toBeUpdated = new ArrayList<>();

    @Override
    public void onItemClick(View view, int pos) {
        // Item Clicked - update notification through server
        boolean read = currentNotifications.get(pos).isRead();
        currentNotifications.get(pos).setRead(!read);
        storageUtil.storeNotifications(currentNotifications);
        view.findViewById(R.id.check_image).setVisibility(currentNotifications.get(pos).isRead() ? View.VISIBLE : View.GONE);

        Notification curNotif = currentNotifications.get(pos);
        int id = curNotif.getId();

        if(isInternetAvailable()) {
            updateNotificationOnServer(id, curNotif);
            System.out.println("DEE onItemClick Internet Available " + curNotif.getTitle());
        } else {
            toBeUpdated.add(curNotif);
            System.out.println("DEE onItemClick Internet NOT Available " + curNotif.getTitle());
        }
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

    private void updateMultipleNotifications(List<Notification> notifications) {
        System.out.println("DEE updateMultipleNotifications()");
        for(int i=0 ; i < notifications.size() ; i++) {
            Notification cur = notifications.get(i);
            subscriptions.add(notificationViewModel.updateNotification(cur.getId(), cur).subscribe(new Observer<Notification>() {
                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) {
                    System.out.println("DEE update error!");
                }

                @Override
                public void onNext(Notification notification) {
                    System.out.println("DEE updated");
                }
            }));
        }
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
                subscriptions.clear();
                getNotificationsFromStorage();
                toBeUpdated.clear();
                System.out.println("DEE connection lost");
            } else { // Connection restored
                if(!toBeUpdated.isEmpty()) {
                    updateMultipleNotifications(toBeUpdated);
                    System.out.println("DEE toBeUpdated Not Empty");
                }
                getNotificationsFromServer();
                System.out.println("DEE connection restored: " + toBeUpdated.size());
            }
        }
    };

    private void registerNetworkListener() {
        IntentFilter filter = new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkListener, filter);
    }
    // endregion
}
