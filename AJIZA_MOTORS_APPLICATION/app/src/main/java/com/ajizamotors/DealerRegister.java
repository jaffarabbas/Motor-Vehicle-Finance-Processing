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

public class DealerRegister extends AppCompatActivity {
    EditText DealerRegisterUsername,DealerRegisterPassword;
    Button DealerRegister,GoogleRegister;
    ImageButton Back;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_register);

        //get ids
        DealerRegisterUsername = findViewById(R.id.DealerRegesterUsername);
        DealerRegisterPassword = findViewById(R.id.DealerUserPassword);
        DealerRegister = findViewById(R.id.DealerRegesterButton);
        GoogleRegister = findViewById(R.id.DealerRegisterGoogleAuth);
        Back = findViewById(R.id.BackToDealerLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        //Email Pass Register
        this.DealerRegister.setOnClickListener(new View.OnClickListener() {
            String email,password;
            @Override
            public void onClick(View v) {
                email = DealerRegisterUsername.getText().toString();
                password = DealerRegisterPassword.getText().toString();

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

        googleSignInClient = GoogleSignIn.getClient(DealerRegister.this, googleSignInOptions);

        GoogleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 9001);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            startActivity(new Intent(DealerRegister.this, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DealerRegister.this,LoginPage.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001){
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
                                startActivity(new Intent(DealerRegister.this, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(DealerRegister.this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DealerRegister.this,"Register Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DealerRegister.this,"Register Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}