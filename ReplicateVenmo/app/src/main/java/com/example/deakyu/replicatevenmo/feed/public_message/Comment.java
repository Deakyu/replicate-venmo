package com.example.deakyu.replicatevenmo.feed.public_message;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("comment") private String comment;

    public Comment(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
