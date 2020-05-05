package com.transport.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.Cities;
import com.transport.api.model.States;
import com.transport.api.model.User;
import com.transport.app.GlideApp;
import com.transport.databinding.ActivityWelcomBinding;
import com.transport.home.HomeActivity;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.SharedPrefs;
import com.transport.vehicleOwner.OwnerHomeActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.transport.utils.Constants.OWNER_TYPE;

public class WelcomeActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityWelcomBinding binding;
    private String name, email, password, phone;
    private States.State state;
    private Cities.City city;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_welcom);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        GlideApp.with(activity).load(R.drawable.ic_welcome_top).fitCenter().into(binding.ivWelcomeTop);
        Intent intent = getIntent();
        name = intent.getStringExtra(Constants.USER_NAME);
        email = intent.getStringExtra(Constants.EMAIL);
        phone = intent.getStringExtra(Constants.PHONE);
        password = intent.getStringExtra(Constants.PASSWORD);
        state = intent.getParcelableExtra(Constants.STATE);
        city = intent.getParcelableExtra(Constants.CITY);

        Timber.d("init: " + name + email + password + state.toString() + city.toString());
        binding.tvName.setText("Mr. " + name);
        binding.tvClickHere.setOnClickListener(v -> openHomeScreen(Constants.USER_TYPE));
        binding.tvClickHereOwner.setOnClickListener(v -> openHomeScreen(OWNER_TYPE));
    }

    private void openHomeScreen(int TYPE) {
        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;

        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        map.put("emailId", email);
        map.put("phone", phone);
        map.put("roleId", TYPE);
        map.put("deviceInfo", Constants.DEVICE_INFO);
        map.put("stateId", state.getStateId());
        map.put("cityId", city.getCityId());
        map.put("countryId", 1);

        disposable.add(ApiClient.getApiService().register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    DialogUtil.dismissProgressDialog();
                    if (user.getStatus().getCode() == 0) {
                        SharedPrefs.setUserType(activity, TYPE);
                        SharedPrefs.setString(activity, SharedPrefs.USER_ID, user.getUserData().getUserId().toString());
                        SharedPrefs.setString(activity, SharedPrefs.AUTH_TOKEN, user.getUserData().getAuthToken());
                        SharedPrefs.setString(activity, SharedPrefs.CITY_NAME, city.getCityName());
                        SharedPrefs.setString(activity, SharedPrefs.CITY_ID, city.getCityId().toString());
                        SharedPrefs.setString(activity, SharedPrefs.STATE_NAME, state.getStateName());
                        SharedPrefs.setString(activity, SharedPrefs.STATE_ID, state.getStateId().toString());

                        SharedPrefs.setString(activity, SharedPrefs.EMAIL, email);
                        SharedPrefs.setString(activity, SharedPrefs.NAME, user.getUserData().getUsername());
                        SharedPrefs.setString(activity, SharedPrefs.PHONE, user.getUserData().getPhone());

                        Intent intent = new Intent(activity, TYPE == OWNER_TYPE ? OwnerHomeActivity.class : HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else
                        Toast.makeText(activity, user.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }
}
