/**
 *
 */
package org.droidtv.ui.playback.ui;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;

import org.droidtv.ui.playback.PlayBackControl2;
import org.droidtv.ui.playback.utility.PlaybackState;

import java.util.Formatter;
import java.util.Locale;

/**
 * AbsPlayBackControlLayout class is used to handle the keyvents of Playback
 * control in generic.
 *
 */
public abstract class AbsPlayBackControlLayout extends RelativeLayout {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    /**
     * Keyevent dispatcher to Playercontroller2 class
     *
     */
    public interface IDelegateEventListener {
        boolean keyEventDispatcherPBC(KeyEvent event);

    }

    ;

    /**
     * @param context
     */
    public AbsPlayBackControlLayout(Context context) {
        super(context);
        initialize();
    }

    /**
     * @param context
     * @param attrs
     */
    public AbsPlayBackControlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AbsPlayBackControlLayout(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);

        initialize();

    }

    private void initialize() {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * Convenience method to delegate the events to {@link PlayBackControl2}
     *
     * @param iIDelegateEventListener
     */
    public void setIDelegateEventListener(
            IDelegateEventListener iIDelegateEventListener) {

    }

    /**
     * Convenience method to set the seek position of progress bar.
     *
     * @param position
     */
    public abstract void seekTo(int position);

    /**
     * Convenience method to set Metadata info to playbackcontrol layout.
     *
     * @param values
     */
    public abstract void setMetadata(ContentValues values);

    /**
     * Convenience method to set the specific Metadata info to playbackcontrol
     * layout.
     *
     * @param key
     * @param values
     */
    public abstract void setMetadata(String key, String values);

    public void setMetadataIconVisible(boolean visible) {

    }


    /**
     * Convenience method to enable/disable the fwd and rwd keys based on
     * application input.
     *
     * @param isEnable
     */
    public abstract void setEnableFwdRwdKeys(boolean isEnable);

    public abstract void setVisibleFwdRwdKeys(boolean isVisible);

    public void setsetVisibilityFwdRwdKeys(int visibility) {

    }

    /**
     * Convenience method to enable/disable the next and prev keys based on
     * application input.
     *
     * @param isEnable
     */
    public abstract void setEnablePrevKeys(boolean isEnable);

    public abstract void setVisiblePrevKeys(boolean isVisible);

    /**
     * Convenience method to enable/disable the next and prev keys based on
     * application input.
     *
     * @param isEnable
     */
    public abstract void setEnableNextKeys(boolean isEnable);

    public abstract void setVisibleNextKeys(boolean isVisible);

    /**
     * Convenience method to enable/disable the play/pause keys based on
     * playback state.
     *
     * @param isEnable
     */
    public abstract void setEnabledPausePlay(boolean isEnable);

    public abstract void setVisiblePausePlay(boolean isVisible);

    /**
     * Convenience method to enable/disable the TSB based on application input.
     *
     * @param isEnable
     */
    public abstract void setEnableProgressBar(boolean isEnable);

    /**
     * Convenience method to enable slow mode based on application input.
     */
    public void showSlowModePlayback() {
        // to show playback text for DLNA
        // by default no text will be shown
    }

    /**
     * Convenience method to display the options key default : false
     */
    public abstract void showOptionsKey(boolean status);

    public abstract View getOptionsKey();

    public abstract View getEndTimeView();

    public abstract View getSeekBar();

    /**
     * Convenience method to set the fwd speed control.
     *
     * @param speed
     */
    public void setForwardSpeed(int speed) {

    }

    /**
     * Convenience method to set the rwd speed control.
     *
     * @param speed
     */
    public void setRewSpeed(float speed) {

    }

    /**
     * Convenience method to enable secondary TSB updated based on application
     * input.
     */
    public void enableSecondaryUpdate() {

    }

    /**
     * Convenience method to disable secondary TSB updated based on application
     * input.
     */
    public void disableSecondaryUpdate() {

    }

    /**
     * Convenience method to remove secondary TSB updated based on application
     * input.
     */
    public void removeSecondaryProgress() {

    }

    /**
     * Convenience method to set the secondray progress value.
     *
     * @param progress
     */
    public void setSecondaryProgress(int progress) {

    }

    /**
     * Convenience method to set the TSB progress max limit.
     *
     * @param max
     */
    public void setMaxProgress(int max) {

    }

    public void setKeyProgressIncrement(int increment) {

    }

    /**
     * Convenience method to set the current TSB progress value.
     *
     * @param progress
     */
    public void setProgress(int progress) {

    }

    /**
     * convenience method to set the view listeners
     *
     * @param prev
     * @param next
     */
    public void setPreNextListener(OnClickListener prev,
                                   OnClickListener next) {

    }

    /**
     * convenience method to set the view listeners
     *
     * @param rwd
     * @param fwd
     */
    public void setRwdFwdListener(OnClickListener rwd,
                                  OnClickListener fwd) {

    }

    /**
     * convenience method to set the view listeners
     *
     * @param pause
     */
    public void setPausePlayListener(OnClickListener pause) {

    }

    /**
     * convenience method to set the view listeners
     *
     * @param seek
     */
    public void setProgressListener(OnSeekBarChangeListener seek) {
    }

    /**
     * To set the filename
     *
     * @param name
     */
    public void setHeaderText(String name) {
    }

    /**
     * To set the current playback time
     *
     * @param currTime
     */
    public void setCurrentTime(int currTime) {

    }

    /**
     * To set the current playback time
     *
     * @param currTime
     */
    public void setSlideShowTime(String currTime) {

    }

    /**
     * To set the end of playback time
     *
     * @param endTime
     */
    public void setEndTime(int endTime) {

    }

    /**
     * To handle the left/right key events in seekbar of playing content only if
     * it is focused.
     *
     * @return
     */
    public boolean getSeekBarFocusability() {
        return false;
    }

    /**
     * Convenience method to refresh the background selector. Set it to default
     * selectors.
     *
     * @param isPlaying
     *            {@link PlaybackState}
     */
    public void refreshSelectors(boolean isPlaying) {

    }

    /**
     * To reset the fwd and rwd selecotors.
     */
    public void refreshFwdRwdSelectors() {

    }

    public void setPausePlayFocus() {


    }

    /**
     * Convenience method to udpate the fwd,rwd selector based on current
     * playback speed.
     *
     * @param fwd
     * @param speed
     */
    public void updateFwdRwdStatus(boolean fwd, int speed) {

    }

    /**
     * Longkey event listeners for Rwd and Fwd buttons
     *
     * @param rwd
     * @param fwd
     */
    public void setRwdFwdLongClickListener(OnLongClickListener rwd,  OnLongClickListener fwd) {
    }

    /**
     * Longkey event listeners for Rwd and Fwd buttons
     *
     * @param prev
     * @param next
     */
    public void setPrevNextLongClickListener(OnLongClickListener prev, OnLongClickListener next) {
    }

    public void setRwdFwdFocus(boolean rwd) {

    }

    public void setPrevNextFocus(boolean prev) {

    }

    public void setPlayPauseFocus() {
    }

    /**
     * set title text(music name)
     * @param text
     */
    public void setTitleText(String text) {
    }

    /**
     * set sub title text(artist name)
     * @param text
     */
    public void setSubTitleText(String text) {
    }

    public void setBackgroundColor(int color) {
    }

    public void setScaleType(ScaleType type) {
    }

    public ImageView getMajorImageView() {
        return null;
    }

    /**
     * set image(album image)
     * @param bitmap
     */
    public void setMajorImg(Bitmap bitmap) {
    }

    public void setMajorImg(int resId) {
    }

    public ImageView getShadowImageView() {
        return null;
    }

    public void setShadowImg(Bitmap bitmap) {
    }

    public void setShadowImg(int resId) {
    }

    /**
     * set bitmap(other)
     * @param bitmap
     */
    public void setMinorImg(Bitmap bitmap) {
    }

    public void setMinorImg(int resId) {
    }

    String stringForTime(int timeMs) {
        int totalSeconds = Math.round((float)timeMs / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
