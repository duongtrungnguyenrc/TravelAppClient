package com.main.travelApp.models;

public class Order {
    private Long id;
    private String orderDate;
    private int adults;
    private int children;
    private String departDate;
    private String endDate;
    private String paymentMethod;
    private String status;
    private double totalPrice;
    private String specialRequest;
    private GeneralTour tour;
    private ContactInfo contactInfo;
    private Hotel hotel;

    public Order(Long id, String orderDate, int adults, int children, String departDate, String endDate, String paymentMethod, String status, double totalPrice, String specialRequest, GeneralTour tour, ContactInfo contactInfo, Hotel hotel) {
        this.id = id;
        this.orderDate = orderDate;
        this.adults = adults;
        this.children = children;
        this.departDate = departDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.totalPrice = totalPrice;
        this.specialRequest = specialRequest;
        this.tour = tour;
        this.contactInfo = contactInfo;
        this.hotel = hotel;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public GeneralTour getTour() {
        return tour;
    }

    public void setTour(GeneralTour tour) {
        this.tour = tour;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
