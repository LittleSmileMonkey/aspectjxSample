package com.sleep.runtime.singleclick;

import android.view.View;

public class FilterClickUtil {
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param v 点击的控件
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v) {

        int viewId = v.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        long defaultValue = 1000;
        if (timeInterval < defaultValue && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }
    public static boolean isAnnotationFastClick(long mills) {
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < mills) {
            return true;
        } else {
            mLastClickTime = time;
            return false;
        }
    }
}
