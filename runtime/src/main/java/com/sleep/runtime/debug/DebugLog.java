package com.sleep.runtime.debug;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author：xuzhenhui on 2020/8/18
 * e-mail：sleepym09@163.net
 */
@Target({TYPE, METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
public @interface DebugLog {

    int LOG_LEVEL_NOT_SET = -1;
    String LOG_TAG_NOT_SET = "";

    /**
     * 日志的tag，默认填充当前类名
     */
    String tag() default LOG_TAG_NOT_SET;

    /**
     * 打印日志的level
     *
     * @see Log
     */
    int logLevel() default LOG_LEVEL_NOT_SET;
}
