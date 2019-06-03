package top.jfunc.common.http.boot;

import java.lang.annotation.*;

@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
/*@Import({//SmartHttpProperties.class ,
        SmartHttpAutoConfigure.OkHttp3.class , SmartHttpAutoConfigure.Jdk.class,
        SmartHttpAutoConfigure.Apache.class , SmartHttpAutoConfigure.Jodd.class})*/
public @interface EnableSmartHttpClient {
}
