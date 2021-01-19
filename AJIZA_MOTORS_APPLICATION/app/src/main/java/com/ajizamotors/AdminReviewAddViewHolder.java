package com.ajizamotors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminReviewAddViewHolder extends FirebaseRecyclerAdapter<Load,AdminReviewAddViewHolder.viewHolder> {
    public AdminReviewAddViewHolder(@NonNull FirebaseRecyclerOptions<Load> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Load model) {
        holder.ClassCarFeedName.setText(model.getName());
        holder.ClassCarFeedModel.setText(model.getCarModel());
        holder.ClassCarFeedPrice.setText(model.getPrice());
        holder.ClassCarFeedPayment.setText(model.getPayment());
        holder.ClassCarFeedAddress.setText(model.getAddress());
        holder.ClassCarFeedSellerName.setText(model.getSellerName());
        holder.ClassCarFeedSellerContact.setText(model.getSellerContact());
        Glide.with(holder.ClassFeedImage.getContext()).load(model.getImageUrl()).into(holder.ClassFeedImage);

        //Update Listener
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(unwrap(holder.ClassFeedImage.getContext()))
                        .setContentHolder(new ViewHolder(R.layout.dialog_for_admin_add_update))
                        .setExpanded(true,1100)
                        .create();

                //Getting Data from ids
                View DataView = dialogPlus.getHolderView();
                EditText imageUrl = DataView.findViewById(R.id.UpdateAdminCarImage);
                EditText name = DataView.findViewById(R.id.UpdateAdminCarName);
                EditText carModel = DataView.findViewById(R.id.UpdateAdminCarModel);
                EditText address = DataView.findViewById(R.id.UpdateUploadCarAddress);
                EditText payment = DataView.findViewById(R.id.UpdateUploadCarPayment);
                EditText price = DataView.findViewById(R.id.UpdateUploadCarPrice);
                EditText sellerName = DataView.findViewById(R.id.UpdateUploadCarSellerName);
                EditText sellerContact = DataView.findViewById(R.id.UpdateUploadCarSellerContact);
                Button UpdateButton =  DataView.findViewById(R.id.UpdateFromAdmin);

                //set text to editors
                imageUrl.setText(model.getImageUrl());
                name.setText(model.getName());
                carModel.setText(model.getCarModel());
                address.setText(model.getAddress());
                payment.setText(model.getPayment());
                price.setText(model.getPrice());
                sellerName.setText(model.getSellerName());
                sellerContact.setText(model.getSellerContact());

                dialogPlus.show();

                //Dialog Update Button
                UpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Map use to take dialog editor value to update text
                        Map<String,Object> map = new HashMap<>();
                        map.put("imageUrl",imageUrl.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("CarModel",carModel.getText().toString());
                        map.put("address",address.getText().toString());
                        map.put("payment",payment.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("sellerName",sellerName.getText().toString());
                        map.put("sellerContact",sellerContact.getText().toString());
                        //Update to database
                        FirebaseDatabase.getInstance().getReference().child("CreateAdd")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        //Delete Listener
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertBox
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ClassFeedImage.getContext());
                builder.setTitle("Delete Add");
                builder.setMessage("Do you Want to Delete the Add");
                //if yes
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("CreateAdd")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                //if No
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //--------
                    }
                });
                //show dialog box
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.createaddreviewfeed,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView ClassCarFeedName,ClassCarFeedModel,ClassCarFeedPrice,ClassCarFeedAddress,ClassCarFeedPayment,ClassCarFeedSellerName,ClassCarFeedSellerContact;
        ImageView ClassFeedImage,edit,delete;
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
            edit = itemView.findViewById(R.id.EditAdd);
            delete = itemView.findViewById(R.id.DeleteAdd);
        }
    }


    //Unwrap Activity
    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }
}
