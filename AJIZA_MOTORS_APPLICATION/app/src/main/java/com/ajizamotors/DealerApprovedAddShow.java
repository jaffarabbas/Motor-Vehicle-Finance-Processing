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

public class DealerApprovedAddShow extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    DealerApprovedAddHolder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_approved_add_show);

        recyclerView = findViewById(R.id.DealerApprovedAdds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = firebaseDatabase.getReference().child("ApprovedAdd");
        loadData();
        //Refresh Feed Every Seconds
        FeedRefresher();
    }

    private void loadData() {
        FirebaseRecyclerOptions<DealerApprovedAddData> options =
                new FirebaseRecyclerOptions.Builder<DealerApprovedAddData>().setQuery(databaseReference,DealerApprovedAddData.class).build();
        adapter = new DealerApprovedAddHolder(options);

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
        getMenuInflater().inflate(R.menu.search_dealer_approved_add,menu);
        MenuItem item = menu.findItem(R.id.SearchDealerApprovedOrders);
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
        FirebaseRecyclerOptions<DealerApprovedAddData> options =
                new FirebaseRecyclerOptions.Builder<DealerApprovedAddData>().setQuery(databaseReference.orderByChild("name").startAt(s).endAt(s+"\uf8ff"),DealerApprovedAddData.class).build();
        adapter = new DealerApprovedAddHolder(options);
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