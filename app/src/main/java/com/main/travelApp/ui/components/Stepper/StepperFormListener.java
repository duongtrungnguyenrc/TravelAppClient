package com.main.travelApp.ui.components.Stepper;


public interface StepperFormListener {
    default void onCompletedForm(){}
    default void onCancelledForm(){}
    default void onStepAdded(int index, Step<?> addedStep){}
    default void onStepRemoved(int index){}
}