package com.ajizamotors;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    private final int RC_SIGN_IN = 0;
    ImageButton BackToMain;
    Button IntoRegistration,CustomerGoogleLogin;
    SignInButton signInButton;
    EditText Username,Password;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth LoginAuth;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //find ids
        BackToMain = findViewById(R.id.BackToMain);
        IntoRegistration = findViewById(R.id.IntoRegistration);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        signInButton = findViewById(R.id.GoogleLogin);
        LoginAuth = FirebaseAuth.getInstance();

        //Login
        this.signInButton.setOnClickListener(new View.OnClickListener() {
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



        //Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.GoogleLogin:
                        signIn();
                        break;
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
            startActivity(new Intent(LoginPage.this, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            displayToast("Login IN");
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

    //Login method from firebase
    private void LoginUser(String name,String password) {
        LoginAuth.signInWithEmailAndPassword(name, password).addOnSuccessListener(LoginPage.this, authResult -> {
            Toast.makeText(LoginPage.this,"Login Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginPage.this, Dashboard.class));
        });
    }
    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}