package com.ajizamotors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdminReviewAddViewHolder extends FirebaseRecyclerAdapter<Load,AdminReviewAddViewHolder.viewHolder> {
    public AdminReviewAddViewHolder(@NonNull FirebaseRecyclerOptions<Load> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Load model) {
        holder.ClassCarFeedName.setText(model.getName());
        holder.ClassCarFeedModel.setText(model.getModel());
        holder.ClassCarFeedPrice.setText(model.getPrice());
        holder.ClassCarFeedPayment.setText(model.getPayment());
        holder.ClassCarFeedAddress.setText(model.getAddress());
        holder.ClassCarFeedSellerName.setText(model.getSellerName());
        holder.ClassCarFeedSellerContact.setText(model.getSellerContact());
        Glide.with(holder.ClassFeedImage.getContext()).load(model.getImageUrl()).into(holder.ClassFeedImage);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.createaddreviewfeed,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView ClassCarFeedName,ClassCarFeedModel,ClassCarFeedPrice,ClassCarFeedAddress,ClassCarFeedPayment,ClassCarFeedSellerName,ClassCarFeedSellerContact;
        ImageView ClassFeedImage;
        public viewHolder(@NonNull View itemView) {
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
}
