package com.zhenqi.baseutil.base;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class ActivityHolder {

    public final static String MAIN = "/main/MainActivity";
    public final static String LOGIN = "/login/LoginActivity";
    public final static String WELCOME = "/welcome/WelcomeActivity";

    public final static String VIDEO_QUESTION = "/video_question/VideoQuestionActivity";            //语音问答
    public final static String AIR_CALENDER = "/air_calender/CalendarActivity";                     //空气日历
    public final static String REALTIME_DISPATCH = "/realtime_dispatch/RealtimeDispatchActivity";   //实时调度
    public final static String VIDEO_DISPATCH = "/video_dispatch/VideoDispatchActivity";            //视频调度
    public final static String HISTORY_CURVE = "/history_curve/HistoryCurveActivity";               //历史曲线
    public final static String TARGET_ASSESSMENT = "/target_assessment/TargetAssessmentActivity";   //目标考核
    public final static String EXPERT_NOTICE = "/expert_notice/ExpertNoticeActivity";               //专家公告
    public final static String SCENE_SUPERVISE = "/scene_supervise/SceneSuperviseActivity";         //现场督察
    public final static String POLLUTION_TRENDS = "/pollution_trends/PollutionTrendsActivity";      //污染形势
    //表格
    public final static String TABLE_CITY = "/table_city/CitySortActivity";                         //城市排名
    public final static String TABLE_POINT = "/table_point/PointSortActivity";                      //站点排名
    public final static String TABLE_SENSOR_COMPANY = "/table_sensor_company/SensorCompanyActivity";//传感器|企业排名
    public static final String TABLE_REALTIME ="/table_realTimeData/RealTimeDataActivity" ;
    public static final String TABLE_CUMULATIVE = "/table_today_air_quality/TodayAirQualityActivity";


    private static ArrayList<Activity> activityArray = new ArrayList<Activity>();

    public static void addActivity(Context context) {
        activityArray.add((Activity) context);
    }

    public static void removeActivity(Context context) {
        activityArray.remove((Activity) context);
    }

    public static void removeAllActivity() {
        for (Activity activity : activityArray) {
            activity.finish();
        }
    }
}
