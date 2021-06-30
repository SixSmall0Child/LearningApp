package com.example.learningapp.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";
    private int mHorizontalSpacing = dp2px(16);//每个item的横向间距
    private int mVerticalSpacing = dp2px(16);//每个item的纵向间距
    //记录所有的行，一行一行的存储，用于layout
    private List<List<View>> allLines = new ArrayList<>();
    //记录每一行的行高，用于layout
    private List<Integer> lineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initMeasureParams() {
        allLines = new ArrayList<>();
        lineHeights = new ArrayList<>();
    }

    /**
     * @param widthMeasureSpec  flowlayout的父布局measure子布局的时候传过来参数
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initMeasureParams();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        Log.d(TAG, "padding=" + paddingLeft + " " + paddingRight + " " + paddingTop + " " + paddingBottom);

        List<View> lineViews = new ArrayList<>();//保存一行中所有的view
        int lineWidthUsed = 0;//记录这行已经使用了多宽的size
        int lineHeight = 0;//一行的行高

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);//viewGroup解析父布局给的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "selfWidth=" + selfWidth + " selfHeight=" + selfHeight);

        int parentNeededWidth = 0;//measure过程中，子view要求的父ViewGroup的宽
        int parentNeededHeight = 0;

        //先度量孩子
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();//拿到xml里面layout_width、layout_height转成layoutParams
            Log.d(TAG, "childLPW=" + childLP.width + " childLPH=" + childLP.height);
            //将layoutParams转变为measureSpec
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingBottom + paddingTop, childLP.height);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            //获取子view的度量宽高
            int childMeasuredWidth = childView.getMeasuredWidth();
            int childMeasuredHeight = childView.getMeasuredHeight();
            Log.d(TAG, "childMeasuredWidth=" + childMeasuredWidth + " childMeasuredHeight=" + childMeasuredHeight);

            if (childMeasuredWidth + lineWidthUsed > selfWidth) {

                allLines.add(lineViews);
                lineHeights.add(lineHeight);

                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }

            //view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
            lineViews.add(childView);
            //每一行都有自己的宽高
            lineWidthUsed = lineWidthUsed + childMeasuredWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasuredHeight);
        }

        //度量自己
        //flowlayout作为viewGroup，他自己也是一个view，他的大小也需要根据他的父亲给他提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.d(TAG, "widthMode=" + widthMode + " heightMode=" + heightMode);

        Log.d(TAG, "parentNeededWidth=" + parentNeededWidth + " parentNeededHeight=" + parentNeededHeight);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;

        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curL = getPaddingLeft();
        int curT = getPaddingTop();
        for (int i = 0; i < allLines.size(); i++) {

            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {

                View view = lineViews.get(j);
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }
    }

    public static int dp2px(int dp) {
        int dp16 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
        Log.d(TAG, "dp=" + dp16);
        return dp16;
    }

}
