package com.eflexsoft.laxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.eflexsoft.laxy.databinding.ActivityLoginBinding;
import com.eflexsoft.laxy.viewmodel.UserLoginViewmodel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    UserLoginViewmodel viewmodel;
    ProgressDialog progressDialog;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignIn googleSignIn;
    GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        viewmodel = new ViewModelProvider(this).get(UserLoginViewmodel.class);

        progressDialog = new ProgressDialog(this);

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountUserActivity.class));
            }
        });

        activityLoginBinding.vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, VendorLoginActivity.class));

            }
        });

        progressDialog.setMessage("Checking please wait");

        activityLoginBinding.signInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activityLoginBinding.logInEmail.getText().toString();
                String password = activityLoginBinding.logInPassword.getText().toString();

                if (email.trim().isEmpty()) {
                    activityLoginBinding.logInEmail.setError("missing");
                } else if (password.trim().isEmpty()) {
                    activityLoginBinding.logInPassword.setError("missing");
                } else {
                    viewmodel.doSignInWithEmailAndPassword(email, password);
                    progressDialog.show();
                }
            }
        });

        viewmodel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    progressDialog.dismiss();
                } else {
                    activityLoginBinding.logInEmail.setError("may be incorrect");
                    activityLoginBinding.logInPassword.setError("also may be incorrect");
                    progressDialog.dismiss();
                }
            }
        });

        activityLoginBinding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    activityLoginBinding.logInPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {

                    activityLoginBinding.logInPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        activityLoginBinding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = signInClient.getSignInIntent();
                startActivityForResult(intent, 4);

            }
        });

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        if (resultCode == 4) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }

            assert account != null;
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            viewmodel.doGoogleSignIn(authCredential);

        }

    }
}
