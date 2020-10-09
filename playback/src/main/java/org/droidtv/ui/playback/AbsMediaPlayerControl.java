package org.droidtv.ui.playback;

public abstract class AbsMediaPlayerControl {

    // Ref PR's issue AN-1787
    public abstract void seekTo(int pos);

    public abstract void play();

    public abstract void pause();

    public abstract int getTotalDuration();

    public abstract int getCurrentDuration();

    public abstract boolean isPlaying();

    public abstract int getBufferPercentage();

    /**
     * @return
     * @deprecated use setSpeed
     */
    public boolean onForwardKeyPressed() {
        return false;

    }

    /**
     * @return
     * @deprecated use setSpeed
     */
    public boolean onRewindKeyPressed() {
        return false;

    }

    public abstract void setSpeed(Boolean forward, int speed);

    public void stop() {

    }

    public boolean onPrevKeyPressed() {
        return false;
    }

    public boolean onNextKeyPressed() {
        return false;
    }

    public void openMenu() {

    }

    public int getSpeed() {
        return 0;
    }

    public String getPlayingMediaFilePath() {
        return "";
    }
}
