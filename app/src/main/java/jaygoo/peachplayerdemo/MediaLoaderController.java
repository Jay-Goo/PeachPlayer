package jaygoo.peachplayerdemo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import jaygoo.peachplayer.media.PeachVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/11
 * 描    述: 网络视频加载控制器
 * ================================================
 */
public class MediaLoaderController {

    String TAG = "fuck";
    private PeachVideoView mVideoView;
    private LottieAnimationView loadingAnimationView;
    private ViewGroup rootLoadingView;

    public MediaLoaderController(PeachVideoView videoView, ViewGroup view){
        mVideoView = videoView;
        rootLoadingView = view;
        loadingAnimationView = (LottieAnimationView)view.findViewById(R.id.loadingAnimationView);

    }

    public void registerListeners(){
        mVideoView.setOnErrorListener(mErrorListener);
        mVideoView.setOnInfoListener(mInfoListener);
        mVideoView.setOnCompletionListener(mCompletionListener);
        mVideoView.setOnPreparedListener(mPreparedListener);
    }

    public void removeListeners(){
        mVideoView.setOnErrorListener(null);
        mVideoView.setOnInfoListener(null);
        mVideoView.setOnCompletionListener(null);
        mVideoView.setOnPreparedListener(null);
    }

    private IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {

        }
    };

    private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {

        }
    };

    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:

                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    //准备音频
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //开始缓存
                    rootLoadingView.setVisibility(View.VISIBLE);
                    loadingAnimationView.playAnimation();
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    //缓存结束
                    loadingAnimationView.cancelAnimation();
                    rootLoadingView.setVisibility(View.GONE);
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i);
                    break;
                case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i1);

                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    break;
            }
            return false;
        }
    };


    private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {

            return true;
        }
    };
}
