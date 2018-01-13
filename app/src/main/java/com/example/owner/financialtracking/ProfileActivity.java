package com.example.owner.financialtracking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
    private String userUID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private TextView first, last, email, phone, city, street;
    private Button LoadImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        targetImage = findViewById(R.id.targetimage);
        mAuth=FirebaseAuth.getInstance();
        //get current user
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userUID = user.getUid();
        myRef = mFirebaseDatabase.getReference();
        LoadImage = findViewById(R.id.loadImage);

        first = findViewById(R.id.firstName);
        last = findViewById(R.id.LastName);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);

        // Read from the database

        LoadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent LoadImageIntent = new Intent(ProfileActivity.this, LoadImageActivity.class);
                ProfileActivity.this.startActivity(LoadImageIntent);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showProfile(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ProfileActivity.this, "Failed to read data..!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showProfile(DataSnapshot dataSnapshot) {
        dataSnapshot = dataSnapshot.child("Account").child(userUID);

        first.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_first) + " " + dataSnapshot.child("FirstName").getValue().toString());
        last.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_last) + " " + dataSnapshot.child("LastName").getValue().toString());
        email.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_email) + " " + dataSnapshot.child("Email").getValue().toString());
        phone.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_phone) + " " + dataSnapshot.child("PhoneNumber").getValue().toString());
        city.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_city) + " " +dataSnapshot.child("City").getValue().toString());
        street.setText(ProfileActivity.this.getResources()
                .getString(R.string.pro_street) + " " + dataSnapshot.child("Street").getValue().toString());

        showProfilePic(dataSnapshot.child("Picture").getValue().toString());
    }

    private void showProfilePic(String PicturePath) {
        if (!PicturePath.contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(PicturePath);
                targetImage.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Picasso.with(ProfileActivity.this).load(PicturePath).into(targetImage);
        }
    }


    private static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}


