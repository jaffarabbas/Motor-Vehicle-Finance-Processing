package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdminLogin extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();;
    DatabaseReference databaseReference;
    EditText AdminUserName,AdminPassword;
    public final LinkedList<AdminProfile> list = new LinkedList<AdminProfile>();
    Button AdminLoginButton,newAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        AdminUserName = findViewById(R.id.AdminUserName);
        AdminPassword = findViewById(R.id.AdminPassword);
        newAdmin = findViewById(R.id.NewAdmin);
        AdminLoginButton = findViewById(R.id.AdminLogin);
        databaseReference = firebaseDatabase.getReference().child("AdminDatabase");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String email,name,password,imageUrl;
                    email = snapshot1.child("email").getValue().toString();
                    password = snapshot1.child("password").getValue().toString();
                    name = snapshot1.child("name").getValue().toString();
                    imageUrl = snapshot1.child("imageUrl").getValue().toString();
                    list.add(new AdminProfile(email,imageUrl,name,password));
                }
                System.out.println(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Login
        this.AdminLoginButton.setOnClickListener(new View.OnClickListener() {
            String name,password;
            @Override
            public void onClick(View v) {
                name = AdminUserName.getText().toString();
                password = AdminPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }else{
                    UserLogin(name,password);
                }
            }
        });

        newAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,AdminRegister.class));
            }
        });
    }

    private void UserLogin(String name, String password) {
        int count_admin_profile = 0;
        boolean flag = false;
        while (list.size()!=count_admin_profile){
            System.out.println(name+"\n"+password);
            System.out.println("name : "+list.get(count_admin_profile).getName()+"\npass : "+list.get(count_admin_profile).getPassword());
            if(list.get(count_admin_profile).getName().equals(name) && list.get(count_admin_profile).getPassword().equals(password)){
                System.out.println("YEs");
                SharedPreferences sharedPreferences = getSharedPreferences("profile",MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                String LocalEmail = list.get(count_admin_profile).getEmail();
                String LocalImage = list.get(count_admin_profile).getImageUrl();
                flag = true;
                editor.putString("name", name);
                editor.putString("email",LocalEmail);
                editor.putString("image",LocalImage);
                editor.apply();
                break;
            }else{
                System.out.println("No");
                System.out.println(count_admin_profile);
                flag = false;
            }
            count_admin_profile++;
        }
        if(flag){
            startActivity(new Intent(AdminLogin.this,AdminDashboard.class));
            displayToast("Logged In");
        }else{
            displayToast("Wrong Information!!");
        }
    }


    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}