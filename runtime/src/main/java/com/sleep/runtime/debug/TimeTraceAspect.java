package com.sleep.runtime.debug;

import android.os.Looper;
import android.os.Trace;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * author：xuzhenhui on 2020/8/18
 * e-mail：sleepym09@163.net
 * <p>
 * aop不应该改变方法运行的逻辑，当有异常时，应该走正常的java异常处理逻辑
 */
@Aspect
public class TimeTraceAspect {
    @Pointcut("within(@com.sleep.runtime.debug.DebugLog *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.sleep.runtime.debug.DebugLog * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Pointcut("execution(@com.sleep.runtime.debug.DebugLog *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {
    }

    @Around("method() || constructor()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        DebugLog methodAnnotation = null;
        final Signature signature = joinPoint.getSignature();
        if (signature instanceof ConstructorSignature)
            methodAnnotation = (DebugLog) ((ConstructorSignature) signature).getConstructor().getAnnotation(DebugLog.class);
        if (signature instanceof MethodSignature) {
            methodAnnotation = ((MethodSignature) signature).getMethod().getAnnotation(DebugLog.class);
        }
        enterMethod(joinPoint, methodAnnotation);

        long startNanos = System.nanoTime();
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);

        exitMethod(joinPoint, methodAnnotation, result, lengthMillis);
        return result;
    }

    private static void enterMethod(JoinPoint joinPoint, DebugLog methodAnnotation) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder("\u21E2 ");
        builder.append(methodName).append('(');
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append('=');
            builder.append(Strings.toString(parameterValues[i]));
        }
        builder.append(')');

        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]");
        }

        if (methodAnnotation != null) {
            Class<?> cls = codeSignature.getDeclaringType();
            log(getLogLevel(cls, methodAnnotation), getTag(cls, methodAnnotation), builder.toString());
        }

        final String section = builder.toString().substring(2);
        Trace.beginSection(section);
    }

    private static void exitMethod(JoinPoint joinPoint, DebugLog methodAnnotation, Object result, long lengthMillis) {
        Trace.endSection();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature
                && ((MethodSignature) signature).getReturnType() != void.class;

        StringBuilder builder = new StringBuilder("\u21E0 ")
                .append(methodName)
                .append(" [")
                .append(lengthMillis)
                .append("ms]");

        if (hasReturnType) {
            builder.append(" = ");
            builder.append(Strings.toString(result));
        }

        if (methodAnnotation != null) {
            Class<?> cls = signature.getDeclaringType();
            log(getLogLevel(cls, methodAnnotation), getTag(cls, methodAnnotation), builder.toString());
        }
    }

    private static int getLogLevel(Class<?> cls, DebugLog methodAnnotation) {
        int logLevel = methodAnnotation.logLevel();
        if (logLevel != DebugLog.LOG_LEVEL_NOT_SET) return logLevel;
        final DebugLog clsAnnotation = cls.getAnnotation(DebugLog.class);
        if (clsAnnotation != null) {
            logLevel = clsAnnotation.logLevel();
        }
        return logLevel;
    }

    private static String getTag(Class<?> cls, DebugLog methodAnnotation) {
        String logTAG = methodAnnotation.tag();
        if (!logTAG.equals(DebugLog.LOG_TAG_NOT_SET)) return logTAG;
        final DebugLog clsAnnotation = cls.getAnnotation(DebugLog.class);
        if (clsAnnotation != null) {
            logTAG = clsAnnotation.tag();
        }
        if (logTAG.trim().length() == 0) {
            logTAG = cls.getSimpleName();
        }
        return logTAG;
    }

    private static void log(int logLevel, String tag, String msg) {
        switch (logLevel) {
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            default:
                Log.v(tag, msg);
                break;

        }
    }
}
