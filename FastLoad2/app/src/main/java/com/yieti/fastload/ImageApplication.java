package com.yieti.fastload;

import android.app.Application;
import android.content.Context;

/**
 * Created by hoooou on 17/4/1.
 */

public class ImageApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtils.init(base);
    }
}
