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

public class CompleteOrderHolder extends FirebaseRecyclerAdapter<CompleteOrderData, CompleteOrderHolder.viewHolder> {

    public CompleteOrderHolder(@NonNull FirebaseRecyclerOptions<CompleteOrderData> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull CompleteOrderData model) {
        holder.OrderNumber.setText(model.getOrderCount());
        holder.OrderPrice.setText(model.getPrice());
        Glide.with(holder.CustomerOrderImage.getContext()).load(model.getImageUrl()).into(holder.CustomerOrderImage);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_orders, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder  {
        TextView OrderNumber, OrderPrice;
        ImageView CustomerOrderImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumber = itemView.findViewById(R.id.CompleteOrderNumber);
            OrderPrice = itemView.findViewById(R.id.CompleteOrderPrice);
            CustomerOrderImage = itemView.findViewById(R.id.CompleteOrderImage);
        }
    }

//    //Car Finance
//    private double FinanceOfCar(double price){
//        double commission = 10;
//        return (commission * price) / 100;
//    }
}

