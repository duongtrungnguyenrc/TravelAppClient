package com.main.travelApp.request;

import com.main.travelApp.models.ContactInfo;
import com.main.travelApp.models.HotelRoom;

import java.text.NumberFormat;
import java.util.Locale;

public class CreateOrderRequest {
    private int adults;
    private int children;
    private String paymentMethod;
    private double amount;
    private double adultPrice;
    private double childPrice;
    private long tourDateId;
    private String roomType;
    private Long hotelId = null;
    private String specialRequest;
    private ContactInfo contactInfo;

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }

    public long getTourDateId() {
        return tourDateId;
    }

    public String getRoomType() {
        return roomType;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTourDateId(long tourDateId) {
        this.tourDateId = tourDateId;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void increaseAdults(HotelRoom room) {
        if(adults < 10) {
            adults++;
            amount += adultPrice + (room != null ? room.getPrice() : 0);
        }
    }

    public void decreaseAdults(HotelRoom room) {
        if(adults > 0) {
            adults--;
            amount -= adultPrice + (room != null ? room.getPrice() : 0);
        }
    }

    public void increaseChilds(HotelRoom room) {
        if(children < 10) {
            children++;
            amount += childPrice + (
                    children % 2 == 0 ?
                    (room != null ? room.getPrice() : 0) : 0
            );
        }
    }

    public void decreaseChilds(HotelRoom room) {
        if(children > 0) {
            children--;
            amount -= childPrice + (
                    children % 2 == 0 ?
                    0 : (room != null ? room.getPrice() : 0)
            );
        }
    }

    public String getStringTotalPrice() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        return numberFormat.format(amount) + " VND";
    }
}
