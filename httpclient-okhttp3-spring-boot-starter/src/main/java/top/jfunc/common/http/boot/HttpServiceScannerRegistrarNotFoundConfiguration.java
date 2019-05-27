package top.jfunc.common.http.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import top.jfunc.common.http.annotation.HttpService;
import top.jfunc.common.http.interfacing.JFuncHttp;
import top.jfunc.common.http.smart.SmartHttpClient;


@org.springframework.context.annotation.Configuration
@ConditionalOnMissingBean(HttpServiceFactoryBean.class)
@AutoConfigureAfter(SmartHttpAutoConfigure.class)
@Import({ HttpServiceScannerRegistrarNotFoundConfiguration.AutoConfiguredHttpServiceScannerRegistrar.class })
public class HttpServiceScannerRegistrarNotFoundConfiguration implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(HttpServiceScannerRegistrarNotFoundConfiguration.class);
    @Override
    public void afterPropertiesSet() {
        logger.debug("No {} found.", HttpServiceFactoryBean.class.getName());
    }



    public static class AutoConfiguredHttpServiceScannerRegistrar
            implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

        private BeanFactory beanFactory;

        private ResourceLoader resourceLoader;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
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
            SmartHttpProperties smartHttpProperties = beanFactory.getBean(SmartHttpProperties.class);
            if(null != smartHttpProperties.getHttpServiceScanPackages() && smartHttpProperties.getHttpServiceScanPackages().length>0){
                //从配置的包名扫描
                packages = smartHttpProperties.getHttpServiceScanPackages();
            }
            scanner.doScan(packages);

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
}

