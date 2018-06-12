package com.example.deakyu.replicatevenmo.help.faq;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.example.deakyu.replicatevenmo.R;

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

        initUi();
        initRecyclerView();
        setPresenter();

        presenter.getCategoriesFromServer();
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
        }
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
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
}
