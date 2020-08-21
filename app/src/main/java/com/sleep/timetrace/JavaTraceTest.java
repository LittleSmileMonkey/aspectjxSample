package com.sleep.timetrace;

import android.util.Log;

import com.sleep.runtime.debug.DebugLog;

/**
 * author：xuzhenhui on 2020/8/18
 * e-mail：sleepym09@163.net
 */
@DebugLog(logLevel = Log.INFO, tag = "JavaTimeTrace")
public class JavaTraceTest {

    @DebugLog
    public static void javaMethodTest() {
        Log.e("TimeTraceAspect", "javaMethodTest invoke");
    }

    @DebugLog
    public static void javaArgsTest(int a, double b, boolean c, String d, Object e, Object nullObj) {
        Log.e("TimeTraceAspect", String.format("javaArgsTest invoke, a = %s, b = %s, c = %s, d = %s, e = %s,nullObj = %s", a, b, c, d, e, nullObj));

    }

    @DebugLog
    public static String javaReturnTest(int a, double b, boolean c, String d, Object e) {
        Log.e("TimeTraceAspect", String.format("javaArgsTest invoke, a = %s, b = %s, c = %s, d = %s, e = %s", a, b, c, d, e));
        return "return a + b = " + (a + b);
    }

    @DebugLog
    public static void javaThreadTest() {
        try {
            Thread.sleep(100);
            Log.e("TimeTraceAspect", "javaThreadTest invoke");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
