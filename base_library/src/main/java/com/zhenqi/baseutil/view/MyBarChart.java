package com.zhenqi.baseutil.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;

public class MyBarChart extends BarChart {
    PointF downPoint = new PointF();

    public MyBarChart(Context context) {
        super(context);
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                if (scrollViewPager != null) {
                    scrollViewPager.setScroll(false);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                downPoint.x = event.getX();
                downPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getY() - downPoint.x) > 20) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (scrollViewPager != null) {
                    scrollViewPager.setScroll(true);
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    private NoScrollViewPager scrollViewPager;

    public void setNoScrollViewPager(NoScrollViewPager scrollViewPager) {
        this.scrollViewPager = scrollViewPager;
    }
}
