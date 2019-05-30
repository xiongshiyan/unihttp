package top.jfunc.common.http.boot;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 不知道为什么自动配置之后属性配置就不行了
 * @author xiongshiyan at 2019/5/30 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public class HttpServiceImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

    private static final String SCAN_PACKAGES = "scanPackages";
    private static final String ANNOTATION_CLASS_SCAN = "annotationClassScan";
    private static final String HTTP_SERVICE_CREATOR = "httpServiceCreator";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableHttpService.class.getName());
        String[] scanPackages = (String[])annotationAttributes.get(SCAN_PACKAGES);
        Class<? extends Annotation> annotationClassScan = (Class<? extends Annotation>)annotationAttributes.get(ANNOTATION_CLASS_SCAN);
        String httpServiceCreator = (String)annotationAttributes.get(HTTP_SERVICE_CREATOR);

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(HttpServiceScanConfigure.class);
        beanDefinitionBuilder.addPropertyValue(SCAN_PACKAGES , scanPackages);
        beanDefinitionBuilder.addPropertyValue(ANNOTATION_CLASS_SCAN, annotationClassScan);
        beanDefinitionBuilder.addPropertyReference(HTTP_SERVICE_CREATOR, httpServiceCreator);

        registry.registerBeanDefinition(HttpServiceScanConfigure.class.getName() , beanDefinitionBuilder.getBeanDefinition());
    }
}
