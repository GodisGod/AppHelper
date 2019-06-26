package com.example.apphelper.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * framlayout默认的clickable是false，所以只能收到down事件，而收不到move和up事件
 * 如果想要收到move和up事件，必须要设置clickable为true或者longClickable为true
 * android:clickable="true"
 * android:longClickable="true"
 */
public class MyFramLayout extends FrameLayout {


    public MyFramLayout(Context context) {
        super(context);
    }

    public MyFramLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFramLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("LHD", "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("LHD", "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("LHD", "dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i("LHD", "MyFramLayout ACTION_CANCEL");
                break;
        }

        Log.i("LHD", "super.dispatchTouchEvent(ev) = " + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("LHD", "onInterceptTouchEvent " + ev.getAction() + "   super.onInterceptTouchEvent(ev) = " + super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("LHD", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("LHD", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("LHD", "ACTION_UP");
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.i("LHD", "MyFramLayout ACTION_CANCEL");
                break;
        }
        Log.i("LHD", "super.onTouchEvent(event) = " + super.onTouchEvent(event));
        //android:clickable="true"
        //android:longClickable="true"以后 super.onTouchEvent(event) = true

        //如果不设置android:clickable="true"或者android:longClickable="true"，super.onTouchEvent(event) 默认返回 false

        return super.onTouchEvent(event);
    }


}
