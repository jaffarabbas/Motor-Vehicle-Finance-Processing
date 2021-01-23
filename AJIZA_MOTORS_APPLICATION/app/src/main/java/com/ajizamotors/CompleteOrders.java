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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompleteOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    CompleteOrderHolder adapter;
    TextView TotalOrderCount;
    String counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_orders);

        //ids
        TotalOrderCount = findViewById(R.id.TotalOrders);
        recyclerView = findViewById(R.id.CompleteOrdersView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference().child("CompleteOrders");
        loadData();
        //Refresh Feed Every Seconds
        FeedRefresher();
    }

    private void loadData() {
        FirebaseRecyclerOptions<CompleteOrderData> options =
                new FirebaseRecyclerOptions.Builder<CompleteOrderData>().setQuery(databaseReference,CompleteOrderData.class).build();
        adapter = new CompleteOrderHolder(options);

        recyclerView.setAdapter(adapter);

        //Count
        TotalOrders();
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
        getMenuInflater().inflate(R.menu.search_form_comlete_orders,menu);
        MenuItem item = menu.findItem(R.id.SearchCompleteOrders);
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
        FirebaseRecyclerOptions<CompleteOrderData> options =
                new FirebaseRecyclerOptions.Builder<CompleteOrderData>().setQuery(databaseReference.orderByChild("OrderCount").startAt(s).endAt(s+"\uf8ff"),CompleteOrderData.class).build();
        adapter = new CompleteOrderHolder(options);
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

    //Total Orders
    private void TotalOrders(){
        FirebaseDatabase.getInstance().getReference().child("CompleteOrders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long value = snapshot.getChildrenCount();
                TotalOrderCount.setText(String.valueOf(value));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
