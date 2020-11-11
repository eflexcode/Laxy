package com.eflexsoft.laxy.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.laxy.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginUserRepository {

    Context context;
    public MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();

    public LoginUserRepository(Context context) {
        this.context = context;
    }

    public void doSignInWithEmailAndPassword(String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                isSignInSuccessful.setValue(true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isSignInSuccessful.setValue(false);
            }
        });

    }

    public void doGoogleSignIn(AuthCredential authCredential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    boolean isUserNew = task.getResult().getAdditionalUserInfo().isNewUser();

                    if (isUserNew) {

                        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);

                        String username = googleSignInAccount.getDisplayName();
                        String email = googleSignInAccount.getEmail();
                        String profileImageUrl = googleSignInAccount.getPhotoUrl().toString();
                        String id = firebaseAuth.getUid();
                        String address = "not specified";

                        User user = new User(username, email, id, profileImageUrl, address);

                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(id);
                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    isSignInSuccessful.postValue(true);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                isSignInSuccessful.postValue(false);
                            }
                        });

                    } else {
                        isSignInSuccessful.postValue(true);
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                isSignInSuccessful.postValue(false);
            }
        });

    }

}
