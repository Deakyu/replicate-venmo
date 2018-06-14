package com.example.deakyu.replicatevenmo.feed.story;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.feed.public_message.Comment;
import com.example.deakyu.replicatevenmo.feed.public_message.Message;
import com.example.deakyu.replicatevenmo.feed.public_message.PublicMessageInteractor;
import com.example.deakyu.replicatevenmo.feed.public_message.PublicMessageViewModel;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import rx.CompletableSubscriber;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class StoryActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private ImageView avatar;
    private TextView userInteraction;
    private TextView sent;
    private TextView content;
    private LinearLayout buttonsRow;
    private EditText commentEditText;
    private TextView sendButton;

    private Message curMessage;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private PublicMessageViewModel publicMessageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        publicMessageViewModel = new PublicMessageViewModel(new PublicMessageInteractor(), AndroidSchedulers.mainThread());

        initView();
        populateView();
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    private void initView() {
        toolbar = findViewById(R.id.story_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        avatar = findViewById(R.id.avatar);
        userInteraction = findViewById(R.id.user_interaction_text_view);
        sent = findViewById(R.id.time_text_view);
        content = findViewById(R.id.content_text_view);
        buttonsRow = findViewById(R.id.buttons_row);
        buttonsRow.setVisibility(View.GONE);
        commentEditText = findViewById(R.id.comment_edit_text);
        sendButton = findViewById(R.id.send_button);
    }

    private void populateView() {
        curMessage = getIntent().getParcelableExtra("curMessage");
        if(curMessage != null) {
            Picasso.get().load(curMessage.getAvatar()).resize(150, 150)
                    .centerCrop().into(avatar, new Callback() {
                @Override
                public void onSuccess() {
                    Bitmap imageBitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
                    RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                    imageDrawable.setCircular(true);
                    imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                    avatar.setImageDrawable(imageDrawable);
                }

                @Override
                public void onError(Exception e) { /* TODO: Put placeholder instead */ }
            });
            userInteraction.setText(curMessage.getSender() + " paid " + curMessage.getReceiver());
            sent.setText(curMessage.getSent());
            content.setText(curMessage.getContent());
            sendButton.setOnClickListener(this);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        String comment = commentEditText.getText().toString();
        if(comment != null && !comment.equals("")) {
            // TODO: Send message to server and redirect back
            Comment newComment = new Comment("Deakyu Lee", comment);
            insertCommentToServer(curMessage.getId(), newComment);
        } else {
            Toast.makeText(this, "Please enter message to send", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertCommentToServer(int id, Comment comment) {
        if(getNetworkStatus() == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            subscriptions.add(publicMessageViewModel.insertComment(id, comment).subscribe(() -> {
                Toast.makeText(this, "Comment successful!", Toast.LENGTH_SHORT).show();
                finish();
            }, t -> {
                t.printStackTrace();
                Toast.makeText(this, "Error sending comment", Toast.LENGTH_SHORT).show();
            }));
        }
    }

    private int getNetworkStatus() {
        return NetworkUtil.getConnectivityStatusString(getApplicationContext());
    }
}
