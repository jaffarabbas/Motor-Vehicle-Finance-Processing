package com.ajizamotors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdminDashboard extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    TextView AdminProfileName,AdminProfileEmail;
    ImageView AdminProfileImage;
    Button AdminLogOut;
    LinearLayout AddReview;
    public String Username,Email,AdminProfilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        //ids
        AdminProfileName = findViewById(R.id.AdminProfileUsername);
        AdminProfileEmail = findViewById(R.id.AdminProfileUserEmail);
        AdminProfileImage = findViewById(R.id.admin_profile_image);
        AdminLogOut = findViewById(R.id.AdminLogout);
        //pages ids
        AddReview = findViewById(R.id.ReviewAdd);
        //Admin Profile
        AdminProfile();

        //pages
        this.AddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this,AdminAddReviewer.class));
                displayToast("Add Review");
            }
        });
    }

    private void AdminProfile(){
        SharedPreferences GetSharedPreferences = getSharedPreferences("profile",MODE_PRIVATE);
        if(GetSharedPreferences.getAll() != null){
            Username = GetSharedPreferences.getString("name","Username");
            Email = GetSharedPreferences.getString("email","Mail");
            AdminProfilePhoto = GetSharedPreferences.getString("image","admin_pic.jpg");

            AdminProfileName.setText(Username);
            AdminProfileEmail.setText(Email);
            Glide.with(this).load(String.valueOf(AdminProfilePhoto)).into(AdminProfileImage);
        }else{
            System.out.println("Empty");
        }
    }
    //log out
    public void BackPress(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Log out")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void signOut() {
        SharedPreferences GetSharedPreferences = getSharedPreferences("profile",MODE_PRIVATE);
        GetSharedPreferences.edit().clear().apply();
        startActivity(new Intent(AdminDashboard.this,AdminLogin.class));
        finish();
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}