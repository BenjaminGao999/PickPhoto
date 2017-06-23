package com.werb.pickphotoview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Author:　Created by benjamin
 * DATE :  2017/6/15 19:19
 * versionCode:　v2.2
 * 选项框
 * 用于框选选择后的图片
 */

public class MySeletedView extends View {

    private Paint mPaint;
    private float stroke_width_paint = 6;

    public MySeletedView(Context context) {
        this(context, null);
    }

    public MySeletedView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MySeletedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ffe75988"));
        float strokeWidth = getResources().getDisplayMetrics().density * stroke_width_paint;
        mPaint.setStrokeWidth(strokeWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        canvas.drawRect(0, 0, width, height, mPaint);
    }
}
