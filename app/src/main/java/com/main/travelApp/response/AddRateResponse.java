package com.main.travelApp.response;

import com.main.travelApp.models.Rate;

public class AddRateResponse {
    private Rate rateAdded;

    public AddRateResponse(Rate rateAdded) {
        this.rateAdded = rateAdded;
    }

    public AddRateResponse() {
    }

    public Rate getRateAdded() {
        return rateAdded;
    }

    public void setRateAdded(Rate rateAdded) {
        this.rateAdded = rateAdded;
    }
}
