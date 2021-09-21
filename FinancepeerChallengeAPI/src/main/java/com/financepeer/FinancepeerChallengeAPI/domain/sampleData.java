package com.financepeer.FinancepeerChallengeAPI.domain;

public class sampleData {
    private String userID;
    private String ID;
    private String title;
    private String body;

    public sampleData(String userID, String id, String title, String body) {
        this.userID = userID;
        this.ID = id;
        this.title = title;
        this.body = body;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
