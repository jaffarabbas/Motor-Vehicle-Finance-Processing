package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DealerLogin extends AppCompatActivity {
    private final int RC_SIGN_IN = 0;
    EditText DealerRegisterUsername,DealerRegisterPassword;
    Button DealerLogin,DealerRegistration;
    ImageButton Back;
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
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
        signInButton = findViewById(R.id.GoogleLoginDealer);
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
        //Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.GoogleLoginDealer:
                        signIn();
                        break;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(DealerLogin.this, DealerDashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            displayToast("Login Successful");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Login
    private void LoginUser(String name,String password) {
        LoginAuth.signInWithEmailAndPassword(name, password).addOnSuccessListener(DealerLogin.this, authResult -> {
            Toast.makeText(DealerLogin.this,"Login Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(DealerLogin.this, DealerDashboard.class));
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}