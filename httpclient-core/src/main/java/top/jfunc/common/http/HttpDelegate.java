package top.jfunc.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jfunc.common.http.smart.SmartHttpClient;
import top.jfunc.common.utils.ClassUtil;

/**
 * 动态获取SmartHttpClient的实现类，基于jar包检测 <pre>HttpUtil.delegate()....</pre>
 * @author xiongshiyan at 2017/12/11
 */
public class HttpDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HttpDelegate.class);
    private HttpDelegate(){}

    /**
     * http请求工具代理对象
     */
    private static final SmartHttpClient delegate;
    public static SmartHttpClient delegate() {
        return delegate;
    }

    static {
            delegate = initDelegate();
    }

    private static SmartHttpClient initDelegate() {
        try {
            //根据类路径的jar加载默认顺序是 OKHttp3、ApacheHttpClient、JoddHttp、URLConnection
            Class delegateClassToUse = null;
            // okhttp3.OkHttpClient ?
            if (ClassUtil.isPresent(HttpDelegate.class.getClassLoader() ,
                    "okhttp3.OkHttpClient" , "okio.Okio" ,
                    "top.jfunc.common.http.smart.OkHttp3SmartHttpClient")) {
                delegateClassToUse = ClassUtil.loadClass("top.jfunc.common.http.smart.OkHttp3SmartHttpClient");
            }
            // org.apache.http.impl.client.CloseableHttpClient ?
            else if (ClassUtil.isPresent(HttpDelegate.class.getClassLoader() ,
                    "org.apache.http.impl.client.CloseableHttpClient",
                    "org.apache.http.impl.client.HttpClientBuilder" ,
                    "top.jfunc.common.http.smart.ApacheSmartHttpClient")) {
                delegateClassToUse = ClassUtil.loadClass("top.jfunc.common.http.smart.ApacheSmartHttpClient");
            }
            // jodd.http.HttpRequest ?
            else if (ClassUtil.isPresent(HttpDelegate.class.getClassLoader() ,
                    "jodd.http.HttpRequest","jodd.http.HttpResponse" ,
                    "top.jfunc.common.http.smart.JoddSmartHttpClient")) {
                delegateClassToUse = ClassUtil.loadClass("top.jfunc.common.http.smart.JoddSmartHttpClient");
            }
            // java.net.URLConnection
            else if (ClassUtil.isPresent(HttpDelegate.class.getClassLoader() ,
                    "top.jfunc.common.http.smart.NativeSmartHttpClient")) {
                delegateClassToUse = ClassUtil.loadClass("top.jfunc.common.http.smart.NativeSmartHttpClient");
            }
            return  null != delegateClassToUse ? (SmartHttpClient) delegateClassToUse.newInstance() : null;
        } catch (Exception e) {
            logger.warn("自动探测SmartHttpClient失败,请不要使用HttpDelegate,或者引入一种实现");
            return null;
        }
    }
}
