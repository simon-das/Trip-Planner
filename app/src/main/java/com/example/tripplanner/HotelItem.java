package com.example.tripplanner;

public class HotelItem {

    private String Name, Picture, Description, IdealPrice, Location;


    public HotelItem() {
    }

    public HotelItem(String name, String picture, String description, String idealPrice, String location) {
        Name = name;
        Picture = picture;
        Description = description;
        IdealPrice = idealPrice;
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public String getPicture() {
        return Picture;
    }

    public String getDescription() {
        return Description;
    }

    public String getIdealPrice() {
        return IdealPrice;
    }

    public String getLocation() {
        return Location;
    }
}
