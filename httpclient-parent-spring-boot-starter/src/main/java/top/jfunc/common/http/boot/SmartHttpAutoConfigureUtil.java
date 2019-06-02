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

    public static void configSmartHttpClient(SmartHttpClient smartHttpClient , SmartHttpConfig smartHttpConfig){
        Config config = Config.defaultConfig();
        if(null != smartHttpConfig.getBaseUrl()){
            config.setBaseUrl(smartHttpConfig.getBaseUrl());
        }
        config.setDefaultConnectionTimeout(smartHttpConfig.getDefaultConnectionTimeout());
        config.setDefaultReadTimeout(smartHttpConfig.getDefaultReadTimeout());
        config.setDefaultBodyCharset(smartHttpConfig.getDefaultBodyCharset());
        config.setDefaultResultCharset(smartHttpConfig.getDefaultResultCharset());

        if(null != smartHttpConfig.getDefaultHeaders()){
            config.headerHolder().setHeaders(smartHttpConfig.getDefaultHeaders());
        }
        if(null != smartHttpConfig.getDefaultQueryParams()){
            config.queryParamHolder().setParams(smartHttpConfig.getDefaultQueryParams());
        }
        SmartHttpConfig.Proxy propertiesProxy = smartHttpConfig.getProxy();
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
