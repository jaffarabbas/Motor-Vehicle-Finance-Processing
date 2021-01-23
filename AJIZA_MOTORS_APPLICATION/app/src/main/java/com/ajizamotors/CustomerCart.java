package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerCart extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    CustomerCartHolder adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        recyclerView = findViewById(R.id.CustomerCartView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference().child("Cart");
        loadData();
        //Refresh Feed Every Seconds
        FeedRefresher();

        test();
    }

    private void test() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   System.out.println("helooooooooooooooooooooooooo"+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadData() {
        FirebaseRecyclerOptions<CartData> options =
                new FirebaseRecyclerOptions.Builder<CartData>().setQuery(databaseReference,CartData.class).build();
        adapter = new CustomerCartHolder(options);

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
        getMenuInflater().inflate(R.menu.search_from_cart,menu);
        MenuItem item = menu.findItem(R.id.SearchCart);
        SearchView searchView = (SearchView)item.getActionView();
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
        FirebaseRecyclerOptions<CartData> options =
                new FirebaseRecyclerOptions.Builder<CartData>().setQuery(databaseReference.orderByChild("OrderCount").startAt(s).endAt(s+"\uf8ff"),CartData.class).build();
        adapter = new CustomerCartHolder(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    //refresh page
    public void FeedRefresher(){
        adapter.notifyDataSetChanged();
        refresh(1000);
    }

    private void refresh(int MilliSeconds) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FeedRefresher();
            }
        };
        handler.postDelayed(runnable,MilliSeconds);
    }

}
