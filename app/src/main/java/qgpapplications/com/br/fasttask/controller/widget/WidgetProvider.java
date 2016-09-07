package qgpapplications.com.br.fasttask.controller.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Date;

import qgpapplications.com.br.fasttask.R;
import qgpapplications.com.br.fasttask.model.util.DateUtils;

/**
 * Created by c1284521 on 10/11/2015.
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {

            RemoteViews remoteViews = updateWidgetListView(context,widgetId);

            // Set the text
            String date = DateUtils.convertDateToString(new Date());
            remoteViews.setTextViewText(R.id.textViewToolbarWidget, date);

            // Register an onClickListener
            Intent intent = new Intent(context, WidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.textViewToolbarWidget, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }


    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, RemoteViewService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);
        return remoteViews;
    }
}
