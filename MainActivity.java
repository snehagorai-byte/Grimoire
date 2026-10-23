package com.midnight.system;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.*;

public class MainActivity extends Activity {
    WebView w;
    ValueCallback<Uri[]> cb;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        getWindow().setStatusBarColor(0xFF070D1F);
        getWindow().setNavigationBarColor(0xFF070D1F);
        w = new WebView(this);
        w.setBackgroundColor(0xFF070D1F);
        setContentView(w);
        WebSettings t = w.getSettings();
        t.setJavaScriptEnabled(true);
        t.setDomStorageEnabled(true);
        t.setAllowFileAccess(true);
        t.setMediaPlaybackRequiresUserGesture(false);
        w.addJavascriptInterface(new Bridge(this), "Android");
        w.setWebViewClient(new WebViewClient());
        w.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> c, FileChooserParams p) {
                if (cb != null) cb.onReceiveValue(null);
                cb = c;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(i, 1);
                return true;
            }
        });
        w.loadUrl("file:///android_asset/www/index.html");
    }

    @Override protected void onActivityResult(int req, int res, Intent d) {
        super.onActivityResult(req, res, d);
        if (req == 1 && cb != null) {
            cb.onReceiveValue(res == RESULT_OK && d != null && d.getData() != null ? new Uri[]{d.getData()} : null);
            cb = null;
        }
    }

    @Override public void onBackPressed() {
        if (w.canGoBack()) w.goBack(); else super.onBackPressed();
    }

    @Override protected void onPause() {
        super.onPause();
        w.onPause();
    }

    @Override protected void onResume() {
        super.onResume();
        w.onResume();
    }
}
