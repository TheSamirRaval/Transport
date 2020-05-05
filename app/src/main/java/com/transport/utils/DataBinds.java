package com.transport.utils;

import com.transport.R;
import com.transport.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 27/3/20.
 */
public class DataBinds {

    public static List<DrawerItem> getDrawerItems() {
        List<DrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem(0, R.string.home, R.drawable.unpress_home, R.drawable.press_home));
        drawerItems.add(new DrawerItem(1, R.string.my_profile, R.drawable.unpress_my_profile, R.drawable.press_my_profile));
        drawerItems.add(new DrawerItem(2, R.string.my_order, R.drawable.unpress_my_order, R.drawable.press_my_order));
        drawerItems.add(new DrawerItem(3, R.string.receive_order, R.drawable.unpress_recive_order, R.drawable.press_recive_order));
        drawerItems.add(new DrawerItem(4, R.string.my_wallet, R.drawable.unpress_my_wallet, R.drawable.press_my_wallet));
        drawerItems.add(new DrawerItem(5, R.string.setting, R.drawable.unpress_setting, R.drawable.press_setting));
        drawerItems.add(new DrawerItem(6, R.string.invite_friends, R.drawable.unpress_invite_friends, R.drawable.press_invite_friends));
        drawerItems.add(new DrawerItem(7, R.string.support_help, R.drawable.unpress_support_help, R.drawable.press_support_help));
        drawerItems.add(new DrawerItem(8, R.string.logout, R.drawable.unpress_log_out, R.drawable.press_log_out));
        return drawerItems;
    }
}
