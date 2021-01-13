package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminAddReviewer extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_reviewer);

        recyclerView = findViewById(R.id.AdminCreateAddReviewView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference("CreateAdd");
        loadData();
    }

    private void loadData() {
        FirebaseRecyclerOptions<Dataload> options =
                new FirebaseRecyclerOptions.Builder<Dataload>().setQuery(databaseReference,Dataload.class).build();
        FirebaseRecyclerAdapter<Dataload, AdminReviewAddViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Dataload,AdminReviewAddViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminReviewAddViewHolder holder, int position, @NonNull Dataload model) {
                     //   Dataload obj = new Dataload(model.getCarAddress(),model.getCarModel(),model.getCarName(),model.getCarPayment(),model.getCarPrice(),model.getCarImage(),model.getCarSellerName(),model.getCarSellerContact());
                        holder.ClassCarFeedName.setText(model.getName());
                        holder.ClassCarFeedModel.setText(model.getModel());
                        holder.ClassCarFeedPrice.setText(model.getPrice());
                        holder.ClassCarFeedPayment.setText(model.getPayment());
                        holder.ClassCarFeedAddress.setText(model.getAddress());
                        holder.ClassCarFeedSellerName.setText(model.getSellerName());
                        holder.ClassCarFeedSellerContact.setText(model.getSellerContact());
                        Picasso.get().load(model.getImage()).into(holder.ClassFeedImage);
                    }

                    @NonNull
                    @Override
                    public AdminReviewAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.createaddreviewfeed,parent,false);
                        return new AdminReviewAddViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}