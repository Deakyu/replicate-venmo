package com.example.deakyu.replicatevenmo.help.faq;

import com.google.gson.annotations.SerializedName;

public class Topic {
    @SerializedName("id")
    private int id;
    @SerializedName("faqId")
    private int faqId;
    @SerializedName("topic")
    private String topic;
    @SerializedName("description")
    private String description;

    public Topic(int id, int faqId, String topic, String description) {
        this.id = id;
        this.faqId = faqId;
        this.topic = topic;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFaqId() { return faqId; }
    public void setFaqId(int faqId) { this.faqId = faqId; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.getId()).append(", ");
        sb.append("faqId: ").append(this.getFaqId()).append(", ");
        sb.append("topic: ").append(this.getTopic()).append(", ");
        sb.append("description: ").append(this.getDescription());
        return sb.toString();
    }
}
