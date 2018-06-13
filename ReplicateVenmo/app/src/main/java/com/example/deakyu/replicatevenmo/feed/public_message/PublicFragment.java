package com.example.deakyu.replicatevenmo.feed.public_message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.feed.MessageListAdapter;

public class PublicFragment extends Fragment {

    private static final String TAG = PublicFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView publicMessageRecyclerView;
    private MessageListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_feed_layout, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.public_messages_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
            refreshItems();
        });

        publicMessageRecyclerView = view.findViewById(R.id.public_messages_recycler_view);
        adapter = new MessageListAdapter();
        publicMessageRecyclerView.setAdapter(adapter);
        publicMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void refreshItems() {
        // Load items..

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify dataset changed

        // stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
