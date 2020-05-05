package com.transport.login.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.Cities;
import com.transport.api.model.States;
import com.transport.api.model.User;
import com.transport.app.GlideApp;
import com.transport.databinding.FragmentSignupBinding;
import com.transport.login.WelcomeActivity;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.SharedPrefs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

public class SignUpFragment extends Fragment {
    private FragmentSignupBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<States.State> stateList = new ArrayList<>();
    private List<Cities.City> cityList = new ArrayList<>();
    private Cities.City selectedCity;
    private States.State selectedState;
    private boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void init() {
//        GlideApp.with(requireContext()).load(R.drawable.login_bg).fitCenter().into(binding.ivBg);
        GlideApp.with(requireContext()).load(R.drawable.ic_bg_login_signup_uper).fitCenter().into(binding.ivUpperBg);
        if (!AppUtils.isNetworkAvailableWithDialog(requireActivity())) return;
        getState();

        binding.actCity.setThreshold(1);
        binding.actState.setThreshold(1);

        binding.actState.setAdapter(new StateAdapter(requireActivity(), android.R.layout.select_dialog_item));
        binding.actCity.setAdapter(new CityAdapter(requireActivity(), android.R.layout.select_dialog_item));

        binding.actState.setOnDismissListener(() -> {
            if (TextUtils.isEmpty(binding.actState.getText())) {
                selectedState = null;
                selectedCity = null;
                binding.actCity.setText("");
                binding.actCity.clearFocus();
            }
            binding.actState.setText(selectedState == null ? "" : selectedState.getStateName());
            binding.actState.clearFocus();
            AppUtils.hideKeyboard(requireContext(), binding.actState);
        });
        binding.actCity.setOnDismissListener(() -> {
            if (TextUtils.isEmpty(binding.actCity.getText())) {
                selectedCity = null;
            }
            binding.actCity.setText(selectedCity == null ? "" : selectedCity.getCityName());
            binding.actCity.clearFocus();
            AppUtils.hideKeyboard(requireContext(), binding.actCity);
        });

        initListener();
    }

    private void initListener() {
        binding.tvSignUp.setOnClickListener(v -> signUp());

        binding.ivHidePassword.setOnClickListener(v -> {
            if (!isPasswordVisible) {
                isPasswordVisible = true;
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.ivHidePassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_show));
            } else {
                isPasswordVisible = false;
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.ivHidePassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_hide));

            }
            binding.etPassword.setSelection(binding.etPassword.length());
        });

        binding.ivHideConfirmPassword.setOnClickListener(v -> {
            if (!isConfirmPasswordVisible) {
                isConfirmPasswordVisible = true;
                binding.etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.ivHideConfirmPassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_show));
            } else {
                isConfirmPasswordVisible = false;
                binding.etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.ivHideConfirmPassword.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_hide));
            }
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.length());
        });
    }

    private void getState() {
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());
        stateList.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("countryId", 1);
        disposable.add(getApiService()
                .getState(map)
                .compose(applySchedulers())
                .subscribe(states -> {
                    DialogUtil.dismissProgressDialog();
                    if (states.getState() != null && !states.getState().isEmpty()) {
                        stateList.addAll(states.getState());
                    } else if (states.getStatus() != null) {
                        if (states.getStatus().getCode() != 0) {
                            Toast.makeText(requireContext(), states.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));
    }

    private void getCity() {
        if (!AppUtils.isNetworkAvailableWithDialog(requireActivity())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());
        cityList.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("stateId", selectedState.getStateId());

        disposable.add(getApiService().getCity(map).compose(applySchedulers())
                .subscribe(cities -> {
                    DialogUtil.dismissProgressDialog();
                    if (cities.getCity() != null && !cities.getCity().isEmpty()) {
                        cityList.addAll(cities.getCity());

                        Timber.d("getCity: %s", cityList.toString());
                    } else if (cities.getStatus() != null) {
                        if (cities.getStatus().getCode() != 0) {
                            Toast.makeText(requireContext(), cities.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    private void signUp() {
        if (!isValid()) return;
        if (!AppUtils.isNetworkAvailableWithDialog(requireActivity())) return;

        Intent intent = new Intent(requireActivity(), WelcomeActivity.class);
        intent.putExtra(Constants.USER_NAME, binding.etUserName.getText().toString().trim());
        intent.putExtra(Constants.EMAIL, binding.etEmail.getText().toString().trim());
        intent.putExtra(Constants.PHONE, binding.etPhoneNumber.getText().toString().trim());
        intent.putExtra(Constants.PASSWORD, binding.etPassword.getText().toString().trim());
        intent.putExtra(Constants.STATE, selectedState);
        intent.putExtra(Constants.CITY, selectedCity);
        startActivity(intent);

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(binding.etUserName.getText())) {
            Toast.makeText(requireContext(), R.string.enter_user_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!AppUtils.isValidEmail(binding.etEmail.getText())) {
            Toast.makeText(requireContext(), R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!AppUtils.isValidPhone(binding.etPhoneNumber.getText())) {
            Toast.makeText(requireContext(), R.string.enter_valid_phone_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.etPassword.getText())) {
            Toast.makeText(requireContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString())) {
            Toast.makeText(requireContext(), R.string.confirm_paswword_not_matched, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedState == null) {
            Toast.makeText(requireContext(), R.string.select_state, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedCity == null) {
            Toast.makeText(requireContext(), R.string.select_city, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private class CityAdapter extends ArrayAdapter<Cities.City> {
        private int resourceId;
        private List<Cities.City> filteredList = new ArrayList<>();

        CityAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public int getCount() {
            return filteredList.size();
        }

        @Nullable
        @Override
        public Cities.City getItem(int position) {
            return filteredList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position, parent);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    filteredList.clear();
                    if (constraint != null) {
                        for (Cities.City city : cityList) {
                            if (city.getCityName().toLowerCase().contains(constraint.toString().toLowerCase()))
                                filteredList.add(city);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }

        private View createView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(requireContext()).inflate(resourceId, parent, false);
            TextView textView = v.findViewById(android.R.id.text1);
            textView.setText(filteredList.get(position).getCityName());

            textView.setOnClickListener(tvClick -> {
                selectedCity = filteredList.get(position);
                binding.actCity.dismissDropDown();
//                binding.actCity.setText(selectedCity.getCityName());
//                binding.actCity.clearFocus();
            });
            return v;
        }
    }

    private class StateAdapter extends ArrayAdapter<States.State> {
        private int resourceId;
        private List<States.State> filteredList = new ArrayList<>();

        StateAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            resourceId = resource;
        }


        @Override
        public int getCount() {
            return filteredList.size();
        }

        @Nullable
        @Override
        public States.State getItem(int position) {
            return filteredList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position, parent);
        }


        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(position, parent);
        }


        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    filteredList.clear();
                    if (constraint != null) {
                        for (States.State state : stateList) {
                            if (state.getStateName().toLowerCase().contains(constraint.toString().toLowerCase()))
                                filteredList.add(state);
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }

        private View createView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(requireContext()).inflate(resourceId, parent, false);
            TextView textView = v.findViewById(android.R.id.text1);
            textView.setText(filteredList.get(position).getStateName());

            textView.setOnClickListener(tvClick -> {
                if (selectedState != null) {
                    if (!selectedState.getStateId().toString().equals(filteredList.get(position).getStateId().toString())) {
                        selectedState = filteredList.get(position);
                        getCity();
                        selectedCity = null;
                        binding.actCity.setText("");
                        binding.actCity.clearFocus();
                    }
                } else {
                    selectedState = filteredList.get(position);
                    getCity();
                }
                binding.actState.dismissDropDown();


            });
            return v;
        }

        private void clearFilterList() {
            filteredList.clear();
        }
    }

}
