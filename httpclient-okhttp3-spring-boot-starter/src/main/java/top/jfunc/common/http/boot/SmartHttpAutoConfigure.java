package top.jfunc.common.http.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import top.jfunc.common.http.annotation.HttpService;
import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.ProxyInfo;
import top.jfunc.common.http.interfacing.JFuncHttp;
import top.jfunc.common.http.smart.OkHttp3SmartHttpClient;
import top.jfunc.common.http.smart.SmartHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
@EnableConfigurationProperties(SmartHttpProperties.class)
public class SmartHttpAutoConfigure {
    private static final Logger logger = LoggerFactory.getLogger(SmartHttpAutoConfigure.class);

    @Autowired
    private SmartHttpProperties smartHttpProperties;

    @Bean(name = "smartHttpClient")
    @ConditionalOnMissingBean
    public SmartHttpClient smartHttpClient(){
        SmartHttpClient smartHttpClient = new OkHttp3SmartHttpClient();

        Config config = Config.defaultConfig();
        if(null != smartHttpProperties.getBaseUrl()){
            config.setBaseUrl(smartHttpProperties.getBaseUrl());
        }
        config.setDefaultConnectionTimeout(smartHttpProperties.getDefaultConnectionTimeout());
        config.setDefaultReadTimeout(smartHttpProperties.getDefaultReadTimeout());
        config.setDefaultBodyCharset(smartHttpProperties.getDefaultBodyCharset());
        config.setDefaultResultCharset(smartHttpProperties.getDefaultResultCharset());

        if(null != smartHttpProperties.getDefaultHeaders()){
            config.setDefaultHeaders(smartHttpProperties.getDefaultHeaders());
        }
        if(null != smartHttpProperties.getDefaultQueryParams()){
            config.setDefaultQueryParams(smartHttpProperties.getDefaultQueryParams());
        }
        SmartHttpProperties.Proxy propertiesProxy = smartHttpProperties.getProxy();
        if(null != propertiesProxy){

            InetSocketAddress inetSocketAddress = new InetSocketAddress(
                    propertiesProxy.getHostName(), propertiesProxy.getPort());
            Proxy.Type type = Proxy.Type.valueOf(propertiesProxy.getType());
            
            config.setProxyInfo(ProxyInfo.of(new Proxy(type,inetSocketAddress),
                    propertiesProxy.getUsername() , propertiesProxy.getPassword()));
        }

        smartHttpClient.setConfig(config);

        return smartHttpClient;
    }



    @org.springframework.context.annotation.Configuration
    @Import({ AutoConfiguredHttpServiceScannerRegistrar.class })
    @ConditionalOnMissingBean(HttpServiceFactoryBean.class)
    public static class HttpServiceScannerRegistrarNotFoundConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() {
            logger.debug("No {} found.", HttpServiceFactoryBean.class.getName());
        }
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
            //从根目录下开始扫描
            scanner.doScan("");

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
