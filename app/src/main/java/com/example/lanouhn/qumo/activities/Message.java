package com.example.lanouhn.qumo.activities;

import android.app.Activity;
//import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.adapters.WeimeiAdapter;


/**
 * Created by lanouhn on 2016/8/17.
 */
public class Message extends Activity {
    protected static final String TAG = null;
    private WebView wv = null;
    private ProgressBar pb;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        pb= (ProgressBar) findViewById(R.id.pb);
        image= (ImageView) findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Message.this,MainActivity.class);
                startActivity(intent);
            }
        });

        wv = (WebView) findViewById(R.id.wv);
        WebSettings settings = wv.getSettings();
        settings.setBuiltInZoomControls(true);//设置是否显示缩放工具
        settings.setSupportZoom(true);//设置是否支持缩放
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(10);
        //让webview适应屏幕大小
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);

        //加载本地assets目录下的html
//        wv.loadUrl("file:///android_asset/test1.html");
        //加载远程网页
        wv.loadUrl("http://www.kuwo.cn/playlist/index?pid=1082656711");
        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //让新打开的网页在当前webview显示
                view.loadUrl(url);
                return true;
            }
        });


        wv.setWebChromeClient(new WebChromeClient()
        {
            //获得网页的加载进度(0-100)
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                if(newProgress <= 100)
                {
                    Log.i(TAG,"progress"+newProgress);
                }
            }
            //获得网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                Log.i(TAG,"titile="+title);
            }
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon)
            {
                super.onReceivedIcon(view, icon);
            }
        });
    }


}