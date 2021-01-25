package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DealerFinance extends AppCompatActivity {
    TextView Total;
    ListView productRates;
    double total;
    ArrayList<String> list = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_finance);

        productRates = findViewById(R.id.PaymentList);
        Total = findViewById(R.id.TotalPayment);

        final ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(DealerFinance.this, android.R.layout.simple_list_item_1,list);
        productRates.setAdapter(productAdapter);
        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("CompleteOrders");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String price = snapshot.child("price").getValue().toString();
                String commission = snapshot.child("AjizaComition").getValue().toString();
                double Price = Double.parseDouble(price);
                double Commission = Double.parseDouble(commission);
                double TotalPrice = Price-Commission;
                list.add(String.valueOf(TotalPrice));
                productAdapter.notifyDataSetChanged();
                //Total
                total+=TotalPrice;
                Total.setText(String.valueOf(total));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}