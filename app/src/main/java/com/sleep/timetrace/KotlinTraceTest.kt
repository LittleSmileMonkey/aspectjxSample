package com.sleep.timetrace

import android.util.Log
import com.sleep.runtime.debug.DebugLog
import java.io.IOException

/**
 * author：xuzhenhui on 2020/8/19
 * e-mail：sleepym09@163.net
 *
 */

class KotlinTraceTest {

    @DebugLog(logLevel = Log.INFO, tag = "KotlinTimeTrace")
    companion object {
        @JvmStatic
        @DebugLog
        fun kotlinMethodTest() {
            Log.e("TimeTraceAspect", "kotlinMethodTest invoke")
        }

        @DebugLog
        @Throws(IOException::class)
        fun kotlinArgsTest(
            a: Int,
            b: Double,
            c: Boolean,
            d: String?,
            e: Any?,
            nullObj:Any?
        ) {
            Log.e(
                "TimeTraceAspect",
                String.format(
                    "javaArgsTest invoke, a = %s, b = %s, c = %s, d = %s, e = %s, nullObj = %s",
                    a,
                    b,
                    c,
                    d,
                    e,
                    nullObj
                )
            )
        }

        @DebugLog
        fun kotlinReturnTest(
            a: Int,
            b: Double,
            c: Boolean,
            d: String?,
            e: Any?
        ): String? {
            Log.e(
                "TimeTraceAspect",
                String.format(
                    "javaArgsTest invoke, a = %s, b = %s, c = %s, d = %s, e = %s",
                    a,
                    b,
                    c,
                    d,
                    e
                )
            )
            return "return a + b = " + (a + b)
        }

        @JvmStatic
        @DebugLog
        fun kotlinThreadTest() {
            try {
                Thread.sleep(100)
                Log.e("TimeTraceAspect", "kotlinThreadTest invoke")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}