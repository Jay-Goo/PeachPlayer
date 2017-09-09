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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
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
    private GestureDetector mGestureDetector;
    private WeakHandler handler = new WeakHandler();
    private boolean isGestureDetectorEnable = true;
    private OrientationEventListener orientationEventListener;
    private PeachPlayerConfig mConfig;

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
        mGestureDetector = new GestureDetector(getContext(),onGestureListener);
        setOrientationChangeListener();
        mConfig = new PeachPlayerConfig(context);
    }

    private OnClickListener mFullScreenListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleFullScreen();
        }
    };


    private VideoGestureListener onGestureListener = new VideoGestureListener(
            DeviceUtils.deviceWidth(getContext()), DeviceUtils.deviceHeight(getContext()) ){

        @Override
        public void leftSlideUp(float distance, float percent) {
            onGestureLeftSlideUp(distance, percent);
        }

        @Override
        public void leftSlideDown(float distance, float percent) {
            onGestureLeftSlideDown(distance, percent);
        }

        @Override
        public void rightSlideUp(float distance, float percent) {
            onGestureRightSlideUp(distance, percent);
        }

        @Override
        public void rightSlideDown(float distance, float percent) {
            onGestureRightSlideDown(distance, percent);
        }

        @Override
        public void left2RightSlideDown(float distance, float percent) {
            onGestureLeft2RightSlideDown(distance, percent);
        }

        @Override
        public void right2LeftSlideDown(float distance, float percent) {
            onGestureRight2LeftSlideDown(distance, percent);
        }

        @Override
        public void left2RightSlideUp(float distance, float percent) {
            onGestureLeft2RightSlideUp(distance, percent);
        }

        @Override
        public void right2LeftSlideUp(float distance, float percent) {
            onGestureRight2LeftSlideUp(distance, percent);
        }

        @Override
        public void doubleTap() {
            onGestureDoubleTap();
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
        if (onGestureListener != null){
            onGestureListener.onConfigurationChanged(DeviceUtils.deviceWidth(getContext()),
                    DeviceUtils.deviceHeight(getContext()));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPlayer != null && mPlayer.isPlaying() && isGestureDetectorEnable) {
            mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
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

    /**
     * 监听手机重力感应的切换屏幕的方向
     */
    private void setOrientationChangeListener(){
        if (!mConfig.getAutoScreenRotationEnable())return;
        orientationEventListener = new OrientationEventListener(activity) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (!mConfig.getAutoScreenRotationEnable())return;
                if (orientation >= 0 && orientation <= 30 || orientation >= 330
                        || (orientation >= 150 && orientation <= 210)) {
                    // 竖屏
                    if (portrait) {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        orientationEventListener.disable();
                    }
                } else if ((orientation >= 90 && orientation <= 120)
                        || (orientation >= 240 && orientation <= 300)) {
                    if (!portrait) {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        orientationEventListener.disable();
                    }
                }
            }
        };
    }


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
                if (orientationEventListener != null) orientationEventListener.enable();

            }
        });

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
    //========================= Override  Methods =====================//
    // you can extend your UI methods in your PeachPlayer

    /**
     * 更新全屏按钮面板
     */
    protected void updateFullScreenButton() {}

    /************************  手势操作  *************************/


    /**
     * 左侧上划
     * @param distance
     * @param percent
     * @return Upward adjustment of brightness
     * 向上调整后的亮度
     */
    protected float onGestureLeftSlideUp(float distance, float percent){
        return DeviceUtils.adjustBrightness(activity, percent);
    }

    /**
     * 左侧下划
     * @param distance
     * @param percent
     * @return Down adjustment of brightness
     * 向下调整后的亮度
     */
    protected float onGestureLeftSlideDown(float distance, float percent){
        return DeviceUtils.adjustBrightness(activity, -percent);
    }


    /**
     * 右侧上划
     * @param distance
     * @param percent
     * @return Upward adjustment of volume
     * 向上调整后的音量
     */
    protected float onGestureRightSlideUp(float distance, float percent){
        return DeviceUtils.adjustVolume(getContext(), 0.5f * percent);
    }

    /**
     * 右侧下划
     * @param distance
     * @param percent
     * @return Down adjustment of volume
     *  向下调整后的音量
     *
     */
    protected float onGestureRightSlideDown(float distance, float percent){
        return DeviceUtils.adjustVolume(getContext(), - 0.5f * percent);
    }

    //从左向右划
    protected void onGestureLeft2RightSlideDown(float distance, float percent){}

    //从右向左划
    protected void onGestureRight2LeftSlideDown(float distance, float percent){}

    /**
     * 从左向右划
     * @param distance
     * @param percent
     * @return now forward seek percent in whole media
     * 快进后的当前进度
     */
    protected float onGestureLeft2RightSlideUp(float distance, float percent){
        int seekDuration = (int) (percent / 0.1 * 5000);
        int newPosition = mPlayer.getCurrentPosition() + seekDuration;
        if (newPosition > mPlayer.getDuration())newPosition = mPlayer.getDuration();
        mPlayer.seekTo(newPosition);
        return 1.0f * newPosition / mPlayer.getDuration();
    }

    /**
     * 从右向左划
     * @param distance
     * @param percent
     * @return now rewind seek percent in whole media
     * 快退后的当前进度
     */
    protected float onGestureRight2LeftSlideUp(float distance, float percent){
        int seekDuration = (int) (percent / 0.1 * 5000);
        int newPosition = mPlayer.getCurrentPosition() - seekDuration;
        if (newPosition < 0)newPosition = 0;
        mPlayer.seekTo(newPosition);
        return 1.0f * newPosition / mPlayer.getDuration();
    }

    //双击
    protected void onGestureDoubleTap(){}

    /************************  手势操作  *************************/



    /**
     * is support GestureDetector, default is support
     * @param gestureDetectorEnable
     */
    public void setGestureDetectorEnable(boolean gestureDetectorEnable) {
        isGestureDetectorEnable = gestureDetectorEnable;
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
