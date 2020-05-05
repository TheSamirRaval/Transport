package com.transport.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ProgressDialogFragment extends DialogFragment {

    private final String TAG = this.getClass().getSimpleName();

    private String mTitle;
    private String mMessage;
    private ProgressOnCancelListener mOnCancelListener;
    private int mRequestCode;
    private boolean mIsCanceledOnTouchOutside = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        setTargetFragment(null, -1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog mDialog;
        if (mTitle != null || mMessage != null) {
            ProgressDialog mPDialog = new ProgressDialog(getActivity());
            if (mTitle != null)
                mPDialog.setTitle(mTitle);
            if (mMessage != null) {
                mPDialog.setMessage(mMessage);
            }
            mPDialog.setCanceledOnTouchOutside(mIsCanceledOnTouchOutside);
            mDialog = mPDialog;
        } else {
            //For transparent dialog without message
            mDialog = new Dialog(requireContext());
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //mDialog.setTitle(mTitle);
            //mDialog.setMessage(mMessage);
            mDialog.setCanceledOnTouchOutside(mIsCanceledOnTouchOutside);
            RelativeLayout mRelative = new RelativeLayout(getActivity());
            mRelative.setBackgroundColor(Color.TRANSPARENT);

            ProgressBar mProgress = new ProgressBar(getActivity());
            mRelative.addView(mProgress);

            mDialog.setContentView(mRelative);
            // set color transpartent
            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Objects.requireNonNull(mDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        }
        return mDialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null)
            mOnCancelListener.onProgressDialogCancelled(mRequestCode);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public void setCanceledOnTouchOutside(final boolean pIsCanceledOnTouchOutside) {
        mIsCanceledOnTouchOutside = pIsCanceledOnTouchOutside;
    }

    public void setTitle(String pTitle) {
        this.mTitle = pTitle;
    }

    public void setMessage(String pMessage) {
        this.mMessage = pMessage;
    }

    public void setOnCancelListener(ProgressOnCancelListener pOnCancelListener) {
        this.mOnCancelListener = pOnCancelListener;
    }

    public void setRequestCode(int pRequestCode) {
        this.mRequestCode = pRequestCode;
    }

    public interface ProgressOnCancelListener {
        public void onProgressDialogCancelled(final int pRequestCode);
    }

}