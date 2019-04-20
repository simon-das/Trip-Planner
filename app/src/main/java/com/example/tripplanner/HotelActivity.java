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

public class HotelActivity extends AppCompatActivity {

    //variables for fetching hotel details
    private TextView hotelIdealPrice, hotelLocation, hotelDescription, hotelNameActivity;
    private ImageView hotelActivityImage;
    private String hotelId, placeId;
    private FirebaseDatabase db;
    private DatabaseReference hotelRef;
    private HotelItem hotelItem;


    //vaiables for fetching similar hotels
    private RecyclerView similarHotelRecyclerView;
    private RecyclerView.Adapter similarHotelAdapter;
    private DatabaseReference similarHotelRef;
    private HotelItem similarHotelItem;
    private List<HotelItem> similarHotelItems;
    private List<String> similarHotelIds;
    private String similarHotelId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);


        //geting the placeId and hotelId
        placeId = getIntent().getStringExtra("placeId");
        hotelId = getIntent().getStringExtra("hotelId");

        Log.d("hotel id specific", hotelId);

        //initializing hotel variables with specific element of activity_hotel layout
        hotelActivityImage = (ImageView) findViewById(R.id.hotel_activity_image);
        hotelIdealPrice = (TextView) findViewById(R.id.hotelIdealPrice);
        hotelLocation = (TextView) findViewById(R.id.hotelLocation);
        hotelDescription = (TextView) findViewById(R.id.hotelDescription);
        hotelNameActivity = (TextView) findViewById(R.id.hotelNameActivity);


        //db reference for hotels and similar hotels
        db = FirebaseDatabase.getInstance();
        hotelRef = db.getReference("Hotel/" + placeId + "/" + hotelId );
        similarHotelRef = db.getReference("Hotel/" + placeId);


        //declaring a Linearlayout instance for horizontal scrolling for similar hotel
        LinearLayoutManager similarHotelLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);



        //setting up the similar hotel recyclerview
        similarHotelRecyclerView = (RecyclerView) findViewById(R.id.similarHotelRecyclerView);
        similarHotelRecyclerView.setHasFixedSize(true);
        similarHotelRecyclerView.setLayoutManager(similarHotelLayoutManager);


        //declaring similar hotel's list variables as arraylist
        similarHotelItems = new ArrayList<>();
        similarHotelIds = new ArrayList<>();


        //fetching hotel details
        hotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hotelItem = dataSnapshot.getValue(HotelItem.class);

                hotelNameActivity.setText(hotelItem.getName());
                hotelIdealPrice.setText(hotelItem.getIdealPrice());
                hotelLocation.setText(hotelItem.getLocation());
                hotelDescription.setText(hotelItem.getDescription());
                Glide.with(HotelActivity.this).load(hotelItem.getPicture()).into(hotelActivityImage);

                Log.d("myTag", hotelItem.getName());//checking through printing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //getting all similar hotels of placeId(variable)
        similarHotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    similarHotelId = child.getKey();
                    Log.d("similar hotel Id before",similarHotelId);//checking through printing
                    if(similarHotelId != hotelId){
                        similarHotelItem = child.getValue(HotelItem.class);
                        similarHotelItems.add(similarHotelItem);
                        similarHotelIds.add(similarHotelId);
                        Log.d("similarHotelIdInsideIf",similarHotelId);//checking through printing
                    }else {

                    }

                    Log.d("similar hotel Id",similarHotelId);//checking through printing
                    Log.d("hotel Id",hotelId);//checking through printing
                }

                similarHotelAdapter = new HotelAdapter(similarHotelItems, similarHotelIds,
                        HotelActivity.this, placeId);
                similarHotelRecyclerView.setAdapter(similarHotelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
