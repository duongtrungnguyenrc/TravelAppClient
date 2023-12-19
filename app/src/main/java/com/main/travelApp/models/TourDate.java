package com.main.travelApp.models;

import java.util.Date;

public class TourDate {
    private long id;
    private String departDate;
    private String endDate;
    private String type;
    private int duration;
    private double adultPrice;
    private double childPrice;
    private int maxPeople;
    private int currentPeople;

    public long getId() {
        return id;
    }

    public String getDepartDate() {
        return departDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }
}
