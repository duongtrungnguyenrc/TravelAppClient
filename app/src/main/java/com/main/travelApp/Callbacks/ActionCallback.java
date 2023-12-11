package com.main.travelApp.Callbacks;

public interface ActionCallback<T>{
    default void onSuccess(T result){}
    default void onSuccess(){}
    default void onProgress(T progress) {}
    default void onFailure(String message){}
    default void onError(Exception e){}
}
