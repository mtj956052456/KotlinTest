package com.zhenqi.baseutil.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public abstract class BaseContainer extends BaseActivity {
    /**
     * fragment 容器类
     */
    protected FragmentManager fm;                   //fm管理器
    protected int mContainerId;                     //容器id,必须的
    protected Fragment mCurrentFragment;            //获取当前Fragment,非必要情况无需
    protected TextView mCurrentTv;                  //获取当前TextView,非必要情况无需
    protected ImageView mCurrentImg;                //获取当前imgView,非必要情况无序
    protected List<View> mLists;                    //预留的可能有多个view的情况

    protected abstract int setContainerId();

    @Override
    protected void afterBinder() {
        super.afterBinder();
        mContainerId = setContainerId();
        fm = getSupportFragmentManager();
    }

    protected void setFm(Fragment fragment, String tag) {
        fm.beginTransaction().add(mContainerId, fragment, tag).commit();
    }

    protected void showFm(String fragmentKey) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (!TextUtils.equals(fragmentKey, mCurrentFragment.getClass().getSimpleName())) {         //如果相同,说明是当前的fragment,无需操作
            if (null == fm.findFragmentByTag(fragmentKey)) {
                addFragment(fragmentKey, transaction);
            } else {
                hasFragment(transaction, fragmentKey);
            }
        }
    }

    /**
     * 在展示新的fragment的时候比如主界面的时候使用
     */
    protected void addFragment(String fragmentKey, FragmentTransaction transaction) {

    }

    protected void hasFragment(FragmentTransaction transaction, int fragmentId) {
        Fragment fragmentById = fm.findFragmentById(fragmentId);                                   //从栈中通过tag寻找fragment
        transaction.hide(mCurrentFragment).show(fragmentById).commit();                            //隐藏显示操作
        mCurrentFragment = fragmentById;                                                           //初始化当前fragment
    }

    protected void hasFragment(FragmentTransaction transaction, String fragmentKey) {
        Fragment fragmentByTag = fm.findFragmentByTag(fragmentKey);                                 //从栈中通过tag寻找fragment
        transaction.hide(mCurrentFragment).show(fragmentByTag).commit();                            //隐藏显示操作
        mCurrentFragment = fragmentByTag;                                                           //初始化当前fragment
    }

//    /**
//     * 触发按钮的返回点击时,检查回退栈是否为空,空的话直接finish,不为空的话弹出当前fragment替换为回退栈里面的顶部fragment
//     *
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (fm.getBackStackEntryCount() > 0) {
//                fm.popBackStack();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}
