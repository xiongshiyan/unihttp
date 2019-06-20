package top.jfunc.common.http;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import top.jfunc.common.http.annotation.HttpService;
import top.jfunc.common.http.interfacing.HttpServiceCreator;

import java.lang.annotation.Annotation;

/**
 * @author xiongshiyan at 2019/5/29 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class HttpServiceScanConfigure implements BeanDefinitionRegistryPostProcessor , ResourceLoaderAware{
    private ResourceLoader resourceLoader;
    /**
     * JFuncHttp用于创建接口的代理对象
     */
    private HttpServiceCreator httpServiceCreator;
    /**
     * 扫描的包
     */
    private String[] scanPackages = {""};
    /**
     * 扫描什么标注的接口
     */
    private Class<? extends Annotation> annotationClassScan = HttpService.class;

    public HttpServiceScanConfigure(HttpServiceCreator httpServiceCreator) {
        this.httpServiceCreator = httpServiceCreator;
    }

    public HttpServiceScanConfigure() {
    }

    public HttpServiceCreator getHttpServiceCreator() {
        return httpServiceCreator;
    }

    public void setHttpServiceCreator(HttpServiceCreator httpServiceCreator) {
        this.httpServiceCreator = httpServiceCreator;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(String... scanPackages) {
        if(null!=scanPackages){
            this.scanPackages = scanPackages;
        }
    }

    public Class<? extends Annotation> getAnnotationClassScan() {
        return annotationClassScan;
    }

    public void setAnnotationClassScan(Class<? extends Annotation> annotationClassScan) {
        this.annotationClassScan = annotationClassScan;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        ClassPathHttpServiceScanner scanner = new ClassPathHttpServiceScanner(registry , httpServiceCreator);
        if (this.resourceLoader != null) {
            scanner.setResourceLoader(this.resourceLoader);
        }
        //只扫描HttpService的
        scanner.setAnnotationClass(getAnnotationClassScan());
        scanner.registerFilters();

        scanner.doScan(getScanPackages());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //do nothing
    }
}
