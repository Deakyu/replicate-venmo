package com.example.deakyu.replicatevenmo.help.faq;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("id")
    private int id;
    @SerializedName("category")
    private String category;
    @SerializedName("topics")
    private List<Topic> topics;

    public Category(int id, String category, List<Topic> topics) {
        this.id = id;
        this.category = category;
        this.topics = topics;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<Topic> getTopics() { return topics; }
    public void setTopics(List<Topic> topics) { this.topics = topics; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.getId()).append(", ");
        sb.append("category: ").append(this.getCategory()).append(", ");
        sb.append("topics: ").append(this.getTopics().toString());
        return sb.toString();
    }
}
