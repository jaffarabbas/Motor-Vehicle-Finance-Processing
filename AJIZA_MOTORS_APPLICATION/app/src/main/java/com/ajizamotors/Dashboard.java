package com.ajizamotors;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Dashboard extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    ImageView CustomerUserMainImage;
    TextView CustomerMainUsername,CustomerMainEmail;
    GoogleSignInClient googleSignInClient;
    Button Logout;
    LinearLayout AddView,CartCard;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        CustomerUserMainImage = findViewById(R.id.CustomerUserImage);
        CustomerMainUsername =(TextView) findViewById(R.id.CustomerUsername);
        CustomerMainEmail = (TextView) findViewById(R.id.CustomerEmail);
        Logout = findViewById(R.id.CustomerLogOut);

        CartCard = findViewById(R.id.Cart);
        AddView = findViewById(R.id.viewAdd);
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        TextView navUsername = (TextView) headerView.findViewById(R.id.CustomerUsername);
//        TextView navEmail = (TextView) headerView.findViewById(R.id.CustomerEmail);
//        ImageView navImage = (ImageView) headerView.findViewById(R.id.CustomerUserImage);
        //Current user work
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            CustomerMainUsername.setText(personName);
            CustomerMainEmail.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(CustomerUserMainImage);
        }

        //Add view
        AddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CustomerAddReview.class));
                displayToast("Add Page");
            }
        });

        //Cart
        CartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CustomerCart.class));
                displayToast("Cart Page");
            }
        });

        //logout
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //dialog box implementation
//                BackPress(v);
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//
//    //menu item
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menu){
//        if(menu.getItemId() == R.id.action_Logout){
//            signOut();
//        }else{
//            return super.onOptionsItemSelected(menu);
//        }
//        return true;
//    }

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
//
    //Sign out
    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(Dashboard.this,FirstPage.class));
                finish();
            }
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}