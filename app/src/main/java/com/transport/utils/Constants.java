package com.transport.utils;

/**
 * Created by SAM on 7/3/20.
 */
public class Constants {

    //region USER ROLE
    public static final int ADMIN_TYPE = 1;
    public static final int OWNER_TYPE = 2;
    public static final int USER_TYPE = 3;
    //endregion

    //region ORDER STATUS
    public static final int ORDER_OPEN = 1;
    public static final int ORDER_PENDING = 2;
    public static final int ORDER_CONFIRM = 3;
    public static final int ORDER_CLOSE = 4;
    public static final int ORDER_CANCEL = 5;
    //endregion

    //region PROPOSAL STATUS
    public static final int PROPOSAL_PENDING = 1;
    public static final int PROPOSAL_CONFIRM = 2;
    public static final int PROPOSAL_DECLINE = 3;
    public static final int PROPOSAL_IN_PROGRESS = 4;
    public static final int PROPOSAL_DONE = 5;
    public static final int PROPOSAL_CANCEL = 6;
    //endregion

    //region PRODUCT STATUS
    public static final int SOLID = 1;
    public static final int LIQUID = 2;
    public static final int GAS = 3;
    public static final int AEROSOL = 4;
    public static final int HUMAN = 5;
    //endregion

    //region DOCUMENT TYPE
    public static final int RC_BOOK = 1;
    public static final int VEHICLE_DOCUMENT = 2;
    public static final int VEHICLE_IMAGE = 3;
    //endregion


    //region PROPOSAL STATE
    public static final int PENDING = 1;
    public static final int CONFIRM = 2;
    public static final int DECLINE = 3;
    public static final int IN_PROGRESS = 4;
    public static final int DONE = 5;
    public static final int CANCEL = 6;
    //endregion


    public static final int REQUEST_CHECK_SETTINGS = 0x100;
    public static final int ORDER_DETAIL = 0x101;
    public static final int AUTOCOMPLETE_REQUEST_CODE = 0x102;

    public static final String DEVICE_INFO = "Android";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";


    public static final String STATE = "state";
    public static final String CITY = "city";

    public static final String BUNDLE = "bundle";

    public static final String ORDER = "order";
    public static final String CONFIRM_ORDER = "confirmOrder";
    public static final String POSITION = "position";
    public static final String ORDER_ID = "orderId";
    public static final String VEHICLE_PROPOSAL = "vehicleProposal";
    public static final String IS_HUMAN = "isHuman";
}

