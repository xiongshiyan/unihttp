package top.jfunc.common.http.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguredHttpServiceScannerRegistrar.class)
public @interface EnableHttpService {
}
