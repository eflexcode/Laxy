package com.eflexsoft.laxy;

import androidx.annotation.Nullable;
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

import com.eflexsoft.laxy.databinding.ActivityLoginBindingImpl;
import com.eflexsoft.laxy.databinding.ActivityVendorLoginBinding;
import com.eflexsoft.laxy.viewmodel.UserLoginViewmodel;
import com.eflexsoft.laxy.viewmodel.UserVendorViewmodel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class VendorLoginActivity extends AppCompatActivity {

    UserVendorViewmodel viewmodel;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignIn googleSignIn;
    GoogleSignInClient signInClient;

    ActivityVendorLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vendor_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vendor_login);

        viewmodel = new ViewModelProvider(this).get(UserVendorViewmodel.class);

        binding.back.setNavigationIcon(R.drawable.ic_left_arrow1);
        binding.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorLoginActivity.this, CreateAccountVendorActivity.class));

            }
        });
        binding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        binding.signInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if (email.trim().isEmpty()) {
                    binding.email.setError("missing");
                } else if (password.trim().isEmpty()) {
                    binding.email.setError("missing");
                } else {
                    viewmodel.doSignInWithEmailAndPassword(email, password);
                    binding.prBar.setVisibility(View.VISIBLE);
                }
            }
        });

        viewmodel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.prBar.setVisibility(View.GONE);
                    startActivity(new Intent(VendorLoginActivity.this, VendorMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    binding.prBar.setVisibility(View.GONE);
                }
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        binding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = signInClient.getSignInIntent();
                startActivityForResult(intent, 4);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {

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
