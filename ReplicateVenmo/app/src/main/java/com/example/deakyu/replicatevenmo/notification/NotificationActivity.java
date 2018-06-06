package com.example.deakyu.replicatevenmo.notification;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private NotificationViewModel notificationViewModel;
    private List<Notification> currentNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        notificationViewModel = new NotificationViewModel(new NotificationInteractorImpl(), AndroidSchedulers.mainThread());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isInternetAvailable()) {
            getNotificationsFromServer();
        } else {
            Toast.makeText(NotificationActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    private void getNotificationsFromServer() {
        subscriptions.add(notificationViewModel.getNotifications().subscribe(new Observer<List<Notification>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("DEE error on server");
                e.printStackTrace();
            }

            @Override
            public void onNext(List<Notification> remoteNotification) {
                currentNotifications = remoteNotification;
                System.out.println("Debugging purpose line");
            }
        }));
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }
}
