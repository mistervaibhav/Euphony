package com.apps.developer_cults.euphony;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Constants {
    public interface ACTION {
        String MAIN_ACTION = "com.marothiatechs.customnotification.action.main";
        String INIT_ACTION = "com.marothiatechs.customnotification.action.init";
        String PREV_ACTION = "com.marothiatechs.customnotification.action.prev";
        String PLAY_ACTION = "com.marothiatechs.customnotification.action.play";
        String NEXT_ACTION = "com.marothiatechs.customnotification.action.next";
        String STARTFOREGROUND_ACTION = "com.marothiatechs.customnotification.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.marothiatechs.customnotification.action.stopforeground";
        String CHANGE_TO_PAUSE = "com.marothiatechs.customnotification.action.changetopause";
        String CHANGE_TO_PLAY = "com.marothiatechs.customnotification.action.changetoplay";
        String NEXT_UPDATE = "com.marothiatechs.customnotification.action.nextupdate";
        String NEXT_UPDATE_SHUFFLE = "com.marothiatechs.customnotification.action.nextupdateshuffle";
        String PREV_UPDATE = "com.marothiatechs.customnotification.action.prevupdate";
        String PREV_UPDATE_SHUFFLE = "com.marothiatechs.customnotification.action.prevupdateshuffle";




    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_image, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

}