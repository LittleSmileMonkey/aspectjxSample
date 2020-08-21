package com.sleep.runtime.singleclick;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author：xuzhenhui on 2020/8/21
 * e-mail：sleepym09@163.net
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface SingleClick {
    long value() default 1000;
}
