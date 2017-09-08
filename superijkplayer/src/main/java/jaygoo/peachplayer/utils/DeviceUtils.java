package jaygoo.peachplayer.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;


import java.io.File;
import java.util.Iterator;
import java.util.List;

import jaygoo.peachplayer.R;

/**
 * 获取设备的一些信息
 */
public class DeviceUtils {

    /**
     * dp 转化为 px
     *
     * @param context context
     * @param dpValue dpValue
     * @return int
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px 转化为 dp
     *
     * @param context context
     * @param pxValue pxValue
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取设备宽度（px）
     *
     * @param context context
     * @return int
     */
    public static int deviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * 获取设备高度（px）
     */
    public static int deviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * SD卡判断
     *
     * @return boolean
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 是否有网
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @param context context
     * @return String
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context context
     * @return String
     */
    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context context
     * @return String
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }


    /**
     * 获取手机品牌
     *
     * @return String
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }


    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }


    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return int
     */
    public static int getBuildLevel() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return String
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取当前App进程的id
     *
     * @return int
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }


    /**
     * 获取当前App进程的Name
     *
     * @param context context
     * @param processId processId
     * @return String
     */
    public static String getAppProcessName(Context context, int processId) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info
                    = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(
                            pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e(DeviceUtils.class.getName(), e.getMessage(), e);
            }
        }
        return processName;
    }


    /**
     * 创建App文件夹
     *
     * @param appName appName
     * @param application application
     * @return String
     */
    public static String createAPPFolder(String appName, Application application) {
        return createAPPFolder(appName, application, null);
    }


    /**
     * 创建App文件夹
     *
     * @param appName appName
     * @param application application
     * @param folderName folderName
     * @return String
     */
    public static String createAPPFolder(String appName, Application application, String folderName) {
        File root = Environment.getExternalStorageDirectory();
        File folder;
        /**
         * 如果存在SD卡
         */
        if (DeviceUtils.isSDCardAvailable() && root != null) {
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        if (folderName != null) {
            folder = new File(folder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }


    /**
     * 通过Uri找到File
     *
     * @param context context
     * @param uri uri
     * @return File
     */
    public static File uri2File(Activity context, Uri uri) {
        File file;
        String[] project = { MediaStore.Images.Media.DATA };
        Cursor actualImageCursor = context.getContentResolver()
                .query(uri, project, null, null, null);
        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor.getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }


    /**
     * 获取AndroidManifest.xml里 <meta-data>的值
     *
     * @param context context
     * @param name name
     * @return String
     */
    public static String getMetaData(Context context, String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 复制到剪贴板
     *
     * @param context context
     * @param content content
     */
    public static void copy2Clipboard(Context context, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboardManager
                    = (android.content.ClipboardManager) context.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(context.getString(R.string.app_name),
                    content);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content);
        }
    }

    /**
     * 判断屏幕方向
     * @param activity
     * @return
     */
    public static int getScreenOrientation(Activity activity) {
        if (activity == null)return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                && height > width
                || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    /**
     * 调整声音
     * @param context
     * @param percent
     * @return aujusted percent
     */
    public static float adjustVolume(Context context, float percent){
        if (context == null)return 1f;
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (volume < 0)
            volume = 0;
        int index = (int) (percent * maxVolume) + volume;
        if (index > maxVolume)
            index = maxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        float ajustedPercent = 1.0f * audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / maxVolume;
        return ajustedPercent;
    }


    /**
     * 滑动改变亮度
     * @param percent
     * @return aujusted percent
     */
    public static float adjustBrightness(Activity activity, float percent) {
        if (activity == null)return 1f;
        float brightness = activity.getWindow().getAttributes().screenBrightness;
        if (brightness <= 0.00f) {
            brightness = 0.50f;
        } else if (brightness < 0.01f) {
            brightness = 0.01f;
        }

        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        activity.getWindow().setAttributes(lpa);

        return lpa.screenBrightness;
    }
}
