package com.main.travelApp.models;

import java.util.Date;

public class Message {
    private long id;
    private String message;
    private Long uid;
    private String time;
    private Long room;
    private String name;
    private String role;
    private String avatar;

    public Message(long id, String message, Long uid, String time, Long room, String name, String role, String avatar) {
        this.id = id;
        this.message = message;
        this.uid = uid;
        this.time = time;
        this.room = room;
        this.name = name;
        this.role = role;
        this.avatar = avatar;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Long getUid() {
        return uid;
    }

    public String getTime() {
        return time;
    }

    public Long getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getAvatar() {
        return avatar;
    }
}
