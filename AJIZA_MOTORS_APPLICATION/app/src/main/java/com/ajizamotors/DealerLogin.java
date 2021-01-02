package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DealerLogin extends AppCompatActivity {
    EditText DealerRegisterUsername,DealerRegisterPassword;
    Button DealerGoogleLogin,DealerLogin,DealerRegistration;
    ImageButton Back;
    FirebaseAuth LoginAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_login);

        //ids
        DealerRegisterUsername = findViewById(R.id.DealerUsername);
        DealerRegisterPassword = findViewById(R.id.DealerPassword);
        DealerLogin = findViewById(R.id.DealerLogin);
        Back = findViewById(R.id.BackToFirstPage);
        DealerRegistration = findViewById(R.id.IntoDealerRegistration);

        //Login
        this.DealerLogin.setOnClickListener(new View.OnClickListener() {
            String user,pass;
            @Override
            public void onClick(View v) {
                user = DealerRegisterUsername.getText().toString();
                pass = DealerRegisterPassword.getText().toString();
                if(user.equals("") && pass.equals("")){
                    Toast.makeText(DealerLogin.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else{
                    LoginUser(user,pass);
                }
            }
        });
        //Registration
        this.DealerRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerLogin.this,DealerRegister.class));
            }
        });
        //Back To First page
        this.Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerLogin.this,FirstPage.class));
            }
        });
    }

    //Login
    private void LoginUser(String name,String password) {
        LoginAuth.signInWithEmailAndPassword(name, password).addOnSuccessListener(DealerLogin.this, authResult -> {
            Toast.makeText(DealerLogin.this,"Login Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(DealerLogin.this, DealerDashboard.class));
        });
    }
}