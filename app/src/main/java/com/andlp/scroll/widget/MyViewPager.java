package com.andlp.scroll.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

/**
 * @ explain:这个 ViewPager是用来解决ScrollView里面嵌套ViewPager的 内部解决法的
 * @ author：xujun on 2016/10/25 16:38
 * @ email：gdutxiaoxu@163.com
 */
public class MyViewPager extends ViewPager {

    private static final String TAG = "xujun";

    int lastX = -1;//最后一次x坐标
    int lastY = -1;//最后一次y坐标

    public MyViewPager(Context context) {
        super(context);
    }
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                getParent().requestDisallowInterceptTouchEvent(true);  break;// 保证子View能够接收到Action_move事件
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Logger.i("MyViewPager的position--x:"+dealtX+",y:"+dealtY);

                if (dealtX >= dealtY) {  // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;

                break;
            case MotionEvent.ACTION_CANCEL:  break;
            case MotionEvent.ACTION_UP: break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
