package jaygoo.peachplayerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

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
    private TextView hintTextView;

    public CustomMediaLoaderView(Context context) {
        super(context);
    }

    public CustomMediaLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        loadingAnimationView = (LottieAnimationView)findViewById(R.id.loadingAnimationView);
        hintTextView = (TextView)findViewById(R.id.hintTextView);
        setVisibility(GONE);
    }

    @Override
    public void prepared() {
        hintTextView.setText("");
    }

    @Override
    public void startBuffering(long realSpeed, String formatedSpeed) {
        setVisibility(VISIBLE);
        hintTextView.setText(formatedSpeed);
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
