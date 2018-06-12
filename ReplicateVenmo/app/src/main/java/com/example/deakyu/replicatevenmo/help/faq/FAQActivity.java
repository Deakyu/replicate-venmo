package com.example.deakyu.replicatevenmo.help.faq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.List;

public class FAQActivity extends AppCompatActivity implements IFAQActivity{

    private Toolbar toolbar;
    private ActionBar ab;
    private ProgressBar loader;
    private CategoryListAdapter adapter;
    private RecyclerView recyclerView;

    private IFAQInteractor interactor;
    private IFAQPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        registerNetworkListener();
        initUi();
        initRecyclerView();
        setPresenter();
    }

    private void initUi() {
        toolbar = findViewById(R.id.faq_toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        loader = findViewById(R.id.loader);
    }


    private void initRecyclerView() {
        adapter = new CategoryListAdapter(this);
        recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setPresenter() {
        interactor = new FAQInteractor();
        presenter = (IFAQPresenter) getLastCustomNonConfigurationInstance();
        if(presenter == null) {
            presenter = new FAQPresenter(interactor);
            presenter.bind(this);
            presenter.getCategoriesFromServer(getNetworkStatus());
        } else {
            updateUiCategories(presenter.getCurrentCategories());
        }
        adapter.setPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        unregisterReceiver(networkListener);
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void updateUiCategories(List<Category> categories) {
        loader.setVisibility(View.GONE);
        adapter.setCategories(categories);
    }

    @Override
    public void onNetworkNotConnected(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        loader.setVisibility(View.GONE);
    }

    private BroadcastReceiver networkListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loader.setVisibility(View.VISIBLE);
            presenter.getCategoriesFromServer(getNetworkStatus());
        }
    };

    private void registerNetworkListener() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkListener, filter);
    }

    @Override
    public void startFAQDescriptionActivity(String topic, String description) {
        Intent intent = new Intent(FAQActivity.this, FAQDescriptionActivity.class);
        intent.putExtra("topic", topic);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    public int getNetworkStatus() {
        int status = NetworkUtil.getConnectivityStatusString(getApplicationContext());
        if(status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) { // Connection Lost
            return NetworkUtil.NETWORK_STATUS_NOT_CONNECTED;
        } else { // Connection restored
            return NetworkUtil.NETWORK_STAUS_WIFI;
        }
    }
}
