package com.main.travelApp.models;

import java.util.List;

public class Tour {
    private Long id;
    private String name;
    private double startFrom;
    private int totalRates;
    private String vehicle;
    private double ratedStar;
    private String type;
    private String depart;
    private String location;
    private List<TourDate> tourDate;
    private int maxPeople;
    private int currentPeople;
    private String img;
    private Overview overview;
    private boolean ratingAcceptance;
    private List<Hotel> hotels;
    private List<Schedule> schedules;
    private List<MinimizeTour> relevantTours;

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startFrom=" + startFrom +
                ", totalRates=" + totalRates +
                ", vehicle='" + vehicle + '\'' +
                ", ratedStar=" + ratedStar +
                ", type='" + type + '\'' +
                ", depart='" + depart + '\'' +
                ", location='" + location + '\'' +
                ", tourDate=" + tourDate +
                ", maxPeople=" + maxPeople +
                ", currentPeople=" + currentPeople +
                ", img='" + img + '\'' +
                ", overview=" + overview +
                ", ratingAcceptance=" + ratingAcceptance +
                ", hotels=" + hotels +
                ", schedules=" + schedules +
                ", relevantTours=" + relevantTours +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getStartFrom() {
        return startFrom;
    }

    public int getTotalRates() {
        return totalRates;
    }

    public String getVehicle() {
        return vehicle;
    }

    public double getRatedStar() {
        return ratedStar;
    }

    public String getType() {
        return type;
    }

    public String getDepart() {
        return depart;
    }

    public String getLocation() {
        return location;
    }

    public List<TourDate> getTourDate() {
        return tourDate;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public String getImg() {
        return img;
    }

    public Overview getOverview() {
        return overview;
    }

    public boolean isRatingAcceptance() {
        return ratingAcceptance;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<MinimizeTour> getRelevantTours() {
        return relevantTours;
    }
}
