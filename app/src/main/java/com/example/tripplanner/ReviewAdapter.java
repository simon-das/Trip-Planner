package com.example.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<ReviewItem> reviewItems;
    private List<String> reviewIds;
    private Context context;

    public ReviewAdapter(List<ReviewItem> reviewItems, List<String> reviewIds, Context context) {
        this.reviewItems = reviewItems;
        this.reviewIds = reviewIds;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review, viewGroup, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {

        final ReviewItem reviewItem = reviewItems.get(i);
        final String reviewId = reviewIds.get(i);

        reviewViewHolder.reviewerName.setText(reviewItem.getName());
        reviewViewHolder.review.setText(reviewItem.getReview());

//        placeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.getContext().startActivity(new Intent(v.getContext(), PlaceActivity.class)
//                        .putExtra("placeId", placeId));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        public TextView reviewerName, review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewerName = itemView.findViewById(R.id.reviewerName);
            review = itemView.findViewById(R.id.review);
        }
    }

}
