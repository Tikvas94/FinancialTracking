package com.example.owner.financialtracking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class IncomeActivity extends AppCompatActivity {

    private DatabaseReference mFirebase;
    Button enterIncomeBtn;
    TextView textViewTotalIncome;
    EditText editTextEnterIncome;
    long priorIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        mFirebase = FirebaseDatabase.getInstance().getReference();
        enterIncomeBtn = findViewById(R.id.enter_income_btn);
        textViewTotalIncome = findViewById(R.id.textViewTotalIncome);
        editTextEnterIncome = findViewById(R.id.editTextEnterIncome);
        getCurrentIncome();
        enterIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentIncome();
                try {
                    long entry = Long.parseLong(editTextEnterIncome.getText().toString());
                    mFirebase.child("Income").child("sum").setValue(priorIncome + entry);
                    getCurrentIncome();
                    Toast.makeText(IncomeActivity.this,IncomeActivity.this.getResources()
                            .getString(R.string.inc_entered) + Long.toString(entry), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(IncomeActivity.this,IncomeActivity.this.getResources()
                            .getString(R.string.inc_read_err) , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void getCurrentIncome(){
        mFirebase.child("Income").child("sum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            priorIncome = (long)dataSnapshot.getValue();
                            textViewTotalIncome.setText( IncomeActivity.this.getResources()
                                    .getString(R.string.inc_tot, priorIncome));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(IncomeActivity.this,IncomeActivity.this.getResources()
                                .getString(R.string.inc_null) , Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(IncomeActivity.this,IncomeActivity.this.getResources()
                        .getString(R.string.inc_canclled) , Toast.LENGTH_LONG).show();
            }
        });

    }

}
