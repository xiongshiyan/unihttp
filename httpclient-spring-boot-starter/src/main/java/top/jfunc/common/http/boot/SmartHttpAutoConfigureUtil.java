package top.jfunc.common.http.boot;

import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.ProxyInfo;
import top.jfunc.common.http.smart.SmartHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class SmartHttpAutoConfigureUtil {
    private SmartHttpAutoConfigureUtil(){}

    public static void configSmartHttpClient(SmartHttpClient smartHttpClient , SmartHttpProperties smartHttpProperties){
        Config config = Config.defaultConfig();
        if(null != smartHttpProperties.getBaseUrl()){
            config.setBaseUrl(smartHttpProperties.getBaseUrl());
        }
        config.setDefaultConnectionTimeout(smartHttpProperties.getDefaultConnectionTimeout());
        config.setDefaultReadTimeout(smartHttpProperties.getDefaultReadTimeout());
        config.setDefaultBodyCharset(smartHttpProperties.getDefaultBodyCharset());
        config.setDefaultResultCharset(smartHttpProperties.getDefaultResultCharset());

        if(null != smartHttpProperties.getDefaultHeaders()){
            config.headerHolder().setHeaders(smartHttpProperties.getDefaultHeaders());
        }
        if(null != smartHttpProperties.getDefaultQueryParams()){
            config.queryParamHolder().setParams(smartHttpProperties.getDefaultQueryParams());
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
    }
}
