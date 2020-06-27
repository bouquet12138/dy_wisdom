package com.example.common_lib.java_bean;

public class OpinionBean {

    private int opinion_id;
    private int user_id;
    private String insert_time;
    private String title;
    private String content;

    public int getOpinion_id() {
        return opinion_id;
    }

    public void setOpinion_id(int opinion_id) {
        this.opinion_id = opinion_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
