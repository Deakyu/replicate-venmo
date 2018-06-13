package com.example.deakyu.replicatevenmo.feed.public_message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.feed.MessageListAdapter;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class PublicFragment extends Fragment {

    private static final String TAG = PublicFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView publicMessageRecyclerView;
    private MessageListAdapter adapter;
    private ProgressBar loader;

    private CompositeSubscription subscriptions = new CompositeSubscription();
    private PublicMessageViewModel publicMessageViewModel;
    private List<Message> currentMessages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_feed_layout, container, false);

        loader = view.findViewById(R.id.loader);

        mSwipeRefreshLayout = view.findViewById(R.id.public_messages_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshItems);

        publicMessageRecyclerView = view.findViewById(R.id.public_messages_recycler_view);
        adapter = new MessageListAdapter(getActivity());
        publicMessageRecyclerView.setAdapter(adapter);
        publicMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        publicMessageRecyclerView.addItemDecoration(new DividerItemDecoration(publicMessageRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        publicMessageViewModel = new PublicMessageViewModel(new PublicMessageInteractor(), AndroidSchedulers.mainThread());
        getMessagesFromServer();

        return view;
    }

    @Override
    public void onDestroyView() {
        subscriptions.unsubscribe();
        super.onDestroyView();
    }

    private void refreshItems() {
        getMessagesFromServer();
    }

    private void getMessagesFromServer() {
        if(getNetworkStatus() == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            subscriptions.add(publicMessageViewModel.getMessages().subscribe(new Observer<List<Message>>() {
                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(List<Message> messages) {
                    currentMessages = messages;
                    adapter.setMessages(currentMessages);

                    loader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }));

        }
    }

    private int getNetworkStatus() {
        return NetworkUtil.getConnectivityStatusString(getActivity().getApplicationContext());
    }

}
