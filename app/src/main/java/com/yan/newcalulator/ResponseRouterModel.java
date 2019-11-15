package com.yan.newcalulator;

import android.support.annotation.Keep;

/**
 * author：yuxinfeng on 2019-11-15 14:23
 * email：yuxinfeng@corp.netease.com
 */
@Keep
public class ResponseRouterModel {

    private Boolean success;

    private int ShowWeb;

    private String PushKey;

    private String Url;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getShowWeb() {
        return ShowWeb;
    }

    public void setShowWeb(int showWeb) {
        ShowWeb = showWeb;
    }

    public String getPushKey() {
        return PushKey;
    }

    public void setPushKey(String pushKey) {
        PushKey = pushKey;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
