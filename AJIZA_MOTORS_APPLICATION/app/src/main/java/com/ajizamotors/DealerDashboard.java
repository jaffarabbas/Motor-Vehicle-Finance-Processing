package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DealerDashboard extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    ImageView DealerUserMainImage;
    TextView DealerMainUsername, DealerMainEmail;
    GoogleSignInClient googleSignInClient;
    LinearLayout CreateAddInApp;
    Button Logout;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_dashboard);
//
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        DealerUserMainImage = findViewById(R.id.DealerUserImage);
        DealerMainUsername = (TextView) findViewById(R.id.DealerUsername);
        DealerMainEmail = (TextView) findViewById(R.id.DealerEmail);
        CreateAddInApp = findViewById(R.id.CreateAdd);
        Logout = findViewById(R.id.DealerLogout);

        //Current user work
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            DealerMainUsername.setText(personName);
            DealerMainEmail.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(DealerUserMainImage);
        }else{
            System.out.println("Error!!!!!!!!");
        }

        //Create Add
        CreateAddInApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerDashboard.this,CreateAdd.class));
            }
        });
    }

    public void BackPress(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Log out")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Sign out
    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(DealerDashboard.this,FirstPage.class));
                finish();
            }
        });
    }

}