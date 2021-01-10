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
    View view;
    public AdminReviewAddViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void SetDetails(Context context,String CarName,String CarModel,String CarPrice,String CarPayment,String CarAddress,String CarSellerName,String CarSellerContact,String CarImage){
        TextView ClassCarFeedName,ClassCarFeedModel,ClassCarFeedPrice,ClassCarFeedAddress,ClassCarFeedPayment,ClassCarFeedSellerName,ClassCarFeedSellerContact;
        ImageView ClassFeedImage;
        ClassCarFeedName = view.findViewById(R.id.FeedCarName);
        ClassCarFeedModel = view.findViewById(R.id.FeedCarModel);
        ClassCarFeedPrice = view.findViewById(R.id.FeedCarPrice);
        ClassCarFeedSellerName = view.findViewById(R.id.FeedCarSellerName);
        ClassCarFeedSellerContact = view.findViewById(R.id.FeedCarSellerContact);
        ClassFeedImage = view.findViewById(R.id.FeedImage);
        ClassCarFeedAddress = view.findViewById(R.id.FeedCarAddress);
        ClassCarFeedPayment = view.findViewById(R.id.FeedCarPayment);
        ClassCarFeedName.setText(CarName);
        ClassCarFeedModel.setText(CarModel);
        ClassCarFeedPrice.setText(CarPrice);
        ClassCarFeedPayment.setText(CarPayment);
        ClassCarFeedAddress.setText(CarAddress);
        ClassCarFeedSellerName.setText(CarSellerName);
        ClassCarFeedSellerContact.setText(CarSellerContact);
        Picasso.get().load(CarImage).into(ClassFeedImage);

        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
}
