package com.ajizamotors;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DealerApprovedAddHolder extends FirebaseRecyclerAdapter<DealerApprovedAddData, DealerApprovedAddHolder.viewHolder> {
    public DealerApprovedAddHolder(@NonNull FirebaseRecyclerOptions<DealerApprovedAddData> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull DealerApprovedAddData model) {
        holder.ClassCarFeedName.setText(model.getName());
        holder.ClassCarFeedModel.setText(model.getCarModel());
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealer_approved_add, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder  {
        TextView ClassCarFeedName, ClassCarFeedModel, ClassCarFeedPrice, ClassCarFeedAddress, ClassCarFeedPayment, ClassCarFeedSellerName, ClassCarFeedSellerContact;
        ImageView ClassFeedImage;
        Button BuyCar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ClassCarFeedName = itemView.findViewById(R.id.ApprovedFeedCarName);
            ClassCarFeedModel = itemView.findViewById(R.id.ApprovedFeedCarModel);
            ClassCarFeedPrice = itemView.findViewById(R.id.ApprovedFeedCarPrice);
            ClassCarFeedSellerName = itemView.findViewById(R.id.ApprovedFeedCarSellerName);
            ClassCarFeedSellerContact = itemView.findViewById(R.id.ApprovedFeedCarSellerContact);
            ClassFeedImage = itemView.findViewById(R.id.ApprovedFeedImage);
            ClassCarFeedAddress = itemView.findViewById(R.id.ApprovedFeedCarAddress);
            ClassCarFeedPayment = itemView.findViewById(R.id.ApprovedFeedCarPayment);
        }
    }
}
