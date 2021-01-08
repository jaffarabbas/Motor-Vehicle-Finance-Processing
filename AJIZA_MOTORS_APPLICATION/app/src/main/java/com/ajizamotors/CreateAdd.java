package com.ajizamotors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAdd extends AppCompatActivity {
    Button UploadMainAdd;
    EditText UploadMainName,UploadMainModel,UploadMainAddress,UploadMainPrice,UploadMainSellerName,UploadMainSellerContact;
    RadioButton UploadMainPayment;
    RadioGroup UploadPaymentMainGroup;
    ImageView UploadedImage;
    ProgressBar waiter;
    Uri imageUri;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("CreateAdd");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_add);
        //get Ids
        UploadMainName = findViewById(R.id.UploadName);
        UploadMainModel = findViewById(R.id.UploadCarModel);
        UploadMainAddress = findViewById(R.id.UploadAddress);
        UploadPaymentMainGroup = findViewById(R.id.UploadPaymentGroup);
        UploadPaymentMainGroup = findViewById(R.id.UploadPaymentGroup);
        UploadMainPrice = findViewById(R.id.UploadPrice);
        UploadMainSellerName = findViewById(R.id.UploadSellerName);
        UploadMainSellerContact = findViewById(R.id.UploadSellerContact);
        UploadMainAdd = findViewById(R.id.UploadAdd);
        UploadedImage = findViewById(R.id.UplaodAddImage);
        waiter = findViewById(R.id.WaitToUpload);

        //Progressbar calling
        ProgressBar(false);

        //Image click
        UploadedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        //Upload Image
        UploadMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UploadMainName.getText().toString().equals("")){
                    displayToast("Car Name is Empty!!");
                }else if(UploadMainModel.getText().toString().equals("")){
                    displayToast("Car Model is Empty!!");
                }else if(UploadMainAddress.getText().toString().equals("")){
                    displayToast("Car Address is Empty!!");
                }else if(!UploadMainPayment.isChecked()){
                    displayToast("Car Payment is Empty!!");
                }else if(UploadMainPrice.getText().toString().equals("")){
                    displayToast("Car Price is Empty!!");
                }else if(UploadMainSellerName.getText().toString().equals("")){
                    displayToast("Car Seller Name is Empty!!");
                }else if(UploadMainSellerContact.getText().toString().equals("")){
                    displayToast("Car Seller Contact is Empty!!");
                }else if(UploadMainSellerContact.getText().length() != 11){
                    displayToast("Car Seller Contact is Invalid!!");
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
                    String CarName,CarModel,CardAddress,CarPayment,CarPrice,SellerName,SellerContact;
                    @Override
                    public void onSuccess(Uri uri) {
                        int RadioChecked = UploadPaymentMainGroup.getCheckedRadioButtonId();
                        UploadMainPayment = findViewById(RadioChecked);
                        CarName = UploadMainName.getText().toString();
                        CarModel = UploadMainModel.getText().toString();
                        CardAddress = UploadMainAddress.getText().toString();
                        CarPayment = UploadMainPayment.getText().toString();
                        CarPrice = UploadMainPrice.getText().toString();
                        SellerName = UploadMainSellerName.getText().toString();
                        SellerContact = UploadMainSellerContact.getText().toString();
                        Model model = new Model(uri.toString());
                        String ModelId = databaseReference.push().getKey();
                        databaseReference.child(ModelId).setValue(model);
                        databaseReference.child(ModelId).child("carname").setValue(CarName);
                        databaseReference.child(ModelId).child("carmodel").setValue(CarModel);
                        databaseReference.child(ModelId).child("caraddress").setValue(CardAddress);
                        databaseReference.child(ModelId).child("carpayment").setValue(CarPayment);
                        databaseReference.child(ModelId).child("carprice").setValue(CarPrice);
                        databaseReference.child(ModelId).child("sellername").setValue(SellerName);
                        databaseReference.child(ModelId).child("sellercontact").setValue(SellerContact);
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
            UploadedImage.setImageURI(imageUri);
        }
    }


    //display toast
    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    //progress bar visibility
    private void ProgressBar(boolean flag){
        if(flag){
            waiter.setVisibility(View.VISIBLE);
        }else{
            waiter.setVisibility(View.INVISIBLE);
        }
    }
    //payment radio setting
    public void checked(View v){
        int RadioChecked = UploadPaymentMainGroup.getCheckedRadioButtonId();
        UploadMainPayment = findViewById(RadioChecked);
    }
}