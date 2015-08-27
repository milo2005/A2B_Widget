package creatic.movil.widgetmusic.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import creatic.movil.widgetmusic.R;
import creatic.movil.widgetmusic.services.MusicService;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent iPlay = new Intent(MusicService.ACTION_PLAY);
        PendingIntent pPlay = PendingIntent.getBroadcast(context,101
                ,iPlay,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent iPause = new Intent(MusicService.ACTION_PAUSE);
        PendingIntent pPause = PendingIntent.getBroadcast(context,102
                ,iPause,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent iStop = new Intent(MusicService.ACTION_STOP);
        PendingIntent pStop = PendingIntent.getBroadcast(context,103
                ,iStop,PendingIntent.FLAG_UPDATE_CURRENT);



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setOnClickPendingIntent(R.id.btn_play,pPlay);
        views.setOnClickPendingIntent(R.id.btn_pause,pPause);
        views.setOnClickPendingIntent(R.id.btn_stop,pStop);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

