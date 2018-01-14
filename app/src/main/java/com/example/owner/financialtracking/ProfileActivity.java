package com.example.owner.financialtracking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private ImageView targetImage;
    private TextView first, last, email, phone, city, street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        targetImage = findViewById(R.id.targetimage);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //get current user
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userUID = user != null ? user.getUid() : "";
        DatabaseReference myRef = mFirebaseDatabase.getReference();
        Button loadImage = findViewById(R.id.loadImage);

        first = findViewById(R.id.firstName);
        last = findViewById(R.id.LastName);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);

        // Read from the database

        loadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent LoadImageIntent = new Intent(ProfileActivity.this, LoadImageActivity.class);
                ProfileActivity.this.startActivity(LoadImageIntent);
            }
        });

        myRef.child("Account").child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showProfile(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to read data..!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @SuppressLint("SetTextI18n")
    void setDetailText(TextView view, int resId, DataSnapshot dataSnapshot, String snapshotKey) {
        try {
            view.setText(ProfileActivity.this.getResources().getString(resId) + "\t\t" + dataSnapshot.child(snapshotKey).getValue());
        } catch (Exception ex) {
            Log.w(this.getClass().getSimpleName(), "Could not set profile detail for resID: " + resId, ex);
        }
    }

    private void showProfile(DataSnapshot dataSnapshot) {
        setDetailText(first, R.string.pro_first, dataSnapshot, "FirstName");
        setDetailText(last, R.string.pro_last, dataSnapshot, "LastName");
        setDetailText(email, R.string.pro_email, dataSnapshot, "Email");
        setDetailText(phone, R.string.pro_phone, dataSnapshot, "PhoneNumber");
        setDetailText(city, R.string.pro_city, dataSnapshot, "City");
        setDetailText(street, R.string.pro_street, dataSnapshot, "Street");
        showProfilePic((String)dataSnapshot.child("Picture").getValue());
    }

    private void showProfilePic(String picturePath) {
        if (picturePath == null) return;
        try {
            if (!picturePath.startsWith("http")) {
                Bitmap imageBitmap = decodeFromFirebaseBase64(picturePath);
                targetImage.setImageBitmap(imageBitmap);
            } else {
                Picasso.with(ProfileActivity.this).load(picturePath).into(targetImage);
            }
        } catch (Exception ex) {
            //TODO
            Log.w(this.getClass().getSimpleName(), "Could not load image!", ex);
        }
    }


    private static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}


