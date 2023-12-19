package com.main.travelApp.utils;


import android.view.View;

import com.main.travelApp.ui.components.Stepper.Step;

public class TourScheduleStep extends Step<String> {

    String scheduleContent;

    public TourScheduleStep(String stepTitle, String scheduleContent) {
        super(stepTitle);
        this.scheduleContent = scheduleContent;
    }

    @Override
    public String getStepData() {
        return scheduleContent;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        return getStepData();
    }

    @Override
    protected void restoreStepData(String data) {

    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        return new IsDataValid(true);
    }

    @Override
    protected View createStepContentLayout() {
        return null;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
