package com.zhenqi.kotlin.map

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhenqi.kotlin.R

/**
 *  创建者: 孟腾蛟
 *  时间: 2019/10/21
 *  描述:
 */
class MapAdapter(layoutResId: Int, data: MutableList<MapBean>?) :
    BaseQuickAdapter<MapBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: MapBean?) {
        helper?.setText(R.id.mTvTitle, item?.cityname)
        helper?.setText(R.id.mTvContent, item?.pointname)

    }
}