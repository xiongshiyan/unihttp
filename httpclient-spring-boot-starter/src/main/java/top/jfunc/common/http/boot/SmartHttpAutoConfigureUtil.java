package top.jfunc.common.http.boot;

import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.ProxyInfo;
import top.jfunc.common.http.interceptor.CompositeInterceptor;
import top.jfunc.common.http.interceptor.Interceptor;
import top.jfunc.common.http.smart.SmartHttpClient;
import top.jfunc.common.utils.BeanUtil;
import top.jfunc.common.utils.ClassUtil;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

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

        //代理配置
        SmartHttpProperties.Proxy propertiesProxy = smartHttpProperties.getProxy();
        if(null != propertiesProxy){

            InetSocketAddress inetSocketAddress = new InetSocketAddress(
                    propertiesProxy.getHostName(), propertiesProxy.getPort());
            Proxy.Type type = Proxy.Type.valueOf(propertiesProxy.getType());

            config.setProxyInfo(ProxyInfo.of(new Proxy(type,inetSocketAddress),
                    propertiesProxy.getUsername() , propertiesProxy.getPassword()));
        }

        //拦截器配置：根据全类名实例化
        List<String> interceptors = smartHttpProperties.getInterceptors();
        if(null != interceptors && !interceptors.isEmpty()){
            List<Interceptor> interceptorList = new ArrayList<>(interceptors.size());
            interceptors.forEach(className->{
                Class<Interceptor> clazz = getInterceptorClass(className);
                interceptorList.add(BeanUtil.newInstance(clazz));
            });
            CompositeInterceptor compositeInterceptor = new CompositeInterceptor(interceptorList);
            config.setCompositeInterceptor(compositeInterceptor);
        }

        smartHttpClient.setConfig(config);
    }

    private static Class<Interceptor> getInterceptorClass(String className) {
        try {
            return  (Class<Interceptor>) ClassUtil.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
