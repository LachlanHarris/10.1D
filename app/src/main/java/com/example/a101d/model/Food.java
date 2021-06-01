package com.example.a101d.model;

import android.net.Uri;

public class Food {
    // THIS IS A TEST
    int foodID;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }
    //


    Uri image;
    String title;
    String description;
    String date;
    String pickUpTime;
    int quantity;
    String Location;
    int inMyList;

    public Food( String title, String description, String date, String pickUpTime, int quantity, String location, int inMyList) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.pickUpTime = pickUpTime;
        this.quantity = quantity;
        this.Location = location;
        this.inMyList = inMyList;
    }

    public Food() {

    }

    public int getInMyList() {
        return inMyList;
    }

    public void setInMyList(int inMyList) {
        this.inMyList = inMyList;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
