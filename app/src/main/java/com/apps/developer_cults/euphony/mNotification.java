package com.apps.developer_cults.euphony;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.apps.developer_cults.euphony.activities.MainActivity;
import com.apps.developer_cults.euphony.fragments.SongPlayingFragment;

/**
 * http://www.tutorialsface.com/2015/08/android-custom-notification-tutorial/
 * https://stackoverflow.com/questions/22789588/how-to-update-notification-with-remoteviews
 */

public class mNotification extends Service {


    MainActivity main;
    static MediaPlayer mMediaPlayer;

    String title = "";
    String artist = "";
    SongPlayingFragment msong;
    RemoteViews views;
    RemoteViews smallviews;
    ImageView imageView;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        msong = new SongPlayingFragment();
        main = new MainActivity();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {


            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {

                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");
                main.setNotify_val(true);

                showNotification();

            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {

                msong.previous();
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();


            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {

                msong.setPlay(msong.playorpause());

                if (msong.getPlay() == false) {

                    views.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                    smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                } else {

                    views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                    smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                }


                updateNotiUI();

            } else if (intent.getAction().equals(Constants.ACTION.CHANGE_TO_PAUSE)) {
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();
            } else if (intent.getAction().equals(Constants.ACTION.CHANGE_TO_PLAY)) {
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);

                updateNotiUI();

            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                msong.next();
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();


            } else if (intent.getAction().equals(Constants.ACTION.NEXT_UPDATE)) {

                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");

                if (title.equals("<unknown>"))
                    title = "Unknown";

                if (artist.equals("<unknown>"))
                    artist = "unknown";

                views.setTextViewText(R.id.song_title_nav, title);
                views.setTextViewText(R.id.song_artist_nav, artist);
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setTextViewText(R.id.song_title_nav, title);
                smallviews.setTextViewText(R.id.song_artist_nav, artist);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();
            }


            else if (intent.getAction().equals(Constants.ACTION.PREV_UPDATE)) {
                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");
                if (title.equals("<unknown>"))
                    title = "Unknown";

                if (artist.equals("<unknown>"))
                    artist = "unknown";
                views.setTextViewText(R.id.song_title_nav, title);
                views.setTextViewText(R.id.song_artist_nav, artist);
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);

                smallviews.setTextViewText(R.id.song_title_nav, title);
                smallviews.setTextViewText(R.id.song_artist_nav, artist);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();

            } else if (intent.getAction().equals(
                    Constants.ACTION.STOPFOREGROUND_ACTION)) {

                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                        .getInstance(this);
                localBroadcastManager.sendBroadcast(new Intent(
                        "com.durga.action.close"));

                msong.unregister();

                mMediaPlayer = msong.getMediaPlayer();
                mMediaPlayer.stop();

                main.setNotify_val(false);
                stopForeground(true);
                stopSelf();

            }

        }

    catch(Exception e) {
        main.finishAffinity();
    }
    finally {
        return START_STICKY;
    }

}

    Notification status;

    private void showNotification() {
// Using RemoteViews to bind custom layouts into Notification
        views = new RemoteViews(getPackageName(),
                R.layout.notification_bar);

        smallviews = new RemoteViews(getPackageName(),
                R.layout.notificaiton_smalll);



        Intent openIntent = new Intent(this, mNotification.class);
        PendingIntent pOpenIntent = PendingIntent.getActivity(this, 0, openIntent, 0);



        Intent notificationIntent = new Intent(this,MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, mNotification.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, mNotification.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, mNotification.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, mNotification.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.playpausebutton_not, pplayIntent);
        smallviews.setOnClickPendingIntent(R.id.playpausebutton_not, pplayIntent);


        views.setOnClickPendingIntent(R.id.nextbutton_not, pnextIntent);
        smallviews.setOnClickPendingIntent(R.id.nextbutton_not, pnextIntent);


        views.setOnClickPendingIntent(R.id.previousbutton_not, ppreviousIntent);
        smallviews.setOnClickPendingIntent(R.id.previousbutton_not, ppreviousIntent);


        views.setOnClickPendingIntent(R.id.close, pcloseIntent);
        smallviews.setOnClickPendingIntent(R.id.close,pcloseIntent);


        if(title.equals("<unknown>"))
            title="Unknown";

        if(artist.equals("<unknown>"))
            artist="unknown";

        views.setTextViewText(R.id.song_title_nav, title);
        smallviews.setTextViewText(R.id.song_title_nav, title);


        views.setTextViewText(R.id.song_artist_nav, artist);
        smallviews.setTextViewText(R.id.song_artist_nav, artist);



        views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
        smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Sets an ID for the notification, so it can be updated.
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_011";// The id of the channel.
            CharSequence name = "Notify";
            int importance = NotificationManager.IMPORTANCE_HIGH;


            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name,  importance);

           // NotificationChannel mChannel=  mNotificationManager.getNotificationChannel("my_channel_07");

//            int importance = mChannel.getImportance();
//            if (importance < NotificationManager.IMPORTANCE_HIGH && importance > 0 ) {
//                mChannel.setImportance(NotificationManager.IMPORTANCE_MAX);
//            }

            // Create a notification and set the notification channel.
            mChannel.setSound(null, null);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mChannel.enableVibration(false);

            mNotificationManager.createNotificationChannel(mChannel);

            status = new Notification.Builder(this, CHANNEL_ID).setOnlyAlertOnce(true)
                    .setVisibility(Notification.VISIBILITY_PUBLIC).setContentIntent(pOpenIntent).build();
            status.contentView=smallviews;
            status.bigContentView = views;
            status.priority=Notification.PRIORITY_MAX;
            status.when =0;


            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.default_image;
            status.contentIntent = pendingIntent;


            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

        }

        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O ){

            status = new Notification.Builder(this).setWhen(0).setContentIntent(pOpenIntent).build();
            status.contentView=smallviews;
            status.bigContentView = views;
            status.visibility=Notification.VISIBILITY_PUBLIC;
            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.default_image;
            status.contentIntent = pendingIntent;
            status.priority = Notification.PRIORITY_MAX;

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

        }


        else {
            status = new Notification.Builder(this).setWhen(0).setContentIntent(pOpenIntent).build();


            status.contentView=smallviews;
            status.bigContentView = views;

            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.default_image;
            status.contentIntent = pendingIntent;
            status.priority = Notification.PRIORITY_MAX;

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
        }

        // binding sensor in notification

        /*Here we call the function*/
//        msong.bindShakeListener();


    }



    public void updateNotiUI() {
        this.startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, this.status);
    }


}