package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class AdminAddReviewer extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    AdminReviewAddViewHolder adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_reviewer);

        recyclerView = findViewById(R.id.AdminCreateAddReviewView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference().child("CreateAdd");
        loadData();
    }


    private void loadData() {
        FirebaseRecyclerOptions<Load> options =
                new FirebaseRecyclerOptions.Builder<Load>().setQuery(databaseReference,Load.class).build();
        adapter = new AdminReviewAddViewHolder(options);

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.SearchAddFromAdmin);
        SearchView  searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchProcess(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                SearchProcess(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void SearchProcess(String s){
        FirebaseRecyclerOptions<Load> options =
                new FirebaseRecyclerOptions.Builder<Load>().setQuery(databaseReference.orderByChild("name").startAt(s).endAt(s+"\uf8ff"),Load.class).build();
        adapter = new AdminReviewAddViewHolder(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}