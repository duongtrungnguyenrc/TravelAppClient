package com.main.travelApp.models;

import java.util.List;

public class Hotel {
    private Long id;
    private String name;
    private String address;
    private String illustration;
    private List<HotelRoom> rooms;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getIllustration() {
        return illustration;
    }

    public List<HotelRoom> getRooms() {
        return rooms;
    }
}
