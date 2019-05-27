package top.jfunc.common.http.boot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import top.jfunc.common.http.annotation.HttpService;
import top.jfunc.common.http.interfacing.JFuncHttp;
import top.jfunc.common.http.smart.SmartHttpClient;

public class AutoConfiguredHttpServiceScannerRegistrar
            implements BeanFactoryAware, ResourceLoaderAware , ImportBeanDefinitionRegistrar{

    private BeanFactory beanFactory;

    private ResourceLoader resourceLoader;

    @Value("${spring.http.smart.httpServiceScanPackages}")
    private String[] httpServiceScanPackages;

    public void doScanAndRegister(BeanDefinitionRegistry registry , String[] httpServiceScanPackages) {
        JFuncHttp jfuncHttp = new JFuncHttp();
        jfuncHttp.setSmartHttpClient(beanFactory.getBean(SmartHttpClient.class));

        ClassPathHttpServiceScanner scanner = new ClassPathHttpServiceScanner(registry , jfuncHttp);
        if (this.resourceLoader != null) {
            scanner.setResourceLoader(this.resourceLoader);
        }
        //只扫描HttpService的
        scanner.setAnnotationClass(HttpService.class);
        scanner.registerFilters();

        //默认从根目录下开始扫描
        String[] packages = {""};
        if(null != httpServiceScanPackages && httpServiceScanPackages.length>0){
            //从配置的包名扫描
            packages = httpServiceScanPackages;
        }
        scanner.doScan(packages);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        doScanAndRegister(registry , httpServiceScanPackages);
    }

    @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }