package com.example.owner.financialtracking;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CashFlowActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // init global
        Button incomebtn = findViewById(R.id.income_btn);
        final Button Expensebtn = findViewById(R.id.expenses_btn);
        Button myProfile = findViewById(R.id.myProfile);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = mFirebaseDatabase.getReference();

        //get current user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) userID = user.getUid();
        else userID = "";

        incomebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                handleButtonClick(v, IncomeActivity.class);
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                handleButtonClick(v, ProfileActivity.class);
            }
        });


        Expensebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleButtonClick(v, ExpensesActivity.class);
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

    static class BackgroundAnalyticsTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object[] objects) {
            ((FirebaseAnalytics)objects[0]).logEvent((String)objects[1], (Bundle) objects[2]);
            return null;
        }
    }

    private void handleButtonClick(View v, Class<? extends Activity> nextActivity) {
        // Notify analytics:
        Bundle params = new Bundle();
        params.putInt("ButtonID", v.getId());
        String actName = nextActivity.getSimpleName();
        new BackgroundAnalyticsTask().execute(mFirebaseAnalytics, actName, params);

        // Navigate to next activity:
        Intent incomeIntent = new Intent(CashFlowActivity.this, nextActivity);
        CashFlowActivity.this.startActivity(incomeIntent);
    }

    private boolean isAdmin(DataSnapshot dataSnapshot) {
        dataSnapshot = dataSnapshot.child("Account").child(userID);
        Object value = dataSnapshot.child("isManager").getValue();
        return value != null && "true".equals(value.toString());
    }
}