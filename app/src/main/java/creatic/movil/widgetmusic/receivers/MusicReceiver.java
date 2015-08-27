package creatic.movil.widgetmusic.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import creatic.movil.widgetmusic.services.MusicService;

/**
 * Created by Dario Chamorro on 26/08/2015.
 */
public class MusicReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentService = new Intent(context, MusicService.class);
        intentService.setAction(intent.getAction());
        context.startService(intentService);

    }

}
