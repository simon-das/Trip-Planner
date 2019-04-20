package com.example.tripplanner;

public class PlaceItem {

    private String Name, Picture, Description, Distance, BestTime, Location, IdealStay;

    public PlaceItem() { }

    public PlaceItem(String name, String picture, String description, String distance, String bestTime, String location, String idealStay) {
        Name = name;
        Picture = picture;
        Description = description;
        Distance = distance;
        BestTime = bestTime;
        Location = location;
        IdealStay = idealStay;
    }

    public String getDescription() {
        return Description;
    }

    public String getDistance() {
        return Distance;
    }

    public String getBestTime() {
        return BestTime;
    }

    public String getLocation() {
        return Location;
    }

    public String getIdealStay() {
        return IdealStay;
    }

    public String getPicture() {
        return Picture;
    }

    public String getName() {
        return Name;
    }

}
