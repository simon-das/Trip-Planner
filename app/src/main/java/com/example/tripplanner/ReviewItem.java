package com.example.tripplanner;

public class ReviewItem {

    private String Name, Review;

    public ReviewItem() {
    }

    public ReviewItem(String name, String review) {
        Name = name;
        Review = review;
    }

    public String getName() {
        return Name;
    }

    public String getReview() {
        return Review;
    }
}
