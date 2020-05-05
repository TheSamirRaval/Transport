package com.transport.proposal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.transport.R;
import com.transport.adapter.VehicleOwnerProposalAdapter;
import com.transport.api.ApiClient;
import com.transport.api.model.OrderProposal;
import com.transport.databinding.ActivityRecyclerViewBinding;
import com.transport.databinding.RvItemVehicleOwnerBinding;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.EndlessRecyclerViewScrollListener;
import com.transport.utils.SharedPrefs;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

public class OrderProposalsActivity extends AppCompatActivity {
    private AppCompatActivity activity = this;
    private ActivityRecyclerViewBinding binding;
    private boolean moreItemLoad = true;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private final int MAX_LIMIT = 20;
    private String orderId;
    private VehicleOwnerProposalAdapter vehicleOwnerProposalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_recycler_view);
        initToolbar();
        init();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
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

        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        if (orderId == null) {
            finish();
            return;
        }

        LinearLayoutManager manager = new LinearLayoutManager(activity);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager, 5) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!moreItemLoad) return;
                getVehicleProposalByOrderId(page);
            }
        };
        binding.rvList.setLayoutManager(manager);
        binding.rvList.addOnScrollListener(scrollListener);


        vehicleOwnerProposalAdapter = new VehicleOwnerProposalAdapter((position, vehicleProposal) -> {

        });
        binding.rvList.setAdapter(vehicleOwnerProposalAdapter);
        getVehicleProposalByOrderId(1);
    }

    private void getVehicleProposalByOrderId(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

        if (page == 1) moreItemLoad = true;

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("pageNo", page);
        map.put("pageSize", MAX_LIMIT);

        mDisposable.add(getApiService().getVehicleProposalByOrderId(SharedPrefs.getAuthToken(activity), map).compose(applySchedulers())
                .subscribe(orderProposal -> {
                    DialogUtil.dismissProgressDialog();
                    if (orderProposal.getStatus() != null) {
                        if (orderProposal.getStatus().getCode() == 0) {
                            if (orderProposal.getVehicleProposal().size() < MAX_LIMIT)
                                moreItemLoad = false;
                            vehicleOwnerProposalAdapter.addData(page, orderProposal.getVehicleProposal());
                        } else
                            Toast.makeText(activity, orderProposal.getStatus().getCode(), Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));
    }
}
