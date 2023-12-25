package com.main.travelApp.models;

import java.text.NumberFormat;
import java.util.Locale;

public class HotelRoom {
    private Long id;
    private String type;
    private double price;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStringPrice() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        return numberFormat.format(price) + " VND";
    }

    public double getPrice() {
        return price;
    }
}
