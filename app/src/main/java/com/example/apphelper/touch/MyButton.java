package com.example.apphelper.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MyButton extends Button {


    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("LHD", "MyButton ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("LHD", "MyButton ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("LHD", "MyButton ACTION_UP");
                return false;
            case MotionEvent.ACTION_CANCEL://ACTION_CANCEL事件后仍然可以收到move事件
                Log.i("LHD", "MyButton ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }

}
