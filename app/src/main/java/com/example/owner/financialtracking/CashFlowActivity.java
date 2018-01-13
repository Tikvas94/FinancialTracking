package com.example.owner.financialtracking;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;

public class CashFlowActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAnalytics mFirebaseAnalytics;

    private Button Incomebtn , Expensebtn;
    private Button MyProfile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // init global
        Incomebtn = findViewById(R.id.income_btn);
        Expensebtn = findViewById(R.id.expenses_btn);
        MyProfile = findViewById(R.id.myProfile);


        mAuth=FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference();

        //get current user
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        Incomebtn.setOnClickListener(this);
        Expensebtn.setOnClickListener(this);
        MyProfile.setOnClickListener(this);


        Incomebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent incomeIntent = new Intent(CashFlowActivity.this, IncomeActivity.class);
                CashFlowActivity.this.startActivity(incomeIntent);
            }
        });

        MyProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent LoadImageIntent = new Intent(CashFlowActivity.this, ProfileActivity.class);
                CashFlowActivity.this.startActivity(LoadImageIntent);
            }
        });


        Expensebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent incomeIntent = new Intent(CashFlowActivity.this, ExpensesActivity.class);
                CashFlowActivity.this.startActivity(incomeIntent);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(isAdmin(dataSnapshot)){
                    //Managerbtn.setVisibility(View.VISIBLE);
                    Expensebtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(CashFlowActivity.this, CashFlowActivity.this.getResources()
                        .getString(R.string.cash_r_err), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isAdmin(DataSnapshot dataSnapshot) {
        dataSnapshot = dataSnapshot.child("Account").child(userID);
        if(dataSnapshot.child("isManager").getValue().toString().equals("true")){

            return true;
        }
        else return false;
    }

    @Override
    public void onClick(View view) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", view.getId());
        String btnName;

        switch(view.getId()){
            case R.id.income_btn:
                btnName = "Incomebtn";
                break;
            case R.id.expenses_btn:
                btnName = "Expensebtn";
                break;
            case R.id.myProfile:
                btnName = "MyProfile";
                break;
            default:
                btnName = "OtherBtn";
        }
        mFirebaseAnalytics.logEvent(btnName, params);
    }
}