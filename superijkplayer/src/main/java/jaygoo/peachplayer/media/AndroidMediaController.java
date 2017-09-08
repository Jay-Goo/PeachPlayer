/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jaygoo.peachplayer.media;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

import jaygoo.peachplayer.WeakHandler;
import jaygoo.peachplayer.utils.DeviceUtils;

public class AndroidMediaController extends BaseMediaController implements IMediaController {
    private ActionBar mActionBar;
    private Activity activity;
    private boolean portrait;
    private WeakHandler handler = new WeakHandler();


    public AndroidMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public AndroidMediaController(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        activity = (Activity)context;
        if (mFullScreenButton != null){
            mFullScreenButton.setOnClickListener(mFullScreenListener);
        }
        portrait = DeviceUtils.getScreenOrientation(activity) == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private OnClickListener mFullScreenListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleFullScreen();
        }
    };

    /**
     * 监听全屏跟非全屏
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        portrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
        doOnConfigurationChanged(portrait);
    }


    /**
     * 用户主动点击大小屏的切换
     * 设置播放视频的是否是全屏
     */
    public void toggleFullScreen() {
        /***
         * 就算只用户主动切换大小，也是去是activity转向来实现的
         */
        if (DeviceUtils.getScreenOrientation(activity) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {// 转小屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {// 转全屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        updateFullScreenButton();
    }

//    /**
//     * 监听手机重力感应的切换屏幕的方向
//     */
//    private OrientationEventListener orientationEventListener = new OrientationEventListener(activity) {
//        @Override
//        public void onOrientationChanged(int orientation) {
//            if (orientation >= 0 && orientation <= 30 || orientation >= 330
//                    || (orientation >= 150 && orientation <= 210)) {
//                // 竖屏
//                if (portrait) {
//                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//                    orientationEventListener.disable();
//                }
//            } else if ((orientation >= 90 && orientation <= 120)
//                    || (orientation >= 240 && orientation <= 300)) {
//                if (!portrait) {
//                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//                    orientationEventListener.disable();
//                }
//            }
//        }
//    };

    /**
     * 当竖横屏切换时处理视频窗口
     * @param portrait
     */
    private void doOnConfigurationChanged(final boolean portrait) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    setFullScreen(!portrait);
                    if (portrait) {
                        int screenWidth = DeviceUtils.deviceWidth(activity);
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        layoutParams.width = screenWidth;
                        layoutParams.height = screenWidth * 9 / 16;
                        setLayoutParams(layoutParams);
                        requestLayout();
                    } else {
                        int heightPixels = activity.getResources().getDisplayMetrics().heightPixels;
                        int widthPixels = activity.getResources().getDisplayMetrics().widthPixels;
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.height = heightPixels;
                        layoutParams.width = widthPixels;
                        setLayoutParams(layoutParams);
                        hide();
                    }
                    updateFullScreenButton();
                }
            });
//            if (orientationEventListener != null) orientationEventListener.enable();

    }

    /**
     * 更新全屏按钮面板
     */
    protected void updateFullScreenButton() {

    }


    /**
     * 主动使窗口横竖屏切换
     * @param fullScreen
     */
    private void setFullScreen(boolean fullScreen) {
        if (activity != null) {
            WindowManager.LayoutParams attrs = activity.getWindow()
                    .getAttributes();
            if (fullScreen) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                activity.getWindow().setAttributes(attrs);
                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setAttributes(attrs);
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }

    }


    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
        if (isShowing()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override
    public void show() {
        super.show();
        if (mActionBar != null)
            mActionBar.show();

    }

    @Override
    public void hide() {
        super.hide();
        if (mActionBar != null)
            mActionBar.hide();
        for (View view : mShowOnceArray)
            view.setVisibility(View.GONE);
        mShowOnceArray.clear();
    }

    @Override
    public void setAnchorView(View view) {

    }


    //----------
    // Extends
    //----------
    private ArrayList<View> mShowOnceArray = new ArrayList<View>();

    public void showOnce(@NonNull View view) {
        mShowOnceArray.add(view);
        view.setVisibility(View.VISIBLE);
        show();
    }
}
