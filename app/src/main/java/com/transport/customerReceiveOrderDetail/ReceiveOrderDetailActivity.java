package com.transport.customerReceiveOrderDetail;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.Orders;
import com.transport.app.GlideApp;
import com.transport.databinding.ActivityReceiveOrderDetailBinding;
import com.transport.utils.Constants;
import com.transport.widget.NestedScrollableViewHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

import static android.view.View.GONE;

public class ReceiveOrderDetailActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityReceiveOrderDetailBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Orders.AllOrder.OrderList order;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, HH:mm", Locale.ENGLISH);
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    private long orderDate = 0;
    private boolean isHuman = false;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_receive_order_detail);
        initToolbar();
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_black_24dp);

    }


    private void init() {
        binding.slidingLayout.setScrollableViewHelper(new NestedScrollableViewHelper());

        order = getIntent().getParcelableExtra(Constants.ORDER);
        if (order == null) finish();
        else setData();

    }

    private void setData() {
        binding.etFrom.setText(order.getFromAddress());
        binding.etTo.setText(order.getToAddress());

        try {
            Date date = apiDateFormat.parse(order.getVehicleOrderDate());
            orderDate = Objects.requireNonNull(date).getTime();
            binding.tvDate.setText(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (order.getProductState() == Constants.HUMAN) {
            isHuman = true;
            binding.tvTitle.setText(R.string.humans_receive_order);


        } else {
            isHuman = false;
            binding.tvTitle.setText(R.string.goods_receive_order);

            GlideApp.with(activity)
                    .load(ApiClient.PREFIX + order.getVehicalTypeImage())
                    .into(binding.ivVehicle);
            binding.tvVehicleSelection.setText(order.getVehicleType());

            binding.tvPrice.setText(Html.fromHtml("<b>" + getString(R.string.price) + "  :  </b>" + numberFormat.format(order.getPrice().doubleValue())));

            binding.rbSolid.setChecked(order.getProductState() == Constants.SOLID);
            binding.rbLiquid.setChecked(order.getProductState() == Constants.LIQUID);
            binding.rbGas.setChecked(order.getProductState() == Constants.GAS);

            binding.tvPerson.setText(Html.fromHtml("<b>" + getString(R.string.hire_kg) + " :  </b>" + order.getQty() + " " + order.getUoM()));


            binding.rbPrivate.setChecked(!order.getShared());
            binding.rbSharing.setChecked(order.getShared());

            binding.rbWithLuggage.setChecked(order.getLoaded());
            binding.rbWithoutLuggage.setChecked(!order.getLoaded());

            binding.tvMaxLuggage.setVisibility(GONE);


        }

    }
}
