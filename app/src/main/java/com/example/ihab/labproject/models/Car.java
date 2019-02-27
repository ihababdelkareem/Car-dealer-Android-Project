package com.example.ihab.labproject.models;

import java.util.ArrayList;

public class Car {
   public static ArrayList<Car> allCars = new ArrayList<>();
    int id;
    int year;
    String make;
    String model;
    String distance;
    int price;
    boolean accidents;
    boolean offers;

    public Car(int year, String make, String model, String distance, int price, boolean accidents, boolean offers,int id) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.distance = distance;
        this.price = price;
        this.accidents = accidents;
        this.offers = offers;
        this.id = id;
    }
    public Car(){

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAccidents() {
        return accidents;
    }

    public void setAccidents(boolean accidents) {
        this.accidents = accidents;
    }

    public boolean isOffers() {
        return offers;
    }

    public void setOffers(boolean offers) {
        this.offers = offers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "year=" + year +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", distance='" + distance + '\'' +
                ", price=" + price +
                ", accidents=" + accidents +
                ", offers=" + offers +
                '}';
    }
}
