package com.zhenqi.kotlin

import android.view.View

/**
 *  创建者: 孟腾蛟
 *  时间: 2019/10/18
 *  描述:
 */
class Sample constructor(var name: String) {

    lateinit var v: View

    fun setViewColor(color: Int) {
        v.setBackgroundColor(color)
    }

    fun getNames(): String {
        return name
    }
}