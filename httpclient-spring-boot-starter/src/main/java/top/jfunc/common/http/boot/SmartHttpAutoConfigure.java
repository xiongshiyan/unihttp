package top.jfunc.common.http.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jfunc.common.http.annotation.HttpService;
import top.jfunc.common.http.interfacing.HttpServiceCreator;
import top.jfunc.common.http.smart.*;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
@EnableConfigurationProperties(SmartHttpProperties.class)
public class SmartHttpAutoConfigure {

    @ConditionalOnMissingBean(type = "top.jfunc.common.http.smart.SmartHttpClient")
    @ConditionalOnClass(OkHttp3SmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.OkHttp3SmartHttpClient", matchIfMissing = true)
    static class OkHttp3{
        /**
         * OkHttp3实现的话
         */
        @Bean(name = "OkHttp3SmartHttpClient")
        public SmartHttpClient okHttp3SmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new OkHttp3SmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }

    @ConditionalOnMissingBean(type = "top.jfunc.common.http.smart.SmartHttpClient")
    @ConditionalOnClass(NativeSmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.NativeSmartHttpClient", matchIfMissing = true)
    static class Jdk{
        /**
         * JDK实现的话
         */
        @Bean(name = "JdkSmartHttpClient")
        public SmartHttpClient jdkSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new NativeSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }

    @ConditionalOnMissingBean(type = "top.jfunc.common.http.smart.SmartHttpClient")
    @ConditionalOnClass(ApacheSmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.ApacheSmartHttpClient", matchIfMissing = true)
    static class Apache{
        /**
         * Apache实现的话
         */
        @Bean(name = "ApacheSmartHttpClient")
        public SmartHttpClient apacheSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new ApacheSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }

    }

    @ConditionalOnMissingBean(type = "top.jfunc.common.http.smart.SmartHttpClient")
    @ConditionalOnClass(JoddSmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.JoddSmartHttpClient", matchIfMissing = true)
    static class Jodd{
        /**
         * Jodd-Http实现的话
         */
        @Bean(name = "JoddSmartHttpClient")
        public SmartHttpClient joddSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new JoddSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }


    @ConditionalOnMissingBean(type = "top.jfunc.common.http.interfacing.HttpServiceCreator")
    static class HttpServiceScannerAutoConfigure{
        /**
         * 配置接口扫描
         */
        @Bean
        public HttpServiceCreator httpServiceCreator(@Autowired SmartHttpClient smartHttpClient){
            return new HttpServiceCreator().setSmartHttpClient(smartHttpClient);
        }

        /**
         * @param scanPackages 必须明确指定扫描的包
         */
        @Bean
        public HttpServiceScanConfigure httpServiceScanConfigure(@Autowired HttpServiceCreator httpServiceCreator ,
                                                                 @Value("${spring.http.smart.scanPackages:top.jfunc.network}") String scanPackages){
            HttpServiceScanConfigure httpServiceScanConfigure = new HttpServiceScanConfigure(httpServiceCreator);
            httpServiceScanConfigure.setAnnotationClassScan(HttpService.class);
            httpServiceScanConfigure.setScanPackages(scanPackages);
            return httpServiceScanConfigure;
        }
    }
}
