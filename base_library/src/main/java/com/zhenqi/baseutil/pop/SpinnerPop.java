package com.zhenqi.baseutil.pop;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zhenqi.baseutil.R;
import razerdp.basepopup.BasePopupWindow;

import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述: 下拉框适配器
 */
public class SpinnerPop extends BasePopupWindow {

    private ListStringAdapter mAdapter;

    public SpinnerPop(Context context, List<String> list) {
        super(context);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerSpinner);
        mAdapter = new ListStringAdapter(R.layout.item_text, list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setBackgroundColor(context.getResources().getColor(R.color.transparent));//透明
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.spinner_layout);
    }

    public ListStringAdapter getAdapter() {
        return mAdapter;
    }
}
