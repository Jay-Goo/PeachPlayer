package jaygoo.peachplayerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

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
        Log.i("fuck", "onGestureLeftSlideUp: "+super.onGestureLeftSlideUp(distance, percent));
        return 0;
    }

    @Override
    protected float onGestureLeftSlideDown(float distance, float percent) {

        Log.i("fuck", "onGestureLeftSlideDown: "+super.onGestureLeftSlideDown(distance, percent));
        return 0;
    }

}
