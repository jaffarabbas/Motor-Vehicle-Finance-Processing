package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    ImageButton BackToMain;
    Button IntoRegistration,Login;
    EditText Username,Password;
    FirebaseAuth LoginAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //find ids
        BackToMain = findViewById(R.id.BackToMain);
        IntoRegistration = findViewById(R.id.IntoRegistration);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.LoginButton);
        LoginAuth = FirebaseAuth.getInstance();

        //Login
        Login.setOnClickListener(new View.OnClickListener() {
            String user,pass;
            @Override
            public void onClick(View v) {
                user = Username.getText().toString();
                pass = Password.getText().toString();

                if(user.equals("") && pass.equals("")){
                    Toast.makeText(LoginPage.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else{
                    LoginUser(user,pass);
                }
            }
        });
        //into registration
        IntoRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Regester.class));
            }
        });
        //back
        BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,FirstPage.class));
            }
        });
    }
    //Login method from firebase
    private void LoginUser(String name,String password) {
        LoginAuth.signInWithEmailAndPassword(name, password).addOnSuccessListener(LoginPage.this, authResult -> {
            Toast.makeText(LoginPage.this,"Login Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginPage.this, Dashboard.class));
        });

//        LoginAuth.signInWithEmailAndPassword(name,password).addOnSuccessListener(LoginPage.this, new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(LoginPage.this,"Login Successful",Toast.LENGTH_LONG).show();
//                startActivity(new Intent(LoginPage.this, Dashboard.class));
//                finish();
//            }
//        });
    }
}