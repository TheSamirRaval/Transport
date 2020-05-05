package com.transport.myOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.transport.R;
import com.transport.adapter.MyOrderAdapter;
import com.transport.api.ApiClient;
import com.transport.api.model.Orders;
import com.transport.databinding.ActivityMyOrderBinding;
import com.transport.databinding.RvItemMyOrderBinding;
import com.transport.listener.OnItemSelectListener;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.SharedPrefs;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

public class MyOrderActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityMyOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        binding.ivGoods.setOnClickListener(v -> changePage(0));
        binding.ivHumans.setOnClickListener(v -> changePage(1));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.ivGoods.setSelected(position == 0);
                binding.ivHumans.setSelected(position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.ivGoods.setSelected(true);
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
            return  new MyOrdersFragment(position == 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
