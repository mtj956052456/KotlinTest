package com.zhenqi.kotlin

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONArray
import com.blankj.utilcode.util.ToastUtils
import com.zhenqi.baseutil.http.API
import com.zhenqi.baseutil.http.OkHttpCallBack
import com.zhenqi.baseutil.http.OkHttpManager
import com.zhenqi.kotlin.map.MapAdapter
import com.zhenqi.kotlin.map.MapBean
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun setView(): Int {
        return R.layout.activity_main
    }

    override fun doSomething() {
        initView()
        initData()
    }

    private val mList: MutableList<MapBean>? = arrayListOf()
    private var mMapAdapter: MapAdapter? = null

    private fun initView() {
        mMapAdapter = MapAdapter(R.layout.item_map_layout, mList)
        mRecyclerView.adapter = mMapAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.isNestedScrollingEnabled = false
    }

    private fun initData() {
        val map = HashMap<String, String>()
        map["city"] = "郑州"
        map["type"] = "NATION"
        OkHttpManager.getInstance()
            .Palm3Post("DATAAPI_OPERATIONALMAP", map, API.Palm3_Dataapi, object : OkHttpCallBack.okCallBack<String>() {
                override fun onSuccess(result: String) {
                    ToastUtils.showShort("result: $result")
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("nation")
                    val array: MutableList<MapBean> =
                        JSONArray.parseArray(jsonArray.toString(), MapBean::class.java).toMutableList()
                    mList?.clear()
                    mList?.addAll(array)
                    mMapAdapter?.notifyDataSetChanged()
                }

                override fun onError(t: Throwable) {
                    super.onError(t)
                    ToastUtils.showShort(t.message)
                }
            })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionButton ->{
                initData()
                ToastUtils.showShort("刷新了")
            }
        }
    }


}
