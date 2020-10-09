/**
 *
 */
package org.droidtv.ui.playback.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;

import org.droidtv.ui.playback.R;
import org.droidtv.ui.playback.utility.PlaybackSpeed;


/**
 * @author muthuraj.s
 *
 */
public class ImageButtonFwd extends androidx.appcompat.widget.AppCompatImageButton {

    /**
     * Dynamically update the attributes based on speed
     */
    private int mSpeed = PlaybackSpeed.SPEED_LEVEL3_NORMAL;

    /**
     * This sparse array is used for getting the playback custom attributes
     * dynamically in onCreateDrawableState() to avoid switch/ladder in this
     * method.
     */
    private static SparseArray<Integer> mFwdStates = new SparseArray<Integer>();

    static {
        /**
         * Speed control custom attributes
         */
        mFwdStates.append(PlaybackSpeed.DEFAULT,
                R.attr.speed_control_fwd_normal);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL1, R.attr.speed_control_14x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL2, R.attr.speed_control_12x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL3_NORMAL,
                R.attr.speed_control_fwd_normal);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL4, R.attr.speed_control_2x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL5, R.attr.speed_control_4x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL6, R.attr.speed_control_8x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL7, R.attr.speed_control_16x);
        mFwdStates.append(PlaybackSpeed.SPEED_LEVEL8, R.attr.speed_control_32x);

    }

    /**
     * @param context
     */
    public ImageButtonFwd(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public ImageButtonFwd(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ImageButtonFwd(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] lDrawableState = super.onCreateDrawableState(extraSpace + 1);

        int[] lFwd_attributes = {mFwdStates.get(mSpeed)};

        mergeDrawableStates(lDrawableState, lFwd_attributes);

        return lDrawableState;

    }

    public void setCurrntSpeedState(int speed) {
        mSpeed = speed;

        refreshDrawableState();
    }

}
