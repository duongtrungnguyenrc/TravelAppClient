package com.main.travelApp.models;

public class Place {
    private String name;
    private int totalBooked;
    private String ratedStar;
    private String background;

    public Place(String name, int totalBooked, String ratedStar, String background) {
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

    public String getRatedStar() {
        return ratedStar;
    }

    public void setRatedStar(String ratedStar) {
        this.ratedStar = ratedStar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
