package com.zhenqi.baseutil.pop;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhenqi.baseutil.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;


/**
 * @author 孟腾蛟
 * @time 2019/03/21
 * @des 预警设置弹窗
 */
public class SlideBottomListPop extends BasePopupWindow {

    public SlideBottomListPop(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        setHeight(ConvertUtils.dp2px(250));
        initView();
    }

    private View view;
    private SlideBottomListAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    private void initView() {
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_slide_bottom_list);
        mAdapter = new SlideBottomListAdapter(R.layout.pop_slide_bottom_list_item, mList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mOnItemClickListener.callBack(mList.get(position));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 300);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 300);
    }

    @Override
    public View onCreateContentView() {
        view = createPopupById(R.layout.pop_slide_bottom_list);
        return view;
    }

    public interface OnItemClickListener {
        void callBack(String value);
    }

    public void setList(List<String> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    public void setList(String... value) {
        mList.clear();
        mList.addAll(Arrays.asList(value));
        mAdapter.notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class SlideBottomListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SlideBottomListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            if (mList.size() - 1 == helper.getAdapterPosition()) {
                helper.setVisible(R.id.view_line, false);
            } else {
                helper.setVisible(R.id.view_line, true);
            }
            helper.setText(R.id.tv_name_item, item);
            helper.addOnClickListener(R.id.tv_name_item);
        }
    }
}
