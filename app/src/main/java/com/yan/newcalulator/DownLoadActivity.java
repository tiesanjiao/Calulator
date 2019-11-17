package com.yan.newcalulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yan.newcalulator.util.RequestUtilHelper;

import java.util.Random;

/**
 * author：yuxinfeng on 2019-11-15 11:03
 * email：yuxinfeng@corp.netease.com
 */
public class DownLoadActivity extends AppCompatActivity {

    private View backgroundImage;
    private ProgressBar mProgressBar;
    private String url;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DownLoadActivity.class);
        intent.putExtra("downloadurl", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("downloadurl");
        setContentView(R.layout.layout_download);
        backgroundImage = findViewById(R.id.rl_back_ground_back);
        mProgressBar = findViewById(R.id.game_pro);
        RequestUtilHelper.get().download(url, "apk", new RequestUtilHelper.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {

                installApk();
            }

            @Override
            public void onDownloading(int progress) {
                mProgressBar.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                Toast.makeText(DownLoadActivity.this, "下载失败", Toast.LENGTH_LONG).show();
            }
        });
        backgroundImage.setBackgroundResource(getRandBackground());

    }

    private void installApk() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RequestUtilHelper.get().cancel();
        // finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtilHelper.get().cancel();
        // finish();
    }

    private int getRandBackground() {
        Random r = new Random();
        String resname = "background_" + (r.nextInt(3) + 1);
        Context ctx =getBaseContext();
        int resId = getResources().getIdentifier(resname, "drawable", ctx.getPackageName());
        return resId;
    }
}
