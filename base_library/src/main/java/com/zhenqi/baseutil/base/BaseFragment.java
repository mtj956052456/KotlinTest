package com.zhenqi.baseutil.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.zhenqi.baseutil.interf.PermissionCallBack;
import com.zhenqi.baseutil.util.SPWXUtil;
import com.zhenqi.baseutil.view.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 孟腾蛟
 * @time 2019/3/23
 * @des 碎片基类
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Bundle savedInstanceState;
    private Unbinder unBinder;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        if (null == rootView) {
            rootView = inflater.inflate(setView(), container, false);
            unBinder = ButterKnife.bind(this, rootView);
            firstBinder();
        } else {
            if (null != rootView.getParent()) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                parent.removeAllViews();
            }
            unBinder = ButterKnife.bind(this, rootView);
            otherBinder();
        }
        fm = getActivity().getSupportFragmentManager();
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
//    protected void setHeadBg() {
//        FrameLayout frameHead = rootView.findViewById(R.id.frame_head);
//        if (frameHead != null)
//            frameHead.setBackgroundResource(R.mipmap.head_bg);
//    }

    public void intoActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public void finishIntoActivity(String path) {
        ARouter.getInstance().build(path).navigation(getActivity(), new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {
                getActivity().finish();
            }

            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onInterrupt(Postcard postcard) {

            }
        });
    }

    private int isShown; //是否显示过

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isShown == 0)
                setUserVisibleHintData();
            isShown++;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void themeColor(EventBusBean busBean) {

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
     * 初始化数据
     *
     * @return
     */
    protected void setUserVisibleHintData() {
        //第一次加载数据
    }

    /**
     * 传入需要绑定的fragment布局
     */
    protected abstract int setView();

    /**
     * 首次用黄油刀绑定
     */
    protected abstract void firstBinder();

    /**
     * 黄油刀绑定过一次后,界面destroy恢复后触发的方法
     */
    protected void otherBinder() {
    }

    protected void setTvText(TextView textView, Object text) {
        if (text != null)
            textView.setText(String.valueOf(text));
    }

    protected void setImageRes(ImageView imageView, int res) {
        if (res != 0)
            imageView.setImageResource(res);
    }



    /**
     * 给控件添加一个状态栏的高度,同时用padding把这段高度给占掉,让布局正产显示
     */
    protected void setHeadView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusId > 0) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int height = layoutParams.height;
                int dimensionPixelSize = getResources().getDimensionPixelSize(statusId);
                height = height + dimensionPixelSize;
                layoutParams.height = height;
                int left = view.getPaddingLeft();
                int top = view.getPaddingTop();
                int right = view.getPaddingRight();
                int bottom = view.getPaddingBottom();
                top = top + dimensionPixelSize;
                view.setPadding(left, top, right, bottom);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    protected void intoActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void onDestroyView() {
        if (unBinder != null) {
            unBinder.unbind();
            unBinder = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    protected boolean isEmpty(String msg) {
        return TextUtils.isEmpty(msg);
    }

    protected void setTv(String msg, TextView tv) {
        if (!TextUtils.isEmpty(msg)) {
            if (null != tv) {
                tv.setText(msg);
            } else {
                Log.e("MTJ", "控件空指针");
            }
        } else {
            Log.e("MTJ", "msg的数据为空");
        }
    }

    protected boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    protected void setVisible(View view, boolean isVisible) {
        if (null != view) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
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
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
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
        mDialog = DialogUtil.CreateLoadingDialog(getContext(), title);
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

    public void dismissDialog() {
        mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG,500);
    }

    public void showSwip() {
        if (swipe != null && !swipe.isRefreshing()) {
            swipe.setRefreshing(true);
        }
    }

    public void dismissSiwp() {
        mHandler.sendEmptyMessage(DISMISS_SWIPE);
    }

    /**
     * 微信是否登录
     *
     * @return
     */
    public boolean wxIsLogin() {
        return !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_OPENID)) && !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_USER_GID));
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


//    @Override
//    public void onAttach(Context context) {Lg.i("onAttach");
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {Lg.i("onCreate");
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Lg.i("onCreateView");
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {Lg.i("onActivityCreated");
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    @Override
//    public void onStart() {Lg.i("onStart");
//        super.onStart();
//    }
//
//    @Override
//    public void onResume() {Lg.i("onResume");
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {Lg.i("onPause");
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {Lg.i("onStop");
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {Lg.i("onDestroy");
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDetach() {Lg.i("onDetach");
//        super.onDetach();
//    }
//    @Override
//    public void onDestroyView() {Lg.i("onDestroyView");
//        super.onDestroyView();
//    }
}
