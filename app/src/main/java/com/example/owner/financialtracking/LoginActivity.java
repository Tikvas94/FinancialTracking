package com.example.owner.financialtracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText UserName = (EditText) findViewById(R.id.UserName);
        final EditText Password = (EditText) findViewById(R.id.Password);

        final Button Login = (Button) findViewById(R.id.Login);

        final TextView RegisterLink = (TextView) findViewById(R.id.Register);

        RegisterLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });



        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
//                Intent CashFlowIntent = new Intent(LoginActivity.this, CashFlowActivity.class);
//                LoginActivity.this.startActivity(CashFlowIntent);
                authenticateUser(UserName.toString(), Password.toString());
            }
        });
    }

    private void authenticateUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // When login failed
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Email or Password are incorrect!", Toast.LENGTH_LONG).show();
                        } else {
                            //When login successful, redirect user to main activity
                            Intent intent = new Intent(LoginActivity.this, CashFlowActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

}
