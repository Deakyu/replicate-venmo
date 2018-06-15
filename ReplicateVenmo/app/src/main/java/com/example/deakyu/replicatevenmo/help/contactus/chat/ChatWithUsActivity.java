package com.example.deakyu.replicatevenmo.help.contactus.chat;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.help.contactus.ContactUsActivity;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

public class ChatWithUsActivity extends AppCompatActivity implements ChatContract.View, AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Spinner issueSpinner;
    private EditText descriptionEditText;
    private ProgressBar loader;

    private ChatContract.Interactor interactor;
    private ChatContract.Presenter presenter;

    private Chat currentChat = new Chat("", "");

    // region Activity LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_us);

        initToolbar();
        initSpinner();
        initLoader();
        setPresenter();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
    // endregion

    // region Init Views
    private void initToolbar() {
        toolbar = findViewById(R.id.chat_with_us_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initSpinner() {
        issueSpinner = findViewById(R.id.issue_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.issues_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(adapter);
        issueSpinner.setOnItemSelectedListener(this);
        descriptionEditText = findViewById(R.id.chat_edit_text);
    }

    private void initLoader() {
        loader = findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
    }
    // endregion

    // region Init Presenter
    private void setPresenter() {
        interactor = new ChatInteractor();
        if(presenter == null) {
            presenter = new ChatPresenter(interactor);
            presenter.bind(this);
        }
    }
    // endregion

    // region App bar methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.submit) {
            // Post request to server - create new chat
            loader.setVisibility(View.VISIBLE);
            currentChat.setDescription(descriptionEditText.getText().toString());
            presenter.insertChat(currentChat, getNetworkStatus());
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion

    // region Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentChat.setIssue(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
    // endregion

    // region View interface methods
    @Override
    public void onNetworkNotConnected(String message) {
        loader.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToContactUsWithToast(String message) {
        loader.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ChatWithUsActivity.this, ContactUsActivity.class);
        startActivity(intent);
    }
    // endregion

    public int getNetworkStatus() {
        return NetworkUtil.getConnectivityStatusString(getApplicationContext());
    }
}
