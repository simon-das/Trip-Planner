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

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<HotelItem> hotelItems;
    private List<String> hotelIds;
    private Context context;
    private String placeId;

    public HotelAdapter(List<HotelItem> hotelItems, List<String> hotelIds, Context context, String placeId) {
        this.hotelItems = hotelItems;
        this.hotelIds = hotelIds;
        this.context = context;
        this.placeId = placeId;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_suggestions, viewGroup,
                false);
        return new HotelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder hotelViewHolder, int i) {
        final HotelItem hotelItem = hotelItems.get(i);
        final String hotelId = hotelIds.get(i);
        hotelViewHolder.hotelName.setText(hotelItem.getName());
        Glide.with(context).load(hotelItem.getPicture()).into(hotelViewHolder.hotelImage);

        hotelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), HotelActivity.class)
                        .putExtra("hotelId", hotelId).putExtra("placeId", placeId));
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelItems.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder{

        private ImageView hotelImage;
        private TextView hotelName;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = (ImageView) itemView.findViewById(R.id.hotel_image);
            hotelName = (TextView) itemView.findViewById(R.id.hotel_name);
        }
    }

}
