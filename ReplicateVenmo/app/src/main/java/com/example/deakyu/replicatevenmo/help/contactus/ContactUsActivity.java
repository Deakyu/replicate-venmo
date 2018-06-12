package com.example.deakyu.replicatevenmo.help.contactus;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.help.contactus.chat.ChatWithUsActivity;
import com.example.deakyu.replicatevenmo.help.contactus.email.EmailUsActivity;
import com.example.deakyu.replicatevenmo.help.faq.FAQActivity;

public class ContactUsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ActionBar ab;

    private ListView quickHelpListView;
    private ListView contactHumanListView;

    private String[] quickHelpItems = {"Browse our Help FAQs"};
    private String[] contactHumanItems = {"Email Us", "Chat with Us", "Call Us"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initView();
        initQuickHelpListView();
        initContactHumanListView();
    }

    private void initView() {
        toolbar = findViewById(R.id.contact_us_toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initQuickHelpListView() {
        ArrayAdapter<String> quickHelpAdapter = new ArrayAdapter<>(this, R.layout.quick_help_row, quickHelpItems);

        quickHelpListView = findViewById(R.id.quick_help_list_view);
        quickHelpListView.setAdapter(quickHelpAdapter);
        quickHelpListView.setOnItemClickListener(this);
    }

    private void initContactHumanListView() {
        ArrayAdapter<String> contactHumanAdapter = new ArrayAdapter<>(this, R.layout.contact_human_row, contactHumanItems);

        contactHumanListView = findViewById(R.id.contact_human_list_view);
        contactHumanListView.setAdapter(contactHumanAdapter);
        contactHumanListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
        Intent intent = new Intent(ContactUsActivity.this, FAQActivity.class);

        if(adapter.getItemAtPosition(pos).equals(quickHelpItems[0])) {
            intent = new Intent(ContactUsActivity.this, FAQActivity.class);
        } else if (adapter.getItemAtPosition(pos).equals(contactHumanItems[0])) {
            intent = new Intent(ContactUsActivity.this, EmailUsActivity.class);
        } else if (adapter.getItemAtPosition(pos).equals(contactHumanItems[1])) {
            intent = new Intent(ContactUsActivity.this, ChatWithUsActivity.class);
        } else if (adapter.getItemAtPosition(pos).equals(contactHumanItems[2])) {
            // TODO: Call Us
        }

        startActivity(intent);
    }
}
