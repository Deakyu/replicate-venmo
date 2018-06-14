package com.example.deakyu.replicatevenmo.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class ProfileActivity extends AppCompatActivity {

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private ProfileViewModel profileViewModel;

    private Toolbar toolbar;
    private TextView userFullName;
    private TextView userUsername;
    private ImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileViewModel = new ProfileViewModel(new ProfileInteractor(), AndroidSchedulers.mainThread());
        initView();

//        int userid = getIntent().getIntExtra("userId", 1);
        int userId = getRandomNumberInRange(1, 11);
        getUserByIdFromServer(userId);
    }

    private void initView() {
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        userFullName = findViewById(R.id.user_full_name);
        userUsername = findViewById(R.id.user_username);
        userAvatar = findViewById(R.id.user_avatar);
    }

    private void populateView(User user) {
        if(user != null) {
            userFullName.setText(user.getFirstName() + " " + user.getLastName());
            userUsername.setText(user.getEmail());
            Picasso.get().load(user.getPicture()).resize(200, 200).centerCrop()
                    .into(userAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) userAvatar.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            userAvatar.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) { /* TODO: Put placeholder instead */ }
                    });
            setTitle(user.getFirstName() + " " + user.getLastName());
        } else {
            userFullName.setText(".");
            userUsername.setText(".");
            Picasso.get().load(R.drawable.ic_person_add_black_24dp).resize(200, 200).centerCrop()
                    .into(userAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) userAvatar.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            userAvatar.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) { /* TODO: Put placeholder instead */ }
                    });
        }
    }

    private void getUserByIdFromServer(int id) {
        if(getNetworkStatus() == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            subscriptions.add(profileViewModel.getUserById(id).subscribe(new SingleSubscriber<User>() {
                @Override
                public void onSuccess(User user) {
                    populateView(user);
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

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
