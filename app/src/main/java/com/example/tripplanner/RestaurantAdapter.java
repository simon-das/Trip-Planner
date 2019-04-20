package com.example.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<RestaurantItem> restaurantItems;
    private List<String> restaurantIds;
    private Context context;
    private String placeId;

    public RestaurantAdapter(List<RestaurantItem> restaurantItems, List<String> restaurantIds, Context context, String placeId) {
        this.restaurantItems = restaurantItems;
        this.restaurantIds = restaurantIds;
        this.context = context;
        this.placeId = placeId;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_suggestions,
                viewGroup, false);

        return new RestaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int i) {
        final RestaurantItem restaurantItem = restaurantItems.get(i);
        final String restaurantId = restaurantIds.get(i);
        restaurantViewHolder.restaurantName.setText(restaurantItem.getName());
        Glide.with(context).load(restaurantItem.getPicture()).into(restaurantViewHolder.restaurantImage);

        restaurantViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), RestaurantActivity.class)
                        .putExtra("restaurantId", restaurantId).putExtra("placeId", placeId));
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantItems.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        private ImageView restaurantImage;
        private TextView restaurantName;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantImage = (ImageView) itemView.findViewById(R.id.restaurant_image);
            restaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
        }
    }

}
