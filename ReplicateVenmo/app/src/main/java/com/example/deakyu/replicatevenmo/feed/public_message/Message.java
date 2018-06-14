package com.example.deakyu.replicatevenmo.feed.public_message;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Message implements Parcelable {
    @SerializedName("id") private int id;
    @SerializedName("sender") private String sender;
    @SerializedName("receiver") private String receiver;
    @SerializedName("avatar") private String avatar;
    @SerializedName("sent") private String sent;
    @SerializedName("content") private String content;
    @SerializedName("liked") private boolean liked;
    @SerializedName("comments") private List<Comment> comments;

    public Message(String sender, String receiver, String avatar, String sent, String content, boolean liked, List<Comment> comments) {
        this.sender = sender;
        this.receiver = receiver;
        this.avatar = avatar;
        this.sent = sent;
        this.content = content;
        this.liked = liked;
        this.comments = comments;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getSent() { return sent; }
    public void setSent(String sent) { this.sent = sent; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    protected Message(Parcel in) {
        id = in.readInt();
        sender = in.readString();
        receiver = in.readString();
        avatar = in.readString();
        sent = in.readString();
        content = in.readString();
        liked = in.readByte() != 0;
        comments = new ArrayList<>();
        in.readTypedList(comments, Comment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(sender);
        dest.writeString(receiver);
        dest.writeString(avatar);
        dest.writeString(sent);
        dest.writeString(content);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeTypedList(comments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
