package com.transport.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.User;
import com.transport.app.GlideApp;
import com.transport.databinding.FragmentLoginBinding;
import com.transport.home.HomeActivity;
import com.transport.login.LoginActivity;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.SharedPrefs;
import com.transport.vehicleOwner.OwnerHomeActivity;

import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private int userType = 0;
    private CompositeDisposable disposable = new CompositeDisposable();
    private boolean isLoginPasswordVisible = false;
    private boolean isSignUpPasswordVisible = false;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void init() {
        GlideApp.with(requireContext()).load(R.drawable.ic_bg_login_signup_uper).fitCenter().into(binding.ivUpperBg);
        binding.tvSignUp.setOnClickListener(v -> ((LoginActivity) requireActivity()).setFragment(true));

        binding.tvLogin.setOnClickListener(v -> login());
        binding.tvSelectType.setOnClickListener(v -> {
            new AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.select)
                    .setItems(new String[]{"Vehicle Owner", "Transporter"}, (dialog, which) -> {
                        userType = which == 0 ? Constants.OWNER_TYPE : Constants.USER_TYPE;
                        binding.tvSelectType.setText(which == 0 ? "Vehicle Owner" : "Transporter");
                    })
                    .show();
        });

        binding.ivHidePassword.setOnClickListener(v -> {
            if (!isLoginPasswordVisible) {
                isLoginPasswordVisible = true;
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.ivHidePassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_show));
            } else {
                isLoginPasswordVisible = false;
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.ivHidePassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_hide));

            }
            binding.etPassword.setSelection(binding.etPassword.length());
        });
    }

    private void login() {
        if (!isValid()) return;
        if (!AppUtils.isNetworkAvailableWithDialog(requireActivity())) return;

        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());
        Map<String, Object> map = new HashMap<>();
        map.put("UserName", binding.etUserName.getText().toString().trim());
        map.put("Password", binding.etPassword.getText().toString().trim());
        map.put("roleId", userType);


        disposable.add(ApiClient.getApiService().login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    DialogUtil.dismissProgressDialog();
                    if (user.getStatus().getCode() == 0) {
                        SharedPrefs.setUserType(requireActivity(), userType);
                        User.UserData mUser = user.getUserData();
                        SharedPrefs.setString(requireActivity(), SharedPrefs.USER_ID, mUser.getUserId().toString());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.AUTH_TOKEN, mUser.getAuthToken());


                        SharedPrefs.setString(requireActivity(), SharedPrefs.NAME, mUser.getUsername());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.EMAIL, mUser.getEmailId());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.PHONE, mUser.getPhone());


                        SharedPrefs.setString(requireActivity(), SharedPrefs.CITY_NAME, mUser.getCityName());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.CITY_ID, mUser.getCityId().toString());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.STATE_NAME, mUser.getStateIdName());
                        SharedPrefs.setString(requireActivity(), SharedPrefs.STATE_ID, mUser.getStateId().toString());


                        Intent intent = new Intent(requireContext(), userType == Constants.USER_TYPE ? HomeActivity.class : OwnerHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else
                        Toast.makeText(requireActivity(), user.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    Toast.makeText(requireActivity(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));
    }

    private boolean isValid() {

        if (!AppUtils.isValidEmail(binding.etUserName.getText())) {
            Toast.makeText(requireContext(), R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.etPassword.getText())) {
            Toast.makeText(requireContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userType == 0) {
            Toast.makeText(requireContext(), R.string.select_type, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
