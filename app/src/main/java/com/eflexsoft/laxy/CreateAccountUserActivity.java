package com.eflexsoft.laxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.eflexsoft.laxy.databinding.ActivityCreateAccountUserAcivityBinding;

public class CreateAccountUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account_user_acivity);

        ActivityCreateAccountUserAcivityBinding acivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_account_user_acivity);

        acivityBinding.back.setNavigationIcon(R.drawable.ic_left_arrow1);
        acivityBinding.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
