package com.example.deakyu.replicatevenmo.feed.story;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deakyu.replicatevenmo.R;

public class StoryActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ImageView avatar;
    private TextView userInteraction;
    private TextView sent;
    private TextView content;
    private LinearLayout buttonsRow;
    private EditText commentEditText;
    private TextView sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
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

    }
}
