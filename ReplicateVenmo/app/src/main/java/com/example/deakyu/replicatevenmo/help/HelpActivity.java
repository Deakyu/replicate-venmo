package com.example.deakyu.replicatevenmo.help;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.help.contactus.ContactUsActivity;
import com.example.deakyu.replicatevenmo.help.faq.FAQActivity;

public class HelpActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ListView helpMainListView;
    private String[] helpItems = {"Browse out Help FAQS", "Contact Us"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        toolbar = findViewById(R.id.help_main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.help_main_row, helpItems);

        helpMainListView = findViewById(R.id.help_list);
        helpMainListView.setAdapter(adapter);
        helpMainListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Intent intent;
        if(adapter.getItemAtPosition(position).equals(helpItems[0])) {
            intent = new Intent(HelpActivity.this, FAQActivity.class);
        } else {
            intent = new Intent(HelpActivity.this, ContactUsActivity.class);
        }
        startActivity(intent);
    }
}
