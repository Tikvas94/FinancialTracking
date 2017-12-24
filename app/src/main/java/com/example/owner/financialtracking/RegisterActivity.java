package com.example.owner.financialtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//copied::

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
//        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        final EditText e_FirstName = findViewById(R.id.FirstName);
        final EditText e_LastName = findViewById(R.id.LastName);
        final EditText e_Password1 = findViewById(R.id.Password1);
        final EditText e_Password2 = findViewById(R.id.Password2);
        final EditText e_PhoneNumber = findViewById(R.id.PhoneNumber);
        final EditText e_City = findViewById(R.id.City);
        final EditText e_Street = findViewById(R.id.Street);
        final EditText e_Email = findViewById(R.id.E_mail);

        final Button LoadImage = (Button) findViewById(R.id.loadImage);
        final Button Register = (Button) findViewById(R.id.Register);

        LoadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent LoadImageIntent = new Intent(RegisterActivity.this, LoadImageActivity.class);
                RegisterActivity.this.startActivity(LoadImageIntent);
            }
        });

//        Register.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick( View v){
//                Intent CashFlowIntent = new Intent(RegisterActivity.this, CashFlowActivity.class);
//                RegisterActivity.this.startActivity(CashFlowIntent);
//            }
//        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String FirstName = e_FirstName.toString();
                final String LastName = e_LastName.toString();
                final String Password1 = e_Password1.toString();
                String Password2 = e_Password2.toString();
                String PhoneNumber = e_PhoneNumber.toString();
                final String City = e_City.toString();
                final String Street = e_Street.toString();
                final String Email = e_Email.toString();
                if (RightFormate(FirstName, LastName, Password1, Password2, PhoneNumber, City, Street, Email)) {
                    mAuth.createUserWithEmailAndPassword("tikvas94@gmail.com", "1111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                if(imageEncoded==null)
//                                    uploadFile();
//                                else{
                                new Account(FirstName, LastName, Password1, Email, Street, City, mAuth.getCurrentUser().getUid()).save();
                                    //toastMessage("added user " + email);
                                    Intent myIntent = new Intent(RegisterActivity.this, CashFlowActivity.class);
                                    startActivityForResult(myIntent, 0);
                                    finish();
//                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    //toastMessage("You didn't fill in all the fields.");
                }
            }
        });
    }

    private boolean RightFormate(String FirstName, String LastName, String Password1, String Password2, String PhoneNumber,
                 String City, String Street ,String Email){

        return true;
//        for(int i = 0; i < FirstName.length(); i++){
//            if(FirstName.toLowerCase().charAt(i) < 'a' || FirstName.toLowerCase().charAt(i) > 'z'){
//                Toast.makeText(RegisterActivity.this, "Invalid first name, only A-Z, a-z letters", Toast.LENGTH_LONG).show();
//                return false;
//            };
//        }
//
//        for(int i = 0; i < LastName.length(); i++){
//            if(LastName.toLowerCase().charAt(i) < 'a' || LastName.toLowerCase().charAt(i) > 'z'){
//                Toast.makeText(RegisterActivity.this, "Invalid last name, only A-Z, a-z letters", Toast.LENGTH_LONG).show();
//                return false;
//            };
//        }
//        if(PhoneNumber.length() != 10){
//            Toast.makeText(RegisterActivity.this,"Invalid phone number, tipe 10 length phone number", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        for(int i = 0; i < PhoneNumber.length(); i++){
//            if(PhoneNumber.toLowerCase().charAt(i) < '0' || PhoneNumber.toLowerCase().charAt(i) > '9'){
//                Toast.makeText(RegisterActivity.this, "Invalid phone number, tap numbers only", Toast.LENGTH_LONG).show();
//                return false;
//            };
//        }
//        if(!Password1.equals(Password2)){
//            Toast.makeText(RegisterActivity.this, "Invalid password, the two passwords arn't match", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        for(int i = 0; i < City.length(); i++){
//            if(City.toLowerCase().charAt(i) < 'a' || City.toLowerCase().charAt(i) > 'z'){
//                Toast.makeText(RegisterActivity.this, "Invalid city, only A-Z, a-z letters", Toast.LENGTH_LONG).show();
//                return false;
//            };
//        }
//
//        for(int i = 0; i < Street.length(); i++){
//            if(Street.toLowerCase().charAt(i) < 'a' || Street.toLowerCase().charAt(i) > 'z'){
//                Toast.makeText(RegisterActivity.this, "Invalid Street, only A-Z, a-z letters", Toast.LENGTH_LONG).show();
//                return false;
//            };
//        }
//
//
//        boolean is_email = false;
//        for(int i = 0; i < Street.length(); i++){
//            if(Street.toLowerCase().charAt(i) == '@'){
//                is_email = true;
//            };
//        }
//
////        if(!is_email){
////            Toast.makeText(RegisterActivity.this, "Invalid Email add @", Toast.LENGTH_LONG).show();
////            return false;
////        }
//
//
//        return true;
    }

}
