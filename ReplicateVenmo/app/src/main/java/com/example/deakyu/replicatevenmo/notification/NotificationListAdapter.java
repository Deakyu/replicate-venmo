package com.example.deakyu.replicatevenmo.notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deakyu.replicatevenmo.R;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView title;
        public TextView description;
        public ImageView checkImage;

        public ViewHolder(View iv) {
            super(iv);

            row = iv.findViewById(R.id.notification_row);
            title = iv.findViewById(R.id.title);
            description = iv.findViewById(R.id.description);
            checkImage = iv.findViewById(R.id.check_image);
        }
    }

    private final LayoutInflater inflater;
    private List<Notification> notifications;
    private Context context;

    public NotificationListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int pos) {
        Notification curNotif = notifications.get(pos);

        vh.title.setText(curNotif.getTitle());
        vh.description.setText(curNotif.getDescription());
        vh.checkImage.setVisibility(curNotif.isRead() ? View.VISIBLE : View.GONE);
    }

    public void setNotifications(List<Notification> notifications) {
        if(notifications != null) {
            this.notifications = notifications;
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Empty Notification List", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

}
