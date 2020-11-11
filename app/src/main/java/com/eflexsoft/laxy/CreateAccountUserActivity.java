package com.eflexsoft.laxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.eflexsoft.laxy.databinding.ActivityCreateAccountUserAcivityBinding;
import com.eflexsoft.laxy.viewmodel.CreateAccountViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class CreateAccountUserActivity extends AppCompatActivity {


    CreateAccountViewModel viewModel;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignIn googleSignIn;
    GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account_user_acivity);

        ActivityCreateAccountUserAcivityBinding acivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account_user_acivity);

        acivityBinding.back.setNavigationIcon(R.drawable.ic_left_arrow1);
        acivityBinding.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

//        viewModel.CreataAccount();

        acivityBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = acivityBinding.fullName.getText().toString();
                String email = acivityBinding.email.getText().toString();
                String password = acivityBinding.password.getText().toString();
                String confirmPassword = acivityBinding.passwordConfirm.getText().toString();

                if (name.trim().isEmpty()) {
                    acivityBinding.fullName.setError("missing");
                } else if (email.trim().isEmpty()) {
                    acivityBinding.email.setError("missing");
                } else if (password.trim().isEmpty()) {
                    acivityBinding.password.setError("missing");
                } else if (confirmPassword.trim().isEmpty()) {
                    acivityBinding.passwordConfirm.setError("missing");
                } else if (password.length() < 6) {
                    acivityBinding.password.setError("to short at least 6 characters");
                } else {
                    viewModel.CreataAccount(email, password, name);
                    acivityBinding.prBar.setVisibility(View.VISIBLE);
                }

            }
        });

        acivityBinding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    acivityBinding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    acivityBinding.passwordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    acivityBinding.passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    acivityBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        viewModel.getIsSucces().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    acivityBinding.prBar.setVisibility(View.GONE);
                    startActivity(new Intent(CreateAccountUserActivity.this,MainActivity.class));
                    finish();
                } else {
                    acivityBinding.prBar.setVisibility(View.GONE);
                }
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        acivityBinding.googleSignIn.setOnClickListener(new View.OnClickListener() {
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

            viewModel.doGoogleSignIn(authCredential);

        }

    }
}
