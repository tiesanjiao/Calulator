package com.yan.newcalulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("downloadurl", url);
        context.startActivity(intent);
    }

    private String url;
    private TextView titleText;
    private ImageView imageView;
    private WebView mWebView;
    private WebChromeClient webChromeClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        url = getIntent().getStringExtra("downloadurl");
        titleText = findViewById(R.id.back_nav_title);
        mWebView = findViewById(R.id.web_view);
        imageView = findViewById(R.id.back_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView != null) {
                    mWebView.destroy();
                }
                finish();
            }
        });
        initWebView();
        mWebView.loadUrl(url);
    }

    private void initWebView() {
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        // 支持flash--3.0以上需要开启硬件加速
        try {
            settings.setPluginState(WebSettings.PluginState.ON);
        } catch (Exception e) {
        }

        // 将网页显示在屏幕区域内
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // 设置离线缓存
        // 开启应用程序缓存
        settings.setAppCacheEnabled(true);
        String dir = getApplicationContext().getDir("cache",
                Context.MODE_PRIVATE).getPath();
        // 设置应用缓存的路径
        settings.setAppCachePath(dir);
        // 设置缓存的模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置应用缓存的最大尺寸
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        // HTML5存储
        // 启用数据库
        settings.setDatabaseEnabled(true);
        String dataDir = getApplicationContext().getDir("database",
                Context.MODE_PRIVATE).getPath();
        // 设置数据库路径
        settings.setDatabasePath(dataDir);
        // 使用localStorage则必须打开
        settings.setDomStorageEnabled(true);

        //地理位置
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(dataDir);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    titleText.setText(title);
                }
            }
        });
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeExpiredCookie();
        cookieManager.removeSessionCookie();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
