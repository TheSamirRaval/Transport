package com.transport.myOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.R;
import com.transport.adapter.MyHumanOrderAdapter;
import com.transport.adapter.MyOrderAdapter;
import com.transport.api.model.Orders;
import com.transport.databinding.FragmentRecyclerViewBinding;
import com.transport.utils.AppUtils;
import com.transport.utils.Constants;
import com.transport.utils.DialogUtil;
import com.transport.utils.EndlessRecyclerViewScrollListener;
import com.transport.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;
import static com.transport.api.ApiClient.applySchedulers;
import static com.transport.api.ApiClient.getApiService;

/**
 * Created by SAM on 3/4/20.
 */
public class MyOrdersFragment extends Fragment {
    private boolean isHuman = false;
    private boolean isReceive = false;

    private FragmentRecyclerViewBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MyOrderAdapter myOrderAdapter;
    private MyHumanOrderAdapter myHumanOrderAdapter;
    private int MAX_LIMIT = 10;
    private boolean moreItemLoad = true;
    private int selectedItem, orderSummeryId;
    private String searchText = "";
    private boolean isOwner = false;

    MyOrdersFragment(boolean isHuman) {
        this.isHuman = isHuman;
        this.isReceive = false;
    }

    public MyOrdersFragment(boolean isReceive, boolean isHuman) {
        this.isHuman = isHuman;
        this.isReceive = isReceive;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isOwner = SharedPrefs.getUserType(requireContext()) == Constants.OWNER_TYPE;
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void init() {
        if (isHuman) {
            myHumanOrderAdapter = new MyHumanOrderAdapter(this::onItemClick);
            binding.rvList.setAdapter(myHumanOrderAdapter);
        } else {
            myOrderAdapter = new MyOrderAdapter(this::onItemClick);
            binding.rvList.setAdapter(myOrderAdapter);
        }
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity());
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager, 5) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!moreItemLoad) return;
                if (isReceive) {
                    if (isOwner) getFeedForVehicle(page);
                    else getFeedForUser(page);
                } else {
                    if (isOwner) getOwnerOrders(page);
                    else getMyOrder(page);
                }
            }
        };
        binding.rvList.setLayoutManager(manager);
        binding.rvList.addOnScrollListener(scrollListener);
        if (isReceive) {
            if (isOwner) getFeedForVehicle(1);
            else getFeedForUser(1);
        } else {
            if (isOwner) getOwnerOrders(1);
            else
                getMyOrder(1);
        }
    }

    private void getFeedForUser(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(requireContext())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());


        Map<String, Object> map = new HashMap<>();
        map.put("searchText", searchText);
        map.put("id", SharedPrefs.getUserId(requireContext()));
        map.put("isHuman", isHuman);
        map.put("pageNo", page);
        map.put("pageSize", MAX_LIMIT);


        disposable.add(getApiService().getFeedForUser(SharedPrefs.getAuthToken(requireContext()), map)
                .compose(applySchedulers())
                .subscribe(orders -> {
                    DialogUtil.dismissProgressDialog();
                    if (orders.getStatus().getCode() == 0) {
                        setList(page, orders.getAllOrders());
                        if (orders.getAllOrders().size() < MAX_LIMIT)
                            moreItemLoad = false;
                    } else
                        Toast.makeText(requireContext(), orders.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }


    private void onItemClick(int position, Orders.AllOrder.OrderList orderList) {
        if (isReceive && !isOwner) {
//            Intent intent =  new Intent(requireContext(),)
        } else {
            Intent intent = new Intent(requireActivity(), MyOrderDetailActivity.class);
            intent.putExtra(Constants.ORDER, orderList);
            if (isReceive) intent.setAction(Constants.CONFIRM_ORDER);
            selectedItem = position;
//            orderSummeryId = !isOwner ? orderList.getVehicleOrderSummaryId() : orderList.getOrderSummaryId();
           orderSummeryId = orderList.getOrderSummaryId();
//        intent.putExtra(Constants.POSITION, position);
            startActivityForResult(intent, Constants.ORDER_DETAIL);
        }
    }

    private void getFeedForVehicle(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(requireContext())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());

        if (page == 1) moreItemLoad = true;

        Map<String, Object> map = new HashMap<>();
        map.put("city", SharedPrefs.getString(requireContext(), SharedPrefs.CITY_ID));
        map.put("searchText", searchText);
        map.put("state", SharedPrefs.getString(requireContext(), SharedPrefs.STATE_ID));
        map.put("area", 0);
        map.put("fromPrice", 0);
        map.put("toPrice", 0);
        map.put("isShared", null);
        map.put("isLoaded", null);
//        map.put("isShared", true);
//        map.put("isLoaded", true);
        map.put("userId", SharedPrefs.getUserId(requireContext()));
        map.put("isHuman", isHuman);
        map.put("pageNo", page);
        map.put("pageSize", MAX_LIMIT);


        disposable.add(getApiService().getFeedForVehicle(SharedPrefs.getAuthToken(requireContext()), map)
                .compose(applySchedulers())
                .subscribe(orders -> {
                    DialogUtil.dismissProgressDialog();
                    if (orders.getStatus().getCode() == 0) {
                        setList(page, orders.getAllOrder());

                        if (orders.getAllOrder().size() < MAX_LIMIT)
                            moreItemLoad = false;
                    } else
                        Toast.makeText(requireContext(), orders.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));


    }

    private void getMyOrder(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(requireContext())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());

        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPrefs.getUserId(requireContext()));
        map.put("isHuman", isHuman);
        map.put("pageNo", page);
        map.put("pageSize", MAX_LIMIT);


        disposable.add(getApiService().getOrderByUserId(SharedPrefs.getAuthToken(requireContext()), map)
                .compose(applySchedulers())
                .subscribe(orders -> {
                    DialogUtil.dismissProgressDialog();
                    if (orders.getStatus().getCode() == 0) {
                        setList(page, orders.getAllOrder());
                    } else
                        Toast.makeText(requireContext(), orders.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    private void setList(int page, List<Orders.AllOrder> allOrder) {
        List<Object> mList = new ArrayList<>();
        int orderSize = 0;
        boolean checkedDateRepeat = page == 1;
        for (Orders.AllOrder order : allOrder) {
            if (!checkedDateRepeat) {
                List<Object> tempList = isHuman ? myHumanOrderAdapter.getData() : myOrderAdapter.getData();
                for (int i = tempList.size() - 1; i >= 0; i--) {
                    if (tempList.get(i) instanceof String) {
                        if (!tempList.get(i).equals(order.getDate()))
                            mList.add(order.getDate());
                        checkedDateRepeat = true;
                        break;
                    }
                }
            } else
                mList.add(order.getDate());
            mList.addAll(order.getOrderList());
            orderSize = orderSize + order.getOrderList().size();
        }
        if (orderSize < MAX_LIMIT) {
            moreItemLoad = false;
        }
        if (isHuman) myHumanOrderAdapter.addData(page, mList);
        else myOrderAdapter.addData(page, mList);

    }

    private void getOwnerOrders(int page) {
        if (!AppUtils.isNetworkAvailableWithDialog(requireContext())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());

        Map<String, Object> map = new HashMap<>();
        map.put("id", SharedPrefs.getUserId(requireContext()));
        map.put("isHuman", isHuman);
        map.put("pageNo", page);
        map.put("pageSize", MAX_LIMIT);

        disposable.add(getApiService().getVehicleOrderByUserId(SharedPrefs.getAuthToken(requireContext()), map)
                .compose(applySchedulers())
                .subscribe(orders -> {
                    DialogUtil.dismissProgressDialog();
                    if (orders.getStatus().getCode() == 0) {
                        setOwnerList(page, orders.getAllOrders());
                    } else
                        Toast.makeText(requireContext(), orders.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));
    }

    private void setOwnerList(int page, List<Orders.AllOrder> allOrders) {

        List<Object> mList = new ArrayList<>();
        int orderSize = 0;
        boolean checkedDateRepeat = page == 1;
        for (Orders.AllOrder order : allOrders) {
            if (!checkedDateRepeat) {
                List<Object> tempList = isHuman ? myHumanOrderAdapter.getData() : myOrderAdapter.getData();
                for (int i = tempList.size() - 1; i >= 0; i--) {
                    if (tempList.get(i) instanceof String) {
                        if (!tempList.get(i).equals(order.getDate()))
                            mList.add(order.getDate());
                        checkedDateRepeat = true;
                        break;
                    }
                }
            } else
                mList.add(order.getDate());
            mList.addAll(order.getOrderList());
            orderSize = orderSize + order.getOrderList().size();
        }

        if (orderSize < MAX_LIMIT) {
            moreItemLoad = false;
        }
        if (isHuman) myHumanOrderAdapter.addData(page, mList);
        else myOrderAdapter.addData(page, mList);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.ORDER_DETAIL) {
            if (resultCode == RESULT_OK) {
                updateItem();
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateItem() {
        if (!AppUtils.isNetworkAvailableWithDialog(requireContext())) return;
        DialogUtil.showProgressDialog(requireActivity(), requireFragmentManager());

        Map<String, Object> map = new HashMap<>();
        map.put("OrderSummaryId", orderSummeryId);
        map.put("isHuman", isHuman);
        map.put("pageNo", 1);
        map.put("pageSize", MAX_LIMIT);

        disposable.add(getApiService().getOrder(SharedPrefs.getAuthToken(requireContext()), map)
                .compose(applySchedulers())
                .subscribe(orders -> {
                    DialogUtil.dismissProgressDialog();
                    if (orders.getStatus().getCode() == 0) {
                        Orders.AllOrder.OrderList order = orders.getAllOrders().get(0).getOrderList().get(0);
                        if (isHuman) myHumanOrderAdapter.updateItem(selectedItem, order);
                        else myOrderAdapter.updateItem(selectedItem, order);

                    } else
                        Toast.makeText(requireContext(), orders.getStatus().getReturnMessage(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    DialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                    Toast.makeText(requireContext(), R.string.fail, Toast.LENGTH_SHORT).show();
                }));

    }

    public void search(String searchText) {
        this.searchText = searchText;
        if (isOwner) getFeedForUser(1);
        else getFeedForVehicle(1);

    }
}
