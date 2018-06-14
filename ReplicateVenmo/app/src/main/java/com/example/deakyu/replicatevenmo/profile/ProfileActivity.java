package com.example.deakyu.replicatevenmo.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfileActivity extends AppCompatActivity {

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private ProfileViewModel profileViewModel;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileViewModel = new ProfileViewModel(new ProfileInteractor(), AndroidSchedulers.mainThread());

    }

    private void getUserByIdFromServer(int id) {
        if(getNetworkStatus() == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            subscriptions.add(profileViewModel.getUserById(id).subscribe(new SingleSubscriber<User>() {
                @Override
                public void onSuccess(User user) {
                    currentUser = user;
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    private int getNetworkStatus() {
        return NetworkUtil.getConnectivityStatusString(getApplicationContext());
    }
}
