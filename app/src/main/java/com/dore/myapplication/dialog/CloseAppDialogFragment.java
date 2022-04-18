package com.dore.myapplication.dialog;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dore.myapplication.R;

import java.util.Objects;

public class CloseAppDialogFragment extends DialogFragment {

    private View mRootView;

    private Button btnCancel;

    private Button btnClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_fragment_close_app, container, false);

        initView();
        initAction();
        return mRootView;

    }

    @Override
    public void onResume() {
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        Objects.requireNonNull(getDialog()).getWindow().setLayout(width, 1200);
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        super.onResume();
    }

    private void initAction() {
        btnCancel.setOnClickListener(v -> {
            CloseAppDialogFragment.this.dismiss();
        });

        btnClose.setOnClickListener(v -> {
            requireActivity().finishAffinity();
        });
    }

    private void initView() {
        btnCancel = mRootView.findViewById(R.id.btn_cancel);
        btnClose = mRootView.findViewById(R.id.btn_close);

    }

}
