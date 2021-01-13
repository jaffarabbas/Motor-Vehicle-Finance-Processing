package com.ajizamotors;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AdminReviewAddViewHolder extends RecyclerView.ViewHolder {
    TextView ClassCarFeedName,ClassCarFeedModel,ClassCarFeedPrice,ClassCarFeedAddress,ClassCarFeedPayment,ClassCarFeedSellerName,ClassCarFeedSellerContact;
    ImageView ClassFeedImage;
    public AdminReviewAddViewHolder(@NonNull View itemView) {
        super(itemView);
        ClassCarFeedName = itemView.findViewById(R.id.FeedCarName);
        ClassCarFeedModel = itemView.findViewById(R.id.FeedCarModel);
        ClassCarFeedPrice = itemView.findViewById(R.id.FeedCarPrice);
        ClassCarFeedSellerName = itemView.findViewById(R.id.FeedCarSellerName);
        ClassCarFeedSellerContact = itemView.findViewById(R.id.FeedCarSellerContact);
        ClassFeedImage = itemView.findViewById(R.id.FeedImage);
        ClassCarFeedAddress = itemView.findViewById(R.id.FeedCarAddress);
        ClassCarFeedPayment = itemView.findViewById(R.id.FeedCarPayment);
    }
}
