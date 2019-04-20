package com.example.tripplanner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private RecyclerView.Adapter placeAdapter;
    private FirebaseDatabase db;
    private DatabaseReference placeRef;

    private PlaceItem placeItem;

    private List<PlaceItem> placeItems;
    private List<String> placeIds;
    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        placeItems = new ArrayList<>();
        placeIds = new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        placeRef = db.getReference("Place");

        //getting all places through the adapter
        placeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    placeId = child.getKey();
                    placeItem = child.getValue(PlaceItem.class);
                    placeItems.add(placeItem);
                    placeIds.add(placeId);

                }

                placeAdapter = new PlaceAdapter(placeItems, placeIds, MainActivity.this);
                mainRecyclerView.setAdapter(placeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
