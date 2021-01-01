package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Regester extends AppCompatActivity {
    ImageButton BackToRegistration;
    Button GoogleSync,Register;
    GoogleSignInClient googleSignInClient;
    EditText RegisterUsername,RegisterPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        //get ids
        BackToRegistration = findViewById(R.id.BackToMainFromRegistration);
        GoogleSync = findViewById(R.id.GoogleSignIn);
        Register = findViewById(R.id.RegisterButton);
        RegisterUsername = findViewById(R.id.RegisterUser);
        RegisterPass = findViewById(R.id.RegisterPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        //Email Pass Register
        this.Register.setOnClickListener(new View.OnClickListener() {
            String email,password;
            @Override
            public void onClick(View v) {
                 email = RegisterUsername.getText().toString();
                 password = RegisterPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                }else{
                    UserRegistration(email,password);
                }
            }
        });


        final GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("322105425453-99isbl317rblqnlkcdlgl5a1ol6rff6i.apps.googleusercontent.com").requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(Regester.this, googleSignInOptions);

        GoogleSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            startActivity(new Intent(Regester.this, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


        BackToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Regester.this,LoginPage.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful()){
                String s= "Google Sign in successful!";
                displayToast(s);

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);

                    AuthCredential authCredential = null;
                    if (googleSignInAccount != null) {
                        authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                    }
                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(Regester.this, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                displayToast("Firebase authentication is successful");
                            }
                            else {
                                displayToast("Authentication Failed : " + task.getException().getMessage());
                            }
                        }
                    });
                }
                catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Register from mail
    private void UserRegistration(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Regester.this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Regester.this,"Register Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Regester.this,"Register Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}