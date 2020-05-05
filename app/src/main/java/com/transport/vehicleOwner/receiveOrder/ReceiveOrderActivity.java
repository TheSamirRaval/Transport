package com.transport.vehicleOwner.receiveOrder;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.transport.R;
import com.transport.databinding.ActivityMyOrderBinding;
import com.transport.myOrder.MyOrdersFragment;

import static android.view.View.VISIBLE;

/**
 * Created by SAM on 17/4/20.
 */
public class ReceiveOrderActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityMyOrderBinding binding;
    private boolean isSearchRequired = true;
    private MyOrdersFragment myOrdersGoodsFragment, myOrdersHumanFragment;
    private int selectedFragment = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_my_order);
        initToolbar();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        binding.tvTitle.setText(R.string.receive_order);
        binding.llSearch.setVisibility(VISIBLE);


        Handler handler = new Handler();
        Runnable runnable = this::Search;

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                if (isSearchRequired)
                    handler.postDelayed(runnable, 800);
            }
        });


        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        binding.ivGoods.setOnClickListener(v -> changePage(0));
        binding.ivHumans.setOnClickListener(v -> changePage(1));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedFragment = position;
                binding.ivGoods.setSelected(position == 0);
                binding.ivHumans.setSelected(position == 1);
                if (!binding.etSearch.getText().toString().isEmpty()) {
                    binding.etSearch.setText("");
                    isSearchRequired = false;
                    new Handler().postDelayed(() -> isSearchRequired = true, 1000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.ivGoods.setSelected(true);
    }

    private void Search() {
        if (selectedFragment == 0)
            myOrdersGoodsFragment.search(TextUtils.isEmpty(binding.etSearch.getText()) ? "" : binding.etSearch.getText().toString().trim());
        else
            myOrdersHumanFragment.search(TextUtils.isEmpty(binding.etSearch.getText()) ? "" : binding.etSearch.getText().toString().trim());

    }

    private void changePage(int page) {
        binding.viewPager.setCurrentItem(page);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                myOrdersGoodsFragment = new MyOrdersFragment(true, false);
                return myOrdersGoodsFragment;
            } else {
                myOrdersHumanFragment = new MyOrdersFragment(true, true);
                return myOrdersHumanFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


    }
}
