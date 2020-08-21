该lib是基于Hugo、aspectj和aspectjx开发。

Hugo功能：

1. 可对指定(@DebugLog注解)的方法进行耗时统计

Aspectjx功能：

1. aspectj在android上的实现
2. 适配kotlin
3. exclude、include

TimeTrace，用于调试，对指定方法进行耗时追踪，具有以下特性：

1. 适配kotlin
2. 可以指定日志级别 & 日志tag
3. 可基于class级别注解，方法->类 向上覆盖。
4. 兼容aspectjx extension，可指定exclude、include、enable

SingleClickAspect，用于屏蔽多次点击，具有以下特性：

1. 可以添加指定方法的快速调用屏蔽
2. 支持kotlin、java及相应lambda

### 接入方法

#### maven依赖

1. push至maven local

   `./gradlew :plugin:publishToMavenLocal`

2. 根 build.gradle

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.sleep.timetrace:plugin:0.0.1'
    }
}
```

3. 要使用的模块 build.gradle

```
apply plugin: 'aop'

aspectjx{
	include('当前包名')
}
```

#### buildSrc直接引用

1. 将buildSrc直接copy至项目中

2. 需要aop的模块build.gradle中

   ```
   apply plugin: 'aop'
   
   aspectjx{
   	include('当前包名')
   }
   ```

   

### 使用方法

#### [TimeTrace](./runtime/src/main/java/com/sleep/runtime/debug/TimeTraceAspect.java)

在想要追踪耗时的方法上加入注解

```
@DebugLog(logLevel = Log.INFO, tag = "JavaTimeTrace")
public static void javaMethodTest() {
  Log.e("TimeTrace", "javaMethodTest invoke");
}
```

当需要注解的方法过多时，可以在class级别统一指定`logLevel`、`tag`

```java
@DebugLog(logLevel = Log.INFO, tag = "JavaTimeTrace")
public class JavaTraceTest {

    @DebugLog(logLevel = Log.INFO, tag = "JavaTimeTrace")
    public static void javaMethodTest() {
        Log.e("TimeTrace", "javaMethodTest invoke");
    }

    @DebugLog
    public static String javaReturnTest(int a, double b, boolean c, String d, Object e) {
        Log.e("TimeTrace", String.format("javaArgsTest invoke, a = %s, b = %s, c = %s, d = %s, e = %s", a, b, c, d, e));
        return "return a + b = " + (a + b);
    }

    @DebugLog
    public static void javaThreadTest() {
        try {
            Thread.sleep(100);
            Log.e("TimeTrace", "javaThreadTest invoke");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

关于优先级说明：

当方法和类同时指定了`logLevel` 或 `tag` ，方法级别注解>类级别注解。

#### [SingleClick](./runtime/src/main/java/com/sleep/runtime/singleclick/SingleClickAspect.java)

会自动添加android.view.View onclick()的快速点击，同时可以通过`@SingleClick`给指定方法添加快速调用屏蔽

```
@SingleClick
  private fun kotlinAnnotationTest() {
  Log.e(TAG, "kotlin annotation test")
}
```

### kotlin需要注意的地方
1. 对companion object中方法进行调试时，注解需要在companion object上，而非class上

### TODO

1. AspectjPlugin中，transform doAspect部分多线程环境下会出错，暂时用单线程规避
2. 由于java lamda的特殊转换，SingleClick目前会切面java所有lamda & (View view)形式的方法进行快速调用拦截
3. Plugin extension根据项目需求扩充配置

### 引用

感谢[Hugo](https://github.com/JakeWharton/hugo)、[aspectj](https://www.eclipse.org/aspectj/)和[Aspectj](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)的开发者，该库基于以上库进行开发。