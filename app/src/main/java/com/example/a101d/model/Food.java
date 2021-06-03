package com.example.a101d.model;

import android.net.Uri;

public class Food {

    int foodID;
    Uri image;
    String title;
    String description;
    String date;
    String pickUpTime;
    int quantity;
    String Location;
    int inMyList;
    int inMyCart;



    public Food(String title, String description, String date, String pickUpTime, int quantity, String location, int inMyList, int inMyCart) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.pickUpTime = pickUpTime;
        this.quantity = quantity;
        this.Location = location;
        this.inMyList = inMyList;
        this.inMyCart = inMyCart;
    }

    public Food() {

    }

    public int getInMyCart() {
        return inMyCart;
    }

    public void setInMyCart(int inMyCart) {
        this.inMyCart = inMyCart;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
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
