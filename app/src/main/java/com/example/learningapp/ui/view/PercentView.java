package com.example.learningapp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.learningapp.R;

/**
 * Created by taoy05 on 2021/6/29.
 */
public class PercentView extends FrameLayout {
    private static final String TAG = "PercentView";
    private static final int PILLAR_NUM = 30;
    private View mView;
    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvValue;
    private LinearLayout mLlContainer;
    private double mElectricity = 0.58;
    private int mainColor;
    private int shadowColor;

    public PercentView(@NonNull Context context) {
        super(context);
        initView();
    }

    public PercentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(attrs);
    }

    public PercentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PercentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
        initAttrs(attrs);
    }

    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_ble_percent, this);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mTvName = mView.findViewById(R.id.tv_name);
        mTvValue = mView.findViewById(R.id.tv_value);
        mLlContainer = mView.findViewById(R.id.ll_percent_container);
    }

    @SuppressLint("DefaultLocale")
    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PercentView);
        int icon = ta.getResourceId(R.styleable.PercentView_PercentView_icon, R.drawable.ic_home_black_24dp);
        String name = ta.getString(R.styleable.PercentView_PercentView_name);
        String colorStr = ta.getString(R.styleable.PercentView_PercentView_color_str);
        mainColor = Color.parseColor("#ff" + colorStr);
        shadowColor = Color.parseColor("#33" + colorStr);
        int value = ta.getInt(R.styleable.PercentView_PercentView_value, 0);
        mIvIcon.setImageResource(icon);
        mTvName.setText(name);
        mTvValue.setText(String.format("%d%%", value));
        mTvValue.setTextColor(mainColor);
        mElectricity = value / 100.0d;
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mLlContainer.getChildCount() == 0) {
            int measuredWidth = mLlContainer.getMeasuredWidth();
            Log.d(TAG, "onMeasure: measuredWidth = " + measuredWidth);
            int pillarWidth = measuredWidth / (PILLAR_NUM * 2 - 1);
            for (int i = 0; i < PILLAR_NUM; i++) {
                @SuppressLint("DrawAllocation")
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pillarWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.leftMargin = i == 0 ? 0 : pillarWidth;
                @SuppressLint("DrawAllocation")
                View view = new View(getContext());
                mLlContainer.addView(view, layoutParams);
            }

            int childCount = mLlContainer.getChildCount();
            Log.d(TAG, "onMeasure: childCount = " + childCount);
            for (int i = 0; i < childCount; i++) {
                int colorInt = i <= mElectricity * PILLAR_NUM ? mainColor : shadowColor;
                mLlContainer.getChildAt(i).setBackgroundColor(colorInt);
            }
        }
    }
}
