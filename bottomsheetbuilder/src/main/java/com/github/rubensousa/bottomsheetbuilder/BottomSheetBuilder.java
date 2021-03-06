/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.bottomsheetbuilder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetAdapterBuilder;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.github.rubensousa.bottomsheetbuilder.adapter.popVivController;


public class BottomSheetBuilder {

    public static final int MODE_LIST = 0;
    public static final int MODE_GRID = 1;
    public static final int MODE_FULL_CUSTOM = 2;
    @DrawableRes
    protected int mItemBackground;
    protected int mItemTextColor;
    protected int mIconTintColor = -1;
    protected BottomSheetAdapterBuilder mAdapterBuilder;
    protected Context mContext;
    @DrawableRes
    private int mBackgroundDrawable;
    @DrawableRes
    private int mDividerBackground;
    @StyleRes
    private int mTheme;
    @LayoutRes
    private int itemLayoutRes = -1;
    private int mBackgroundColor;
    private int mTitleTextColor;
    private int rightshift = 0;
    private boolean mDelayedDismiss = true;
    private boolean mExpandOnStart = false;
    private boolean mExpandFullHeight = false;
    private int mMode;
    private Menu mMenu;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private BottomSheetItemClickListener mItemClickListener;
    private popVivController mpopVivController;

    public BottomSheetBuilder(Context context, CoordinatorLayout coordinatorLayout) {
        mContext = context;
        mCoordinatorLayout = coordinatorLayout;
        mAdapterBuilder = getBottomSheetAdapterBuilder();
    }

    public BottomSheetBuilder(Context context) {
        this(context, 0);
    }

    public BottomSheetBuilder(Context context, @StyleRes int theme) {
        mContext = context;
        mTheme = theme;
        mAdapterBuilder = getBottomSheetAdapterBuilder();
    }

    @NonNull
    protected BottomSheetAdapterBuilder getBottomSheetAdapterBuilder() {
        return new BottomSheetAdapterBuilder(mContext);
    }

    public BottomSheetBuilder setMode(int mode) {
        if (mode != MODE_LIST && mode != MODE_GRID && mode != MODE_FULL_CUSTOM) {
            throw new IllegalArgumentException("Mode must be one of BottomSheetBuilder.MODE_LIST" +
                    "or BottomSheetBuilder.MODE_GRID");
        }
        mMode = mode;
        mAdapterBuilder.setMode(mode);
        return this;
    }

    public BottomSheetBuilder setItemClickListener(BottomSheetItemClickListener listener) {
        mItemClickListener = listener;
        return this;
    }


    public BottomSheetBuilder setItemLayout(@LayoutRes int layoutRes) {
        this.itemLayoutRes = layoutRes;
        return this;
    }

    public BottomSheetBuilder setPeekAll() {
        mExpandFullHeight = true;
        return this;
    }

    public BottomSheetBuilder setMenu(Menu menu) {
        mMenu = menu;
        mAdapterBuilder.setMenu(mMenu);
        return this;
    }

    public BottomSheetBuilder addTitleItem(@StringRes int title) {
        return addTitleItem(mContext.getString(title));
    }

    public BottomSheetBuilder addTitleItem(String title) {
        if (mMode == BottomSheetBuilder.MODE_GRID) {
            throw new IllegalStateException("You can't add a title with MODE_GRID. " +
                    "Use MODE_LIST instead");
        }
        mAdapterBuilder.addTitleItem(title, mTitleTextColor);
        return this;
    }

    public BottomSheetBuilder addDividerItem() {
        if (mMode == BottomSheetBuilder.MODE_GRID) {
            throw new IllegalStateException("You can't add a divider with MODE_GRID. " +
                    "Use MODE_LIST instead");
        }
        mAdapterBuilder.addDividerItem(mDividerBackground);
        return this;
    }

    public BottomSheetBuilder addItem(int id, @StringRes int title, @DrawableRes int icon) {
        return addItem(id, mContext.getString(title), ContextCompat.getDrawable(mContext, icon));
    }

    public BottomSheetBuilder addItem(int id, @StringRes int title, Drawable icon) {
        return addItem(id, mContext.getString(title), icon);
    }

    public BottomSheetBuilder addItem(int id, String title, @DrawableRes int icon) {
        return addItem(id, title, ContextCompat.getDrawable(mContext, icon));
    }

    public BottomSheetBuilder addItem(int id, String title, Drawable icon) {
        mAdapterBuilder.addItem(id, title, icon, mItemTextColor, mItemBackground, mIconTintColor);
        return this;
    }

    public BottomSheetBuilder setItemTextColor(@ColorInt int color) {
        mItemTextColor = color;
        return this;
    }

    public BottomSheetBuilder setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        return this;
    }

    public BottomSheetBuilder setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
        return this;
    }

    public BottomSheetBuilder setItemTextColorResource(@ColorRes int color) {
        mItemTextColor = ResourcesCompat.getColor(mContext.getResources(), color, mContext.getTheme());
        return this;
    }

    public BottomSheetBuilder setTitleTextColorResource(@ColorRes int color) {
        mTitleTextColor = ResourcesCompat.getColor(mContext.getResources(), color, mContext.getTheme());
        return this;
    }

    public BottomSheetBuilder setBackground(@DrawableRes int background) {
        mBackgroundDrawable = background;
        return this;
    }

    public BottomSheetBuilder setBackgroundColorResource(@ColorRes int background) {
        mBackgroundColor = ResourcesCompat.getColor(mContext.getResources(), background,
                mContext.getTheme());
        return this;
    }

    public BottomSheetBuilder setDividerBackground(@DrawableRes int background) {
        mDividerBackground = background;
        return this;
    }

    public BottomSheetBuilder setRightShift(int shift) {
        rightshift = shift;
        return this;
    }

    public BottomSheetBuilder setItemBackground(@DrawableRes int background) {
        mItemBackground = background;
        return this;
    }

    public BottomSheetBuilder expandOnStart(boolean expand) {
        mExpandOnStart = expand;
        return this;
    }

    public BottomSheetBuilder delayDismissOnItemClick(boolean dismiss) {
        mDelayedDismiss = dismiss;
        return this;
    }

    public BottomSheetBuilder setAppBarLayout(AppBarLayout appbar) {
        mAppBarLayout = appbar;
        return this;
    }

    public BottomSheetBuilder setIconTintColorResource(@ColorRes int color) {
        mIconTintColor = ContextCompat.getColor(mContext, color);
        return this;
    }

    public BottomSheetBuilder setIconTintColor(int color) {
        mIconTintColor = color;
        return this;
    }

    /**
     * the controller settings for specific
     *
     * @param controller controller
     * @return bottom sheet builder
     */
    public BottomSheetBuilder setControllerView(popVivController controller) {
        mpopVivController = controller;
        return this;
    }

    public BottomSheetBuilder setMenu(@MenuRes int menu) {
        mMenu = new MenuBuilder(mContext);
        new SupportMenuInflater(mContext).inflate(menu, mMenu);
        return setMenu(mMenu);
    }

    public View createView() {

        if (mMenu == null && mAdapterBuilder.getItems().isEmpty()) {
            throw new IllegalStateException("You need to provide at least one Menu " +
                    "or an item with addItem");
        }

        if (mCoordinatorLayout == null) {
            throw new IllegalStateException("You need to provide a coordinatorLayout" +
                    "so the view can be placed on it");
        }
        View sheet;

        if (mMode == MODE_FULL_CUSTOM) {
            sheet = LayoutInflater.from(mContext).inflate(itemLayoutRes, null);
        } else {
            sheet = mAdapterBuilder.createView(mTitleTextColor, mBackgroundDrawable,
                    mBackgroundColor, mDividerBackground, mItemTextColor, mItemBackground,
                    mIconTintColor, itemLayoutRes, mItemClickListener);
        }

        ViewCompat.setElevation(sheet, mContext.getResources()
                .getDimensionPixelSize(R.dimen.bottomsheet_elevation));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sheet.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        }

        CoordinatorLayout.LayoutParams layoutParams
                = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setBehavior(new BottomSheetBehavior());

        if (mContext.getResources().getBoolean(R.bool.tablet_landscape)) {
            layoutParams.width = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.bottomsheet_width);
        }

        mCoordinatorLayout.addView(sheet, layoutParams);
        mCoordinatorLayout.postInvalidate();
        return sheet;
    }


    public BottomSheetMenuDialog createDialog() {

        BottomSheetMenuDialog dialog = mTheme == 0
                ? new BottomSheetMenuDialog(mContext, R.style.BottomSheetBuilder_DialogStyle)
                : new BottomSheetMenuDialog(mContext, mTheme);


        if (mTheme != 0) {
            setupThemeColors(mContext.obtainStyledAttributes(mTheme, new int[]{
                    R.attr.bottomSheetBuilderBackgroundColor,
                    R.attr.bottomSheetBuilderItemTextColor,
                    R.attr.bottomSheetBuilderTitleTextColor}));
        } else {
            setupThemeColors(mContext.getTheme().obtainStyledAttributes(new int[]{
                    R.attr.bottomSheetBuilderBackgroundColor,
                    R.attr.bottomSheetBuilderItemTextColor,
                    R.attr.bottomSheetBuilderTitleTextColor,}));
        }

        View sheet;

        if (mMode == MODE_FULL_CUSTOM) {
            sheet = LayoutInflater.from(mContext).inflate(itemLayoutRes, null);
            if (mpopVivController != null)
                mpopVivController.onCreate(sheet);
        } else {
            if (mMenu == null && mAdapterBuilder.getItems().isEmpty()) {
                throw new IllegalStateException("You need to provide at least one Menu " +
                        "or an item with addItem");
            }
            sheet = mAdapterBuilder.createView(mTitleTextColor, mBackgroundDrawable,
                    mBackgroundColor, mDividerBackground, mItemTextColor, mItemBackground,
                    mIconTintColor, itemLayoutRes, dialog);

            sheet.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        }
        dialog.setRightShift(rightshift);
        dialog.setPeekHeight(mExpandFullHeight);
        dialog.setAppBar(mAppBarLayout);
        dialog.expandOnStart(mExpandOnStart);
        dialog.delayDismiss(mDelayedDismiss);
        dialog.setBottomSheetItemClickListener(mItemClickListener);

        if (mContext.getResources().getBoolean(R.bool.tablet_landscape)) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mContext.getResources()
                    .getDimensionPixelSize(R.dimen.bottomsheet_width), ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(sheet, layoutParams);
        } else {
            dialog.setContentView(sheet);
        }

        return dialog;
    }

    @SuppressWarnings("ResourceType")
    private void setupThemeColors(TypedArray typedArray) {
        int backgroundRes = typedArray.getResourceId(0, mBackgroundColor);
        int textRes = typedArray.getResourceId(1, mItemTextColor);
        int titleRes = typedArray.getResourceId(2, mTitleTextColor);

        if (backgroundRes != mBackgroundColor) {
            mBackgroundColor = ContextCompat.getColor(mContext, backgroundRes);
        }

        if (titleRes != mTitleTextColor) {
            mTitleTextColor = ContextCompat.getColor(mContext, titleRes);
        }

        if (textRes != mItemTextColor) {
            mItemTextColor = ContextCompat.getColor(mContext, textRes);
        }

        typedArray.recycle();
    }

}
