package org.droidtv.ui.playback;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import org.droidtv.ui.playback.ui.AbsPlayBackControlLayout;
import org.droidtv.ui.playback.ui.PerviewPlayLayout;
import org.droidtv.ui.playback.utility.PlaybackControlConstants;
import org.droidtv.ui.playback.utility.PlaybackSpeed;
import org.droidtv.ui.playback.utility.PlaybackState;


/**
 * * <style>
 * table,th,td
 * {
 * border:1px solid black;
 * }
 * </style>
 * <h1 style="text-align: center;"><span style="font-family:arial,helvetica,sans-serif;">PlaybackControl Component Design</span></h1>
 *
 * <p><strong>Purpose and Scope :</strong></p>
 * <p>Playback component gives user interface while media player plays any media file or images.</p>
 * <p>User Interactions includes following actions by remote keys.</p>
 *
 *
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Playing /Pausing a file.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Fast Forwarding/ rewinding.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Playing Next/Previous file from list of selected files.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dragging progress bar to desired position forward or backward.</p>
 *
 *
 * <p>Playback controls are used for situations where the progress of a running video, music, slide show is indicated continuously. It gives user a view of how longer the play back will take, what is speed for ffwd/rwd .</p>
 * <p>Playback controls are time line based. The bar is empty at the beginning of the activity showed in blue color and fills from left to right. The user can interact with the progress bar by using DPad keys to change the progress back or forward on the time line. It also shows buffered amount of content with gray color on progress bar in time shift feature, where channel can be paused and buffered and then played.</p>
 * <p>It also shows Name of file which is getting played and some more information like count of file in selected folder, name of album etc. at top window of play back component.</p>
 * <p>Playback controls is shown for 4sec after 4sec if there is no user interaction then it is hidden. Info key of remote can be used if user wants control to be displayed continuously without time out.</p>
 * <p>PlayBack component doesn&rsquo;t play media file.</p>
 *
 *
 * <p><strong>References :</strong></p>
 * <p>Component reference document link (share point link with the component name)</p>
 *
 *
 *
 * <p><strong>List of applications using this component</strong></p>
 * <ol>
 * <li>Content Explorer &ndash; While running video file, audio file and slide show of images playback component is shown to user which shows status of media file.</li>
 * <li>PTA &ndash; Media file from other device like mobile or tablet is pushed on Tv set to view.</li>
 * <li>PlayTv &ndash; User is playing already recorded content from storage media, Playback comp is used .</li>
 * <li>Timeshift - When live channel needs to be recorded, stream is paused and content starts buffering. Playback component shows this buffered content with gray color.</li>
 * <li>Nettv browser &ndash; Videos from network are viewed in full screen mode.</li>
 * </ol>
 * <p><strong>&nbsp;</strong></p>
 *
 *
 * <p><strong>Requirements :</strong></p>
 *
 * <p>Handle following actions using keys and pointer</p>
 *
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Play /Pause a file.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Fast Forward/ rewind.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Playing Next/Previous file from list of selected files.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Changing progress bar to desired position forward or backward using DPad keys.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Show current time duration of media file at left side and total duration of media file at right side of bottom window above progress bar.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Show speed at the time user does forward and rewind, Name of file and other information about that file c/a metadata, Type of video played (Dolby digital etc.) at top window.</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Handle visibility by using &nbsp;media keys in remote</p>
 * <p>-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Update UI when user uses media keys in remote.</p>
 *
 * <p><strong><span style="text-decoration: underline;">Usage Guidelines for Apps:</span></strong></p>
 * <p><strong>Packages exposed for Apps:</strong></p>
 * <ol>
 * <li><strong>1.&nbsp;&nbsp;&nbsp;&nbsp; </strong><strong>org.droidtv.ui.comps.media</strong></li>
 * <li><strong>2.&nbsp;&nbsp;&nbsp;&nbsp; </strong><strong>org.droidtv.ui.comps.media.utility</strong></li>
 * </ol>
 *
 * <pre><strong>Interfaces/Classes &nbsp;exposed to Client application:</strong></pre>
 * <pre>&nbsp;</pre>
 * <pre>1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Abstract class AbsMediaPlayerControl</pre>
 * <pre>&nbsp;</pre>
 * <pre>2.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IPbcDissmissListener //Playbackcontrol dismiss listener</pre>
 * <pre>and&nbsp; org.droidtv.ui.comps.media.utility constant files for specifying Speed and metadata contentvalues.</pre>
 *
 *
 * <pre><strong>Usage:</strong></pre>
 *
 * <pre><strong>&nbsp;</strong></pre>
 * <pre>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Activity using playback component should create an instance of it using following constructor.</pre>
 * <pre>1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; It accepts View as first argument which acts as anchor view on which top and bottom popup windows are rendered.</pre>
 * <pre>2.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Second argument is boolean &ndash; which specifies whether it&rsquo;s a slide show of images or not.</pre>
 * <pre>3.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Third argument is not used (printing the uri to identify the app).</pre>
 * <pre>&nbsp;</pre>
 * <pre><strong>public</strong> <a href="http://opengrok.tpvision.com:8080/source/s?refs=PlayBackControl&amp;project=device"><strong>PlayBackControl</strong></a>2(<strong>final</strong> <a href="http://opengrok.tpvision.com:8080/source/s?defs=View&amp;project=device">View</a> <a href="http://opengrok.tpvision.com:8080/source/s?refs=anchor&amp;project=device"><strong>anchor</strong></a>, <strong>boolean</strong> <a href="http://opengrok.tpvision.com:8080/source/s?refs=slideshow&amp;project=device"><strong>slideshow</strong></a>, <a href="http://opengrok.tpvision.com:8080/source/s?defs=String&amp;project=device">String</a> <a href="http://opengrok.tpvision.com:8080/source/s?refs=uri&amp;project=device"><strong>uri</strong></a>) {}</pre>
 * <pre>&nbsp;</pre>
 * <pre><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Following are the methods of playback control exposed to Activity and its purpose.</strong></pre>
 * <pre>&nbsp;</pre>
 * <pre>1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void show(int timeout) : &nbsp;Shows the PlayBackComponent on screen. It will go away automatically after 'timeout' milliseconds of inactivity. Pass 0 to show the controller&nbsp; until hide() is called.</pre>
 * <pre>2.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void hide() :Remove the controller from the screen.</pre>
 * <pre>3.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void setMediaPlayer(AbsMediaPlayerControlplayer) : Activity will pass object of type AbsMediaPlayerControlplayer&nbsp; to communicate between player and component.</pre>
 * <pre>4.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void setEnabled(boolean enabled) : set the enability of &nbsp;all playback buttons.</pre>
 * <pre>Specific call back for this for playback control,</pre>
 * <pre>&nbsp;</pre>
 *
 * <pre>&nbsp;&nbsp; setEnabledFfwd(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp; setEnabledRew(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp; setEnabledNext(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp; setEnabledPrev(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp; setEnabledPausePlay(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp; setEnabledProgress(boolean enabled)</pre>
 * <pre>&nbsp;</pre>
 *
 * <pre>5.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void setEnabledTrickAndJumpMode(boolean enabled)</pre>
 * <pre>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; To enable the trickmode option.</pre>
 * <pre>&nbsp;</pre>
 * <pre>6.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; setSlowPlayControlsEnabled(Boolean enable)</pre>
 * <pre>&nbsp;&nbsp; To enable DLNA trickplay along with trickmode. To be called with&nbsp; 5.</pre>
 * <pre>&nbsp;</pre>
 * <pre>7.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; setTrickModeRange(int fwdMax, int rwdMax)</pre>
 * <pre>&nbsp;&nbsp; Set the range for trickmode playback.</pre>
 * <pre>&nbsp;</pre>
 * <pre>8.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void enableSecondaryUpdate() : In timeshift , activity has to call this to notify component to start displaying secondary progress in progressbar.</pre>
 * <pre>9.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public void onSeekCompleted() : This is to notify component that activity has done the task.On this notification component will update UI and displays it.</pre>
 * <pre>10.&nbsp; void showOptionKey(boolean status )</pre>
 * <pre>&nbsp;&nbsp; To show the optionkey(2k15), playbackcontrol will give this callback to apps via unhandled keys.</pre>
 * <pre>11.&nbsp; Activity has to implement NewKeyRegisterListener and implement public boolean unHandledKeyEvents(KeyEvent event) to get the keys which are not handled by component as unhandled keys. Such keys are returned to activity by </pre>
 * <pre>mNewKeyRegisterListener.unHandledKeyEvents(event).</pre>
 * <pre>12.&nbsp; public void setMediaPlayer(AbsMediaPlayerControl player) : Activity will pass object of type AbsMediaPlayerControl to communicate between player and component</pre>
 * <pre>&nbsp;</pre>
 * <p><strong>PlaybackControl &ndash; Internal only: &nbsp;&nbsp;&nbsp;&nbsp; </strong></p>
 * <p><strong>Package: </strong>org.droidtv.ui.comps.media.ui</p>
 * <p><strong>Requirements Traceability - Design </strong></p>
 * <p><strong>&nbsp;Static Design : </strong>Playback Component includes following classes.<strong></strong></p>
 *
 * <ul>
 * <li>AbsPlayBackControlLayout extends RelativeLayout :
 * <ul>
 *
 * <li>AbsPlayBackControlLayout class is used to handle the keyvents of Playback control in generic.</li>
 * </ul>
 * </li>
 * </ul>
 * <p>&nbsp;</p>
 * <ul>
 *
 * <li>PlayBackControlLayout extends AbsPlayBackControlLayout
 * <ul>
 * <li>PlayBackControlLayout class is used to define the Video(DLNA,CB)</li>
 * <li>playbackcontrol components and its state.</li>
 * </ul>
 * </li>
 * </ul>
 * <p>&nbsp;</p>
 * <ul>
 * <li>PhotoLayout extends AbsPlayBackControlLayout
 * <ul>
 * <li>Photolayout class is used to define the slideshow playbackcontrol components and its state.</li>
 * </ul>
 * </li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong>&nbsp;</strong></p>
 * <p><strong>Execution Design </strong></p>
 *
 *
 * <pre>Handler is used here to schedule messages to be executed at some point in the future. </pre>
 * <pre>&nbsp;</pre>
 * <p><strong>private</strong> Handler mHandler = <strong>new</strong> Handler() {</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; @Override</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>public</strong> <strong>void</strong> handleMessage(Message msg) {</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>int</strong> pos = 0;</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>switch</strong> (msg.what) {</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>case</strong> <em>FADE_OUT</em>:</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; // Hide playbar</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>break</strong>;</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>case</strong> <em>SHOW_PROGRESS</em>:</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //Set or update Progress and durations of progress bar</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>break</strong>;</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>case</strong> <em>SECONDARYPROGRESS</em>:</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //setSecondaryProgress of progressbar received from activity&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>break</strong>;</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>case</strong> <em>LRKEYPRESSED</em>:</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
 * <p>//incrementPlayBack by certain amount continuously for long key //pressed</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>break</strong>;</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
 * <p>};</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p><strong>Class Diagram</strong></p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <img src="{@docRoot}/playback_design_asset/PlaybackControl_ClassDig.PNG" />
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p><strong>Sequence Diagram</strong></p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <img src="{@docRoot}/playback_design_asset/PlaybackControl_SeqDig.PNG" />
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 * <p>&nbsp;</p>
 */
public class PlayBackControl2 implements AbsPlayBackControlLayout.IDelegateEventListener {

    protected static final String TAG = PlayBackControl2.class.getSimpleName();

    private AbsMediaPlayerControl mPlayer = null;
    private int mState = PlaybackUtils.NOLONGLRKEYPRSD;
    private int mLastTempState = PlaybackUtils.NOLONGLRKEYPRSD;

    private final PlaybackViewCreator mViewHandler;
    private PlayerHandler mHandler;

    private boolean slideshow = false;
    private IPbcDissmissListener mIPbcDissmissListener;
    private int currDurAnim = 0;

    private int mPlaybackState = PlaybackState.PLAY;
    private int mPlaybackSpeed = PlaybackSpeed.DEFAULT;
    private int mTimeoutFromApplication = 0;
    private boolean isSecondaryProgressEnabled = false;
    private int mSsecondaryprogress = 0;

    private int mTrickModeFwdMax = PlaybackSpeed.SPEED_LEVEL8;
    private int mTrickModeRwdMax = PlaybackSpeed.SPEED_LEVEL8;
    /**
     * To support thr trick mode functionality. It can be any xX.
     */
    private boolean isTrickModeEnabled = false;
    /**
     * In TrickMode slow play enabled or not 1/4X,1/2X support
     */
    private boolean isSlowPlayEnabled = false;
    /**
     * To set normal speed to default/play - for slowplay. Used for UI and media
     * pause/play keys.
     */
    private boolean isDLNAEnabled = false;

    private boolean enableWrapAround = true;
    private boolean enableNext = true;
    private boolean enablePrev = true;
    private boolean enablePausePlay = true;
    private boolean mEnableGetDuration = true;

    private boolean mEnableTimeOut = true;

    private boolean visibleNext = true;
    private boolean visiblePrev = true;
    private boolean visiblefwdrwd = true;

    /**
     * Interface for handling the Key Events - init by application
     */
    private NewKeyRegisterListener mNewKeyRegisterListener = null;

    // Used for photoplayback longkeyevnt animator
    private ObjectAnimator mSlideShowAnimator;

    private Animator mProgressSliding;
    private Animator mProgressSlidingBak;
    private OnControlBarDismissListener mDismissListener = null;
    private OnProgressUpdateListener mProgressListener = null;

    private long mTotalDuration = -1;
    private static final long PROGRASS_MAX = 10000;

    /**
     * Create a BetterPopupWindow
     *
     * @param anchor the view that the BetterPopupWindow will be displaying 'from'
     */
    public PlayBackControl2(final View anchor, boolean slideshow) {
        this.slideshow = slideshow;
        mViewHandler = new PlaybackViewCreator(anchor, slideshow);
        if (slideshow) {
            // No seekbar,rwd,fwd buttons removed form this xml. block the keys
            // for fwd,rwd.
            isTrickModeEnabled = false;
            mSlideShowAnimator = new ObjectAnimator();
            mSlideShowAnimator.setTarget(this);
            mSlideShowAnimator.setInterpolator(new LinearInterpolator());
            mSlideShowAnimator.setDuration(250);
        }
        getPlayBackLayout().setIDelegateEventListener(this);
        mViewHandler.getPupupWindow().setOnDismissListener(onDismissListener);
        getPlayBackLayout().refreshSelectors(true);
        initControllerView();
    }

    public PlayBackControl2(final View anchor) {
        this.slideshow = false;
        mViewHandler = new PlaybackViewCreator(anchor);
        Log.d(TAG, "getPlayBackLayout=" + getPlayBackLayout());

        getPlayBackLayout().setIDelegateEventListener(this);
        mViewHandler.getPupupWindow().setOnDismissListener(onDismissListener);

        initControllerView();

        mProgressSliding =
                (Animator)AnimatorInflater
                        .loadAnimator(anchor.getContext(), R.animator.preview_playback_progress_slide);

        mProgressSlidingBak = (Animator)AnimatorInflater
                .loadAnimator(anchor.getContext(), R.animator.preview_playback_progress_slide_bak);
    }


    /**
     * Anything you want to have happen when created. Probably should create a
     * view and setup the event listeners on child views.
     */

    private void initControllerView() {
        mHandler = new PlayerHandler(this);
        getPlayBackLayout().setPreNextListener(mPrevListener, mNextListener);
        getPlayBackLayout().setPausePlayListener(mPauseListener);
        if (slideshow) {
            getPlayBackLayout().setPrevNextLongClickListener(
                    mPrevLongClickListener, mNextLongClickListener);
        } else {
            getPlayBackLayout().setRwdFwdListener(mRewListener, mFfwdListener);
            getPlayBackLayout().setRwdFwdLongClickListener(mRwdLongClickListener,
                    mFwdLongClickListener);
            getPlayBackLayout().setProgressListener(mSeekListener);
        }

    }

    public interface OnControlBarDismissListener {
        void onControlBarDismiss();
    }

    public void setOnControlBarListener(OnControlBarDismissListener listener) {
        this.mDismissListener = listener;
    }

    public interface OnProgressUpdateListener {
        void onProgress(int current, int total);
    }

    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.mProgressListener = listener;
    }

    void updateHandlerFadout() {
        if (mTimeoutFromApplication != 0) {
            doHide();
        }
    }

    void upateHandlerSetProg() {
        //Log.d(TAG, "SHOW_PROGRESS");
        setProgress();
        if (mViewHandler.getPupupWindow().isShowing()) {
            mHandler.sendEmptyMessageDelayed(PlaybackUtils.SHOW_PROGRESS,
                    PlaybackUtils.UPDATE_INTERVAL);
        }
    }

    void updateHandlerSecondrayProg() {
        mSsecondaryprogress = mPlayer.getBufferPercentage();
        getPlayBackLayout().setSecondaryProgress(mSsecondaryprogress);
        Message msg = mHandler.obtainMessage(PlaybackUtils.SECONDARYPROGRESS);
        mHandler.sendMessageDelayed(msg, 1000);
        if (mSsecondaryprogress == 100) {
            mHandler.removeMessages(PlaybackUtils.SECONDARYPROGRESS);
        }
    }

    void updateHandlerLongKeyPressed() {
        mEnableGetDuration = false;
        if (mState == PlaybackUtils.LONGKEYPRSDRWD) {
            incrementPlayBack(-9000);
        }
        if (mState == PlaybackUtils.LONGKEYPRSDFWD) {
            incrementPlayBack(9000);
        }
        if (mState != PlaybackUtils.NOLONGLRKEYPRSD
                && mPlayer.getCurrentDuration() > 0
                && (mPlayer.getTotalDuration()) > mPlayer.getCurrentDuration()) {
            mHandler.sendEmptyMessageDelayed(PlaybackUtils.LRKEYPRESSED, 50);
        }
    }

    int setProgress() {
        Log.d(TAG, "The progress will be shown" + currDurAnim);
        if (mPlayer == null) {
            return 0;
        }
        if (!slideshow) {

            if (mState != PlaybackUtils.NOLONGLRKEYPRSD) {
                return 0;
            }

            int position = -1;
            int duration = mPlayer.getTotalDuration();
            if (mEnableGetDuration) {
                position = mPlayer.getCurrentDuration();
            } else {
                if (currDurAnim < 0) {
                    currDurAnim = 0;
                } else if (currDurAnim > duration) {
                    currDurAnim = duration;
                } else {
                    position = currDurAnim;
                }
            }
            //Log.d(TAG, "position = " + position);
            if (duration > 0) {
                // use long to avoid overflow
                long progress = PROGRASS_MAX * position / duration;
                getPlayBackLayout().setProgress((int)progress);
                if (mProgressListener != null) {
                    mProgressListener.onProgress(position, duration);
                }
                // 解决音频播放器调用show时，mediaplayer不处于播放状态
                // 获取的duration为0导致progressbar不能获取焦点的问题
                getPlayBackLayout().setEnableProgressBar(true);//xuzhc
            } else {
                getPlayBackLayout().setProgress(0);
            }
        }
        if (slideshow) {
            // set slide text currtime/endtime 5/15.
            getPlayBackLayout().setSlideShowTime(mPlayer.getCurrentDuration() + "/" + mPlayer.getTotalDuration());
        } else {
            getPlayBackLayout().setCurrentTime(mPlayer.getCurrentDuration());
            getPlayBackLayout().setEndTime(mPlayer.getTotalDuration());
        }
        //Log.d(TAG, " The progress will be shown end ");
        return 0;
    }

    private void incrementPlayBack(int dur) {
        int duration = dur;
        currDurAnim = currDurAnim + duration;
        duration = mPlayer.getTotalDuration();
        Log.d(TAG, "incrementPlayBack step 1 " + currDurAnim);
        if (!slideshow) {
            // No SeekBar for slide show.This condition has to be checked if we need the seekTo in special
            // media with no totalduration
            if (duration > 0) {
                if (currDurAnim < 0) {
                    currDurAnim = 0;
                } else if (currDurAnim > duration) {
                    currDurAnim = duration;
                }
                long lPos = PROGRASS_MAX * currDurAnim / duration;
                Log.d(TAG, "incrementPlayBack step 2 " + lPos);
                getPlayBackLayout().setProgress((int)lPos);
            }
        }
        doShow(PlaybackUtils.DEFAULT_FADEOUT_TIME);
    }

    /**
     * set the controller bar will timeout to disappear or not
     *
     * @param enableTimeout
     */
    public void setEnableTimeout(boolean enableTimeout) {
        mEnableTimeOut = enableTimeout;
    }

    /**
     * Show the controller on screen. It will go away automatically after 4
     * seconds of inactivity.
     */
    public void show() {
        Log.d(TAG, "show() is called from application with default timeout ");
        doShow(PlaybackUtils.DEFAULT_FADEOUT_TIME);
        getPlayBackLayout().setPausePlayFocus();
        getPlayBackLayout().setEnableProgressBar(mPlayer.getTotalDuration() != 0);
    }

    /**
     * Show the PlayBackComponent on screen. It will go away automatically after
     * 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show the controller
     *                until hide() is called.
     */
    public void show(int timeout) {
        Log.d(TAG, "Show() is called from application with timeout = " + timeout);
        mTimeoutFromApplication = timeout;
        doShow(timeout);
    }

    public void showPreview() {
        Log.d(TAG, "showPreview");
        mHandler.removeMessages(PlaybackUtils.FADE_OUT);
        mHandler.removeMessages(PlaybackUtils.SECONDARYPROGRESS);
        if ((isSecondaryProgressEnabled == true) && (mSsecondaryprogress < 100)) {
            mHandler.sendEmptyMessage(PlaybackUtils.SECONDARYPROGRESS);
        }
        mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
        mHandler.sendEmptyMessage(PlaybackUtils.SHOW_PROGRESS);
        Log.d(TAG, "the media player is " + mPlayer.isPlaying() + mState);
        // Attach the popup window to anchor view
        mViewHandler.showPreview();
        mHandler.sendEmptyMessageDelayed(PlaybackUtils.FADE_OUT,
                mTimeoutFromApplication);
    }

    private void doShow(int timeout) {
        mTotalDuration = mPlayer.getTotalDuration();
        Log.d(TAG, "show timeout = " + timeout
                + mHandler.hasMessages(PlaybackUtils.LRKEYPRESSED)
                + " secondary progress will be shown =" + isSecondaryProgressEnabled);
        mHandler.removeMessages(PlaybackUtils.FADE_OUT);
        mHandler.removeMessages(PlaybackUtils.SECONDARYPROGRESS);
        if ((isSecondaryProgressEnabled == true) && (mSsecondaryprogress < 100)) {
            mHandler.sendEmptyMessage(PlaybackUtils.SECONDARYPROGRESS);
        }
        mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
        mHandler.sendEmptyMessage(PlaybackUtils.SHOW_PROGRESS);
        mTimeoutFromApplication = timeout;
        Log.d(TAG, "the media player is " + mPlayer.isPlaying() + mState);
        // Attach the popup window to anchor view
        mViewHandler.show();
        if (mEnableTimeOut) {
            mHandler.sendEmptyMessageDelayed(PlaybackUtils.FADE_OUT,
                    mTimeoutFromApplication);
        }
    }

    /**
     * Dismiss the popupwindow
     *
     * @deprecated similar to show()
     */
    public void dismiss() {
        doHide();
    }

    /**
     * Remove the controller from the screen.
     */
    public void hide() {
        Log.d(TAG, " hide () is called from application ");
        doHide();
    }

    private void doHide() {
        if (getPlayBackLayout() == null) {
            return;
        }
        if (mViewHandler.getPupupWindow().isShowing()) {
            try {
                Log.d(TAG, "the hide request is done");
                mViewHandler.getPupupWindow().dismiss();
                mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
                mHandler.removeMessages(PlaybackUtils.FADE_OUT);
                mHandler.removeMessages(PlaybackUtils.SECONDARYPROGRESS);
                mHandler.removeMessages(PlaybackUtils.LRKEYPRESSED);
            } catch (IllegalArgumentException ex) {
                Log.w("MediaController", "already removed");
            }
        }

    }

    /**
     * Convenience method to set Metadata info to playbackcontrol layout. Use
     * {@link PlaybackControlConstants} key values to set the corresponding
     * info.
     *
     * @param values
     */
    public void setMetadata(ContentValues values) {
        getPlayBackLayout().setMetadata(values);
    }

    /**
     * Convenience method to set the specific Metadata info to playbackcontrol
     * layout. Use {@link PlaybackControlConstants} key values to set the
     * corresponding info.
     *
     * @param Key
     * @param values
     */
    public void setMetadata(String Key, String values) {
        getPlayBackLayout().setMetadata(Key, values);
    }

    /**
     * Convenience method to set the mediaplayer instance to controller. This
     * sets the progress bar to the max 1000 for audio and video and for images
     * the total number of images
     */
    public void setMediaPlayer(AbsMediaPlayerControl player) {
        mPlayer = player;
        if (!slideshow) {
            getPlayBackLayout().setMaxProgress((int)PROGRASS_MAX);
        }
    }

    /**
     * Enable or disable Fwd button Issue number 1771
     *
     * @param enabled
     */
    public void setEnabledFfwd(boolean enabled) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setEnableFwdRwdKeys(enabled);
        }
    }

    public void setVisiblefwdrwd(boolean isVisible) {
        if (getPlayBackLayout() != null) {
            visiblefwdrwd = isVisible;
            getPlayBackLayout().setVisibleFwdRwdKeys(isVisible);
        }
    }

    /**
     * Enable or disable Rew button Issue number 1771
     *
     * @param enabled
     */
    public void setEnabledRew(boolean enabled) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setEnableFwdRwdKeys(enabled);
        }
    }

    public void setVisibleRew(boolean isVisible) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setVisibleFwdRwdKeys(isVisible);
        }
    }

    /**
     * Enable or disable Next button
     *
     * @param enabled if true button is enabled false button is not enabled
     */

    public void setEnabledNext(boolean enabled) {
        if (getPlayBackLayout() != null) {
            enableNext = enabled;
            getPlayBackLayout().setEnableNextKeys(enabled);
        }
    }

    public void setVisibleNext(boolean isVisible) {
        if (getPlayBackLayout() != null) {
            visibleNext = isVisible;
            getPlayBackLayout().setVisibleNextKeys(isVisible);
        }
    }

    /**
     * Enable or disable Prev button
     *
     * @param enabled if true button is enabled false button is not enabled
     */
    public void setEnabledPrev(boolean enabled) {
        if (getPlayBackLayout() != null) {
            enablePrev = enabled;
            getPlayBackLayout().setEnablePrevKeys(enabled);
        }
    }


    public void setVisiblePrev(boolean isVisible) {
        if (getPlayBackLayout() != null) {
            visiblePrev = isVisible;
            getPlayBackLayout().setVisiblePrevKeys(isVisible);
        }
    }

    /**
     * Enable or disable Play/Pause button
     *
     * @param enabled if true button is enabled false button is not enabled
     */
    public void setEnabledPausePlay(boolean enabled) {
        if (getPlayBackLayout() != null) {
            enablePausePlay = enabled;
            getPlayBackLayout().setEnabledPausePlay(enabled);
        }
    }

    public void setVisiblePausePlay(boolean isVisible) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setVisiblePausePlay(isVisible);
        }
    }

    /**
     * Enable or disable Progress bar button Issue number 1771
     *
     * @param enabled
     */

    public void setEnabledProgress(boolean enabled) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setEnableProgressBar(enabled);
        }
    }

    /**
     * set the enabling of buttons. Will enable all the
     * buttons(Play,prev,next,fwd,rwd). All are enabled by default.
     *
     * @param enabled if true buttons are enabled false button is not enabled
     */
    public void setEnabled(boolean enabled) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().setEnabledPausePlay(enabled);
            getPlayBackLayout().setEnableFwdRwdKeys(enabled);
            getPlayBackLayout().setEnableNextKeys(enabled);
            getPlayBackLayout().setEnablePrevKeys(enabled);
            getPlayBackLayout().setEnableProgressBar(enabled);
        }
    }

    /**
     * Update the secondary buffer limit in progress bar
     */
    public void enableSecondaryUpdate() {
        // start running a handler and update the secondary value
        isSecondaryProgressEnabled = true;
        Message msg = Message.obtain();
        msg.what = PlaybackUtils.SECONDARYPROGRESS;
        mHandler.sendMessage(msg);
    }

    /**
     * To disable the secondary progress update
     * <p>
     */
    public void disableSecondaryUpdate() {
        isSecondaryProgressEnabled = false;
        removeSecondaryProgress();
        mHandler.removeMessages(PlaybackUtils.SECONDARYPROGRESS);
    }

    /**
     * Convenience method to remove the secondary progress
     */
    public void removeSecondaryProgress() {
        getPlayBackLayout().removeSecondaryProgress();
    }

    public interface NewKeyRegisterListener {
        /**
         * Application should return false if it is not handled. (eg: keys
         * up/down/left/right app should return false)
         *
         * @param keyEvent
         * @return
         */
        public boolean unHandledKeyEvents(KeyEvent keyEvent);
    }

    /**
     * The param has to be set by the class who has implemented the
     * KeyRegisterListener to get the unhandled keys.
     *
     * @param keyRegisterListener AN-766
     */
    // The New Public API for observer.
    public void registerNewKeyListenerUnhandledKeys(
            NewKeyRegisterListener keyRegisterListener) {
        mNewKeyRegisterListener = keyRegisterListener;
    }

    /**
     * Notified by application which is using this component
     */
    public void onSeekCompleted() {
        Log.d(TAG, "onSeekCompleted is called");
        mState = PlaybackUtils.NOLONGLRKEYPRSD;
        currDurAnim = 0;
        mEnableGetDuration = true;
        mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
        mHandler.sendEmptyMessage(PlaybackUtils.SHOW_PROGRESS);
    }

    /**
     * Playbackcontroller dismiss listener
     */
    public interface IPbcDissmissListener {
        public void onPbcDismissed();
    }

    public void setBackKeyListener(IPbcDissmissListener argListener) {
        mIPbcDissmissListener = argListener;
    }

    /**
     * To be called by Application use case : after image gets uploaded(by
     * PicturePlayerActivity)
     */
    public void setCurrentDuration() {
        Log.d(TAG, "Show () is called from Application");
        mHandler.sendEmptyMessage(PlaybackUtils.SHOW_PROGRESS);
    }

    /**
     * This sets SlowPlayTrickMode From Application use case : slowPlayEnabled
     * is false for AudioPlayer
     *
     * @param enabled (Value=true, available)( Value=false, not available)
     */
    public void setSlowPlayTrickMode(boolean enabled) {
        isSlowPlayEnabled = enabled;
    }

    /**
     * Controls visibility of slow play controls on play bar - Forward and
     * Rewind
     * <p>
     * This is to reset the slowplaycontrol.
     *
     * @param enable (Value=Boolean.TRUE, not used)( Value=Boolean.FALSE, reset to
     *               normal)
     */
    public void setSlowPlayControlsEnabled(Boolean enable) {
//	    Log.i("play", "PlayBackControl2, setSlowPlayControlsEnabled");
        Log.d(TAG, "setSlowPlayControlsEnabled is called from Application");
        isDLNAEnabled = false;
        resetPlayControls();
    }

    /**
     * This method will be called by application for resetting Playback
     * component before starting Next playback/any other keyevent like rwd to
     * playkey.
     */
    public void resetPlayControls() {
//	    Log.i("play", "Playbackcontrol2, resetPlayControls");
        if (getPlayBackLayout() != null) {
            boolean lState = mPlayer.isPlaying();
            getPlayBackLayout().refreshSelectors(lState);
            setPlaybackSpeed(PlaybackSpeed.SPEED_LEVEL3_NORMAL);// reset.
            if (lState) {
                setPlaybackState(KeyEvent.KEYCODE_MEDIA_PLAY);
            } else {
                setPlaybackState(KeyEvent.KEYCODE_MEDIA_PAUSE);
            }
        }
    }

    /**
     * API to update play pause state of playback from Application
     */
    public void updatePlaybackState() {
//	    Log.i("play", "PlayBackControl2, updatePlaybackState");
        resetPlayControls();
    }

    /**
     * API to be called to enable or disable trickplay and seek actions. should
     * be called with setEnabledProgress()
     *
     * @param enabled true = enabled and false = disabled
     */
    public void setEnabledTrickAndJumpMode(boolean enabled) {
        isTrickModeEnabled = enabled;
    }

    /**
     * API to set wrap around of images during slideshow
     * Note : 2k15 No seekbar for Phot playback control(Input from UXD), hence
     * will give onNextkeypressed callback only, app has to maintain this.
     *
     * @param enabled = true when wrapAround is allowed.
     */
    public void setEnabledWrapAround(boolean enabled) {
        enableWrapAround = enabled;
        Log.d(TAG, "enableWrapAround " + enableWrapAround);
    }

    /**
     * Sets the play back speed as per defined constants in @link
     * PlaybackSpeed.java
     *
     * @param speed constant value passed for key event
     */
    private void setPlaybackSpeed(int speed) {
//		Log.d("play", "PlayBackControl2 setPlaybackSpeed to " + speed);
        mPlaybackSpeed = speed;
    }

    /**
     * Gets the current play back speed
     *
     * @return int Constant for play back speed defined in @link
     * PlaybackSpeed.java
     */
    private int getPlaybackSpeed() {
//		Log.i("play", "PlayBackControl2, getPlaybackSpeed = " + mPlaybackSpeed);
        return mPlaybackSpeed;
    }

    /**
     * Sets the play back state for Trick play keys only.
     *
     * @param keycode constant value passed for key event
     */
    private void setPlaybackState(int keycode) {

        Log.d(TAG, "setPlaybackState - keycode=" + keycode);
        mPlaybackState = PlaybackUtils.getPlaybackState(keycode);
    }

    /**
     * Gets the current play back state
     *
     * @return int Constant for play back state defined in @link
     * PlaybackState.java
     */
    private int getPlaybackState() {
        return mPlaybackState;
    }

    /**
     * Application Sets the current play back speed and playbackState Used for
     * DMR trick play ( app : VideoPlayerActivity)
     *
     * @param speed = playbackspeed playbackState=state
     * @deprecated Not used by any application.
     */
    public void setPlaybackSpeedAndState(int speed, int state) {

    }

    private View.OnLongClickListener mFwdLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Mediakeys : mFwdLongClickListener");
            sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
            return true;
        }
    };

    private View.OnLongClickListener mRwdLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Mediakeys : mRwdLongClickListener");
            sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
            return true;
        }
    };

    private View.OnLongClickListener mNextLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            startPhotoAnimator("picturePlayerFwd");
            //Ref : setPicturePlayerFwd(int value)
            return true;
        }
    };
    private View.OnLongClickListener mPrevLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            startPhotoAnimator("picturePlayerRwd");
            //Ref : setPicturePlayerRwd(int value)
            return true;
        }
    };

    private void startPhotoAnimator(String property) {
        cancelPhotoAninmator();
        mSlideShowAnimator.setPropertyName(property);
        mSlideShowAnimator.setIntValues(0, mPlayer.getTotalDuration());
        mSlideShowAnimator.start();
    }

    private void cancelPhotoAninmator() {
        if (mSlideShowAnimator.isStarted()) {
            mSlideShowAnimator.cancel();
        }
    }

    /**
     * Animator for photoplayback continuous next key press
     */
    private void setPicturePlayerFwd(int value) {
        Log.i(TAG, "Mediakeys : setPicturePlayerFwd animator is called");
        sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
    }

    /**
     * Animator for photoplayback continuous next key press
     */
    private void setPicturePlayerRwd(int value) {
        Log.i(TAG, "Mediakeys : setPicturePlayerRwd animator is called");
        sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);

    }

    private View.OnClickListener mNextListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "Mediakeys : mNextListener");
            sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
        }
    };

    private View.OnClickListener mPrevListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "Mediakeys : mPrevListener");
            sendPrevNextKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);

        }
    };

    private void sendPrevNextKeyEvent(int key) {
        // Log.i("play", "PlayBackControl2, sendPrevNextKeyEvent");
        if (mPlayer != null) {
            if (key == KeyEvent.KEYCODE_MEDIA_NEXT
                    || key == KeyEvent.KEYCODE_CHANNEL_UP) {
                mPlayer.onNextKeyPressed();
                getPlayBackLayout().setPrevNextFocus(false);
            } else {
                mPlayer.onPrevKeyPressed();
                getPlayBackLayout().setPrevNextFocus(true);
            }
        }
        resetPlayControls();
        showHandler();
    }

    private View.OnClickListener mFfwdListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "Mediakeys : mfwdspeed count");
            showHandler();
            if (mPlayer != null && isTrickModeEnabled) {
                onKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MEDIA_FAST_FORWARD));
            }
        }
    };
    private View.OnClickListener mRewListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "Mediakeys : mRewListener");
            showHandler();
            if (mPlayer != null && isTrickModeEnabled) {
                onKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MEDIA_REWIND));
            }
        }
    };

    private View.OnClickListener mPauseListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.d(TAG, "Mediakeys : mPauseListener");
            if (mPlayer.isPlaying()) {
                isDLNAEnabled = true;
                onKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MEDIA_PAUSE));
            } else {
                isDLNAEnabled = false;
                onKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MEDIA_PLAY));
            }
        }
    };

    /**
     * Convenience metho to set the playback pause/play mode
     */
    private void doPauseResume() {
//	    Log.i("play", "PlayBackControl2 doPauseResume");
        Log.d(TAG, "doPauseResume is called - mPlayer.isPlaying() ="
                + mPlayer.isPlaying());
        mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);

        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            setPlaybackState(KeyEvent.KEYCODE_MEDIA_PAUSE);
        } else {
            mHandler.sendEmptyMessage(PlaybackUtils.SHOW_PROGRESS);
            mPlayer.play();
            setPlaybackState(KeyEvent.KEYCODE_MEDIA_PLAY);
        }
        resetPlayControls();
    }

    private OnDismissListener onDismissListener = new OnDismissListener() {

        public void onDismiss() {
            Log.d(TAG, "OnDismissListener be called");
            mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
            if (null != mViewHandler) {
                Log.d(TAG, "OnDismissListener be called mViewHandler");
                mViewHandler.getPupupWindow().dismiss();
                //add by jianyu.ke 20170804 for hisi620
//				android 6.0之后 popwindow会消费back键
                if (mDismissListener != null) {
                    mDismissListener.onControlBarDismiss();
                }
                //add by jianyu.ke 20170804 for hisi620
            }

            if (null != mIPbcDissmissListener) {
                Log.d(TAG, "OnDismissListener be called mIPbcDissmissListener");
                mIPbcDissmissListener.onPbcDismissed();
            }
        }
    };


    private AbsPlayBackControlLayout getPlayBackLayout() {
        return (AbsPlayBackControlLayout)mViewHandler.getPupupWindow().getContentView();
    }

    private OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                // We're not interested in programmatically generated changes to the progress bar's position.
                return;
            }
            long lDuration = mPlayer.getTotalDuration();
            long lNewposition = (lDuration * progress) / PROGRASS_MAX;
            if (mState == PlaybackUtils.NOLONGLRKEYPRSD) {
                Log.d(TAG, "Called the seek To in onProgressChanged at position = " + lNewposition);
                ((ProgressBar)bar).setProgress(progress);
                mPlayer.seekTo((int)lNewposition);
            }
        }

        public void onStopTrackingTouch(SeekBar bar) {
            Log.d(TAG, "onProgressChnaged:onStopTrackingTouch");
        }
    };

    /**
     * To set the trickmode forward and rewind speed range.
     * <p>
     * Use {@link org.droidtv.ui.playback.utility.PlaybackSpeed} Default is
     * max speed defined in
     * {@link org.droidtv.ui.playback.utility.PlaybackSpeed} .
     *
     * @param fwdMax
     * @param rwdMax
     */
    public void setTrickModeRange(int fwdMax, int rwdMax) {
        mTrickModeFwdMax = fwdMax;
        mTrickModeRwdMax = rwdMax;
    }

    /**
     * To display the option key in the view. Default : invisible.
     *
     * @param status
     */
    public void showOptionKey(boolean status) {
        if (getPlayBackLayout() != null) {
            getPlayBackLayout().showOptionsKey(status);
        }
    }

    public View getOptionsKey() {
        View optionsKey = null;
        if (getPlayBackLayout() != null) {
            optionsKey = getPlayBackLayout().getOptionsKey();
        }
        return optionsKey;
    }

    public View getEndTimeView() {
        View endTimeView = null;
        if (getPlayBackLayout() != null) {
            endTimeView = getPlayBackLayout().getEndTimeView();
        }
        return endTimeView;
    }

    public View getSeekBar() {
        View seekbar = null;
        if (getPlayBackLayout() != null) {
            seekbar = getPlayBackLayout().getSeekBar();
        }
        return seekbar;
    }

    public boolean keyEventDispatcherPBC(KeyEvent event) {
        return handleEvent(event);
    }

    public boolean handleEvent(KeyEvent event) {
        if (mTimeoutFromApplication == 0) {
            // Update timer and progress - called from app.
            mTimeoutFromApplication = PlaybackUtils.DEFAULT_FADEOUT_TIME;
        }
        int lKeyCode = event.getKeyCode();
        if (slideshow) {
            cancelPhotoAninmator();
        }

        // Check for enabledTrickAndJumpMode is added to check whether
        // action on seekbar is allowed or not as In Long key press of Dpad
        // seekbar should not jump.
        int lTempKeySTate = checkLongKeyEvent(event);

        //because MST platForm when long press will send down up down up...
        //so here we do a speceil handle
        if (getPlayBackLayout().getFocusedChild() instanceof SeekBar) {
            int keyIncrement = 1;
            if (lTempKeySTate != mLastTempState) {
                mState = PlaybackUtils.LRKEYPRESSED;
                if (0 != mTotalDuration / 60000) { //Added Toby.Xu@2016-04-05 in order to avoid exception that divide by zero
                    keyIncrement = (int)(PROGRASS_MAX / (mTotalDuration / 60000));
                }
            } else {
                mState = PlaybackUtils.NOLONGLRKEYPRSD;
                if (0 != mTotalDuration / 10000) { //Added Toby.Xu@2016-04-05 in order to avoid exception that divide by zero
                    keyIncrement = (int)(PROGRASS_MAX / (mTotalDuration / 10000));
                }
            }
            getPlayBackLayout().setKeyProgressIncrement(
                    keyIncrement == 0 ? 1 : keyIncrement);
        }
        mLastTempState = lTempKeySTate;
		/*-
		if ((lTempKeySTate != PlaybackUtils.NOLONGLRKEYPRSD)
				&& mState != PlaybackUtils.NOLONGLRKEYPRSD) {
			// Handle will tc of continuous navigation.
			return true;
		}	
				
		if (event.getAction() == KeyEvent.ACTION_UP
				&& mState != PlaybackUtils.NOLONGLRKEYPRSD) {
			Log.d(TAG, "seeking the player after long key pressed"
					+ currDurAnim);
			if (!slideshow) {
				int totalDuration = mPlayer.getTotalDuration();
				if (totalDuration > 0) {
					if (currDurAnim == totalDuration){
						mPlayer.seekTo(currDurAnim - 10);
					}else{
						mPlayer.seekTo(currDurAnim);}
				}
			}
			mHandler.removeMessages(PlaybackUtils.LRKEYPRESSED);
			if (mTimeoutFromApplication != 0) {
				mHandler.removeMessages(PlaybackUtils.FADE_OUT);
			}
			mHandler.sendEmptyMessageDelayed(PlaybackUtils.FADE_OUT, 4000);
			mState = PlaybackUtils.NOLONGLRKEYPRSD;
			return true;
		}
		if (lTempKeySTate != PlaybackUtils.NOLONGLRKEYPRSD) {

			mEnableGetDuration = false;
			Log.d(TAG, "running the message again 500");
			mHandler.removeMessages(PlaybackUtils.FADE_OUT);
			currDurAnim = mPlayer.getCurrentDuration();
			mHandler.sendEmptyMessageDelayed(PlaybackUtils.LRKEYPRESSED, 500);
			mState = lTempKeySTate;
		}

		if (mState != PlaybackUtils.NOLONGLRKEYPRSD) {
			return true;

		} else if ((mState == PlaybackUtils.NOLONGLRKEYPRSD)
				&& event.getAction() == KeyEvent.ACTION_UP
				&& ((lKeyCode == KeyEvent.KEYCODE_DPAD_LEFT) || (lKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT))) {
			mHandler.removeMessages(PlaybackUtils.LRKEYPRESSED);
			mHandler.sendEmptyMessageDelayed(PlaybackUtils.FADE_OUT, 4000);
		}*/

        Log.d(TAG, "Calling onKeyEvent  " + event);
        if (checkMediaKeyStatus(event)) {
            // Consume the Meida keys and no process, based on availability constraints.
            return true;
        }
        return onKeyEvent(event);

    }

    private int checkLongKeyEvent(KeyEvent event) {
        if (getPlayBackLayout().getSeekBarFocusability()) {

            if ((event.getRepeatCount() >= 4) && isTrickModeEnabled
                    && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                    return PlaybackUtils.LONGKEYPRSDRWD;
                } else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)) {
                    return PlaybackUtils.LONGKEYPRSDFWD;
                }
            }
        }
        return PlaybackUtils.NOLONGLRKEYPRSD;
    }


    private void handlePausePlay(int keyCode) {
//	    Log.i(TAG, "PlayBackControl2 handlePausePlay,keycode:"
//	            + keyCode
//	            + ",playing:" + mPlayer.isPlaying());

        if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            mPlayer.setSpeed(true, PlaybackSpeed.SPEED_LEVEL1);
            resetPlayControls();
            if (!mPlayer.isPlaying()) {
                //resetPlayControls();
                isDLNAEnabled = false;
                doPauseResume();
            } else {
                isDLNAEnabled = true;
                return;
            }
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (mPlayer.isPlaying()) {
                resetPlayControls();
                mPlayer.setSpeed(true, PlaybackSpeed.SPEED_LEVEL1);
                isDLNAEnabled = true;
                doPauseResume();
            } else {
                isDLNAEnabled = false;
                return;
            }

//			isDLNAEnabled = true;
        } else {
            resetPlayControls();
            doPauseResume();
        }
        showHandler();
        getPlayBackLayout().setPlayPauseFocus();

    }

    private boolean onKeyEvent(KeyEvent event) {

        Log.i("play", "PlayBackControl2 onKeyEvent,keycode:"
                + event.getKeyCode());

        boolean retValue = false;
        int lKeyCode = event.getKeyCode();
        final boolean lUniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;

        if (lKeyCode != KeyEvent.KEYCODE_MEDIA_STOP
                && lKeyCode != KeyEvent.KEYCODE_BACK) {
            showHandler();
        }
        if (lUniqueDown) {
            if (mHandler.hasMessages(PlaybackUtils.LRKEYPRESSED)) {
                mState = PlaybackUtils.NOLONGLRKEYPRSD;
                mHandler.removeMessages(PlaybackUtils.LRKEYPRESSED);
            }
        }

        switch (lKeyCode) {
            case KeyEvent.KEYCODE_MENU: {
                mPlayer.openMenu();
                break;
            }
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    handlePausePlay(lKeyCode);
                }
                retValue = true;
                break;

            case KeyEvent.KEYCODE_MEDIA_STOP:
                if (lUniqueDown) {
                    if (mPlayer != null) {
                        Log.d(TAG, " STOP callback to application ");
                        resetPlayControls();
                        mPlayer.stop();
                    }
                }
                retValue = true;
                break;

            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                //Log.i("play", "onkeyevent,KEYCODE_MEDIA_FAST_FORWARD");
                if (lUniqueDown && !slideshow) {
                    processFFKey();
                    getPlayBackLayout().setRwdFwdFocus(false);
                }
                retValue = true;
                break;

            case KeyEvent.KEYCODE_MEDIA_REWIND:
                if (lUniqueDown && !slideshow) {
                    processRWKey();
                    getPlayBackLayout().setRwdFwdFocus(true);
                }
                retValue = true;
                break;

            case KeyEvent.KEYCODE_CHANNEL_DOWN:
            case KeyEvent.KEYCODE_CHANNEL_UP:
            case KeyEvent.KEYCODE_MEDIA_NEXT:
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                if (lUniqueDown) {
                    sendPrevNextKeyEvent(lKeyCode);
                }
                retValue = true;
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (lUniqueDown && !visiblefwdrwd && !visibleNext && !visiblePrev) {
                    retValue = handleSeekLeftRight(lKeyCode);
                }
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                return false;
            case KeyEvent.KEYCODE_BACK:
                // 瑙ｅ喅sourcelist鐣岄潰鎸塨ack瀵艰嚧music涔熼�鍑虹殑闂(back鐨剈p浜嬩欢琚崟鑾�
                if (lUniqueDown) {
                    doHide();
                }
                retValue = false;
            default:
                break;
        }

        Log.d(TAG, "Calling HandleEvent " + event);
        if (retValue) {
            return retValue;
        }

        // The new API for call for unhandled keys
        if (mNewKeyRegisterListener != null) {
            retValue = mNewKeyRegisterListener.unHandledKeyEvents(event);
            Log.d(TAG, "The rest unhandled key" + event
                    + " retvlaue from app : " + retValue);
        }
        mState = PlaybackUtils.NOLONGLRKEYPRSD;
        return retValue;
    }

    private void processRWKey() {
        //Log.i("play", "PlayBackControl2 processRWKey");
        if (getPlaybackState() != PlaybackState.REWIND) {
            resetPlayControls();
            setPlaybackSpeed(PlaybackSpeed.DEFAULT);
            if (isDLNAEnabled) {
                setPlaybackSpeed(PlaybackSpeed.DEFAULT);
            } else {
                setPlaybackSpeed(PlaybackSpeed.SPEED_LEVEL3_NORMAL);
            }
        }
        setPlaybackState(KeyEvent.KEYCODE_MEDIA_REWIND);
        int nextRwdSpeed = getNextPlaybackSpeed(getPlaybackSpeed());
        mPlayer.setSpeed(false, nextRwdSpeed);
        getPlayBackLayout().updateFwdRwdStatus(false, nextRwdSpeed);
        //Log.i("play", "PlayBackControl2 processRWKey end,nextRwdSpeed:" + nextRwdSpeed);
    }

    private void processFFKey() {
//	    Log.i("play", "PlayBackControl2, processFFKey,getPlaybackState():"
//	            + getPlaybackState());
        if (getPlaybackState() != PlaybackState.FORWARD) {
            resetPlayControls();
            if (isDLNAEnabled) {
                setPlaybackSpeed(PlaybackSpeed.DEFAULT);
            } else {
                setPlaybackSpeed(PlaybackSpeed.SPEED_LEVEL3_NORMAL);
            }
        }
        setPlaybackState(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD);
        int nextFwdSpeed = getNextPlaybackSpeed(getPlaybackSpeed());
        mPlayer.setSpeed(true, nextFwdSpeed);
        getPlayBackLayout().updateFwdRwdStatus(true, nextFwdSpeed);
//		Log.i("play", "PlayBackControl2, processFFKey, nextFwdSpeed:" + nextFwdSpeed);
    }

    private boolean checkMediaKeyStatus(KeyEvent event) {
        int lKeyCode = event.getKeyCode();
        if (!isTrickModeEnabled) {
            Log.d(TAG, " TrickPlayAndJumpMode is enabled ?  = "
                    + isTrickModeEnabled);
            switch (lKeyCode) {
                case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                case KeyEvent.KEYCODE_MEDIA_REWIND:
                    Log.d(TAG,
                            "Absorb this key as TrickPlayAndJumpMode is disabled "
                                    + event);
                    return true;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (getPlayBackLayout().getSeekBarFocusability()) {
                        Log.d(TAG,
                                "Absorb this key as TrickPlayAndJumpMode is disabled "
                                        + event);
                        return true;
                    } else {
                        return false;
                    }
            }
        }
        if (!enableNext && lKeyCode == KeyEvent.KEYCODE_MEDIA_NEXT) {
            Log.d(TAG, " Next is enabled ?  = " + enableNext
                    + " Absorb this key as Next is disabled");
            return true;
        }
        if (!enablePrev && lKeyCode == KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
            Log.d(TAG, " Prev is enabled ?  = " + enableNext
                    + " Absorb this key as Prev is disabled");
            return true;
        }
        if (!enablePausePlay) {
            Log.d(TAG, " PausePlay is enabled ?  = " + enablePausePlay);
            switch (lKeyCode) {
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    Log.d(TAG, "Absorb this key as PausePlay is disabled " + event);
                    return true;
            }
        }
        return false;
    }

    private void showHandler() {
        if (mTimeoutFromApplication != 0) {
            doShow(mTimeoutFromApplication/*PlaybackUtils.DEFAULT_FADEOUT_TIME*/);
        } else {
            doShow(0);
        }
    }

    /**
     * To get the next playback speed .
     *
     * @param currentSpeed
     * @return
     */
    private int getNextPlaybackSpeed(int currentSpeed) {
        int lNextSpeed = currentSpeed;
        Log.d("PlayBackControl",
                "SlowPlayControl => getNextPlaybackSpeed - currentSpeed="
                        + currentSpeed);

        if (isSlowPlayEnabled
                && currentSpeed < PlaybackSpeed.SPEED_LEVEL3_NORMAL) {
            // Slow play jump
            lNextSpeed = PlaybackUtils.getNextSlowPlaySpeed(currentSpeed);
            if (lNextSpeed == PlaybackSpeed.SPEED_LEVEL3_NORMAL) {
                isDLNAEnabled = false;
            }
        } else {
            isDLNAEnabled = false;
            lNextSpeed = PlaybackUtils.getNextNormalPlaySpeed(currentSpeed);
            if (lNextSpeed == PlaybackSpeed.SPEED_LEVEL3_NORMAL) {
                isDLNAEnabled = false;
            }
        }
        if (getPlaybackState() == PlaybackState.FORWARD
                && lNextSpeed > mTrickModeFwdMax) {
            lNextSpeed = PlaybackSpeed.DEFAULT;
        } else {
            if (lNextSpeed > mTrickModeRwdMax) {
                lNextSpeed = PlaybackSpeed.DEFAULT;
            }
        }
        setPlaybackSpeed(lNextSpeed);
        return lNextSpeed;
    }

    private boolean handleSeekLeftRight(int keyCode) {
//	    Log.i("play", "PlayBackControl2 handleSeekLeftRight");
        if (!getPlayBackLayout().getSeekBarFocusability()) {
            return false;
        }
        if (getPlaybackState() != PlaybackState.PLAY) {
            mPlayer.play();
            resetPlayControls();
            setPlaybackState(PlaybackState.PLAY);
        }
        mHandler.removeMessages(PlaybackUtils.SHOW_PROGRESS);
        mEnableGetDuration = false;
        if (currDurAnim == 0) {
            currDurAnim = mPlayer.getCurrentDuration();
        }
        if (KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
            currDurAnim -= 10000;
        } else {
            currDurAnim += 10000;
        }
        if (currDurAnim <= 0) {
            currDurAnim = 0;
        }
        Log.d(TAG, "currDurAnim" + currDurAnim);
		/*mHandler.removeMessages(PlaybackUtils.FADE_OUT);
		mHandler.sendEmptyMessageDelayed(PlaybackUtils.FADE_OUT, 4000);*/
        mPlayer.seekTo(currDurAnim);
        return true;
    }


    //add for preview play start
    public void setWindowFocusable(boolean focusable) {
        mViewHandler.getPupupWindow().setFocusable(focusable);
    }

    public void setTitleText(String text) {
        getPlayBackLayout().setTitleText(text);
    }

    public void setSubTitleText(String text) {
        getPlayBackLayout().setSubTitleText(text);
    }

    public void setBackgroundColor(int color) {
        getPlayBackLayout().setBackgroundColor(color);
    }

    public void setScaleType(ScaleType scaleType) {
        getPlayBackLayout().setScaleType(scaleType);
    }

    public void setMajorImg(Bitmap bitmap) {
        getPlayBackLayout().setMajorImg(bitmap);
    }

    public void setShadowImg(Bitmap bitmap) {
        getPlayBackLayout().setShadowImg(bitmap);
    }

    public void setMinorImg(Bitmap bitmap) {
        getPlayBackLayout().setMinorImg(bitmap);
    }

    public ImageView getMajorImagView() {
        return getPlayBackLayout().getMajorImageView();
    }

    public void setMajorImg(int resId) {
        getPlayBackLayout().setMajorImg(resId);
    }

    public ImageView getShadowImageView() {
        return getPlayBackLayout().getShadowImageView();
    }

    public void setShadowImg(int resId) {
        getPlayBackLayout().setShadowImg(resId);
    }

    public void setMinorImg(int resId) {
        getPlayBackLayout().setMinorImg(resId);
    }

    public View getLayout() {
        return getPlayBackLayout();
    }

    public void startPreviewAnimate() {
        View view = getPlayBackLayout().findViewById(R.id.preview_progress);
        getPlayBackLayout().setProgress(0);
        mProgressSlidingBak.setTarget(view);
        mProgressSlidingBak.start();

        mProgressSliding.setTarget(view);
        mProgressSliding.start();
        view.setVisibility(View.VISIBLE);

//        view = getPlayBackLayout().findViewById(R.id.endtime);
//        view.setVisibility(View.VISIBLE);
        view = getPlayBackLayout().findViewById(R.id.current_time);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Remove the controller from the screen.
     */
    public void hidePreview() {
        Log.d(TAG, " hidePreview() is called from application ");
        doHide();
        View view = getPlayBackLayout().findViewById(R.id.preview_progress);
        view.setVisibility(View.INVISIBLE);
        view = getPlayBackLayout().findViewById(R.id.endtime);
        view.setVisibility(View.INVISIBLE);
        view = getPlayBackLayout().findViewById(R.id.current_time);
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * Remove the controller Progress from the screen.
     */
    public void hidePreviewProgress() {
        Log.d(TAG, " hidePreviewProgress() is called from application ");

        View view = getPlayBackLayout().findViewById(R.id.preview_progress);
        view.setVisibility(View.INVISIBLE);
        view = getPlayBackLayout().findViewById(R.id.endtime);
        view.setVisibility(View.INVISIBLE);
        view = getPlayBackLayout().findViewById(R.id.current_time);
        view.setVisibility(View.INVISIBLE);
    }

    public void setPreviewEndTime(String endTime) {
        AbsPlayBackControlLayout absPlayBackControlLayout = getPlayBackLayout();
        if (absPlayBackControlLayout instanceof PerviewPlayLayout) {
            ((PerviewPlayLayout)absPlayBackControlLayout).setPreviewEndTime(endTime);
        }
    }
}
