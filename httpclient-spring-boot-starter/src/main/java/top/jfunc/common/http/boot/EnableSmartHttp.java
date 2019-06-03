package top.jfunc.common.http.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({//SmartHttpProperties.class ,
        SmartHttpAutoConfigure.OkHttp3.class , SmartHttpAutoConfigure.Jdk.class,
        SmartHttpAutoConfigure.Apache.class , SmartHttpAutoConfigure.Jodd.class,
        SmartHttpAutoConfigure.HttpServiceScannerAutoConfigure.class,
        SmartHttpAutoConfigure.class})
public @interface EnableSmartHttp {
}
