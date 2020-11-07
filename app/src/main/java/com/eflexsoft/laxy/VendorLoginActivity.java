package com.eflexsoft.laxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.eflexsoft.laxy.databinding.ActivityLoginBindingImpl;
import com.eflexsoft.laxy.databinding.ActivityVendorLoginBinding;

public class VendorLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vendor_login);

        ActivityVendorLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vendor_login);

        binding.back.setNavigationIcon(R.drawable.ic_left_arrow1);
        binding.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
