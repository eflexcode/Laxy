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

import com.eflexsoft.laxy.databinding.ActivityCreateAccountVendorBinding;
import com.eflexsoft.laxy.viewmodel.CreateAccountViewModel;

public class CreateAccountVendorActivity extends AppCompatActivity {

    CreateAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account_vendor);

        ActivityCreateAccountVendorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account_vendor);

        binding.back.setNavigationIcon(R.drawable.ic_left_arrow1);
        binding.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        binding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.passwordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    binding.passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        viewModel.getIsSucces().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.prBar.setVisibility(View.GONE);
                    startActivity(new Intent(CreateAccountVendorActivity.this,VendorMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    binding.prBar.setVisibility(View.GONE);
                }
            }
        });

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.accountName.getText().toString();
                String email = binding.email.getText().toString();
                String addresss = binding.address.getText().toString();
                String password = binding.password.getText().toString();
                String confirmPassword = binding.passwordConfirm.getText().toString();

                if (name.trim().isEmpty()) {
                    binding.accountName.setError("missing");
                } else if (email.trim().isEmpty()) {
                    binding.email.setError("missing");
                } else if (addresss.trim().isEmpty()) {
                    binding.address.setError("missing");
                } else if (password.trim().isEmpty()) {
                    binding.password.setError("missing");
                } else if (confirmPassword.trim().isEmpty()) {
                    binding.passwordConfirm.setError("missing");
                } else if (password.length() < 6) {
                    binding.password.setError("to short at least 6 characters");
                } else {
                    viewModel.CreataAccount(email, password, name);
                    binding.prBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
