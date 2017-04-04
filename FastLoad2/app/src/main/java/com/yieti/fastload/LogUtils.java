package com.yieti.fastload;

import android.content.Context;
import android.util.Log;

/**
 * Created by hoooou on 17/4/1.
 */

public class LogUtils {
    static String tag;
    static int LOG_LEVEL= Log.ERROR;
    public static void init(Context context){
        tag=context.getPackageName();
    }
    public static void write(String msg) {
        Log.println(LOG_LEVEL, tag, msg+"");
    }
}
