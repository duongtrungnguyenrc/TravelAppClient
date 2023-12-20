package com.main.travelApp.response;

import com.main.travelApp.models.Rate;
import com.main.travelApp.models.StarDistribution;

import java.util.List;

public class RateDetailResponse {
    private double average;
    private int totalStar;
    private int pages;
    private List<Rate> rates;
    private StarDistribution starDistribution;

    public double getAverage() {
        return average;
    }

    public int getTotalStar() {
        return totalStar;
    }

    public int getPages() {
        return pages;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public StarDistribution getStarDistribution() {
        return starDistribution;
    }
}
