package com.zhenqi.baseutil.base;

import android.content.Intent;
import android.webkit.WebView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.zhenqi.baseutil.R;
import com.zhenqi.baseutil.util.WebviewUtil;

public class BaseWebActivity extends BaseActivity {

    protected WebView mWebview;
    protected SwipeRefreshLayout mWebSwipe;

    @Override
    protected int setView() {
        return R.layout.activity_base_web;
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
        Intent intent = getIntent();
        if (intent == null)
            return;
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        initCustomToolbar(title);
        mWebview = findViewById(R.id.webview);
        mWebSwipe = findViewById(R.id.web_swipe);
        setSwipeRefreshLayout(mWebSwipe);
        WebviewUtil.instance().initWeb(mWebview, mWebSwipe);
        mWebview.loadUrl(url);
    }

}
