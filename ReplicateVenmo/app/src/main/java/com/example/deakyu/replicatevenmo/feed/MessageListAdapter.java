package com.example.deakyu.replicatevenmo.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deakyu.replicatevenmo.R;
import com.example.deakyu.replicatevenmo.feed.public_message.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public ImageView avatar;
        public TextView userInteraction;
        public TextView timeSent;
        public TextView content;
        public ImageView buttonUnfilled;
        public ImageView buttonFilled;
        public ImageView commentButton;

        public ViewHolder(View iv) {
            super(iv);

            row = iv.findViewById(R.id.message_row);
            avatar = iv.findViewById(R.id.avatar);
            userInteraction = iv.findViewById(R.id.user_interaction_text_view);
            timeSent = iv.findViewById(R.id.time_text_view);
            content = iv.findViewById(R.id.content_text_view);
            buttonUnfilled = iv.findViewById(R.id.like_button_unfilled);
            buttonFilled = iv.findViewById(R.id.like_button_filled);
            commentButton = iv.findViewById(R.id.comment_button);
        }
    }

    private final LayoutInflater inflater;
    private List<Message> messages;
    private Context context;

    public MessageListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int pos) {
        Message curMessage = messages.get(pos);
        int filledVisibility = curMessage.isLiked() ? View.VISIBLE : View.GONE;
        int unFilledVisibility = curMessage.isLiked() ? View.GONE : View.VISIBLE;

        Picasso.get().load(curMessage.getAvatar()).resize(200, 200).centerCrop().into(vh.avatar);
        vh.userInteraction.setText(curMessage.getSender() + " paid " + curMessage.getReceiver());
        vh.timeSent.setText(curMessage.getSent());
        vh.content.setText(curMessage.getContent());
        vh.buttonUnfilled.setVisibility(unFilledVisibility);
        vh.buttonFilled.setVisibility(filledVisibility);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return messages != null ? messages.size() : 0; }
}
