package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerAddReview extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    CustomerAddHolder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_review);

        recyclerView = findViewById(R.id.CustomerCreateAddReviewView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference().child("ApprovedAdd");
        loadData();
        //Refresh Feed Every Seconds
        FeedRefresher();
    }

    private void loadData() {
        FirebaseRecyclerOptions<CustomerAdd> options =
                new FirebaseRecyclerOptions.Builder<CustomerAdd>().setQuery(databaseReference,CustomerAdd.class).build();
        adapter = new CustomerAddHolder(options);

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
        getMenuInflater().inflate(R.menu.search_from_cutomer,menu);
        MenuItem item = menu.findItem(R.id.SearchAddFromCustomer);
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
        FirebaseRecyclerOptions<CustomerAdd> options =
                new FirebaseRecyclerOptions.Builder<CustomerAdd>().setQuery(databaseReference.orderByChild("name").startAt(s).endAt(s+"\uf8ff"),CustomerAdd.class).build();
        adapter = new CustomerAddHolder(options);
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