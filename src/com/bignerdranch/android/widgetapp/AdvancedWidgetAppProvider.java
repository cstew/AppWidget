package com.bignerdranch.android.widgetapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AdvancedWidgetAppProvider extends AppWidgetProvider {
	
	private static final String SWITCH_ACTION_FAR = "com.bignerdranch.android.widgetapp.AdvancedWidgetAppProvider.switchActionFar";
	private static final String SWITCH_ACTION_CLOSE = "com.bignerdranch.android.widgetapp.AdvancedWidgetAppProvider.switchActionClose";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		if (SWITCH_ACTION_FAR.equals(action) || SWITCH_ACTION_CLOSE.equals(action)) {
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_advanced_layout);
			
			boolean isFar = SWITCH_ACTION_FAR.equals(action);
			views.setImageViewResource(R.id.widget_image, isFar? R.drawable.brian_the_human : R.drawable.brian_up_close);
			manager.partiallyUpdateAppWidget(appWidgetId, views);
		}
		
		super.onReceive(context, intent);
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int n = appWidgetIds.length;

        for (int i = 0; i < n; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_advanced_layout);
            views.setOnClickPendingIntent(R.id.widget_far_button, generatePendingIntent(context, SWITCH_ACTION_FAR, appWidgetId));
            views.setOnClickPendingIntent(R.id.widget_close_button, generatePendingIntent(context, SWITCH_ACTION_CLOSE, appWidgetId));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
	
	private PendingIntent generatePendingIntent(Context context, String action, int appWidgetId) {
		Intent switchIntent = new Intent(context, AdvancedWidgetAppProvider.class);
        switchIntent.setAction(action);
        switchIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return PendingIntent.getBroadcast(context, 0, switchIntent, PendingIntent.FLAG_UPDATE_CURRENT); 
	}

}
