package org.droidtv.ui.playback;


import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import org.droidtv.ui.playback.ui.AbsPlayBackControlLayout;


public class PlaybackViewCreator {

    private final PopupWindow mPopupwindow;
    private final View mAnchor;
    private final int PLAY_FULL_SCREEN = 1;
    private final int PLAY_RIGHT_PANEL = 2;

    public PlaybackViewCreator(View anchor, boolean isSlideShow) {
        mAnchor = anchor;
        mPopupwindow = new PopupWindow(anchor.getContext());

        LayoutInflater inflater = (LayoutInflater)anchor.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AbsPlayBackControlLayout mPlaybackLayout;
        if (isSlideShow) {
            mPlaybackLayout = (AbsPlayBackControlLayout)inflater.inflate(
                    R.layout.photocontrol, null);
        } else {
            mPlaybackLayout = (AbsPlayBackControlLayout)inflater.inflate(
                    R.layout.playercontrol, null);
        }
        mPopupwindow.setContentView(mPlaybackLayout);
        initWindow(PLAY_FULL_SCREEN);
    }

    public PlaybackViewCreator(View anchor) {
        mAnchor = anchor;
        mPopupwindow = new PopupWindow(anchor.getContext());

        LayoutInflater inflater = (LayoutInflater)anchor.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AbsPlayBackControlLayout mPreviewPlayLayout = (AbsPlayBackControlLayout)inflater.inflate(
                R.layout.preview_audio_play, null);

        mPopupwindow.setContentView(mPreviewPlayLayout);

        initWindow(PLAY_RIGHT_PANEL);
    }

    private void initWindow(int size) {
        if (size == PLAY_FULL_SCREEN) {
            mPopupwindow.setFocusable(true);
            mPopupwindow.setWidth(LayoutParams.MATCH_PARENT);
            mPopupwindow.setHeight(LayoutParams.MATCH_PARENT);
            mPopupwindow.setBackgroundDrawable(null);
        } else {
            Resources resources = mAnchor.getContext().getResources();
            int width = (int)resources
                    .getDimension(R.dimen.preview_playbackcontrols_container_width);
            int height = (int)resources
                    .getDimension(R.dimen.preview_playbackcontrols_container_height);
            mPopupwindow.setFocusable(false);
            mPopupwindow.setWidth(width);
            mPopupwindow.setHeight(height);
            mPopupwindow.setBackgroundDrawable(null);
        }

        mPopupwindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
    }

    PopupWindow getPupupWindow() {
        return mPopupwindow;
    }

    void show() {
        mPopupwindow.showAtLocation(mAnchor, Gravity.TOP, 0, 0);
    }

    void showPreview() {
        Resources resources = mAnchor.getContext().getResources();
        int marginLeft = (int)resources
                .getDimension(R.dimen.preview_playbackcontrols_margin_left);
        int marginTop = (int)resources
                .getDimension(R.dimen.preview_playbackcontrols_margin_top);
        mPopupwindow.showAtLocation(mAnchor, Gravity.CENTER_VERTICAL, marginLeft, marginTop);
    }
}
