package com.sleep.runtime.singleclick;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * author：xuzhenhui on 2020/8/21
 * e-mail：sleepym09@163.net
 */
@Aspect
public class SingleClickAspect {

    private static final String TAG = SingleClickAspect.class.getSimpleName();

    @Pointcut("execution(@com.sleep.runtime.singleclick.SingleClick * *(..))")
    public void annotation() {
    }

    //TODO 该拦截会拦截所以包含view参数的lambda表达式，看需求是否需要
    @Pointcut("execution(void *..lambda*(android.view.View))")
    public void javaLambdaClickListener() {
    }

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void commonClickListener() {
    }

    @Around("annotation() || javaLambdaClickListener() || commonClickListener()")
    public void clickAdvance(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(SingleClick.class)) {
            SingleClick singleClick = method.getAnnotation(SingleClick.class);
            boolean annotationFastClick = FilterClickUtil.isAnnotationFastClick(singleClick.value());
            if (!annotationFastClick) {
                // 不是快速点击，执行原方法
                joinPoint.proceed();
            } else {
                Log.d(TAG, "ignore by annotation fast click");
            }

        } else {
            // 取出方法的参数
            final Object arg = joinPoint.getArgs()[0];
            if (!(arg instanceof View)) {
                Log.d(TAG, "ignore by null or other type");
                return;
            }
            View view = (View) arg;
            // 判断是否快速点击
            if (!FilterClickUtil.isFastDoubleClick(view)) {
                // 不是快速点击，执行原方法
                joinPoint.proceed();
            } else {
                Log.d(TAG, "ignore by fast click");
            }
        }

    }
}
