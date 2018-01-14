package com.example.owner.financialtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText e_Email;
    EditText e_Password;

    Button Login;
    TextView RegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        e_Email = findViewById(R.id.Email);
        e_Password = findViewById(R.id.Password);

        Login = findViewById(R.id.Login);
        RegisterLink = findViewById(R.id.Register);

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
                final String Email = e_Email.getText().toString().trim();
                final String Password = e_Password.getText().toString().trim();
                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources()
                        .getString(R.string.login_try_conn), Toast.LENGTH_LONG).show();
                authenticateUser(Email, Password);
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
                            Toast.makeText(LoginActivity.this,
                                    task.getException() != null ? task.getException().getMessage() : LoginActivity.this.getResources()
                                            .getString(R.string.login_incorrect),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, LoginActivity.this.getResources()
                                    .getString(R.string.login_succ), Toast.LENGTH_LONG).show();
                            //When login successful, redirect user to main activity
                            Intent intent = new Intent(LoginActivity.this, CashFlowActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

}