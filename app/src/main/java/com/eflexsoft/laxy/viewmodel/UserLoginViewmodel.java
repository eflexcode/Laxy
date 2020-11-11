package com.eflexsoft.laxy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.laxy.repository.LoginUserRepository;
import com.google.firebase.auth.AuthCredential;

public class UserLoginViewmodel extends AndroidViewModel {

    LoginUserRepository repository;
    MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();

    public UserLoginViewmodel(@NonNull Application application) {
        super(application);
        repository = new LoginUserRepository(application);
    }

    public void doSignInWithEmailAndPassword(String email, String password) {
        repository.doSignInWithEmailAndPassword(email, password);
    }

    public LiveData<Boolean> isSuccess() {
        isSignInSuccessful = repository.isSignInSuccessful;

        return isSignInSuccessful;
    }
    public void doGoogleSignIn(AuthCredential authCredential) {
        repository.doGoogleSignIn(authCredential);
    }

}
