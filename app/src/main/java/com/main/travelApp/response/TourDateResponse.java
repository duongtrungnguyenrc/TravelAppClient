package com.main.travelApp.response;

import com.main.travelApp.models.Hotel;
import com.main.travelApp.models.TourDate;

import java.util.List;

public class TourDateResponse {
    private String tourName;
    private String tourImage;
    private List<TourDate> tourDates;
    private List<Hotel> tourHotels;

    public String getTourName() {
        return tourName;
    }

    public String getTourImage() {
        return tourImage;
    }

    public List<TourDate> getTourDates() {
        return tourDates;
    }

    public List<Hotel> getTourHotels() {
        return tourHotels;
    }
}
