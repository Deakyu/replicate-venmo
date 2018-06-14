package com.example.deakyu.replicatevenmo.feed.public_message;

import android.content.Intent;
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
import com.example.deakyu.replicatevenmo.feed.AvatarClickListener;
import com.example.deakyu.replicatevenmo.feed.CommentButtonClickListener;
import com.example.deakyu.replicatevenmo.feed.LikeButtonClickListener;
import com.example.deakyu.replicatevenmo.feed.MessageListAdapter;
import com.example.deakyu.replicatevenmo.feed.story.StoryActivity;
import com.example.deakyu.replicatevenmo.network.NetworkUtil;
import com.example.deakyu.replicatevenmo.profile.ProfileActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class PublicFragment extends Fragment implements LikeButtonClickListener,
                                                        CommentButtonClickListener,
                                                        AvatarClickListener {

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
        adapter.setClickListeners(this, this, this);
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

    private void likeMessage(int id, Message message) {
        if(getNetworkStatus() == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            subscriptions.add(publicMessageViewModel.likedMessage(id, message).subscribe(() -> {
                Toast.makeText(getActivity(), message.isLiked() ? "Liked!" : "Unliked!", Toast.LENGTH_SHORT).show();
            }, t -> {
                Toast.makeText(getActivity(), "Error liking the payment", Toast.LENGTH_SHORT).show();
            }));
        }
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

    @Override
    public void onLikeButtonClick(View v, int pos) {
        currentMessages.get(pos).setLiked(!currentMessages.get(pos).isLiked());
        likeMessage(currentMessages.get(pos).getId(), currentMessages.get(pos));
        adapter.setMessages(currentMessages);
    }

    @Override
    public void onCommentButtonClick(View v, int pos) {
        Intent intent = new Intent(getActivity(), StoryActivity.class);
        intent.putExtra("curMessage", currentMessages.get(pos));
        startActivity(intent);
    }

    @Override
    public void onAvatarClick(View v, int pos) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        // TODO: intent.putExtra("userId", currentMessages.get(pos).getUserId());
        startActivity(intent);
    }
}
