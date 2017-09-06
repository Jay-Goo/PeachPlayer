package jaygoo.ijkplayerdemo;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import jaygoo.superijkplayer.media.AndroidMediaController;
import jaygoo.superijkplayer.media.IjkVideoView;
import jaygoo.superijkplayer.media.SuperMediaController;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomMediaPlayerController mMediaController = (CustomMediaPlayerController) findViewById(R.id.mediaController);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        final IjkVideoView mVideoView = (IjkVideoView) findViewById(R.id.ijkVideoView);
        TableLayout mHudView = (TableLayout) findViewById(R.id.hud_view);

        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Video/demo2.mp4");

//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/Movies/Screenrecords/demo.mp4");
//        mVideoView.setVideoURI(Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"));
//        mVideoView.setHudView(mHudView);

        findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.setSpeed(2f);

            }
        });
    }
}
