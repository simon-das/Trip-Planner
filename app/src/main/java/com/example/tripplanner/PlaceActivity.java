package com.example.tripplanner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends AppCompatActivity {

    //variables for fetching hotel details
    private TextView bestTimeToVisit, idealStay, placeLocation, placeDistance, placeDescription, placeName;
    private ImageView placeActivityImage;
    private String placeId;
    private FirebaseDatabase db;
    private DatabaseReference placeRef;
    private PlaceItem placeItem;

    //vaiables for fetching nearby hotels
    private RecyclerView hotelRecyclerView;
    private RecyclerView.Adapter hotelAdapter;
    private DatabaseReference hotelRef;
    private HotelItem hotelItem;
    private List<HotelItem> hotelItems;
    private List<String> hotelIds;
    private String hotelId;



    //vaiables for fetching nearby restaurants
    private RecyclerView restaurantRecyclerView;
    private RecyclerView.Adapter restaurantAdapter;
    private DatabaseReference restaurantRef;
    private RestaurantItem restaurantItem;
    private List<RestaurantItem> restaurantItems;
    private List<String> restaurantIds;
    private String restaurantId;


    //vaiables for fetching reviews
    private RecyclerView reviewRecyclerView;
    private RecyclerView.Adapter reviewAdapter;
    private DatabaseReference reviewRef;
    private ReviewItem reviewItem;
    private List<ReviewItem> reviewItems;
    private List<String> reviewIds;
    private String reviewId;


    //variables for review
    private EditText reviewEditText;
    private Button reviewButton;

    //db reference variables for storing review
    private DatabaseReference reviewRefName, reviewRefReview;

    //variables for tracking current user
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

    //        tt = (TextView) findViewById(R.id.tt); (checking line)

        //getting the specific place id
        placeId = getIntent().getStringExtra("placeId");

    //        tt.setText(s);(checking line)



        //initializing place variables with specific element of activity_place layout
        placeName = (TextView) findViewById(R.id.placeName);
        bestTimeToVisit = (TextView) findViewById(R.id.bestTimeToVisit);
        idealStay = (TextView) findViewById(R.id.idealStay);
        placeLocation = (TextView) findViewById(R.id.placeLocation);
        placeDistance = (TextView) findViewById(R.id.placeDistance);
        placeDescription = (TextView) findViewById(R.id.placeDescription);
        placeActivityImage = (ImageView) findViewById(R.id.place_activity_image);



        //db reference for place, hotels, restaurants and reviews
        db = FirebaseDatabase.getInstance();
        placeRef = db.getReference("Place/"+placeId);
        hotelRef = db.getReference("Hotel/" + placeId);
        restaurantRef = db.getReference("Restaurant/"+placeId);
        reviewRef = db.getReference("Review/"+placeId);



        //declaring a Linearlayout instance for horizontal scrolling for hotel
        LinearLayoutManager hotelLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);


        ////declaring a Linearlayout instance for horizontal scrolling for restaurant
        final LinearLayoutManager restaurantLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);



        //setting up the hotel recyclerview
        hotelRecyclerView = (RecyclerView) findViewById(R.id.hotelRecyclerView);
        hotelRecyclerView.setHasFixedSize(true);
        hotelRecyclerView.setLayoutManager(hotelLayoutManager);


        //setting up the restaurant recyclerview
        restaurantRecyclerView = (RecyclerView) findViewById(R.id.restaurantRecyclerView);
        restaurantRecyclerView.setHasFixedSize(true);
        restaurantRecyclerView.setLayoutManager(restaurantLayoutManager);


        //setting up the review recyclerview
        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviewRecyclerView);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        //declaring hotel's list variables as arraylist
        hotelItems = new ArrayList<>();
        hotelIds = new ArrayList<>();


        //declaring restaurant's list variables as arraylist
        restaurantItems = new ArrayList<>();
        restaurantIds = new ArrayList<>();


        //declaring reviews's list variables as arraylist
        reviewItems = new ArrayList<>();
        reviewIds = new ArrayList<>();



        //getting firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //getting the current user
        currentUser = mAuth.getCurrentUser();



        //initializing the review variables
        reviewEditText = (EditText) findViewById(R.id.reviewTextView);
        reviewButton = (Button) findViewById(R.id.reviewButton);



        //enabling or disabling review button depends on
        //the current user sign in or not
        if (currentUser == null)
            reviewButton.setEnabled(false);
        else
            reviewButton.setEnabled(true);


        //db write when review button clicked
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!reviewEditText.getText().toString().isEmpty()){
                    //setting the db references
                    reviewRefName = db.getReference("Review/" + placeId + "/" + currentUser.getUid() +
                            "/Name");
                    reviewRefReview = db.getReference("Review/" + placeId + "/" + currentUser.getUid() +
                            "/Review");

                    //getting the review
                    String review = reviewEditText.getText().toString();

                    //save the user name
                    reviewRefName.setValue(currentUser.getDisplayName().toString());

                    //save the review
                    reviewRefReview.setValue(review);
                    startActivity(getIntent());

                }else{
                    Toast.makeText(PlaceActivity.this, "Review field is empty", Toast.LENGTH_LONG).show();

                }

            }
        });


        //fetching place details
        placeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                placeItem = dataSnapshot.getValue(PlaceItem.class);

                placeName.setText(placeItem.getName());
                bestTimeToVisit.setText(placeItem.getBestTime());
                idealStay.setText(placeItem.getIdealStay());
                placeDistance.setText(placeItem.getDistance());
                placeLocation.setText(placeItem.getLocation());
                placeDescription.setText(placeItem.getDescription());
                Glide.with(PlaceActivity.this).load(placeItem.getPicture()).into(placeActivityImage);
                Log.d("myTag", placeItem.getBestTime());//checking through printing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //getting all nearby hotels of placeId(variable)
        hotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    hotelId = child.getKey();
                    hotelItem = child.getValue(HotelItem.class);
                    hotelItems.add(hotelItem);
                    hotelIds.add(hotelId);

                    Log.d("hotel Id",hotelId);//checking through printing
                }

                hotelAdapter = new HotelAdapter(hotelItems, hotelIds, PlaceActivity.this, placeId);
                hotelRecyclerView.setAdapter(hotelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //getting all nearby restaurants of placeId(variable)
        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    restaurantId = child.getKey();
                    restaurantItem = child.getValue(RestaurantItem.class);
                    restaurantItems.add(restaurantItem);
                    restaurantIds.add(restaurantId);

                    Log.d("tag",restaurantItem.getName());//checking through printing
                }

                restaurantAdapter = new RestaurantAdapter(restaurantItems, restaurantIds,
                        PlaceActivity.this, placeId);
                restaurantRecyclerView.setAdapter(restaurantAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //getting all nearby reviews of placeId(variable)
        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    reviewId = child.getKey();
                    reviewItem = child.getValue(ReviewItem.class);
                    reviewItems.add(reviewItem);
                    reviewIds.add(reviewId);

                    Log.d("review Id",reviewId);//checking through printing
                }

                reviewAdapter = new ReviewAdapter(reviewItems, reviewIds, PlaceActivity.this);
                reviewRecyclerView.setAdapter(reviewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
