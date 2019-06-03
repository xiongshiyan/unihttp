package top.jfunc.common.http.boot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated //在配置接口掃描的時候條件配置等就會失效
//@Configuration
//@EnableConfigurationProperties(SmartHttpProperties.class)
public class SmartHttpAutoConfigure {

/*    @ConditionalOnClass(OkHttp3SmartHttpClient.class)
    @ConditionalOnMissingBean(SmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.OkHttp3SmartHttpClient", matchIfMissing = true)
    static class OkHttp3{
        @Bean(name = "OkHttp3SmartHttpClient")
        @ConditionalOnMissingBean(SmartHttpClient.class)
        public SmartHttpClient okHttp3SmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new OkHttp3SmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }

    @ConditionalOnClass(NativeSmartHttpClient.class)
    @ConditionalOnMissingBean(SmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.NativeSmartHttpClient", matchIfMissing = true)
    static class Jdk{
        @Bean(name = "JdkSmartHttpClient")
        @ConditionalOnMissingBean(SmartHttpClient.class)
        public SmartHttpClient jdkSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new NativeSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }

    @ConditionalOnClass(ApacheSmartHttpClient.class)
    @ConditionalOnMissingBean(SmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.ApacheSmartHttpClient", matchIfMissing = true)
    static class Apache{
        @Bean(name = "ApacheSmartHttpClient")
        @ConditionalOnMissingBean(SmartHttpClient.class)
        public SmartHttpClient apacheSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new ApacheSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }

    }

    @ConditionalOnClass(JoddSmartHttpClient.class)
    @ConditionalOnMissingBean(SmartHttpClient.class)
    @ConditionalOnProperty(name = "spring.http.smart.type", havingValue = "top.jfunc.common.http.smart.JoddSmartHttpClient", matchIfMissing = true)
    static class Jodd{
        @Bean(name = "JoddSmartHttpClient")
        @ConditionalOnMissingBean(SmartHttpClient.class)
        public SmartHttpClient joddSmartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
            SmartHttpClient smartHttpClient = new JoddSmartHttpClient();
            SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);
            return smartHttpClient;
        }
    }*/


    ///必须手动配置以下代码才可以
    /*@ConditionalOnMissingBean(type = "top.jfunc.common.http.interfacing.HttpServiceCreator")
    static class HttpServiceScannerAutoConfigure{
        @Bean
        public HttpServiceCreator httpServiceCreator(@Autowired SmartHttpClient smartHttpClient){
            return new HttpServiceCreator().setSmartHttpClient(smartHttpClient);
        }

        @Bean
        public HttpServiceScanConfigure httpServiceScanConfigure(@Autowired HttpServiceCreator httpServiceCreator ,
                                                                 @Value("${spring.http.smart.scanPackages:top.jfunc.network}") String scanPackages){
            HttpServiceScanConfigure httpServiceScanConfigure = new HttpServiceScanConfigure(httpServiceCreator);
            httpServiceScanConfigure.setAnnotationClassScan(HttpService.class);
            httpServiceScanConfigure.setScanPackages(scanPackages);
            return httpServiceScanConfigure;
        }
    }*/
}
