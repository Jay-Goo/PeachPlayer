package jaygoo.peachplayer.media;

import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/9/8
 * 描    述: 手势判断监听器
 * ================================================
 */
public abstract class VideoGestureListener extends GestureDetector.SimpleOnGestureListener{
    private static final float MIN_MOVE_DISTANCE = 40;//最小滑动距离
    private static final double DEGREE_LIMIT = 45 * Math.PI / 180;
    private int screenWith;
    private int screenHeight;
    private int leftLimitX;
    private int rightLimitX;

    //左侧上划
    public abstract void leftSlideUp(float distance, float percent);
    //左侧下划
    public abstract void leftSlideDown(float distance, float percent);
    //右侧上划
    public abstract void rightSlideUp(float distance, float percent);
    //右侧下划
    public abstract void rightSlideDown(float distance, float percent);
    //从左向右划
    public abstract void slideLeft2Right(float distance, float percent);
    //从右向左划
    public abstract void slideRight2Left(float distance, float percent);
    //双击
    public abstract void doubleTap();

    public VideoGestureListener(int screenWith, int screenHeight){
        onConfigurationChanged(screenWith, screenHeight);
        leftLimitX = (int)(0.35f * screenWith);
        rightLimitX = (int)(0.65f * screenWith);
    }

    /**
     * change your screenWith and screenHeight when your screen rotation
     * @param screenWith
     * @param screenHeight
     */
    public void onConfigurationChanged(int screenWith, int screenHeight){
        this.screenWith = screenWith;
        this.screenHeight = screenHeight;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int beginX = (int)e1.getX();
        int endX = (int)e2.getX();
        int beginY = (int)e1.getY();
        int endY = (int)e2.getY();
        int moveDistanceX = endX - beginX;
        int moveDistanceY = endY - beginY;
        int absMoveDistanceX = Math.abs(moveDistanceX);
        int absMoveDistanceY = Math.abs(moveDistanceY);

        //移动有效
        if (absMoveDistanceX >= MIN_MOVE_DISTANCE || absMoveDistanceY >= MIN_MOVE_DISTANCE){
            if (beginX < leftLimitX && getDegree(absMoveDistanceX, absMoveDistanceY) < DEGREE_LIMIT){
                if (moveDistanceY > 0){
                    leftSlideDown(absMoveDistanceY, (float) absMoveDistanceY/screenHeight);
                }else {
                    leftSlideUp(absMoveDistanceY, (float) absMoveDistanceY/screenHeight);
                }
            }else if (beginX > rightLimitX && getDegree(absMoveDistanceX, absMoveDistanceY) < DEGREE_LIMIT){
                if (moveDistanceY > 0){
                    rightSlideDown(absMoveDistanceY, (float) absMoveDistanceY/screenHeight);
                }else {
                    rightSlideUp(absMoveDistanceY, (float) absMoveDistanceY/screenHeight);
                }
            }else if (getDegree(absMoveDistanceX, absMoveDistanceY) >= DEGREE_LIMIT){
                if (moveDistanceX > 0){
                    slideLeft2Right(absMoveDistanceX, (float) absMoveDistanceX/screenWith);
                }else {
                    slideRight2Left(absMoveDistanceX, (float) absMoveDistanceX/screenWith);
                }
            }
        }

        return true;
    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//
//    }

    /**
     *
     * @param x abs distanceX
     * @param y abs distanceY
     * @return real degree = Math.atan2(x,y) * 180 / PI
     */
    private double getDegree(int x, int y){
        return Math.atan2(x,y);
    }


    /**
     * 双击
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        doubleTap();
        return true;
    }
}
