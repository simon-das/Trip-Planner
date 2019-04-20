package com.example.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<PlaceItem> placeItems;
    private List<String> placeIds;
    private Context context;

    public PlaceAdapter(List<PlaceItem> placeItems, List<String> placeIds, Context context) {
        this.placeItems = placeItems;
        this.placeIds = placeIds;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.place_suggestions, viewGroup, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder placeViewHolder, int i) {
        final PlaceItem placeItem = placeItems.get(i);
        final String placeId = placeIds.get(i);
        placeViewHolder.placeName.setText(placeItem.getName());
        Glide.with(context).load(placeItem.getPicture()).into(placeViewHolder.placeImage);

        placeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), PlaceActivity.class)
                        .putExtra("placeId", placeId));
            }
        });

        Log.d("myLog", placeId);
    }

    @Override
    public int getItemCount() {
        return placeItems.size();
    }

    //PlaceViewHolder class
    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        public ImageView placeImage;
        public TextView placeName;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = (ImageView) itemView.findViewById(R.id.place_image);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
//            Log.d("myTag", placeId);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.getContext().startActivity(new Intent(v.getContext(), PlaceActivity.class)
//                    .putExtra("placeId", "ok"));
//                }
//            });
        }
    }
}
