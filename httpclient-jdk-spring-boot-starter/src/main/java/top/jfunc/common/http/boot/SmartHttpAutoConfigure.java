package top.jfunc.common.http.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.smart.NativeSmartHttpClient;
import top.jfunc.common.http.smart.SmartHttpClient;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
@EnableConfigurationProperties(SmartHttpProperties.class)
public class SmartHttpAutoConfigure {

    @Autowired
    SmartHttpProperties smartHttpProperties;

    @Bean(name = "smartHttpClient")
    @ConditionalOnMissingBean
    public SmartHttpClient smartHttpClient(){
        SmartHttpClient smartHttpClient = new NativeSmartHttpClient();

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

        smartHttpClient.setConfig(config);
        
        return smartHttpClient;
    }
}
