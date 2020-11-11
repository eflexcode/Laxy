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

import com.eflexsoft.laxy.databinding.ActivityLoginBindingImpl;
import com.eflexsoft.laxy.databinding.ActivityVendorLoginBinding;
import com.eflexsoft.laxy.viewmodel.UserLoginViewmodel;
import com.eflexsoft.laxy.viewmodel.UserVendorViewmodel;

public class VendorLoginActivity extends AppCompatActivity {

    UserVendorViewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vendor_login);

        ActivityVendorLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vendor_login);

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
                    startActivity(new Intent(VendorLoginActivity.this,VendorMainActivity.class));
                    finish();
                } else {
                    binding.prBar.setVisibility(View.GONE);
                }
            }
        });

        

    }
}
