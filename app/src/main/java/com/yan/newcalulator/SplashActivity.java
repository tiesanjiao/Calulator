package com.yan.newcalulator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;

import com.yan.newcalulator.util.JsonHelper;
import com.yan.newcalulator.util.RequestUtilHelper;


public class SplashActivity extends AppCompatActivity {

    private final static int HandleCallback = 1;

    private Handler handler = new Handler () {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String reponseString = msg.getData().getString("data");
                if (TextUtils.isEmpty(reponseString)) {
                    MyCalculator.start(SplashActivity.this);
                } else {
                    ResponseRouterModel model = JsonHelper.fromJson(reponseString, ResponseRouterModel.class);
                    if (model == null || model.getShowWeb() == 0) {
                        MyCalculator.start(SplashActivity.this);
                    } else {
                        if (model.getShowWeb() == 1) {
                            String downloadUrl = model.getUrl();
                            if (TextUtils.isEmpty(downloadUrl)) {
                                MyCalculator.start(SplashActivity.this);
                            } else if (downloadUrl.contains(".apk")) {
                                DownLoadActivity.start(SplashActivity.this, downloadUrl);
                            } else {
                                WebViewActivity.start(SplashActivity.this, downloadUrl);
                            }
                        }
                    }
                }
                finish();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        final String requestUrl = Config.BASE_ROUTE + BuildConfig.APPID;
        new Thread() {
            @Override
            public void run() {
                super.run();
                String reponseString = RequestUtilHelper.get().getGetResponse(requestUrl);
                Message message = new Message();
                message.what = HandleCallback;
                Bundle bundle = new Bundle();
                bundle.putString("data", reponseString);
                message.setData(bundle);
                handler.sendMessageAtTime(message, 0);
            }
        }.start();
    }
}
