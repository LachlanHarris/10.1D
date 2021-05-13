package com.example.a61d.model;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.Date;

public class Food {
    int image;
    String title;
    String description;
    String date;
    String pickUpTime;
    int quantity;
    String Location;
    int inMyList;

    public Food( String title, String description, String date, String pickUpTime, int quantity, String location, int inMyList) {
        //moved image setting to a setter because its a pain when creating new food items
        //this.image = image;
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

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
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
