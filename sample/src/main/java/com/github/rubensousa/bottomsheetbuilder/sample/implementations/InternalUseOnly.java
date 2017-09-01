package com.github.rubensousa.bottomsheetbuilder.sample.implementations;

import android.content.DialogInterface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.github.rubensousa.bottomsheetbuilder.sample.R;

import butterknife.OnClick;

/**
 * Created by hesk on 1/9/2017.
 */

public abstract class InternalUseOnly extends AppCompatActivity implements BottomSheetItemClickListener {

    protected BottomSheetMenuDialog mBottomSheetDialog;
    protected BottomSheetBehavior mBehavior;


    protected boolean mShowingSimpleDialog;
    protected boolean mShowingHeaderDialog;
    protected boolean mShowingGridDialog;
    protected boolean mShowingLongDialog;


    protected abstract AppBarLayout getAppBarLayout();


    @SuppressWarnings("unused")
    @OnClick(R.id.showDialogBtn)
    public void onShowDialogClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }

        mShowingSimpleDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(getAppBarLayout())
                .addTitleItem("Custom title")
                .addItem(0, "Preview", R.drawable.ic_preview_24dp)
                .addItem(1, "Share", R.drawable.ic_share_24dp)
                .addDividerItem()
                .addItem(2, "Get link", R.drawable.ic_link_24dp)
                .addItem(3, "Make a copy", R.drawable.ic_content_copy_24dp)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingSimpleDialog = false;
                    }
                })
                .createDialog();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingSimpleDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.showDialogHeadersBtn)
    public void onShowDialogHeadersClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingHeaderDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(getAppBarLayout())
                .setMenu(R.menu.menu_bottom_headers_sheet)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingHeaderDialog = false;
                    }
                })
                .createDialog();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingHeaderDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.showDialogGridBtn)
    public void onShowDialogGridClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingGridDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_GRID)
                .setAppBarLayout(getAppBarLayout())
                .setMenu(getResources().getBoolean(R.bool.tablet_landscape)
                        ? R.menu.menu_bottom_grid_tablet_sheet : R.menu.menu_bottom_grid_sheet)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingGridDialog = false;
                    }
                })
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingGridDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.showDialogLongBtn)
    public void onShowLongDialogClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingLongDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(getAppBarLayout())
                .setMenu(R.menu.menu_bottom_list_sheet)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingLongDialog = false;
                    }
                })
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingLongDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.onShowCustom)
    public void onShowCustom() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingLongDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_FULL_CUSTOM)
                .setAppBarLayout(getAppBarLayout())
                .setItemLayout(R.layout.demo_res)
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingLongDialog = false;
            }
        });
        mBottomSheetDialog.show();

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.onShowTy)
    public void onShowCustomTwo() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingLongDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_FULL_CUSTOM)
                .setItemLayout(R.layout.demo_res)
                .setRightShift(300)
                .setPeekAll()
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingLongDialog = false;
            }
        });
        mBottomSheetDialog.show();

    }



    @SuppressWarnings("unused")
    @OnClick(R.id.showViewBtn)
    public void onShowViewClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


}
