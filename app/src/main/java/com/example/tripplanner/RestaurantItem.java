package com.example.tripplanner;

public class RestaurantItem {
    private String Name, Description, Location, Picture;

    public RestaurantItem() {
    }

    public RestaurantItem(String name, String description, String location, String picture) {
        Name = name;
        Description = description;
        Location = location;
        Picture = picture;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public String getPicture() {
        return Picture;
    }
}
