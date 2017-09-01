package com.github.rubensousa.bottomsheetbuilder.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.github.rubensousa.bottomsheetbuilder.sample.implementations.InternalUseOnly;
import com.github.rubensousa.bottomsheetbuilder.util.BottomSheetBuilderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends InternalUseOnly {

    public static final String STATE_SIMPLE = "state_simple";
    public static final String STATE_HEADER = "state_header";
    public static final String STATE_GRID = "state_grid";
    public static final String STATE_LONG = "state_long";

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        View bottomSheet = new BottomSheetBuilder(this, coordinatorLayout)
                .setMode(BottomSheetBuilder.MODE_GRID)
                .setBackgroundColorResource(android.R.color.white)
                .setMenu(R.menu.menu_bottom_grid_sheet)
                .setItemClickListener(this)
                .createView();

        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    fab.show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BottomSheetBuilderUtils.saveState(outState, mBehavior);
        outState.putBoolean(STATE_SIMPLE, mShowingSimpleDialog);
        outState.putBoolean(STATE_GRID, mShowingGridDialog);
        outState.putBoolean(STATE_HEADER, mShowingHeaderDialog);
        outState.putBoolean(STATE_LONG, mShowingLongDialog);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BottomSheetBuilderUtils.restoreState(savedInstanceState, mBehavior);
        if (savedInstanceState.getBoolean(STATE_GRID)) onShowDialogGridClick();

        if (savedInstanceState.getBoolean(STATE_HEADER)) onShowDialogHeadersClick();

        if (savedInstanceState.getBoolean(STATE_SIMPLE)) onShowDialogClick();

        if (savedInstanceState.getBoolean(STATE_LONG)) onShowLongDialogClick();
    }

    @Override
    protected void onDestroy() {
        // Avoid leaked windows
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }


    @Override
    public void onBottomSheetItemClick(MenuItem item) {
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    public void onFabClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        fab.hide();
    }

}