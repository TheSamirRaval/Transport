package com.transport.vehicleOwner;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.OrderProposal;
import com.transport.app.GlideApp;
import com.transport.databinding.ActivityConfirmOrderSelectOwnerBinding;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.SharedPrefs;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

public class ConfirmOrderSelectOwnerActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityConfirmOrderSelectOwnerBinding binding;
    private OrderProposal.VehicleProposal vehicleProposal;
    private boolean isHuman = false;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_confirm_order_select_owner);
        initToolbar();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void init() {
        vehicleProposal = getIntent().getParcelableExtra(Constants.VEHICLE_PROPOSAL);
        if (vehicleProposal == null) {
            finish();
            return;
        }
        if (getIntent().hasExtra(Constants.IS_HUMAN))
            isHuman = getIntent().getBooleanExtra(Constants.IS_HUMAN, false);

        setData();

        binding.tvConfirmOrder.setOnClickListener(v -> confirmOrder());

    }

    private void confirmOrder() {
        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", vehicleProposal.getOrderSummaryId().toString());
        map.put("proposalId", vehicleProposal.getVehicleProposalId().toString());

        disposable.add(getApiService().confirmProposalInOrder(SharedPrefs.getAuthToken(activity), map).compose(applySchedulers())
                .subscribe(statusObjectHolder -> {
                    DialogUtil.dismissProgressDialog();
                    if (statusObjectHolder.getStatus() != null) {
                        Toast.makeText(activity, statusObjectHolder.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                        if (statusObjectHolder.getStatus().getCode() == 0) {
                            finish();
                        }
                    } else {
                        Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    private void setData() {
        binding.tvName.setText(Html.fromHtml("<b>" + getString(R.string.name) + "  :  </b>" + vehicleProposal.getUserName()));
        if (isHuman) {
            binding.tvPeoples.setText(Html.fromHtml("<b>" + getString(R.string.peoples) + "  :  </b>" + vehicleProposal.getMaxCapacity()));

        } else {
            binding.tvPeoples.setVisibility(View.GONE);
        }

        binding.tvTotalProject.setText(Html.fromHtml("<b>" + getString(R.string.total_project) + "  :  -</b>"));
        binding.tvVehicleOwnerPrice.setText(Html.fromHtml("<b>" + getString(R.string.vehicle_owner_price) + "  :  </b>" + numberFormat.format(vehicleProposal.getPrice().doubleValue())));
        GlideApp.with(activity).load(ApiClient.PREFIX + vehicleProposal.getImage()).into(binding.ivVehicleImage);

        binding.tvVehicleName.setText(vehicleProposal.getVehicalType());
        binding.tvVehicleType.setText(Html.fromHtml("<b>" + getString(R.string.vehicle_type) + "  :  -</b>"));
        binding.tvLuggage.setText(Html.fromHtml("<b>" + getString(isHuman ? R.string.luggage : R.string.loading_unloding) + "  :  -</b>"));

        binding.tvProfile.setText(Html.fromHtml("<b>" + getString(R.string.profile) + "  :  -</b>"));
        binding.tvContactNo.setText(Html.fromHtml("<b>" + getString(R.string.contact_mo_no) + "  :  </b>" + vehicleProposal.getContactNumber()));
        binding.tvLastCustomerNo.setText(Html.fromHtml("<b>" + getString(R.string.last_customer_mo_no) + "  :  -</b>"));


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

}
