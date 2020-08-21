package com.sleep.timetrace;

import android.util.Log;
import android.view.View;

import com.sleep.runtime.singleclick.SingleClick;

/**
 * author：xuzhenhui on 2020/8/21
 * e-mail：sleepym09@163.net
 */
public class JavaClickProxy {

    private static final String TAG = JavaClickProxy.class.getSimpleName();

    public static void lambdaTest(View view) {
        view.setOnClickListener(v -> {
            Log.i(TAG, "lambdaTest invoke");
        });
    }

    @SingleClick
    public static void annotationTest() {
        Log.i(TAG, "annotationTest invoke");
    }
}
