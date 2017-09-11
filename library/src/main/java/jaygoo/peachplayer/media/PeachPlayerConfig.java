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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import jaygoo.peachplayer.R;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/9
 * 描    述: 播放器全局配置
 * ================================================
 */
public class PeachPlayerConfig {
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;

    public static final int PV_PLAYER__Auto = 0;
    public static final int PV_PLAYER__AndroidMediaPlayer = 1;
    public static final int PV_PLAYER__IjkMediaPlayer = 2;
    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;

    public PeachPlayerConfig(Context context) {
        mAppContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public PeachPlayerConfig setEnableAutoScreenRotation(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_auto_screen_rotation);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableBackgroundPlay(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_background_play);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableUsingMediaCodec(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_using_media_codec);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableUsingMediaCodecAutoRotate(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableMediaCodecHandleResolutionChange(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    /**
     * 是否使用OpenSLES
     * OpenSLES可以提高音频数据处理效率
     * 但是不支持MIDI、不支持播放DRM和加密内容、不支持音频数据的编解码
     * @return
     */
    public PeachPlayerConfig setEnableUsingOpenSLES(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_using_opensl_es);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableNoView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_no_view);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableSurfaceView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableTextureView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableDetachedSurfaceTextureView(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_enable_detached_surface_texture);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setEnableMediaDataSource(boolean enable){
        String key = mAppContext.getString(R.string.pref_key_using_mediadatasource);
        mSharedPreferences.edit().putBoolean(key, enable).apply();
        return this;
    }

    public PeachPlayerConfig setLastDirectory(String path) {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        mSharedPreferences.edit().putString(key, path).apply();
        return this;
    }


    public boolean getEnableBackgroundPlay() {
        String key = mAppContext.getString(R.string.pref_key_enable_background_play);
        return mSharedPreferences.getBoolean(key, false);
    }

    public int getPlayer() {
        String key = mAppContext.getString(R.string.pref_key_player);
        String value = mSharedPreferences.getString(key, "");
        try {
            return Integer.valueOf(value).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 视频硬解码
     * @return
     */
    public boolean getUsingMediaCodec() {
        String key = mAppContext.getString(R.string.pref_key_using_media_codec);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 使用MediaCodec信息自动旋转
     * @return
     */
    public boolean getUsingMediaCodecAutoRotate() {
        String key = mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate);
        return mSharedPreferences.getBoolean(key, true);
    }

    /**
     * 媒体编码器处理分辨率变化
     * @return
     */
    public boolean getMediaCodecHandleResolutionChange() {
        String key = mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * link{ setEnableUsingOpenSLES(boolean enable)}
     * @return
     */
    public boolean getUsingOpenSLES() {
        String key = mAppContext.getString(R.string.pref_key_using_opensl_es);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 设置像素格式
     * @return
     */
    public String getPixelFormat() {
        String key = mAppContext.getString(R.string.pref_key_pixel_format);
        return mSharedPreferences.getString(key, "");
    }

    public boolean getEnableNoView() {
        String key = mAppContext.getString(R.string.pref_key_enable_no_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getEnableSurfaceView() {
        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 旋转需要支持TextureView
     * @return
     */
    public boolean getEnableTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
        return mSharedPreferences.getBoolean(key, true);
    }

    public boolean getEnableDetachedSurfaceTextureView() {
        String key = mAppContext.getString(R.string.pref_key_enable_detached_surface_texture);
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getUsingMediaDataSource() {
        String key = mAppContext.getString(R.string.pref_key_using_mediadatasource);
        return mSharedPreferences.getBoolean(key, true);
    }

    public String getLastDirectory() {
        String key = mAppContext.getString(R.string.pref_key_last_directory);
        return mSharedPreferences.getString(key, "/");
    }

    public boolean getAutoScreenRotationEnable(){
        String key = mAppContext.getString(R.string.pref_key_enable_auto_screen_rotation);
        return mSharedPreferences.getBoolean(key, true);
    }




}
