package com.midnight.system;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import java.io.OutputStream;

public class Bridge {
    final Context c;
    Bridge(Context c) { this.c = c.getApplicationContext(); }

    void toast(final String m) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(c, m, Toast.LENGTH_LONG).show());
    }

    @JavascriptInterface public void sync(String json) {
        c.getSharedPreferences("s", 0).edit().putString("f", json).apply();
        SystemWidget.refresh(c);
    }

    @JavascriptInterface public void saveFile(String name, String data) {
        try {
            ContentValues v = new ContentValues();
            v.put(MediaStore.Downloads.DISPLAY_NAME, name);
            v.put(MediaStore.Downloads.MIME_TYPE, "application/json");
            Uri u = c.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, v);
            OutputStream o = c.getContentResolver().openOutputStream(u);
            o.write(data.getBytes("UTF-8"));
            o.close();
            toast("Backup saved in Downloads: " + name);
        } catch (Exception e) {
            toast("Could not save backup");
        }
    }
}
