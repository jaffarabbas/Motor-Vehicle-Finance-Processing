package com.ajizamotors;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerCartHolder extends FirebaseRecyclerAdapter<CartData, CustomerCartHolder.viewHolder> {

    public CustomerCartHolder(@NonNull FirebaseRecyclerOptions<CartData> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull CartData model) {
        holder.OrderNumber.setText(model.getOrderCount());
        holder.OrderPrice.setText(model.getPrice());
        Glide.with(holder.CustomerOrderImage.getContext()).load(model.getImageUrl()).into(holder.CustomerOrderImage);
        //Approve add From Admin

        holder.CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.CustomerOrderImage.getContext());
                builder.setTitle("Approve Add");
                builder.setMessage("Do you Want to Approve the Add");
                //if yes
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("CompleteOrders");
                        String ModelId = databaseReference.push().getKey();
                        databaseReference.child(ModelId).child("imageUrl").setValue(model.getImageUrl());
                        databaseReference.child(ModelId).child("name").setValue(model.getName());
                        databaseReference.child(ModelId).child("CarModel").setValue(model.getCarModel());
                        databaseReference.child(ModelId).child("address").setValue(model.getAddress());
                        databaseReference.child(ModelId).child("payment").setValue(model.getPayment());
                        databaseReference.child(ModelId).child("price").setValue(model.getPrice());
                        databaseReference.child(ModelId).child("sellerName").setValue(model.getSellerName());
                        databaseReference.child(ModelId).child("sellerContact").setValue(model.getSellerContact());
                        databaseReference.child(ModelId).child("AjizaComition").setValue(model.getAjizaComition());
                        databaseReference.child(ModelId).child("OrderCount").setValue(model.getOrderCount());

                        boolean flag = true;
                        if(flag){
                            FirebaseDatabase.getInstance().getReference().child("Cart")
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_cart_feeds, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder  {
        TextView OrderNumber, OrderPrice;
        ImageView CustomerOrderImage;
        Button CartButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            OrderPrice = itemView.findViewById(R.id.OrderPrice);
            CustomerOrderImage = itemView.findViewById(R.id.OrderImage);
            CartButton = itemView.findViewById(R.id.BuyOrder);
        }
    }

//    //Car Finance
//    private double FinanceOfCar(double price){
//        double commission = 10;
//        return (commission * price) / 100;
//    }
}

