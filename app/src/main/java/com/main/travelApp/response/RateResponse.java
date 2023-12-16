package com.main.travelApp.response;

import com.main.travelApp.models.Rate;

import java.util.List;

public class RateResponse {
    private int pages;
    private List<Rate> rates;

    public RateResponse(int pages, List<Rate> rates) {
        this.pages = pages;
        this.rates = rates;
    }

    public RateResponse() {
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
