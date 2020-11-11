package com.eflexsoft.laxy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.laxy.repository.CreateAccountRepository;
import com.eflexsoft.laxy.repository.CreateAccountVendorRepository;
import com.google.firebase.auth.AuthCredential;

public class CreateAccountVendorViewModel extends AndroidViewModel {

    CreateAccountVendorRepository repository;
    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public CreateAccountVendorViewModel(@NonNull Application application) {
        super(application);
        repository = new CreateAccountVendorRepository(application);
    }


    public void CreataAccount(String email, String password, String accountName) {

        repository.CreataAccount(email, password, accountName);

    }

    public LiveData<Boolean> getIsSucces(){
        isSuccess = repository.isSuccess;
        return isSuccess;
    }
    public void doGoogleSignIn(AuthCredential authCredential) {
        repository.doGoogleSignIn(authCredential);
    }
}
