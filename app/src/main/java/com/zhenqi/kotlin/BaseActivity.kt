package com.zhenqi.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *  创建者: lenovo
 *  时间: 2019/10/18
 *  描述:
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var savedInstanceState: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setView())
        doSomething()
    }

    abstract fun setView(): Int

    open fun doSomething() {

    }


}