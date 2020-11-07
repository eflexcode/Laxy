package com.eflexsoft.laxy.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eflexsoft.laxy.CreateAccountVendorActivity;
import com.eflexsoft.laxy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountRepository {

    Context context;

    public CreateAccountRepository(Context context) {
        this.context = context;
    }

    public void CreataAccount(String email,String password,String accountName){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                User user = new User(accountName,email,FirebaseAuth.getInstance().getUid(),
                        "none","not Specified");

//                FirebaseFirestore

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
