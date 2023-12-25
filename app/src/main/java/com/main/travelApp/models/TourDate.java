package com.main.travelApp.models;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

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

    public String getStringAdultPrice() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        return numberFormat.format(adultPrice) + " VND";
    }

    public double getChildPrice() {
        return childPrice;
    }

    public String getStringChildPrice() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        return numberFormat.format(childPrice) + " VND";
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }
}
