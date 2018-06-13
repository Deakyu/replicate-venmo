package com.example.deakyu.replicatevenmo.help.contactus.chat;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    private int id;
    @SerializedName("issue")
    private String issue;
    @SerializedName("description")
    private String description;

    public Chat(String issue, String description) {
        this.issue = issue;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
