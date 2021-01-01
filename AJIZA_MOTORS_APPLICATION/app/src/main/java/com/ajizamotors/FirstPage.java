package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {
    Button IntoCustomer,IntoDealer,IntoAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        //get ids
        IntoAdmin = findViewById(R.id.intoadmin);
        IntoCustomer = findViewById(R.id.intocustomer);
        IntoDealer = findViewById(R.id.intodealer);

        //Into login customer
        IntoCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstPage.this,LoginPage.class));
            }
        });
    }
}