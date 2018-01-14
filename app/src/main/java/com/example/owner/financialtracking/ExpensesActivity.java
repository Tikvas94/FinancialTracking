package com.example.owner.financialtracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ExpensesActivity extends AppCompatActivity {
    private DatabaseReference mFirebase;
    Button enterExpBtn;
    TextView textViewTotalExp;
    EditText editTextEnterExp;
    long priorEpx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        mFirebase = FirebaseDatabase.getInstance().getReference();
        enterExpBtn = findViewById(R.id.btnEnterExp);
        textViewTotalExp = findViewById(R.id.textViewExpSum);
        editTextEnterExp = findViewById(R.id.editTextExpensEntry);

        getCurrentExp();

        enterExpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentExp();
                try {
                    long entry = Long.parseLong(editTextEnterExp.getText().toString());
                    mFirebase.child("Expense").child("sum").setValue(priorEpx + entry);
                    getCurrentExp();
                    Toast.makeText(ExpensesActivity.this, ExpensesActivity.this.getResources()
                            .getString(R.string.exp_entered) + Long.toString(entry), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(ExpensesActivity.this, ExpensesActivity.this.getResources()
                            .getString(R.string.exp_read_err), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void getCurrentExp(){
        mFirebase.child("Expense").child("sum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            priorEpx = (long)dataSnapshot.getValue();
                            textViewTotalExp.setText(ExpensesActivity.this.getResources()
                                    .getString(R.string.exp_tot, priorEpx));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ExpensesActivity.this, R.string.exp_not_entered, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
