package com.example.tripplanner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    //variables for fetching hotel details
    private TextView restaurantLocation, restaurantDescription, restaurantNameActivity;
    private ImageView restaurantActivityImage;
    private String restaurantId, placeId;
    private FirebaseDatabase db;
    private DatabaseReference restaurantRef;
    private RestaurantItem restaurantItem;



    //vaiables for fetching similar restaurants
    private RecyclerView similarRestaurantRecyclerView;
    private RecyclerView.Adapter similarRestaurantAdapter;
    private DatabaseReference similarRestaurantRef;
    private RestaurantItem similarRestaurantItem;
    private List<RestaurantItem> similarRestaurantItems;
    private List<String> similarRestaurantIds;
    private String similarRestaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //geting the placeId and hotelId
        placeId = getIntent().getStringExtra("placeId");
        restaurantId = getIntent().getStringExtra("restaurantId");

        //initializing restaurant variables with specific element of activity_restaurant layout
        restaurantActivityImage = (ImageView) findViewById(R.id.restaurant_activity_image);
        restaurantLocation = (TextView) findViewById(R.id.restaurantLocation);
        restaurantDescription = (TextView) findViewById(R.id.restaurantDescription);
        restaurantNameActivity = (TextView) findViewById(R.id.restaurantNameActivity);



        //db reference for restaurants and similar restaurants
        db = FirebaseDatabase.getInstance();
        restaurantRef = db.getReference("Restaurant/" + placeId + "/" + restaurantId );
        similarRestaurantRef = db.getReference("Restaurant/" + placeId);



        //declaring a Linearlayout instance for horizontal scrolling for similar restaurant
        LinearLayoutManager similarRestaurantLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);



        //setting up the similar hotel recyclerview
        similarRestaurantRecyclerView = (RecyclerView) findViewById(R.id.similarRestaurantRecyclerView);
        similarRestaurantRecyclerView.setHasFixedSize(true);
        similarRestaurantRecyclerView.setLayoutManager(similarRestaurantLayoutManager);


        //declaring similar hotel's list variables as arraylist
        similarRestaurantItems = new ArrayList<>();
        similarRestaurantIds = new ArrayList<>();



        //fetching hotel details
        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                restaurantItem = dataSnapshot.getValue(RestaurantItem.class);

                restaurantNameActivity.setText(restaurantItem.getName());
                restaurantLocation.setText(restaurantItem.getLocation());
                restaurantDescription.setText(restaurantItem.getDescription());
                Glide.with(RestaurantActivity.this)
                        .load(restaurantItem.getPicture()).into(restaurantActivityImage);

               // Log.d("myTag", restaurantItem.getName());//checking through printing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //getting all similar restaurants of placeId(variable)
        similarRestaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    similarRestaurantId = child.getKey();
                    Log.d("similar hotel Id before",similarRestaurantId);//checking through printing
                    if(similarRestaurantId != restaurantId){
                        similarRestaurantItem = child.getValue(RestaurantItem.class);
                        similarRestaurantItems.add(similarRestaurantItem);
                        similarRestaurantIds.add(similarRestaurantId);
                        Log.d("similarHotelIdInsideIf",similarRestaurantId);//checking through printing
                    }else {

                    }

                    Log.d("similar hotel Id",similarRestaurantId);//checking through printing
                    Log.d("hotel Id",restaurantId);//checking through printing
                }

                similarRestaurantAdapter = new RestaurantAdapter(similarRestaurantItems, similarRestaurantIds,
                        RestaurantActivity.this, placeId);
                similarRestaurantRecyclerView.setAdapter(similarRestaurantAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
