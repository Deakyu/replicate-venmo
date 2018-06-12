package com.example.deakyu.replicatevenmo.help.faq;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.deakyu.replicatevenmo.R;

public class FAQDescriptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar ab;

    private TextView topic;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqdescription_acitivity);

        toolbar = findViewById(R.id.description_toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        topic = findViewById(R.id.topic);
        description = findViewById(R.id.description);

        topic.setText(getIntent().getStringExtra("topic"));
        description.setText(getIntent().getStringExtra("description"));
        setTitle(getIntent().getStringExtra("topic"));
    }
}
