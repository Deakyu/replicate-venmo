package com.example.deakyu.replicatevenmo.notification;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("descriptions")
    private String description;
    @SerializedName("read")
    private boolean read;

    public Notification(int id, String title, String description, boolean read) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.read = read;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public int getId() {  return id; }
    public void setId(int id) { this.id = id; }

    protected Notification(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        read = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (read ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
