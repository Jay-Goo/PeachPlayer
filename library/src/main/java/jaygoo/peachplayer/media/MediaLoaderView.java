package jaygoo.peachplayer.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/11
 * 描    述:
 * ================================================
 */
public abstract class MediaLoaderView extends RelativeLayout{
    public MediaLoaderView(Context context) {
        super(context);
    }

    public MediaLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //初始化
    public abstract void init();

    //开始缓冲
    public abstract void startBuffering();

    //结束缓存
    public abstract void stopBuffering();

    //缓冲超时
    public abstract void bufferTimeOut(int times);

    //加载出错
    public abstract void loadError();
}
