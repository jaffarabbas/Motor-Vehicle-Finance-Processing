package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminRegister extends AppCompatActivity {
    ImageView AdminProfile;
    Button AdminRegister;
    ProgressBar waiter;
    EditText Admin_name,Admin_email,Admin_password,Admin_confirm_password;
    Uri imageUri;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminDatabase");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        AdminProfile = findViewById(R.id.AdminAddImage);
        AdminRegister = findViewById(R.id.RegisterAdmin);
        Admin_name = findViewById(R.id.AdminName);
        Admin_email = findViewById(R.id.AdminEmail);
        Admin_password = findViewById(R.id.AdminPassword);
        Admin_confirm_password = findViewById(R.id.AdminConfirmPassword);
        waiter = findViewById(R.id.WaitToUploadAdmin);

        //Progressbar calling
        ProgressBar(false);

        //Image click
        AdminProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        //Upload Image
        AdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Admin_name.getText().toString().equals("")){
                    displayToast("Admin Name is Empty!!");
                }else if(Admin_email.getText().toString().equals("")){
                    displayToast("Admin Email is Empty!!");
                }else if(Admin_password.getText().toString().equals("")){
                    displayToast("Admin Password is Empty!!");
                }else if(Admin_confirm_password.getText().toString().equals("")){
                    displayToast("Confirm Password is Empty!!");
                }else if(!Admin_confirm_password.getText().toString().equals(Admin_confirm_password.getText().toString())){
                    displayToast("Confirm Password is not Match!!");
                }else if(imageUri == null){
                    displayToast("Please Select Image!!");
                }else{
                    UploadToFirebase(imageUri);
                }
            }
        });
    }



    //Upload Images
    private void UploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + fileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    String AdminName,AdminEmail,AdminPassword;
                    @Override
                    public void onSuccess(Uri uri) {
                        AdminName = Admin_name.getText().toString();
                        AdminEmail = Admin_email.getText().toString();
                        AdminPassword = Admin_password.getText().toString();
                        Model model = new Model(uri.toString());
                        String ModelId = databaseReference.push().getKey();
                        databaseReference.child(ModelId).setValue(model);
                        databaseReference.child(ModelId).child("name").setValue(AdminName);
                        databaseReference.child(ModelId).child("email").setValue(AdminEmail);
                        databaseReference.child(ModelId).child("password").setValue(AdminPassword);
                        ProgressBar(false);
                        displayToast("Upload Successfully");
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                waiter.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ProgressBar(false);
                displayToast("Uploading Failed!!!");
            }
        });
    }

    private String fileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

    //Activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!= null){
            imageUri = data.getData();
            AdminProfile.setImageURI(imageUri);
        }
    }

    //progress bar visibility
    private void ProgressBar(boolean flag){
        if(flag){
            waiter.setVisibility(View.VISIBLE);
        }else{
            waiter.setVisibility(View.INVISIBLE);
        }
    }

    //display toast
    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}