package com.main.travelApp.response;

import com.main.travelApp.models.GeneralTour;

import java.util.List;

public class AllTourResponse {
    private int pages;
    private List<GeneralTour> tours;

    public AllTourResponse() {
    }

    public AllTourResponse(int pages, List<GeneralTour> tours) {
        this.pages = pages;
        this.tours = tours;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<GeneralTour> getTours() {
        return tours;
    }

    public void setTours(List<GeneralTour> tours) {
        this.tours = tours;
    }
}
