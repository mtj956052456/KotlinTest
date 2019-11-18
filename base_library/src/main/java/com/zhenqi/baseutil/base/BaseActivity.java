package com.zhenqi.baseutil.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.zhenqi.baseutil.R;
import com.zhenqi.baseutil.http.API;
import com.zhenqi.baseutil.http.OkHttpCallBack;
import com.zhenqi.baseutil.http.OkHttpManager;
import com.zhenqi.baseutil.interf.PermissionCallBack;
import com.zhenqi.baseutil.swipeback.app.SwipeBackActivity;
import com.zhenqi.baseutil.util.Base64;
import com.zhenqi.baseutil.util.Lg;
import com.zhenqi.baseutil.util.SPUtil;
import com.zhenqi.baseutil.util.SystemUtil;
import com.zhenqi.baseutil.view.CustomToolbar;
import com.zhenqi.baseutil.view.DialogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends SwipeBackActivity implements Thread.UncaughtExceptionHandler {
    private Unbinder mUnbinders; //黄油刀绑定
    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //getWindow().addFlags(0x00000000);   //透明状态栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 透明导航栏
        }
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);//注册路由
        Thread.setDefaultUncaughtExceptionHandler(this);
        //        Eyes.setStatusBarLightMode(this, getResources().getColor(BaseConstant.toolBarColor));
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityHolder.addActivity(this);
        beforeSetView();
        setContentView(setView());
        mUnbinders = ButterKnife.bind(this);                               //黄油刀绑定
        afterBinder();                                                            //界面控件初始化

    }

    public void intoActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public void intoActivitywithBundle(String path,Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public void finishIntoActivity(String path) {
        ARouter.getInstance().build(path).navigation(this, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {
                finish();
            }

            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onInterrupt(Postcard postcard) {

            }
        });
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        overridePendingTransition(0, 0);
//    }
//
//    @Override
//    protected void onRestart() {
//        overridePendingTransition(0, 0);
//        super.onRestart();
//    }

    public void finishAnimationIntoActivity(String path) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_bottom_in, R.anim.fade_bottom_out)
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        finish();
                    }

                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    /**
     * 初始化ToolBar
     *
     * @param title
     */
    public void initCustomToolbar(String title) {
        CustomToolbar mCustomToolbar = findViewById(R.id.customtoolbar);
        mCustomToolbar.setMainTitleLeftDrawable(R.mipmap.pg_return);
        mCustomToolbar.findViewById(R.id.lt_main_title_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCustomToolbar.setMainTitle("" + title);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setHeadBg();
    }

    protected void setHeadBg() {
        FrameLayout frameHead = findViewById(R.id.frame_head);
        if (frameHead != null)
            frameHead.setBackgroundResource(R.mipmap.head_bg);
    }

    protected void beforeSetView() {
    }                                //设置界面之前做的事

    protected abstract int setView();                                //设置界面

    protected void afterBinder() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            Window window = getWindow();
        //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //            window.setStatusBarColor(getResources().getColor(BaseConstant.toolBarColor));
        //        }

        //        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        //        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //        //设置状态栏透明
        //        StatusBarUtil.setTranslucentStatus(this);
        //        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        ////        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
        ////            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
        ////            //这样半透明+白=灰, 状态栏的文字能看得清
        ////            StatusBarUtil.setStatusBarColor(this,0x55000000);
        ////        }
        //        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
    }

    protected boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    protected boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 检查微信是否登录
     *
     * @return
     */
    public boolean checkWechatLogin() {
//        String usergid = SPWXUtil.get(SPWXUtil.WX_USER_GID);
//        if (usergid == null || "".equals(usergid) || "null".equals(usergid)) // 如果没有token说明没有微信登录过
//            return false;
//        else
        return true;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //获取堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        //完整的错误信息
        String sbError = e.getMessage() + sw.toString();
        SPUtil.save(SPUtil.ERRORINFO, replaceAllNT(sbError));
        try {
            Thread.sleep(1000); //延迟一秒 存储数据
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        ActivityHolder.removeAllActivity();
        System.exit(0);
    }

    private String replaceAllNT(String str) {
        str = str.replaceAll("\n", Base64.encode("\n"));
        str = str.replaceAll("\t", Base64.encode("\t"));
        return str;
    }

    /**
     * 上传设备信息和错误信息
     */
    public void pushException() {
        String strError = SPUtil.get(SPUtil.ERRORINFO);
        if (TextUtils.isEmpty(strError) || "true".equals(strError)) {
            Lg.e("已经传过了: ");
            return; //清理缓存 || 传过异常信息  就不用再传了
        }
        Lg.e("开始上传: ");
        StringBuilder sbDevice = new StringBuilder();
        sbDevice.append("APP名称:" + AppUtils.getAppName()).append("\n")
                .append("APP版本号:" + AppUtils.getAppVersionName()).append("\n")
                .append("APP版本码:" + AppUtils.getAppVersionCode()).append("\n")
                .append(SystemUtil.getDeviceBrand()).append("\n")
                .append(SystemUtil.getSystemModel()).append("\n")
                .append(SystemUtil.getSystemVersion());
        String strDevice = replaceAllNT(sbDevice.toString());
        Lg.e("strError : " + strError + "\n strDevice : " + strDevice);

        Map<String, String> map = new HashMap<>();
        map.put("username", SPUtil.get(SPUtil.USERNAME));
        map.put("content", strError);
        map.put("deviceinfo", strDevice);
        map.put("clienttype", "Android");
        OkHttpManager.getInstance().PushErrorInfo("ERRORLOGAPI_RECORD", map, API.Palm2, new OkHttpCallBack.okCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                Lg.e("上传成功onSuccess: " + result);
                SPUtil.save(SPUtil.ERRORINFO, String.valueOf(result));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Lg.e("onError: " + e.getMessage());
            }
        });
    }

    public void checkPermission(final PermissionCallBack callBack, String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .onGranted(new com.yanzhenjie.permission.Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        callBack.success();
                    }
                }).onDenied(new com.yanzhenjie.permission.Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                ToastUtils.showShort("禁用权限部分功能将无法使用");
            }
        }).start();
    }

    public void checkPermission(final PermissionCallBack callBack, String[]... groups) {
        AndPermission.with(this)
                .runtime()
                .permission(groups)
                .onGranted(new com.yanzhenjie.permission.Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        callBack.success();
                    }
                }).onDenied(new com.yanzhenjie.permission.Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                ToastUtils.showShort("禁用权限部分功能将无法使用");
            }
        }).start();
    }


    /**
     * 设置下拉刷新颜色
     *
     * @param refreshLayout
     */
    public void setSwipeRefreshLayout(SwipeRefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeColors(getResources().getColor(BaseConstant.toolBarColor));
        }
    }

    public Dialog mDialog;
    public SwipeRefreshLayout swipe;
    private int DISMISS_DIALOG = 1;
    private int DISMISS_SWIPE = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DISMISS_DIALOG) {
                dismissDialog();
            } else if (msg.what == DISMISS_SWIPE) {
                if (swipe != null && swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
            }
        }
    };

    /**
     * 初始化Dialog
     *
     * @param title
     */
    public void initDialog(String title) {
        mDialog = DialogUtil.CreateLoadingDialog(this, title);
    }

    /**
     * 初始化SWipe
     *
     * @param refreshLayout
     */
    public void initSwipe(SwipeRefreshLayout refreshLayout) {
        swipe = refreshLayout;
        setSwipeRefreshLayout(swipe);
    }

    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialogDelayed() {
        mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG, 500);
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void showSwip() {
        if (swipe != null && !swipe.isRefreshing()) {
            swipe.setRefreshing(true);
        }
    }

    public void dismissSiwp() {
        mHandler.sendEmptyMessageDelayed(DISMISS_SWIPE, 500);
    }


    /**
     * 设置最大系统字体为标准字体
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1.0f;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

}
