package org.droidtv.ui.playback.ui;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.droidtv.ui.playback.R;


public class PerviewPlayLayout extends AbsPlayBackControlLayout {
    private static final String TAG = "PerviewPlayLayout";
    private TextView mAudioName;
    private TextView mArtistName;
    private TextView mCurrTime;
    private TextView mEndTime;
    private TextView mEndTimePreview;
    private ImageView mAudioAlbum;
    private ImageView mAudioShadow;
    private ImageView mAudioDisk;
    private ProgressBar mProgress;

    public PerviewPlayLayout(Context context) {
        super(context);
    }

    public PerviewPlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PerviewPlayLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
        mAudioAlbum = findViewById(R.id.preview_audio_album_iv);
        mAudioShadow = findViewById(R.id.preview_audio_shadow);
        mAudioDisk = findViewById(R.id.preview_audio_disk);
        mAudioName = findViewById(R.id.preview_audio_name);
        mArtistName = findViewById(R.id.preview_audio_artist);
        mProgress = findViewById(R.id.preview_audio_play_progress);
        mCurrTime = findViewById(R.id.current_time);
        mEndTime = findViewById(R.id.endtime);
        mEndTimePreview = findViewById(R.id.endtime_preview);
    }

    @Override
    public void seekTo(int position) {


    }

    @Override
    public void setMetadata(ContentValues values) {


    }

    @Override
    public void setMetadata(String key, String values) {
    }

    @Override
    public void setEnableFwdRwdKeys(boolean isEnable) {
    }

    @Override
    public void setEnablePrevKeys(boolean isEnable) {

    }

    @Override
    public void setEnableNextKeys(boolean isEnable) {

    }

    @Override
    public void setEnabledPausePlay(boolean isEnable) {

    }

    @Override
    public void setEnableProgressBar(boolean isEnable) {

    }

    @Override
    public void setRwdFwdListener(OnClickListener rwd, OnClickListener fwd) {

    }

    @Override
    public void setRwdFwdLongClickListener(OnLongClickListener rwd,
                                           OnLongClickListener fwd) {

    }

    @Override
    public void setPausePlayListener(OnClickListener pause) {

    }

    @Override
    public void setPreNextListener(OnClickListener prev, OnClickListener next) {

    }

    @Override
    public void showOptionsKey(boolean status) {

    }

    @Override
    public View getOptionsKey() {
        return null;
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
    public void setMaxProgress(int max) {
        mProgress.setMax(max);

    }

    @Override
    public void setProgress(int progress) {
        mProgress.setProgress(progress);
    }

    @Override
    public void setHeaderText(String name) {
        super.setHeaderText(name);
    }

    @Override
    public void setTitleText(String text) {

        if (null == text) {
            return;
        }
        if (text.equals("")) {
            mAudioName.setVisibility(View.GONE);
        } else {
            mAudioName.setText(text);
            mAudioName.setSelected(true);
        }
    }

    @Override
    public void setSubTitleText(String text) {

        if (null == text) {
            return;
        }
        if (text.equals("")) {
            mArtistName.setVisibility(View.GONE);
        } else {
            mArtistName.setVisibility(View.VISIBLE);
            mArtistName.setText(text);
        }
    }


    @Override
    public void setBackgroundColor(int color) {

        mAudioAlbum.setBackgroundColor(color);
    }

    @Override
    public void setScaleType(ScaleType type) {

        mAudioAlbum.setScaleType(type);
    }

    @Override
    public void setMajorImg(Bitmap bitmap) {

        if (null == bitmap) {
            return;
        }
        mAudioAlbum.setImageBitmap(bitmap);
    }


    @Override
    public ImageView getMajorImageView() {

        return mAudioAlbum;
    }

    @Override
    public void setMajorImg(int resId) {

        mAudioAlbum.setImageResource(resId);
    }


    @Override
    public ImageView getShadowImageView() {

        return mAudioShadow;
    }

    @Override
    public void setShadowImg(Bitmap bitmap) {

        if (null == bitmap) {
            mAudioShadow.setVisibility(View.GONE);
        } else {
            mAudioShadow.setVisibility(View.VISIBLE);
            mAudioShadow.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setShadowImg(int resId) {

        if (0 == resId) {
            mAudioShadow.setVisibility(View.GONE);
        } else {
            mAudioShadow.setVisibility(View.VISIBLE);
            mAudioShadow.setImageResource(resId);
        }
    }

    @Override
    public void setMinorImg(Bitmap bitmap) {

        if (null == bitmap) {
            return;
        }
        mAudioDisk.setImageBitmap(bitmap);
    }

    @Override
    public void setMinorImg(int resId) {

        mAudioDisk.setImageResource(resId);
    }

    @Override
    public void setVisibleFwdRwdKeys(boolean isVisible) {


    }

    @Override
    public void setVisiblePrevKeys(boolean isVisible) {


    }

    @Override
    public void setVisibleNextKeys(boolean isVisible) {
    }

    @Override
    public void setVisiblePausePlay(boolean isVisible) {
    }

    public void setPreviewEndTime(String endTimeStr) {
        mEndTimePreview.setText(endTimeStr);
    }

    public void setPreviewEndTime(int endTime) {
        // 如果媒体文件还没开始播放，endTime可能为0
        // 导致将正确的时间刷新为0
        if (0 == endTime) {
            return;
        }
        String endTimeStr = stringForTime(endTime);
        mEndTimePreview.setText(endTimeStr);
    }
}
