package com.zhenqi.baseutil.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.zhenqi.baseutil.R;


public class CustomToolbar extends Toolbar {

    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TextView mTvMainTitleLeft;
    private TextView mTvMainTitle;
    private TextView mTvMainTitleRight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvMainTitleLeft = (TextView) findViewById(R.id.lt_main_title_left);
        mTvMainTitle = (TextView) findViewById(R.id.lt_main_title);
        mTvMainTitleRight = (TextView) findViewById(R.id.lt_main_title_right);
    }

    //设置主title的内容
    public void setMainTitle(String text) {
        this.setTitle("");
        mTvMainTitle.setVisibility(View.VISIBLE);
        mTvMainTitle.setText(text);
        //setBackgroundColor(getResources().getColor(R.color.transparent));
        //setBackgroundColor(getResources().getColor(BaseConstant.toolBarColor));
    }

    //设置主title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTvMainTitle.setTextColor(color);
    }

    //设置title左边文字
    public void setMainTitleLeftText(String text) {
        mTvMainTitleLeft.setVisibility(View.VISIBLE);
        mTvMainTitleLeft.setText(text);
    }

    //设置title左边文字颜色
    public void setMainTitleLeftColor(int color) {
        mTvMainTitleLeft.setTextColor(color);
    }

    //设置title左边图标
    public void setMainTitleLeftDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTvMainTitleLeft.setCompoundDrawables(dwLeft, null, null, null);
    }

    //设置title右边文字
    public void setMainTitleRightText(String text) {
        mTvMainTitleRight.setVisibility(View.VISIBLE);
        mTvMainTitleRight.setText(text);
    }

    //设置title右边文字颜色
    public void setMainTitleRightColor(int color) {
        mTvMainTitleRight.setTextColor(color);
    }

    //设置title右边图标
    public void setMainTitleRightDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTvMainTitleRight.setCompoundDrawables(null, null, dwRight, null);
    }

    //设置toolbar状态栏颜色
    public void setToolbarBackground(int res) {
        this.setBackgroundResource(res);
    }

    //设置toolbar左边图标
    public void setToolbarLeftBackImageRes(int res) {
        this.setNavigationIcon(res);
    }

    //设置toolbar左边文字
    public void setToolbarLeftText(String text) {
        this.setNavigationContentDescription(text);
    }

    //设置toolbar的标题
    public void setToolbarTitle(String text) {
        this.setTitle(text);
    }

    //设置toolbar标题的颜色
    public void setToolbarTitleColor(int color) {
        this.setTitleTextColor(color);
    }

    //设置toolbar子标题
    public void setToolbarSubTitleText(String text) {
        this.setSubtitle(text);
    }

    //设置toolbar子标题颜色
    public void setToolbarSubTitleTextColor(int color) {
        this.setSubtitleTextColor(color);
    }

    //获取左边控件
    public void setLeftViewOnClickListener(OnClickListener listener){
        mTvMainTitleLeft.setOnClickListener(listener);
    }
    //获取右边控件
    public void setRightViewOnClickListener(OnClickListener listener){
        mTvMainTitleRight.setOnClickListener(listener);
    }

    public TextView getTvMainTitleRight() {
        return mTvMainTitleRight;
    }
}
