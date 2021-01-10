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

public class AdminAddReviewer extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_reviewer);

        recyclerView = findViewById(R.id.AdminCreateAddReviewView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CreateAdd");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminFeedReviewClass> options =
                new FirebaseRecyclerOptions.Builder<AdminFeedReviewClass>().setQuery(databaseReference,AdminFeedReviewClass.class).build();
        FirebaseRecyclerAdapter<AdminFeedReviewClass,AdminReviewAddViewHolder>firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<AdminFeedReviewClass, AdminReviewAddViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminReviewAddViewHolder holder, int position, @NonNull AdminFeedReviewClass model) {
                        holder.SetDetails(getApplicationContext(),model.getCarName(),model.getCarModel(),model.getCarPrice(),model.getCarPayment(),model.getCarAddress(),model.getCarSellerName(),model.getCarSellerContact(),model.getCarImage());
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