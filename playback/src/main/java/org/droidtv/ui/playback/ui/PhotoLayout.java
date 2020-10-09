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
import android.widget.TextView;

import org.droidtv.ui.playback.R;
import org.droidtv.ui.playback.utility.PlaybackControlConstants;

/**
 * Photolayout class is used to define the slideshow playbackcontrol components
 * and its state.
 */
public class PhotoLayout extends AbsPlayBackControlLayout {

    private IDelegateEventListener mIDelegateEventListener = null;
    private static final String TAG = PlayBackControlLayout.class.getSimpleName();

    private ImageButton mPrev, mNext, mPausePlay, mOptKey;

    private TextView mHeader, mMetaInfo, mCurrTime;

    private String mFileName = " ";
    private String mMetaDataPath = " ";
    private String mDolbyText = null;

    /**
     * @param context
     */
    public PhotoLayout(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public PhotoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PhotoLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        // Will be available all the time, find..wont fail.
        mHeader = (TextView)findViewById(R.id.TxtFileNm);
        mMetaInfo = (TextView)findViewById(R.id.metaInfo);

        mPausePlay = (ImageButton)findViewById(R.id.pause);
        mPrev = (ImageButton)findViewById(R.id.prevbutton);
        mNext = (ImageButton)findViewById(R.id.nextButton);

        mCurrTime = (TextView)findViewById(R.id.current_time);
        mOptKey = (ImageButton)findViewById(R.id.optionskey);


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
    public void setMetadata(String Key, String values) {
        parseMetaInfo(Key, values);
        refreshMetaInfo();
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

        mHeader.setText(mFileName);
        mMetaInfo.setText(mMetaDataPath);
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
       
        return null;
    }

    @Override
    public View getSeekBar() {
       
        return null;
    }

    @Override
    public void setHeaderText(String txt) {
        mHeader.setText(txt);
    }

    @Override
    public void setSlideShowTime(String currTime) {
        mCurrTime.setText(currTime);
    }

    @Override
    public void refreshSelectors(boolean isPlaying) {
        if (isPlaying) {
            mPausePlay.setBackgroundResource(R.drawable.play_pause_selector);
        } else {
            mPausePlay.setBackgroundResource(R.drawable.play_playicon_selector);
        }
    }

    @Override
    public void setPausePlayFocus() {
        mPausePlay.requestFocus();
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
    public void setVisibleFwdRwdKeys(boolean isVisible) {


    }

    @Override
    public void setPlayPauseFocus() {
        mPausePlay.requestFocus();
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
    public void setPausePlayListener(OnClickListener pause) {
        mPausePlay.setOnClickListener(pause);
    }

    @Override
    public void setPreNextListener(OnClickListener prev, OnClickListener next) {
        mPrev.setOnClickListener(prev);
        mNext.setOnClickListener(next);
    }

    @Override
    public void setPrevNextLongClickListener(OnLongClickListener prev,
                                             OnLongClickListener next) {
        mPrev.setOnLongClickListener(prev);
        mNext.setOnLongClickListener(next);
    }

    @Override
    public void seekTo(int position) {
       

    }

    @Override
    public void setEnableFwdRwdKeys(boolean isEnable) {
       

    }

    @Override
    public void setEnableProgressBar(boolean isEnable) {
       

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
