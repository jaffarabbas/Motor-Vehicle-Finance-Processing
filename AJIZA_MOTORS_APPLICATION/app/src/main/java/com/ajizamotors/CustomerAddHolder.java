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

public class CustomerAddHolder extends FirebaseRecyclerAdapter<CustomerAdd, CustomerAddHolder.viewHolder> {

    public CustomerAddHolder(@NonNull FirebaseRecyclerOptions<CustomerAdd> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull CustomerAdd model) {
        holder.ClassCarFeedName.setText(model.getName());
        holder.ClassCarFeedModel.setText(model.getCarModel());
        holder.ClassCarFeedPrice.setText(model.getPrice());
        holder.ClassCarFeedPayment.setText(model.getPayment());
        holder.ClassCarFeedAddress.setText(model.getAddress());
        holder.ClassCarFeedSellerName.setText(model.getSellerName());
        holder.ClassCarFeedSellerContact.setText(model.getSellerContact());
        Glide.with(holder.ClassFeedImage.getContext()).load(model.getImageUrl()).into(holder.ClassFeedImage);
        //Approve add From Admin

        holder.BuyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ClassFeedImage.getContext());
                builder.setTitle("Approve Add");
                builder.setMessage("Do you Want to Approve the Add");
                //if yes
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart");
                        String ModelId = databaseReference.push().getKey();
                        databaseReference.child(ModelId).child("imageUrl").setValue(model.getImageUrl());
                        databaseReference.child(ModelId).child("name").setValue(model.getName());
                        databaseReference.child(ModelId).child("CarModel").setValue(model.getCarModel());
                        databaseReference.child(ModelId).child("address").setValue(model.getAddress());
                        databaseReference.child(ModelId).child("payment").setValue(model.getPayment());
                        databaseReference.child(ModelId).child("price").setValue(model.getPrice());
                        databaseReference.child(ModelId).child("sellerName").setValue(model.getSellerName());
                        databaseReference.child(ModelId).child("sellerContact").setValue(model.getSellerContact());
                        databaseReference.child(ModelId).child("AjizaComition").setValue(String.valueOf(FinanceOfCar(Double.parseDouble(model.getPrice()))));

                        boolean flag = true;
                        if(flag){
                            FirebaseDatabase.getInstance().getReference().child("ApprovedAdd")
                                    .child(getRef(position).getKey()).removeValue();
                        }

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_add_view, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder  {
        TextView ClassCarFeedName, ClassCarFeedModel, ClassCarFeedPrice, ClassCarFeedAddress, ClassCarFeedPayment, ClassCarFeedSellerName, ClassCarFeedSellerContact;
        ImageView ClassFeedImage;
        Button BuyCar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ClassCarFeedName = itemView.findViewById(R.id.CustomerFeedCarName);
            ClassCarFeedModel = itemView.findViewById(R.id.CustomerFeedCarModel);
            ClassCarFeedPrice = itemView.findViewById(R.id.CustomerFeedCarPrice);
            ClassCarFeedSellerName = itemView.findViewById(R.id.CustomerFeedCarSellerName);
            ClassCarFeedSellerContact = itemView.findViewById(R.id.CustomerFeedCarSellerContact);
            ClassFeedImage = itemView.findViewById(R.id.CustomerFeedImage);
            ClassCarFeedAddress = itemView.findViewById(R.id.CustomerFeedCarAddress);
            ClassCarFeedPayment = itemView.findViewById(R.id.CustomerFeedCarPayment);
            BuyCar = itemView.findViewById(R.id.CustomerApproveFromCutomer);
        }
    }

    //Car Finance
    private double FinanceOfCar(double price){
        double commission = 10;
        return (commission * price) / 100;
    }
}
