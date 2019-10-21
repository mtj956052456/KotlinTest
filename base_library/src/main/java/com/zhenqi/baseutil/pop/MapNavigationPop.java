package com.zhenqi.baseutil.pop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.zhenqi.baseutil.R;

import java.io.File;

import razerdp.basepopup.BasePopupWindow;

/**
 * 孟腾蛟
 * 20180207
 * des:地图导航弹窗
 */

public class MapNavigationPop extends BasePopupWindow implements View.OnClickListener {

    private boolean isBaiduMap, isGaodeMap;
    private Context mContext;

    public MapNavigationPop(Context mContext) {
        super(mContext);
        setPopupGravity(Gravity.BOTTOM);
        this.mContext = mContext;
        TextView tvBaidu = findViewById(R.id.baidu);
        TextView tvGaode = findViewById(R.id.gaode);
        TextView tv_cancel = findViewById(R.id.cancel);
        tvBaidu.setOnClickListener(this);
        tvGaode.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        isBaiduMap = isInstallPackage("com.baidu.BaiduMap");
        isGaodeMap = isInstallPackage("com.autonavi.minimap");
        if (!isBaiduMap) {
            tvBaidu.setText("百度地图(未安装)");
        }
        if (!isGaodeMap) {
            tvGaode.setText("高德地图(未安装)");
        }
    }

    private double mLongitude;
    private double mLatitude;
    private String mAddress;

    public void setParams(double latitude, double longitude, String address) {
        mLatitude = latitude;
        mLongitude = longitude;
        mAddress = address;
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
        return createPopupById(R.layout.bottom_pop_layout);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.baidu) {
            if (isBaiduMap) {
                openBaiduMap(mContext, mLongitude, mLatitude, mAddress);
            } else {
                Toast.makeText(mContext, "请安装百度地图应用", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.gaode) {
            if (isGaodeMap) {
                openGaoDeMap(mContext, mLongitude, mLatitude, mAddress);
            } else {
                Toast.makeText(mContext, "请安装高德地图应用", Toast.LENGTH_SHORT).show();
            }
        }
        dismiss();
    }


    /**
     * 检查有没有安装应用
     */
    private boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 跳转高德地图
     */
    private void openGaoDeMap(Context context, double lon, double lat, String address) {
        try {
            //double[] gd_lat_lon = bdToGaoDe(lon, lat);
            StringBuilder loc = new StringBuilder();
            loc.append("androidamap://viewMap?sourceApplication=XX");
            loc.append("&poiname=");
            loc.append(address);
            loc.append("&lat=");
            loc.append(lat);
            loc.append("&lon=");
            loc.append(lon);
            loc.append("&dev=0");
            Log.e("MTJ", "高德: " + loc.toString());
            Intent intent = Intent.getIntent(loc.toString());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转百度地图
     */
    private void openBaiduMap(Context context, double lon, double lat, String address) {
        try {
            double[] gd_lon_lat = gaoDeToBaidu(lon, lat);
            StringBuilder loc = new StringBuilder();
            loc.append("intent://map/direction?origin=latlng:");
            loc.append(gd_lon_lat[1]);
            loc.append(",");
            loc.append(gd_lon_lat[0]);
            loc.append("|name:");
            loc.append("我的位置");
            loc.append("&destination=latlng:");
            loc.append(gd_lon_lat[1]);
            loc.append(",");
            loc.append(gd_lon_lat[0]);
            loc.append("|name:");
            loc.append(address);
            loc.append("&mode=driving");
            loc.append("&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            Log.e("MTJ", "百度: " + loc.toString());
            Intent intent = Intent.getIntent(loc.toString());
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 高德转百度
     **/
    private static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lon_lat = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lon_lat[0] = z * Math.cos(theta) + 0.0065;
        bd_lon_lat[1] = z * Math.sin(theta) + 0.006;
        return bd_lon_lat;
    }

}
