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


public class RegisterActivity extends AppCompatActivity {
    //create variables to connect the fields
    EditText e_FirstName;
    EditText e_LastName;
    EditText e_Password1;
    EditText e_Password2;
    EditText e_PhoneNumber;
    EditText e_City;
    EditText e_Street;
    EditText e_Email;

    Button Register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //set the edit text:
        e_FirstName = findViewById(R.id.FirstName);
        e_LastName = findViewById(R.id.LastName);
        e_Password1 = findViewById(R.id.Password1);
        e_Password2 = findViewById(R.id.Password2);
        e_PhoneNumber = findViewById(R.id.PhoneNumber);
        e_City = findViewById(R.id.City);
        e_Street = findViewById(R.id.Street);
        e_Email = findViewById(R.id.E_mail);

        Register = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create strings:
                final String FirstName =e_FirstName.getText().toString();
                final String LastName = e_LastName.getText().toString();
                final String Password1 = e_Password1.getText().toString().trim();
                final String Password2 = e_Password2.getText().toString().trim();
                final String PhoneNumber = e_PhoneNumber.getText().toString();
                final String City = e_City.getText().toString();
                final String Street = e_Street.getText().toString();
                final String Email = e_Email.getText().toString().trim();
                final boolean isManager = false;

                //try to add a user:
                if (RightFormate(FirstName, LastName, Password1, Password2, PhoneNumber, City, Street, Email))
                    mAuth.createUserWithEmailAndPassword(Email, Password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                new Account(FirstName, LastName, Password1, Email, Street, PhoneNumber, City,isManager, mAuth.getCurrentUser().getUid()).save();
                                Toast.makeText(RegisterActivity.this, R.string.reg_succ, Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(RegisterActivity.this, CashFlowActivity.class);
                                startActivityForResult(myIntent, 0);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, R.string.reg_err, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });
    }

    private boolean RightFormate(String FirstName, String LastName, String Password1, String Password2, String PhoneNumber,
                 String City, String Street ,String Email){

        for( int i = 0; i < FirstName.length(); i++){
            if(FirstName.toLowerCase().charAt(i) < 'a' || FirstName.toLowerCase().charAt(i) > 'z'){
                Toast.makeText(RegisterActivity.this, R.string.reg_fn_err, Toast.LENGTH_LONG).show();
                return false;
            };
        }

        for(int i = 0; i < LastName.length(); i++){
            if(LastName.toLowerCase().charAt(i) < 'a' || LastName.toLowerCase().charAt(i) > 'z'){
                Toast.makeText(RegisterActivity.this, R.string.reg_ln_err, Toast.LENGTH_LONG).show();
                return false;
            };
        }
        if(PhoneNumber.length() != 10){
            Toast.makeText(RegisterActivity.this,R.string.reg_pn10_err, Toast.LENGTH_LONG).show();
            return false;
        }
        for(int i = 0; i < PhoneNumber.length(); i++){
            if(PhoneNumber.toLowerCase().charAt(i) < '0' || PhoneNumber.toLowerCase().charAt(i) > '9'){
                Toast.makeText(RegisterActivity.this, R.string.reg_pn_err, Toast.LENGTH_LONG).show();
                return false;
            };
        }
        if(!Password1.equals(Password2)){
            Toast.makeText(RegisterActivity.this, R.string.reg_cp_err, Toast.LENGTH_LONG).show();
            return false;
        }
        for(int i = 0; i < City.length(); i++){
            if(City.toLowerCase().charAt(i) < 'a' || City.toLowerCase().charAt(i) > 'z'){
                Toast.makeText(RegisterActivity.this, R.string.reg_c_err, Toast.LENGTH_LONG).show();
                return false;
            };
        }

        for(int i = 0; i < Street.length(); i++){
            if(Street.toLowerCase().charAt(i) < 'a' || Street.toLowerCase().charAt(i) > 'z'){
                Toast.makeText(RegisterActivity.this, R.string.reg_s_err, Toast.LENGTH_LONG).show();
                return false;
            };
        }

        boolean is_email = false;
        for(int i = 0; i < Email.length(); i++){
            if(Email.toLowerCase().charAt(i) == '@'){
                is_email = true;
            };
        }

        if(!is_email){
            Toast.makeText(RegisterActivity.this, R.string.reg_e_err, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
