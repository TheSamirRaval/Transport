package com.transport.myOrder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.R;
import com.transport.adapter.OwnerVehicleDialogAdapter;
import com.transport.adapter.RowMatirealDialogAdapter;
import com.transport.adapter.VehicleDialogAdapter;
import com.transport.api.ApiClient;
import com.transport.api.model.AddUpdateOrder;
import com.transport.api.model.Orders;
import com.transport.api.model.OwnerVehicle;
import com.transport.api.model.RowMaterials;
import com.transport.api.model.Vehicles;
import com.transport.app.GlideApp;
import com.transport.databinding.ActivityMyOrderDetailBinding;
import com.transport.databinding.DialogRowMaterialBinding;
import com.transport.proposal.OrderProposalsActivity;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.EndlessRecyclerViewScrollListener;
import com.transport.utils.SharedPrefs;
import com.transport.widget.NestedScrollableViewHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

import static android.view.View.GONE;
import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

public class MyOrderDetailActivity extends AppCompatActivity {
    //region PARAM
    private AppCompatActivity activity = this;
    private ActivityMyOrderDetailBinding binding;
    private Orders.AllOrder.OrderList order;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, HH:mm", Locale.ENGLISH);
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

    private final String[] weights = new String[]{"gm", "kg", "tone"};
    private final String[] sizes = new String[]{"inch", "cm", "fit"};

    private List<RowMaterials.ProductMaterial> materialList;
    private List<Vehicles.VehicleType> vehicleList;

    private RowMaterials.ProductMaterial selectedMaterial;
    private Vehicles.VehicleType selectedVehicle;
    private Vehicles.VehicleType humanSelectedVehicle;
    private OwnerVehicle.Vehicle selectedOwnerVehicle;
    private long orderDate = 0;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int position;
    private boolean isOwner = false;
    private static final int MAX_LIMIT = 20;
    private boolean moreOwnerVehicleLoad = true;
    private OwnerVehicleDialogAdapter ownerVehicleDialogAdapter;
    private boolean isHuman = false;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_my_order_detail);
        initToolbar();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
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
        binding.slidingLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
        String action = getIntent().getAction();
        if (action != null && action.equals(Constants.CONFIRM_ORDER)) {
            isOwner = true;
        }

        order = getIntent().getParcelableExtra(Constants.ORDER);
        if (order == null) finish();
        else setData();

    }

    private void setData() {
        binding.etFrom.setText(order.getFromAddress());
        binding.etTo.setText(order.getToAddress());

        try {
            Date date = apiDateFormat.parse(order.getOrderDate());
            orderDate = Objects.requireNonNull(date).getTime();
            binding.tvDate.setText(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Orders.AllOrder.OrderList.ProductList product = order.getProductList().get(0);
        int productState = product.getProductState();

        if (productState == Constants.HUMAN) {
            isHuman = true;
            if (isOwner)
                binding.tvTitle.setText(R.string.humans_receive_order);

            binding.cvFromData.setVisibility(GONE);
            binding.cvFormData2.setVisibility(View.VISIBLE);
            binding.etPrice.setText(product.getPrice().toString());
            binding.etPeoples.setText(product.getQty().toString());
            binding.rbWithLuggage.setChecked(order.getLoaded());
            binding.rbWithoutLuggage.setChecked(!order.getLoaded());
            binding.etLuggageWeight.setText(product.getSize());
            humanSelectedVehicle = new Vehicles.VehicleType(order.getVehicleTypeId(), order.getVehicleType(), order.getVehicalTypeImage());

            binding.tvVehicleSelectionHumans.setText(order.getVehicleType());
            GlideApp.with(activity).load(ApiClient.PREFIX + order.getVehicalTypeImage()).into(binding.ivVehicleSelectionHuman);


            if (isOwner) {
                binding.tvEditHumans.setVisibility(GONE);
                binding.tvSelectVehicleOwnerHumans.setText(R.string.confirm_order);

                binding.etOwnerProposalPriceHumans.setVisibility(View.VISIBLE);
                binding.llOwnerVehicleSelectionHumans.setVisibility(View.VISIBLE);
                binding.llOwnerVehicleSelectionHumans.setOnClickListener(v -> getYourVehicles(1));

            } else {
                if (order.getProposalCount() > 0) {
                    binding.tvEditHumans.setVisibility(GONE);
                } else {
                    binding.tvEditHumans.setOnClickListener(v -> {
                        if (binding.tvEditHumans.getText().toString().toLowerCase().equals(getString(R.string.edit).toLowerCase())) {

                            setEditable(binding.etFrom);
                            setEditable(binding.etTo);
                            setEditable(binding.etPrice);
                            setEditable(binding.etPeoples);
                            setEditable(binding.etLuggageWeight);
                            binding.rbWithLuggage.setClickable(true);
                            binding.rbWithoutLuggage.setClickable(true);
                            binding.tvEditHumans.setText(R.string.save);


                            binding.llVehicleSelectionHumans.setOnClickListener(v1 -> getVehicles());
                            binding.cvDate.setOnClickListener(v1 -> selectDate());
                            Toast.makeText(activity, R.string.now_you_can_edit_this_order, Toast.LENGTH_SHORT).show();
                        } else {
                            updateOrder();
                        }
                    });
                }
            }
            binding.tvSelectVehicleOwnerHumans.setOnClickListener(v -> selectOwnerVehicleOrConfirmOrder());
        } else {
            isHuman = false;
            if (isOwner)
                binding.tvTitle.setText(R.string.goods_receive_order);

            binding.etProductName.setText(product.getProductName());
            selectedMaterial = new RowMaterials.ProductMaterial(product.getMaterialId()
                    , product.getMaterialName(), product.getMaterialImage());
            binding.tvSelectMaterial.setText(product.getMaterialName());

            binding.rbSolid.setChecked(productState == Constants.SOLID);
            binding.rbLiquid.setChecked(productState == Constants.LIQUID);
            binding.rbGas.setChecked(productState == Constants.GAS);

            binding.etProductWeight.setText(product.getQty().toString());
            binding.tvSelectWeight.setText(product.getUoM());
            binding.tvSelectSize.setText(product.getSizeType());
            binding.etWidth.setText(product.getSizeWidth());
            binding.etHeight.setText(product.getSizeHeight());

            binding.etBudget.setText(product.getPrice().toString());

            selectedVehicle = new Vehicles.VehicleType(order.getVehicleTypeId(), order.getVehicleType(), order.getVehicalTypeImage());

            binding.tvVehicleSelectionGoods.setText(order.getVehicleType());
            GlideApp.with(activity).load(ApiClient.PREFIX + order.getVehicalTypeImage()).into(binding.ivVehicleSelectionGoods);

            binding.rbPrivate.setChecked(!order.getShared());
            binding.rbSharing.setChecked(order.getShared());
            binding.rbByCustomer.setChecked(order.getLoaded());
            binding.rbByVehicleOwner.setChecked(!order.getLoaded());

            binding.etReceiverName.setText(order.getContactName());
            binding.etReceiverNumber.setText(order.getContactNumber());

            if (isOwner) {

                binding.tvEditGoods.setVisibility(GONE);
                binding.tvSelectVehicleOwnerGoods.setText(R.string.confirm_order);


                binding.etOwnerProposalPriceGoods.setVisibility(View.VISIBLE);
                binding.llOwnerVehicleSelectionGoods.setVisibility(View.VISIBLE);
                binding.llOwnerVehicleSelectionGoods.setOnClickListener(v -> getYourVehicles(1));

            } else {
                if (order.getProposalCount() > 0) {
                    binding.tvEditGoods.setVisibility(GONE);
                } else {
                    binding.tvEditGoods.setOnClickListener(v -> {
                        if (binding.tvEditGoods.getText().toString().toLowerCase().equals(getString(R.string.edit).toLowerCase())) {
                            setEditable(binding.etFrom);
                            setEditable(binding.etTo);
                            setEditable(binding.etProductName);
                            binding.rbSolid.setClickable(true);
                            binding.rbLiquid.setClickable(true);
                            binding.rbGas.setClickable(true);

                            setEditable(binding.etProductWeight);
                            setEditable(binding.etWidth);
                            setEditable(binding.etHeight);
                            setEditable(binding.etBudget);

                            binding.rbPrivate.setClickable(true);
                            binding.rbSharing.setClickable(true);
                            binding.rbByCustomer.setClickable(true);
                            binding.rbByVehicleOwner.setClickable(true);

                            setEditable(binding.etReceiverName);
                            setEditable(binding.etReceiverNumber);


                            binding.tvEditGoods.setText(R.string.save);

                            binding.tvSelectSize.setOnClickListener(v1 -> {
                                new AlertDialog.Builder(activity)
                                        .setTitle(R.string.select_size_unit)
                                        .setItems(sizes, (dialog, which) -> binding.tvSelectSize.setText(sizes[which]))
                                        .setPositiveButton(android.R.string.cancel, null)
                                        .show();
                            });
                            binding.tvSelectWeight.setOnClickListener(v1 -> {
                                new AlertDialog.Builder(activity)
                                        .setTitle(R.string.select_weight_unit)
                                        .setItems(weights, (dialog, which) -> binding.tvSelectWeight.setText(weights[which]))
                                        .setPositiveButton(android.R.string.cancel, null)
                                        .show();
                            });

                            binding.tvSelectMaterial.setOnClickListener(v1 -> getMaterials());
                            binding.llVehicleSelectionGoods.setOnClickListener(v1 -> getVehicles());
                            binding.cvDate.setOnClickListener(v1 -> selectDate());

                            Toast.makeText(activity, R.string.now_you_can_edit_this_order, Toast.LENGTH_SHORT).show();

                        } else {
                            updateOrder();
                        }

                    });
                }
            }
            binding.tvSelectVehicleOwnerGoods.setOnClickListener(v -> selectOwnerVehicleOrConfirmOrder());

        }
    }

    private void selectOwnerVehicleOrConfirmOrder() {
        if (isOwner) {
            if (isValidConfirmOrder()) {
                if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
                DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

                Map<String, Object> data = new HashMap<>();
                data.put("vehicleId", selectedOwnerVehicle.getVehicleId());
                data.put("userId", SharedPrefs.getUserId(activity));
                data.put("orderSummaryId", order.getOrderSummaryId());
                data.put("price", order.getProductList().get(0).getProductState() == Constants.HUMAN ?
                        binding.etOwnerProposalPriceHumans.getText().toString() : binding.etOwnerProposalPriceGoods.getText().toString());
                data.put("description", "");
                data.put("proposalDateTime", AppUtils.getDateCurrentTimeZone(System.currentTimeMillis()));
                data.put("createdBy", SharedPrefs.getUserId(activity));

                disposable.add(getApiService().addVehicleProposal(SharedPrefs.getAuthToken(activity), data).compose(applySchedulers())
                        .subscribe(statusObjectHolder -> {
                            DialogUtil.dismissProgressDialog();
                            if (statusObjectHolder.getStatus().getCode() == 0) {
                            } else {
                            }
                            Toast.makeText(activity, statusObjectHolder.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            DialogUtil.dismissProgressDialog();
                            throwable.printStackTrace();
                            Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                        }));
            }
        } else {
            Intent intent = new Intent(activity, OrderProposalsActivity.class);
            intent.putExtra(Constants.ORDER_ID, order.getOrderSummaryId().toString());
            intent.putExtra(Constants.IS_HUMAN,isHuman);
            startActivity(intent);
        }
    }

    private boolean isValidConfirmOrder() {
        if (selectedOwnerVehicle == null) {
            Toast.makeText(activity, R.string.select_your_vehicle, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (order.getProductList().get(0).getProductState() == Constants.HUMAN) {
            if (TextUtils.isEmpty(binding.etOwnerProposalPriceHumans.getText())) {
                Toast.makeText(activity, R.string.enter_proposal_price, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (TextUtils.isEmpty(binding.etOwnerProposalPriceGoods.getText())) {
                Toast.makeText(activity, R.string.enter_proposal_price, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void getYourVehicles(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

        Map<String, Object> data = new HashMap<>();
        data.put("id", SharedPrefs.getUserId(activity));
        data.put("pageNo", page);
        data.put("pageSize", MAX_LIMIT);

        if (page == 1) moreOwnerVehicleLoad = true;

        disposable.add(getApiService().getVehicleByUserId(SharedPrefs.getAuthToken(activity), data)
                .compose(applySchedulers())
                .subscribe(ownerVehicle -> {
                    DialogUtil.dismissProgressDialog();
                    if (ownerVehicle.getStatus().getCode() == 0) {
                        if (selectedOwnerVehicle != null) {
                            for (OwnerVehicle.Vehicle vehicle : ownerVehicle.getVehicle()) {
                                vehicle.setSelected(selectedOwnerVehicle.getVehicleId().equals(vehicle.getVehicleId()));
                            }
                        }
                        if (page == 1)
                            openOwnerVehicleSelectDialog(page, ownerVehicle.getVehicle());
                        else {
                            ownerVehicleDialogAdapter.addData(page, ownerVehicle.getVehicle());
                        }
                    } else
                        Toast.makeText(activity, ownerVehicle.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));
    }

    private void openOwnerVehicleSelectDialog(int page, List<OwnerVehicle.Vehicle> vehicleList) {
        Dialog dialog = new Dialog(activity);
        DialogRowMaterialBinding dialogRowMaterialBinding = DialogRowMaterialBinding.
                inflate(getLayoutInflater(), null, false);
        dialog.setContentView(dialogRowMaterialBinding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setOnShowListener(dialog1 ->
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)));

        dialog.setCancelable(true);

        ownerVehicleDialogAdapter = new OwnerVehicleDialogAdapter((position, mVehicle) -> {
            selectedOwnerVehicle = mVehicle;
            if (order.getProductList().get(0).getProductState() != Constants.HUMAN) {
                binding.tvOwnerVehicleSelectionGoods.setText(selectedOwnerVehicle.getVehicleType());
                GlideApp.with(activity).load(ApiClient.PREFIX + selectedOwnerVehicle.getImage()).into(binding.ivOwnerVehicleSelectionGoods);
            } else {
                binding.tvOwnerVehicleSelectionHuman.setText(selectedOwnerVehicle.getVehicleType());
                GlideApp.with(activity).load(ApiClient.PREFIX + selectedOwnerVehicle.getImage()).into(binding.ivOwnerVehicleSelectionHuman);
            }
            dialog.dismiss();
        });


        GridLayoutManager manager = new GridLayoutManager(activity, 3);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager, 5) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!moreOwnerVehicleLoad) return;
                getYourVehicles(page);
            }
        };
        dialogRowMaterialBinding.rvList.setLayoutManager(manager);
        dialogRowMaterialBinding.rvList.addOnScrollListener(scrollListener);


        dialogRowMaterialBinding.tvTitle.setText(R.string.select_your_vehicle);
        dialogRowMaterialBinding.rvList.setAdapter(ownerVehicleDialogAdapter);


        ownerVehicleDialogAdapter.addData(page, vehicleList);
        dialog.show();
    }

    private void updateOrder() {
        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
        if (!isValid()) return;
        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

        List<AddUpdateOrder.ProductList> productList = new ArrayList<>();
        boolean isNotHuman = order.getProductList().get(0).getProductState() != Constants.HUMAN;
        productList.add(new AddUpdateOrder.ProductList(isNotHuman ?
                (binding.rbSolid.isChecked() ? Constants.SOLID
                        : binding.rbLiquid.isChecked() ? Constants.LIQUID : Constants.GAS)
                : Constants.HUMAN,
                isNotHuman ? binding.etProductName.getText().toString() : "",
                order.getProductList().get(0).getOrderDetailId(),
                isNotHuman ? binding.etProductWeight.getText().toString() : binding.etPeoples.getText().toString(),
                isNotHuman ? binding.tvSelectWeight.getText().toString() : "",
                isNotHuman ? binding.tvSelectSize.getText().toString() : "",
                isNotHuman ? binding.etWidth.getText().toString() : "",
                isNotHuman ? binding.etHeight.getText().toString() : "",
                isNotHuman ? binding.etBudget.getText().toString() : binding.etPrice.getText().toString(),
                isNotHuman ? selectedMaterial.getMaterialId().toString() : "",
                isNotHuman ? "" : binding.etLuggageWeight.getText().toString()));

        AddUpdateOrder addUpdateOrder =
                new AddUpdateOrder(binding.etTo.getText().toString(),
                        Integer.parseInt(order.getOrderNo()),
                        24.585777,
                        isNotHuman ? binding.etReceiverName.getText().toString() : "",
                        72.222222,
                        SharedPrefs.getUserId(activity),
                        24.585255,
                        binding.etTo.getText().toString(),
                        isNotHuman ? binding.rbByCustomer.isChecked() : binding.rbWithLuggage.isChecked(),
                        72.222555,
                        isNotHuman ? binding.etBudget.getText().toString() : binding.etPrice.getText().toString(),
                        order.getOrderSummaryId(),
                        isNotHuman ? binding.etReceiverNumber.getText().toString() : "",
                        binding.etFrom.getText().toString(),
                        binding.etFrom.getText().toString(),
                        isNotHuman ? selectedVehicle.getVehicleTypeId() : humanSelectedVehicle.getVehicleTypeId(),
                        AppUtils.getDateCurrentTimeZone(orderDate),
                        isNotHuman && binding.rbSharing.isChecked(),
                        productList,
                        Constants.ORDER_OPEN,
                        SharedPrefs.getString(activity, SharedPrefs.CITY_ID),
                        SharedPrefs.getString(activity, SharedPrefs.STATE_ID));

//        disposable.add(getApiService().addUpdateOrder(header, data).compose(applySchedulers())
        disposable.add(getApiService().addUpdateOrder(SharedPrefs.getAuthToken(activity), addUpdateOrder)
                .compose(applySchedulers())
                .subscribe(addUpdateOrderResponse -> {
                    DialogUtil.dismissProgressDialog();
                    if (addUpdateOrderResponse.getStatus() != null) {
                        if (addUpdateOrderResponse.getStatus().getCode() == 0) {
                            setResult(RESULT_OK);
                        }
                        Toast.makeText(activity, addUpdateOrderResponse.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(binding.etFrom.getText())) {
            Toast.makeText(activity, R.string.select_your_location, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(binding.etTo.getText())) {
            Toast.makeText(activity, R.string.select_target_location, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (orderDate == 0) {
            Toast.makeText(activity, R.string.select_date, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (order.getProductList().get(0).getProductState() != Constants.HUMAN) {
            if (TextUtils.isEmpty(binding.etProductName.getText())) {
                Toast.makeText(activity, R.string.enter_product_name, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (selectedMaterial == null) {
                Toast.makeText(activity, R.string.select_materials, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (binding.rbgMaterial.getCheckedRadioButtonId() == -1) {
                Toast.makeText(activity, R.string.select_product_status_type, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(binding.etProductWeight.getText())) {
                Toast.makeText(activity, R.string.enter_weight, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(binding.etWidth.getText())) {
                Toast.makeText(activity, R.string.enter_width, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(binding.etHeight.getText())) {
                Toast.makeText(activity, R.string.enter_height, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(binding.etBudget.getText())) {
                Toast.makeText(activity, R.string.enter_your_budget, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (selectedVehicle == null) {
                Toast.makeText(activity, R.string.select_vehicle, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (binding.rbgVehicleTyp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(activity, R.string.select_vehicle_type, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (binding.rbgLoading.getCheckedRadioButtonId() == -1) {
                Toast.makeText(activity, R.string.select_loading_unloading, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(binding.etReceiverName.getText())) {
                Toast.makeText(activity, R.string.enter_contact_name, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(binding.etReceiverNumber.getText())) {
                Toast.makeText(activity, R.string.enter_receiver_contact_number, Toast.LENGTH_SHORT).show();
                return false;
            }


        } else {
            if (TextUtils.isEmpty(binding.etPrice.getText())) {
                Toast.makeText(activity, R.string.enter_price, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(binding.etPeoples.getText())) {
                Toast.makeText(activity, R.string.enter_peoples_number, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (binding.rbgLuggage.getCheckedRadioButtonId() == -1) {
                Toast.makeText(activity, R.string.select_luggage_option, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (humanSelectedVehicle == null) {
                Toast.makeText(activity, R.string.select_vehicle, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(binding.etLuggageWeight.getText())) {
                Toast.makeText(activity, R.string.enter_luggage_approx_weight, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void getVehicles() {
        if (vehicleList != null) {
            openVehicleSelectDialog();
            return;
        }

        if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
        DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

        Map<String, Object> header = new HashMap<>();
        header.put("AuthToken", SharedPrefs.getAuthToken(activity));

        disposable.add(getApiService().getVehicleType(header)
                .compose(applySchedulers())
                .subscribe(vehicles -> {
                    DialogUtil.dismissProgressDialog();
                    if (vehicles.getStatus().getCode() == 0) {
                        vehicleList = vehicles.getVehicleType();
                        openVehicleSelectDialog();
                    } else
                        Toast.makeText(activity, vehicles.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    private void openVehicleSelectDialog() {
        Dialog dialog = new Dialog(activity);
        DialogRowMaterialBinding dialogRowMaterialBinding = DialogRowMaterialBinding.inflate(getLayoutInflater(), null, false);
        dialog.setContentView(dialogRowMaterialBinding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setOnShowListener(dialog1 -> Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)));

        dialog.setCancelable(true);


        VehicleDialogAdapter vehicleDialogAdapter = new VehicleDialogAdapter((position, vehicleType) -> {
            if (order.getProductList().get(0).getProductState() != Constants.HUMAN) {
                selectedVehicle = vehicleType;
                binding.tvVehicleSelectionGoods.setText(selectedVehicle.getVehicleType());
                GlideApp.with(activity).load(ApiClient.PREFIX + selectedVehicle.getImage()).into(binding.ivVehicleSelectionGoods);
            } else {
                humanSelectedVehicle = vehicleType;
                binding.tvVehicleSelectionHumans.setText(humanSelectedVehicle.getVehicleType());
                GlideApp.with(activity).load(ApiClient.PREFIX + humanSelectedVehicle.getImage()).into(binding.ivVehicleSelectionHuman);
            }
            dialog.dismiss();
        });

        dialogRowMaterialBinding.tvTitle.setText(R.string.select_vehicle);
        dialogRowMaterialBinding.rvList.setAdapter(vehicleDialogAdapter);

        vehicleDialogAdapter.addData(vehicleList);
        dialog.show();
    }

    private void getMaterials() {
        if (materialList == null) {
            if (!AppUtils.isNetworkAvailableWithDialog(activity)) return;
            DialogUtil.showProgressDialog(activity, getSupportFragmentManager());

            Map<String, Object> header = new HashMap<>();
            header.put("AuthToken", SharedPrefs.getAuthToken(activity));


            disposable.add(getApiService().getProductMaterial(header)
                    .compose(ApiClient.applySchedulers())
                    .subscribe(rowMaterials -> {
                        DialogUtil.dismissProgressDialog();
                        if (rowMaterials.getStatus().getCode() == 0) {
                            materialList = rowMaterials.getProductMaterial();
                            openSelectMaterialDialog();
                        } else
                            Toast.makeText(activity, rowMaterials.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        DialogUtil.dismissProgressDialog();
                        throwable.printStackTrace();
                        Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
                    }));
        } else openSelectMaterialDialog();
    }


    private void openSelectMaterialDialog() {
        if (selectedMaterial != null) {
            for (RowMaterials.ProductMaterial productMaterial : materialList) {
                if (selectedMaterial.getMaterialId().equals(productMaterial.getMaterialId())) {
                    productMaterial.setSelected(true);
                    selectedMaterial = productMaterial;
                } else productMaterial.setSelected(false);
            }
        }

        Dialog dialog = new Dialog(activity);
        DialogRowMaterialBinding dialogRowMaterialBinding = DialogRowMaterialBinding.inflate(getLayoutInflater(), null, false);
        dialog.setContentView(dialogRowMaterialBinding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setOnShowListener(dialog1 -> Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)));

        dialog.setCancelable(true);
        RowMatirealDialogAdapter adapter = new RowMatirealDialogAdapter((position, productMaterial) -> {
            selectedMaterial = productMaterial;
            binding.tvSelectMaterial.setText(selectedMaterial.getName());
            dialog.dismiss();
        });
        dialogRowMaterialBinding.rvList.setAdapter(adapter);
        adapter.addData(materialList);
        dialog.show();
    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        new DatePickerDialog(activity, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(activity, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                orderDate = calendar.getTimeInMillis();
                binding.tvDate.setText(dateFormat.format(calendar.getTime()));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void setEditable(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setClickable(true);
    }

}
