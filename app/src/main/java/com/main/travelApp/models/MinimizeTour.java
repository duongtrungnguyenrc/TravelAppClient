package com.main.travelApp.models;

import androidx.annotation.Nullable;

public class MinimizeTour {
    private long id;
    private String name;
    private double price;
    private double ratedStar;
    private String depart;
    private String location;
    private int maxPeople;
    private int duration;
    private String img;
    private String type;
    private String typeTitle;

    @Nullable
    private String activityTime;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRatedStar() {
        return ratedStar;
    }

    public void setRatedStar(double ratedStar) {
        this.ratedStar = ratedStar;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    @Nullable
    public String getActivityTime() {
        return activityTime;
    }
    public MinimizeTour(){}

    public MinimizeTour(int id, String name, double price, double ratedStar, String depart, String location, int maxPeople, int duration, String img, String type, String typeTitle) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ratedStar = ratedStar;
        this.depart = depart;
        this.location = location;
        this.maxPeople = maxPeople;
        this.duration = duration;
        this.img = img;
        this.type = type;
        this.typeTitle = typeTitle;
    }
}
