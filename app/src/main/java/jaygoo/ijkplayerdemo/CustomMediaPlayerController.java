package jaygoo.ijkplayerdemo;

import android.content.Context;
import android.util.AttributeSet;

import jaygoo.superijkplayer.media.AndroidMediaController;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/6
 * 描    述:
 * ================================================
 */
public class CustomMediaPlayerController extends AndroidMediaController {
    public CustomMediaPlayerController(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomMediaPlayerController(Context context) {
        super(context);
    }
}
