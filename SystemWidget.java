package com.midnight.system;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import org.json.JSONObject;

public class SystemWidget extends AppWidgetProvider {
    @Override public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        for (int id : ids) m.updateAppWidget(id, build(c));
    }

    static void refresh(Context c) {
        AppWidgetManager m = AppWidgetManager.getInstance(c);
        for (int id : m.getAppWidgetIds(new ComponentName(c, SystemWidget.class)))
            m.updateAppWidget(id, build(c));
    }

    static RemoteViews build(Context c) {
        SharedPreferences p = c.getSharedPreferences("s", 0);
        String text = "You have acquired the qualifications to be a Player.\nWill you accept?";
        try {
            JSONObject j = new JSONObject(p.getString("f", ""));
            StringBuilder b = new StringBuilder(j.optString("s").toUpperCase() + ": " + j.optString("t"));
            String line2 = j.optString("tm");
            if (!j.optString("sub").isEmpty()) line2 = line2.isEmpty() ? j.optString("sub") : line2 + " \u00B7 " + j.optString("sub");
            if (!line2.isEmpty()) b.append("\n").append(line2);
            b.append("\n\u201C").append(j.optString("q")).append("\u201D");
            text = b.toString();
        } catch (Exception e) { /* nothing synced yet */ }
        RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.widget);
        v.setTextViewText(R.id.msg, text);
        v.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(c, 0,
                new Intent(c, MainActivity.class), PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
        return v;
    }
}
