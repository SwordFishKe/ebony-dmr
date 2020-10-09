/**
 *
 */
package org.droidtv.ui.playback.ui;


import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import org.droidtv.ui.playback.R;
import org.droidtv.ui.playback.utility.PlaybackControlConstants;
import org.droidtv.ui.playback.utility.PlaybackSpeed;

/**
 * PlayBackControlLayout class is used to define the Video(DLNA,CB)
 * playbackcontrol components and its state.
 */
public class PlayBackControlLayout extends AbsPlayBackControlLayout {

    private IDelegateEventListener mIDelegateEventListener = null;
    private static final String TAG = PlayBackControlLayout.class.getSimpleName();

    private ImageButton mPrev, mNext, mPausePlay, mOptKey;

    private ImageButtonRwd mRwd;
    private ImageButtonFwd mFwd;

    private SeekBar mProg;

    private TextView mHeader, mMetaInfo, mCurrTime, mEndTime;

    private String mFileName = " ";
    private String mMetaDataPath = " ";
    private String mDolbyText = null;

    /**
     * @param context
     */
    public PlayBackControlLayout(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public PlayBackControlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PlayBackControlLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {

        // Will be available all the time, find..wont fail.
        super.onFinishInflate();
        mHeader = (TextView)findViewById(R.id.TxtFileNm);
        mMetaInfo = (TextView)findViewById(R.id.metaInfo);

        mPausePlay = (ImageButton)findViewById(R.id.pause);
        mPrev = (ImageButton)findViewById(R.id.prevbutton);
        mNext = (ImageButton)findViewById(R.id.nextButton);

        mCurrTime = (TextView)findViewById(R.id.current_time);
        mOptKey = (ImageButton)findViewById(R.id.optionskey);

        mProg = (SeekBar)findViewById(R.id.mediacontroller_progress);

        mEndTime = (TextView)findViewById(R.id.endtime);
        mFwd = (ImageButtonFwd)findViewById(R.id.ffwd);

        mRwd = (ImageButtonRwd)findViewById(R.id.rew);

    }

    @Override
    public void setMetadata(ContentValues values) {

        if (values != null && values.size() > 0) {

            mDolbyText = values
                    .getAsString(PlaybackControlConstants.PLAY_DOLBY_DTS_TEXT);
            mMetaDataPath = values
                    .getAsString(PlaybackControlConstants.PLAY_METADATA_TEXT);
            mFileName = values
                    .getAsString(PlaybackControlConstants.PLAY_SPEED_TEXT);
        }
        refreshMetaInfo();

    }

    @Override
    public void setMetadata(String key, String values) {
        parseMetaInfo(key, values);
        refreshMetaInfo();
    }

    @Override
    public void setMetadataIconVisible(boolean visible) {
        if (visible) {
            mMetaInfo.setCompoundDrawablePadding(7);
            mMetaInfo.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.dolby_logo, 0, 0, 0);
        } else {
            mMetaInfo.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, 0, 0);
        }
    }

    private void parseMetaInfo(String key, String value) {
        if (key.equals(PlaybackControlConstants.PLAY_DOLBY_DTS_TEXT)) {
            mDolbyText = value;
        }
        if (key.equals(PlaybackControlConstants.PLAY_METADATA_TEXT)) {
            mMetaDataPath = value;
        } else {
            mFileName = value;
        }
        Log.i(TAG, "parseMetaInfo  mFileName : " + mFileName + "  mDolbyText :" + mDolbyText + " mMetaDataPath " + mDolbyText);
        refreshMetaInfo();
    }

    private void refreshMetaInfo() {

        if (mHeader != null) {
            mHeader.setText(mFileName);
        }
        if (mMetaInfo != null) {
            mMetaInfo.setText(mMetaDataPath);
        }

    }

    @Override
    public void showOptionsKey(boolean status) {

        if (status) {
            mOptKey.setVisibility(VISIBLE);
        } else {
            mOptKey.setVisibility(INVISIBLE);
        }
        mOptKey.setFocusable(false);
    }

    @Override
    public View getOptionsKey() {
        
        return mOptKey;
    }

    @Override
    public View getEndTimeView() {
        
        return mEndTime;
    }

    @Override
    public View getSeekBar() {
        
        return mProg;
    }

    @Override
    public void seekTo(int position) {
        mProg.setProgress(position);

    }

    @Override
    public void setEnableFwdRwdKeys(boolean isEnable) {
        mRwd.setEnabled(isEnable);
        mRwd.setFocusable(isEnable);
        mFwd.setEnabled(isEnable);
        mFwd.setFocusable(isEnable);
    }

    @Override
    public void setVisibleFwdRwdKeys(boolean isVisible) {
        if (isVisible) {
            mRwd.setVisibility(View.VISIBLE);
            mFwd.setVisibility(View.VISIBLE);
        } else {
            mRwd.setVisibility(View.INVISIBLE);
            mFwd.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void setsetVisibilityFwdRwdKeys(int visibility) {
        
        mRwd.setVisibility(visibility);
        mFwd.setVisibility(visibility);
    }

    @Override
    public void setEnableNextKeys(boolean isEnable) {
        mNext.setEnabled(isEnable);
        mNext.setFocusable(isEnable);

    }

    @Override
    public void setVisibleNextKeys(boolean isVisible) {
        if (isVisible) {
            mNext.setVisibility(View.VISIBLE);
        } else {
            mNext.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void setEnablePrevKeys(boolean isEnable) {
        mPrev.setEnabled(isEnable);
        mPrev.setFocusable(isEnable);
    }

    @Override
    public void setVisiblePrevKeys(boolean isVisible) {
        if (isVisible) {
            mPrev.setVisibility(View.VISIBLE);
        } else {
            mPrev.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setEnabledPausePlay(boolean isEnable) {
        mPausePlay.setEnabled(isEnable);
        mPausePlay.setFocusable(isEnable);

    }

    @Override
    public void setVisiblePausePlay(boolean isVisible) {
        if (isVisible) {
            mPausePlay.setVisibility(View.VISIBLE);
        } else {
            mPausePlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setEnableProgressBar(boolean isEnable) {
        mProg.setEnabled(isEnable);
        mProg.setFocusable(isEnable);

    }

    @Override
    public void setRwdFwdListener(View.OnClickListener rwd, View.OnClickListener fwd) {
        mRwd.setOnClickListener(rwd);
        mFwd.setOnClickListener(fwd);
    }

    @Override
    public void setRwdFwdLongClickListener(View.OnLongClickListener rwd,
                                           View.OnLongClickListener fwd) {
        mRwd.setOnLongClickListener(rwd);
        mFwd.setOnLongClickListener(fwd);
    }

    @Override
    public void setPausePlayListener(View.OnClickListener pause) {
        mPausePlay.setOnClickListener(pause);
    }

    @Override
    public void setPreNextListener(View.OnClickListener prev, View.OnClickListener next) {
        mPrev.setOnClickListener(prev);
        mNext.setOnClickListener(next);
    }

    @Override
    public void setProgressListener(OnSeekBarChangeListener seek) {
        mProg.setOnSeekBarChangeListener(seek);
    }

    @Override
    public void setHeaderText(String txt) {
        if (mHeader != null) {
            mHeader.setText(txt);
        }
    }

    @Override
    public void setCurrentTime(int currTime) {
        String currTimeStr = stringForTime(currTime);
        mCurrTime.setText(currTimeStr);

    }

    @Override
    public void setEndTime(int endTime) {
        String endTimeStr = stringForTime(endTime);
        mEndTime.setText(endTimeStr);
    }

    @Override
    public boolean getSeekBarFocusability() {

        return mProg.isFocusable();

    }

    @Override
    public void refreshSelectors(boolean isPlaying) {

        // if both will be avail together , we can relay on
        // single.

        if (isPlaying) {
            mPausePlay.setBackgroundResource(R.drawable.play_pause_selector);
        } else {
            mPausePlay.setBackgroundResource(R.drawable.play_playicon_selector);
        }

        refreshFwdRwdSelectors();

    }

    @Override
    public void refreshFwdRwdSelectors() {
        // if both will be avail together , we can relay on single.
        mFwd.setCurrntSpeedState(PlaybackSpeed.SPEED_LEVEL3_NORMAL);
        mRwd.setCurrntSpeedState(PlaybackSpeed.SPEED_LEVEL3_NORMAL);
    }

    @Override
    public void setPausePlayFocus() {
        mPausePlay.requestFocus();
    }

    @Override
    public void updateFwdRwdStatus(boolean fwd, int speed) {
        Log.i("play", "PlayBackControlLayout, updateFwdRwdStatus");
        if (fwd) {
            mFwd.setCurrntSpeedState(speed);
        } else {
            mRwd.setCurrntSpeedState(speed);
        }
    }

    @Override
    public void setRwdFwdFocus(boolean rwd) {

        if (rwd) {
            mRwd.requestFocus();
        } else {
            mFwd.requestFocus();
        }
    }

    @Override
    public void setPrevNextFocus(boolean prev) {

        if (prev) {
            mPrev.requestFocus();
        } else {
            mNext.requestFocus();
        }
    }

    @Override
    public void setPlayPauseFocus() {

        mPausePlay.requestFocus();
    }

    @Override
    public void setMaxProgress(int max) {
        mProg.setMax(max);

    }

    @Override
    public void setKeyProgressIncrement(int increment) {
        mProg.setKeyProgressIncrement(increment);
    }

    @Override
    public void setProgress(int progress) {
        mProg.setProgress(progress);
    }

    @Override
    public void setSecondaryProgress(int progress) {
        long secondaryupdate = (mProg.getMax() * progress) / 100;
        mProg.setSecondaryProgress((int)secondaryupdate);
    }

    @Override
    public void removeSecondaryProgress() {
        mProg.setSecondaryProgress(0);
    }

    @Override
    public void setIDelegateEventListener(
            IDelegateEventListener iIDelegateEventListener) {
        mIDelegateEventListener = iIDelegateEventListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e(TAG, "dispatchKeyEvent " + event.toString() + " " + toString());
        boolean ret = false;
        if (mIDelegateEventListener != null) {
            ret = mIDelegateEventListener.keyEventDispatcherPBC(event);
        }
        if (!ret) {
            return super.dispatchKeyEvent(event);
        } else {
            return ret;
        }

    }


}
