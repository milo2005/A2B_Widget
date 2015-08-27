package creatic.movil.widgetmusic.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.io.IOException;

/**
 * Created by Dario Chamorro on 26/08/2015.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    public static final String ACTION_PLAY="creatic.play";
    public static final String ACTION_PAUSE="creatic.pause";
    public static final String ACTION_STOP="creatic.stop";

    MediaPlayer player;
    Boolean paused;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals(ACTION_PLAY)){
            playMusic();
        }else if(intent.getAction().equals(ACTION_PAUSE)){
            pauseMusic();
        }else{
            stopMusic();
        }
        return START_NOT_STICKY;
    }

    private void stopMusic() {
        if(player != null){
            player.stop();
            stopSelf();
        }
    }

    private void pauseMusic() {
        if(player!=null) {
            player.pause();
            paused = true;
        }
    }

    private void playMusic() {

        if(player == null){

            initMediaPlayer();
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Reproduciendo")
                    .setContentText("Bangaran")
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .build();
            startForeground(101, notification);

            try {
                AssetFileDescriptor assetFileDescriptor = getAssets().openFd("music/cancion.mp3");
                player.setDataSource(assetFileDescriptor.getFileDescriptor()
                ,assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

                player.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(paused){
            player.start();
        }
        paused = false;

    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        releaseMediaPlayer();
        super.onDestroy();
    }

    public void initMediaPlayer(){
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
    }

    public void releaseMediaPlayer(){
        player.release();
        player = null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
