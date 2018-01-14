package com.example.owner.financialtracking;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class MockitoTests {
    @Test
    public void registerFakeAccount() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final Account account = Mockito.mock(Account.class);
        Mockito.when(account.getFirstName()).thenReturn("TOTE");
        Mockito.when(account.getLastName()).thenReturn("Financial");
        Mockito.when(account.getEmail()).thenReturn(UUID.randomUUID() + "@q.com");
        Mockito.when(account.getCity()).thenReturn("Ariel");
        Mockito.when(account.getStreet()).thenReturn("Hagolan");
        Mockito.when(account.getPassword()).thenReturn("123456");
        Mockito.when(account.getPhoneNumber()).thenReturn("1234567890");

        mAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                account.save();
                            } catch (Exception e) {
                                Assert.assertTrue("Accound did not save ?!", false);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Assert.assertTrue("Could not create user ?!", false);
                    }
                });
    }
}
