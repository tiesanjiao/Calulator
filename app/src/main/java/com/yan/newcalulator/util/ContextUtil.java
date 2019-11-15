package com.yan.newcalulator.util;

import android.content.Context;

/**
 * author：yuxinfeng on 2019-11-15 15:32
 * email：yuxinfeng@corp.netease.com
 */
public class ContextUtil {

    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ContextUtil.context = context;
    }
}
