package jaygoo.peachplayer.media;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jaygoo.peachplayer.R;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/7
 * 描    述: 播放器全屏，小屏切换控制器
 * ================================================
 */
public class ScreenChangeController {

    private FrameLayout fullScreenLayout;
    private ViewGroup videoScreenLayout;
    private PeachVideoView videoView;
    private AndroidMediaController mediaController;
    private List<View> childViews = new ArrayList<>();

    public ScreenChangeController(ViewGroup videoScreen, FrameLayout customFullScreenLayout){
        if (videoScreen == null)throw new IllegalStateException("video screen is null");
        videoScreenLayout = videoScreen;
        Context context = videoScreen.getContext();

        ViewGroup parent = (ViewGroup)videoScreen.getParent();
        if (parent != null){
            if (customFullScreenLayout == null) {
                fullScreenLayout = new FrameLayout(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                fullScreenLayout.setLayoutParams(params);
                fullScreenLayout.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.color_video_screen_background));
            }else {
                fullScreenLayout = customFullScreenLayout;
            }
            fullScreenLayout.setVisibility(View.GONE);
            parent.addView(fullScreenLayout);

            //将toolbar重新放置到fullScreenLayout顶部
            Toolbar toolbar = null;
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) instanceof Toolbar){
                    toolbar = (Toolbar) parent.getChildAt(i);
                    parent.removeView(toolbar);
                    break;
                }
            }
            if (toolbar != null){
                parent.addView(toolbar);
            }
        }
        for (int i = 0; i < videoScreen.getChildCount(); i++){
            if (videoScreen.getChildAt(i) instanceof AndroidMediaController){
                mediaController = (AndroidMediaController)videoScreen.getChildAt(i);
            }else if (videoScreen.getChildAt(i) instanceof PeachVideoView){
                videoView = (PeachVideoView)videoScreen.getChildAt(i);
            }else {
                childViews.add(videoScreen.getChildAt(i));
            }
        }
    }

    /**
     * player full screen, portrait screen switch
     * <p>
     *     call it in your activity 、fragment 's onConfigurationChanged
     * </p>
     *
     * @param newConfig
     * @return true is full screen , false is portrait screen.
     */

    public boolean onConfigurationChanged(Configuration newConfig) {

        if (mediaController != null && videoScreenLayout != null && videoView != null && fullScreenLayout != null) {
            mediaController.onConfigurationChanged(newConfig);
            // 切换为小屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreenLayout.setVisibility(View.GONE);
                fullScreenLayout.removeAllViews();

                videoScreenLayout.removeAllViews();
                videoScreenLayout.addView(videoView);
                videoScreenLayout.addView(mediaController);
                for (int i = 0; i < childViews.size(); i++) {
                    videoScreenLayout.addView(childViews.get(i));
                }
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullScreenLayout.setSystemUiVisibility(mShowFlags);
                return false;
            } else {
                //切换为全屏
                ViewGroup viewGroup = (ViewGroup) mediaController.getParent();
                if (viewGroup == null)
                    return false;
                viewGroup.removeAllViews();
                fullScreenLayout.addView(videoView);
                fullScreenLayout.addView(mediaController);
                for (int i = 0; i < childViews.size(); i++) {
                    videoScreenLayout.addView(childViews.get(i));
                }
                fullScreenLayout.setVisibility(View.VISIBLE);
                int mHideFlags =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                fullScreenLayout.setSystemUiVisibility(mHideFlags);
                return true;
            }
        }
        return false;
    }

}
