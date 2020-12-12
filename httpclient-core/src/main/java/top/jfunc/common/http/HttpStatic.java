package top.jfunc.common.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jfunc.common.http.base.FormFile;
import top.jfunc.common.http.request.DownloadRequest;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.request.StringBodyRequest;
import top.jfunc.common.http.request.UploadRequest;
import top.jfunc.common.http.response.Response;
import top.jfunc.common.http.smart.SmartHttpClient;
import top.jfunc.common.utils.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 1.静态初始化SmartHttpClient，基于ServiceLoader机制，提供者需要把实现类的全面放到 <pre>/META-INF/services/top.jfunc.common.http.smart.SmartHttpClient</pre> 文件下
 * 2.动态设置SmartHttpClient的实现类
 * 3.提供对SmartHttpClient的静态代理，使可以一句话实现Http请求
 * @author xiongshiyan at 2017/12/11
 */
public class HttpStatic {
    private static final Logger logger = LoggerFactory.getLogger(HttpStatic.class);

    private HttpStatic(){}

    private static SmartHttpClient SMART_HTTP_CLIENT;
    public static SmartHttpClient getSmartHttpClient() { return SMART_HTTP_CLIENT; }
    public static void setSmartHttpClient(SmartHttpClient smartHttpClient){SMART_HTTP_CLIENT = smartHttpClient;}

    static {
        initHttpStatic();
    }

    private static void initHttpStatic() {
        try {
            ServiceLoader<SmartHttpClient> smartHttpClients = ServiceLoader.load(SmartHttpClient.class);
            Iterator<SmartHttpClient> iterator = smartHttpClients.iterator();
            if(iterator.hasNext()){
                SMART_HTTP_CLIENT = iterator.next();
                logger.info("HttpStatic loads SmartHttpClient's implement successfully..." + SMART_HTTP_CLIENT);
            }else {
                logger.warn("没有成功加载到SmartHttpClient接口的实现类...请调用HttpStatic#setSmartHttpClient初始化,否则无法使用HttpStatic功能");
            }
        } catch (Exception e) {
            logger.warn("通过jar包自动加载实现类发生异常...请调用HttpStatic#setSmartHttpClient初始化,否则无法使用HttpStatic功能" , e);
        }
    }

    /**
     * 以下代码是对SmartHttpClient的代理，使用更方便
     * <pre>
     *     HttpStatic.get(...)
     *     HttpStatic.post(...)
     *     HttpStatic.getAsBytes(...)
     *     HttpStatic.getAsFile(...) 文件下载
     *     HttpStatic.upload(...) 文件上传
     * </pre>
     * @since 1.0.1
     */
    /*****************************************Proxy the method of SmartHttpClient interface*************************************************/

    public static Response get(HttpRequest request) throws IOException{
        return getSmartHttpClient().get(request);
    }
    public static Response post(StringBodyRequest request) throws IOException{
        return getSmartHttpClient().post(request);
    }
    public static byte[] getAsBytes(HttpRequest request) throws IOException{
        return getSmartHttpClient().getAsBytes(request);
    }
    public static File download(DownloadRequest request) throws IOException{
        return getSmartHttpClient().download(request);
    }
    public static Response upload(UploadRequest request) throws IOException{
        return getSmartHttpClient().upload(request);
    }
    /*****************************************Proxy the method of SimpleHttpClient interface*************************************************/
    public static String get(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout, int readTimeout, String resultCharset) throws IOException{
        return getSmartHttpClient().get(url, params, headers, connectTimeout, readTimeout, resultCharset);
    }
    public static String get(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().get(url, params, headers, connectTimeout, readTimeout);
    }
    public static String get(String url, Map<String, String> params, Map<String, String> headers, String resultCharset) throws IOException{
        return getSmartHttpClient().get(url,params,headers,resultCharset);
    }
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException{
        return getSmartHttpClient().get(url,params,headers);
    }
    public static String get(String url, Map<String, String> params, int connectTimeout, int readTimeout, String resultCharset) throws IOException{
        return getSmartHttpClient().get(url,params,connectTimeout,readTimeout,resultCharset);
    }
    public static String get(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().get(url,params,connectTimeout,readTimeout);
    }
    public static String get(String url, Map<String, String> params, String resultCharset) throws IOException{
        return getSmartHttpClient().get(url,params,resultCharset);
    }
    public static String get(String url, Map<String, String> params) throws IOException{
        return getSmartHttpClient().get(url,params);
    }
    public static String get(String url, String resultCharset) throws IOException{
        return getSmartHttpClient().get(url,resultCharset);
    }
    public static String get(String url) throws IOException{
        return getSmartHttpClient().get(url);
    }

    public static String post(String url, String body, String contentType, Map<String, String> headers, int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url, body, contentType, headers, connectTimeout, readTimeout, bodyCharset, resultCharset);
    }
    public static String post(String url, String body, String contentType, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().post(url,body,contentType,headers,connectTimeout,readTimeout);
    }
    public static String post(String url, String body, String contentType, Map<String, String> headers, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url,body,contentType,headers,bodyCharset,resultCharset);
    }
    public static String post(String url, String body, String contentType, Map<String, String> headers) throws IOException{
        return getSmartHttpClient().post(url, body, contentType, headers);
    }
    public static String post(String url, String body, String contentType, int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url,body,contentType,connectTimeout,readTimeout,bodyCharset,resultCharset);
    }
    public static String post(String url, String body, String contentType, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().post(url,body,contentType,connectTimeout,readTimeout);
    }
    public static String post(String url, String body, String contentType, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url,body,contentType,bodyCharset,resultCharset);
    }
    public static String post(String url, String body, String contentType) throws IOException{
        return getSmartHttpClient().post(url,body,contentType);
    }
    public static String postJson(String url, String body, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().postJson(url,body,bodyCharset,resultCharset);
    }
    public static String postJson(String url, String body) throws IOException{
        return getSmartHttpClient().postJson(url,body);
    }
    public static String post(String url, Map<String, String> params, Map<String, String> headers, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url, params , headers ,bodyCharset , resultCharset);
    }
    public static String post(String url, Map<String, String> params, Map<String, String> headers) throws IOException{
        return getSmartHttpClient().post(url, params,headers);
    }
    public static String post(String url, Map<String, String> params, String bodyCharset, String resultCharset) throws IOException{
        return getSmartHttpClient().post(url, params, bodyCharset, resultCharset);
    }
    public static String post(String url, Map<String, String> params) throws IOException{
        return getSmartHttpClient().post(url, params);
    }

    public static byte[] getAsBytes(String url, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().getAsBytes(url, headers, connectTimeout, readTimeout);
    }
    public static byte[] getAsBytes(String url, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().getAsBytes(url, connectTimeout , readTimeout);
    }
    public static byte[] getAsBytes(String url, MultiValueMap<String, String> headers) throws IOException{
        return getSmartHttpClient().getAsBytes(url , headers);
    }
    public static byte[] getAsBytes(String url) throws IOException{
        return getSmartHttpClient().getAsBytes(url);
    }

    public static File getAsFile(String url, MultiValueMap<String, String> headers, File file, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().getAsFile(url, headers, file, connectTimeout, readTimeout);
    }
    public static File getAsFile(String url, File file, int connectTimeout, int readTimeout) throws IOException{
        return getSmartHttpClient().getAsFile(url, file , connectTimeout , readTimeout);
    }
    public static File getAsFile(String url, MultiValueMap<String, String> headers, File file) throws IOException{
        return getSmartHttpClient().getAsFile(url, headers  , file);
    }
    public static File getAsFile(String url, File file) throws IOException{
        return getSmartHttpClient().getAsFile(url, file);
    }

    public static String upload(String url, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout, String resultCharset, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, headers, connectTimeout, readTimeout, resultCharset, files);
    }
    public static String upload(String url, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, headers ,connectTimeout , readTimeout , files);
    }
    public static String upload(String url, MultiValueMap<String, String> headers, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, headers ,files);
    }
    public static String upload(String url, int connectTimeout, int readTimeout, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url,connectTimeout , readTimeout , files);
    }
    public static String upload(String url, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, files);
    }

    public static String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout, String resultCharset, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, params, headers, connectTimeout, readTimeout, resultCharset, files);
    }
    public static String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, params ,headers ,connectTimeout , readTimeout, files);
    }
    public static String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, params ,headers , files);
    }
    public static String upload(String url, int connectTimeout, int readTimeout, MultiValueMap<String, String> params, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, params,connectTimeout , readTimeout, files);
    }
    public static String upload(String url, Map<String, String> params, FormFile... files) throws IOException{
        return getSmartHttpClient().upload(url, params , files);
    }
}
