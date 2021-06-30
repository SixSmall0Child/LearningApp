package com.example.learningapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.learningapp.R;

// TODO: 2021/6/29 手动draw圆角背景
public class BleItemView extends FrameLayout {
    private View mView;
    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvValue;

    public BleItemView(@NonNull Context context) {
        super(context);
        init();
    }

    public BleItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public BleItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BleItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void init() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_ble_item, this);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mTvName = mView.findViewById(R.id.tv_name);
        mTvValue = mView.findViewById(R.id.tv_value);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BleItemView);
        int icon = ta.getResourceId(R.styleable.BleItemView_BleItemView_icon, R.drawable.ic_home_black_24dp);
        String name = ta.getString(R.styleable.BleItemView_BleItemView_name);
        String value = ta.getString(R.styleable.BleItemView_BleItemView_value);
        mIvIcon.setImageResource(icon);
        mTvName.setText(name);
        mTvValue.setText(value);
        ta.recycle();
    }

}
