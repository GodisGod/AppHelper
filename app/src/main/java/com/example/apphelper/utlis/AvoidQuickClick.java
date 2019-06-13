package com.example.apphelper.utlis;

import java.util.HashMap;

/**
 * 避免两次快速的调用
 */
public class AvoidQuickClick {

    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于500ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public static boolean isFastClick(long duration) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= duration) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

}
