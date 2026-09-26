package com.autovideo.autovideogenerator.model.dto;

public class VideoRequest {
    private String topic;
    private String language;

    public VideoRequest() {
    }

    public VideoRequest(String topic, String language) {
        this.topic = topic;
        this.language = language;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}