package jaygoo.peachplayerdemo;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import jaygoo.peachplayer.media.PeachVideoView;
import jaygoo.peachplayer.media.ScreenChangeController;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fullScreen;
    private CustomMediaPlayerController mMediaController;
    private PeachVideoView mVideoView;
    private ScreenChangeController mScreenChangeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaController = (CustomMediaPlayerController) findViewById(R.id.mediaController);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (PeachVideoView) findViewById(R.id.ijkVideoView);

        mVideoView.setMediaController(mMediaController);
//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Video/demo1.mp4");

//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/Movies/Screenrecords/demo.mp4");
        mVideoView.setVideoURI(Uri.parse("http://media6.smartstudy.com/f4/10/3993/2/dest.m3u8"));
//        mVideoView.setHudView(mHudView);
//        fullScreen = (FrameLayout) findViewById(R.id.full_screen);
        mScreenChangeController = new ScreenChangeController((FrameLayout)findViewById(R.id.video_screen),null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mScreenChangeController != null){
            mScreenChangeController.onConfigurationChanged(newConfig);
        }
    }
}
