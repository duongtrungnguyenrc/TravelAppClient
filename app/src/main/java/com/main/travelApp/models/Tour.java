package com.main.travelApp.models;

public class Tour {
    private Long id;
    private String tourName;
    private float ratedStar;
    private String depart;
    private String departDate;
    private int duration;
    private double price;
    private int totalBooked;
    private String destination;

    public Tour(Long id, String tourName, float ratedStar, String destination, String departDate, int duration, double price, int totalBooked) {
        this.id = id;
        this.tourName = tourName;
        this.ratedStar = ratedStar;
        this.destination = destination;
        this.departDate = departDate;
        this.duration = duration;
        this.price = price;
        this.totalBooked = totalBooked;
    }

    public Tour() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalBooked() {
        return totalBooked;
    }

    public void setTotalBooked(int totalBooked) {
        this.totalBooked = totalBooked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public float getRatedStar() {
        return ratedStar;
    }

    public void setRatedStar(float ratedStar) {
        this.ratedStar = ratedStar;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
