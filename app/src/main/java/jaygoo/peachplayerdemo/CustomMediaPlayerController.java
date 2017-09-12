package jaygoo.peachplayerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import jaygoo.peachplayer.media.AndroidMediaController;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/6
 * 描    述: 自定义视频控制面板，支持自定义UI
 * ================================================
 */
public class CustomMediaPlayerController extends AndroidMediaController {
    public CustomMediaPlayerController(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomMediaPlayerController(Context context) {
        super(context);
    }

    @Override
    protected float onGestureLeftSlideUp(float distance, float percent) {
        super.onGestureLeftSlideUp(distance, percent);
        return 0;
    }

    @Override
    protected float onGestureLeftSlideDown(float distance, float percent) {
        super.onGestureLeftSlideDown(distance, percent);
        return 0;
    }

    @Override
    protected void onClickOptionPause(MediaPlayerControl mPlayer) {
        if (mPauseButton == null || mPlayer == null)
            return;
        if (mPauseButton instanceof ImageView){
            ImageView pauseButton = (ImageView) mPauseButton;
            if (mPlayer.isPlaying()) {
                pauseButton.setImageResource(R.drawable.ic_video_pause);
            } else {
                pauseButton.setImageResource(R.drawable.ic_video_play);
            }
        }
    }
}
