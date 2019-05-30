package top.jfunc.common.http.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jfunc.common.http.smart.OkHttp3SmartHttpClient;
import top.jfunc.common.http.smart.SmartHttpClient;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration@ConditionalOnClass(OkHttp3SmartHttpClient.class)
@EnableConfigurationProperties(SmartHttpProperties.class)
public class SmartHttpAutoConfigure {

    @Bean(name = "smartHttpClient")
    @ConditionalOnMissingBean
    public SmartHttpClient smartHttpClient(@Autowired SmartHttpProperties smartHttpProperties){
        SmartHttpClient smartHttpClient = new OkHttp3SmartHttpClient();

        SmartHttpAutoConfigureUtil.configSmartHttpClient(smartHttpClient , smartHttpProperties);

        return smartHttpClient;
    }
}
