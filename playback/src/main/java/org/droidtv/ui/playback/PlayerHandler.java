/**
 *
 */
package org.droidtv.ui.playback;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * PlayberHandler allows you to send and process Message and Runnable objects
 * associated with a thread's MessageQueue..
 *
 */
public class PlayerHandler extends Handler {

    private static final String TAG = PlayerHandler.class.getSimpleName();
    private WeakReference<PlayBackControl2> mPlayController = null;

    public PlayerHandler(PlayBackControl2 play) {
        super();
        mPlayController = new WeakReference<>(play);
    }

    public void handleMessage(Message msg) {
        PlayBackControl2 lPlay = mPlayController.get();
        if (lPlay != null) {
            switch (msg.what) {
                case PlaybackUtils.FADE_OUT:
                    Log.d(TAG, "hide is called here");
                    lPlay.updateHandlerFadout();
                    break;
                case PlaybackUtils.SHOW_PROGRESS:
                    //Log.d(TAG, "SHOW_PROGRESS");
                    lPlay.upateHandlerSetProg();
                    break;
                case PlaybackUtils.SECONDARYPROGRESS:
                    lPlay.updateHandlerSecondrayProg();
                    break;
                case PlaybackUtils.LRKEYPRESSED:
                    Log.d(TAG, "LONGKEYPRESSED time =" + System.currentTimeMillis());
                    lPlay.updateHandlerLongKeyPressed();
                    break;
                default:
                    break;
            }
        }
    }
}
