package top.jfunc.common.http.boot;

import org.springframework.context.annotation.Import;
import top.jfunc.common.http.annotation.HttpService;

import java.lang.annotation.*;

@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(HttpServiceImportBeanDefinitionRegistrar.class)
public @interface EnableHttpService {
    String[] scanPackages();
    Class<? extends Annotation> annotationClassScan() default HttpService.class;
    String httpServiceCreator() default "httpServiceCreator";
}
