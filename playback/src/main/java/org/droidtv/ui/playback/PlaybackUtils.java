package org.droidtv.ui.playback;


import android.view.KeyEvent;

import org.droidtv.ui.playback.utility.PlaybackSpeed;
import org.droidtv.ui.playback.utility.PlaybackState;


public final class PlaybackUtils {

    static final int DEFAULT_FADEOUT_TIME = 4000;
    static final int UPDATE_INTERVAL = 1000;

    static final int FADE_OUT = 1;
    static final int SHOW_PROGRESS = 2;
    static final int SECONDARYPROGRESS = 3;
    static final int LRKEYPRESSED = 4;
    static final int NOLONGLRKEYPRSD = 0;
    static final int LONGKEYPRSDFWD = 2;
    static final int LONGKEYPRSDRWD = 1;

    static int getNextSlowPlaySpeed(int currentSpeed) {
        int lNextSpeed = PlaybackSpeed.SPEED_LEVEL3_NORMAL;
        switch (currentSpeed) {
            case PlaybackSpeed.DEFAULT:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL1;
                break;
            case PlaybackSpeed.SPEED_LEVEL1:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL2;
                break;

            case PlaybackSpeed.SPEED_LEVEL2:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL3_NORMAL;
                break;
            default:
                break;
        }

        return lNextSpeed;
    }

    static int getNextNormalPlaySpeed(int currentSpeed) {
        int lNextSpeed = PlaybackSpeed.SPEED_LEVEL3_NORMAL;
        switch (currentSpeed) {
            case PlaybackSpeed.DEFAULT:
            case PlaybackSpeed.SPEED_LEVEL1:
            case PlaybackSpeed.SPEED_LEVEL2:
            case PlaybackSpeed.SPEED_LEVEL3_NORMAL:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL4;
                break;

            case PlaybackSpeed.SPEED_LEVEL4:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL5;
                break;

            case PlaybackSpeed.SPEED_LEVEL5:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL6;
                break;

            case PlaybackSpeed.SPEED_LEVEL6:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL7;
                break;

            case PlaybackSpeed.SPEED_LEVEL7:
                lNextSpeed = PlaybackSpeed.SPEED_LEVEL8;
                break;

            case PlaybackSpeed.SPEED_LEVEL8:
                lNextSpeed = PlaybackSpeed.DEFAULT;
                break;

            default:
                break;
        }
        return lNextSpeed;
    }

    static int getPlaybackState(int keycode) {

        switch (keycode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_STOP:
                return PlaybackState.PLAY;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                return PlaybackState.PAUSE;

            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return PlaybackState.FORWARD;

            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return PlaybackState.REWIND;
            default:
                break;
        }
        return PlaybackState.PLAY;
    }

}
