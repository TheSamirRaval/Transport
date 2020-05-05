package com.transport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.transport.home.HomeActivity;
import com.transport.login.LoginActivity;
import com.transport.utils.Constants;
import com.transport.utils.SharedPrefs;
import com.transport.vehicleOwner.OwnerHomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SplashActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (SharedPrefs.getAuthToken(activity).isEmpty())
                startActivity(new Intent(activity, LoginActivity.class));
            else
                startActivity(new Intent(activity, SharedPrefs.isUser(activity) ? HomeActivity.class : OwnerHomeActivity.class));
            finish();
        }, 500);
    }


}
