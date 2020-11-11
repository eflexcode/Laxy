package com.eflexsoft.laxy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.laxy.repository.LoginUserRepository;
import com.eflexsoft.laxy.repository.LoginVendorRepository;

public class UserVendorViewmodel extends AndroidViewModel {

    LoginVendorRepository repository;
    MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();

    public UserVendorViewmodel(@NonNull Application application) {
        super(application);
        repository = new LoginVendorRepository(application);
    }

    public void doSignInWithEmailAndPassword(String email, String password) {
        repository.doSignInWithEmailAndPassword(email, password);
    }

    public LiveData<Boolean> isSuccess() {
        isSignInSuccessful = repository.isSignInSuccessful;

        return isSignInSuccessful;
    }


}
