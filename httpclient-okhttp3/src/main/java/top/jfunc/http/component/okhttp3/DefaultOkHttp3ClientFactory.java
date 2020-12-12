package top.jfunc.http.component.okhttp3;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import top.jfunc.http.base.Config;
import top.jfunc.http.base.ProxyInfo;
import top.jfunc.http.component.AbstractRequesterFactory;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.OkHttp3Util;
import top.jfunc.http.util.ParamUtil;
import top.jfunc.common.utils.ObjectUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 每次都创建一个，会有性能问题，高并发的话很快就会报内存溢出
 * @see SingleOkHttp3ClientFactory
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultOkHttp3ClientFactory extends AbstractRequesterFactory<OkHttpClient> {
    @Override
    public OkHttpClient doCreate(HttpRequest httpRequest) throws IOException {
        return createAndConfigBuilder(httpRequest).build();
    }

    protected OkHttpClient.Builder createAndConfigBuilder(HttpRequest httpRequest) {
        Config config = httpRequest.getConfig();
        //1.构造OkHttpClient
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(config.getConnectionTimeoutWithDefault(httpRequest.getConnectionTimeout()), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeoutWithDefault(httpRequest.getReadTimeout()), TimeUnit.MILLISECONDS);
        //1.1如果存在就设置代理
        ProxyInfo proxyInfo = ObjectUtil.defaultIfNull(httpRequest.getProxyInfo(), config.getDefaultProxyInfo());
        if(null != proxyInfo){
            clientBuilder.proxy(proxyInfo.getProxy());
        }

        //是否重定向
        boolean followRedirects = ObjectUtil.defaultIfNull(httpRequest.followRedirects() , config.followRedirects());
        clientBuilder.followRedirects(followRedirects);

        ////////////////////////////////////ssl处理///////////////////////////////////
        if(ParamUtil.isHttps(httpRequest.getCompletedUrl())){
            initSSL(clientBuilder , httpRequest);
        }

        //因为每次都创建一个OkHttpClient，也就会创建一个连接池，如果高并发的话就会内存溢出
        clientBuilder.connectionPool(new ConnectionPool(5 , 30, TimeUnit.SECONDS));

        doWithBuilder(clientBuilder , httpRequest);

        ////////////////////////////////////ssl处理///////////////////////////////////
        return clientBuilder;
    }

    protected void initSSL(OkHttpClient.Builder clientBuilder , HttpRequest httpRequest){
        Config config = httpRequest.getConfig();
        HostnameVerifier hostnameVerifier = ObjectUtil.defaultIfNull(httpRequest.getHostnameVerifier(), config.sslHolder().getHostnameVerifier());
        SSLSocketFactory sslSocketFactory = ObjectUtil.defaultIfNull(httpRequest.getSslSocketFactory(), config.sslHolder().getSslSocketFactory());
        X509TrustManager x509TrustManager = ObjectUtil.defaultIfNull(httpRequest.getX509TrustManager(), config.sslHolder().getX509TrustManager());
        OkHttp3Util.initSSL(clientBuilder , hostnameVerifier , sslSocketFactory , x509TrustManager);
    }

    protected void doWithBuilder(OkHttpClient.Builder clientBuilder , HttpRequest httpRequest){ }
}
