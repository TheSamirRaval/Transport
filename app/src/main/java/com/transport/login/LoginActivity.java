package com.transport.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.transport.R;
import com.transport.databinding.ActivityLoginBinding;
import com.transport.login.fragment.LoginFragment;
import com.transport.login.fragment.SignUpFragment;

public class LoginActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityLoginBinding binding;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_login);
        init();
    }

    private void init() {
//        GlideApp.with(activity).load(R.drawable.login_bg).fitCenter().into(binding.ivBg);
        setFragment(false);

    }

    public void setFragment(boolean isSignUp) {
        fragment = isSignUp ? new SignUpFragment() : new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof SignUpFragment) {
            setFragment(false);
        } else
            super.onBackPressed();
    }


}
