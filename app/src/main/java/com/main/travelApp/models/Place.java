package com.main.travelApp.models;

import com.google.gson.annotations.SerializedName;

public class Place {
    private String name;
    @SerializedName("orderQuantity")
    private int totalBooked;
    private double ratedStar;
    @SerializedName("img")
    private String background;

    public Place(String name, int totalBooked, double ratedStar, String background) {
        this.name = name;
        this.totalBooked = totalBooked;
        this.ratedStar = ratedStar;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBooked() {
        return totalBooked;
    }

    public void setTotalBooked(int totalBooked) {
        this.totalBooked = totalBooked;
    }

    public double getRatedStar() {
        return ratedStar;
    }

    public void setRatedStar(double ratedStar) {
        this.ratedStar = ratedStar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
