package com.main.travelApp.request;

public class NewMessageRequest {
    private long room;
    private long uid;
    private String role;
    private String message;

    public NewMessageRequest(long room, long uid, String role, String message) {
        this.room = room;
        this.uid = uid;
        this.role = role;
        this.message = message;
    }
}
