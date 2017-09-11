package jaygoo.peachplayerdemo;

import android.content.Context;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

import jaygoo.peachplayer.media.MediaLoaderView;


/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/11
 * 描    述:
 * ================================================
 */
public class CustomMediaLoaderView extends MediaLoaderView {

    private LottieAnimationView loadingAnimationView;

    public CustomMediaLoaderView(Context context) {
        super(context);
    }

    public CustomMediaLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        loadingAnimationView = (LottieAnimationView)findViewById(R.id.loadingAnimationView);
        setVisibility(GONE);
    }

    @Override
    public void startBuffering() {
        setVisibility(VISIBLE);
        loadingAnimationView.playAnimation();
    }

    @Override
    public void stopBuffering() {
        setVisibility(GONE);
        loadingAnimationView.cancelAnimation();
    }

    @Override
    public void bufferTimeOut(int times) {

    }

    @Override
    public void loadError() {

    }


}
