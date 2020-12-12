package top.jfunc.http.holder;

import top.jfunc.http.ssl.SSLSocketFactoryBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;

/**
 * 处置SSL相关设置的
 * @author xiongshiyan
 */
public interface SSLHolder{
    /**
     * HostnameVerifier
     * @return HostnameVerifier
     */
    HostnameVerifier getHostnameVerifier();

    /**
     * 设置HostnameVerifier
     * @param hostnameVerifier HostnameVerifier
     * @return this
     */
    SSLHolder setHostnameVerifier(HostnameVerifier hostnameVerifier);

    /**
     * SSLContext
     * @return SSLContext
     */
    SSLContext getSslContext();

    /**
     * 设置 SSLContext
     * @see SSLSocketFactoryBuilder#getSSLContext()
     * @see SSLSocketFactoryBuilder#getSSLContext(String, String)
     * @see SSLSocketFactoryBuilder#getSSLContext(InputStream, String)
     * @param sslContext SSLContext
     * @return this
     */
    SSLHolder setSslContext(SSLContext sslContext);

    /**
     * SSLSocketFactory
     * 因为一般地 SslSocketFactory 都是从sslContext产生出来的 ， 所以废弃其set方法，从sslContext产生
     * @return SSLSocketFactory
     */
    default SSLSocketFactory getSslSocketFactory(){
        SSLContext sslContext = getSslContext();
        return null == sslContext ? null : sslContext.getSocketFactory();
    }

    /**
     * X509TrustManager
     * @return X509TrustManager
     */
    X509TrustManager getX509TrustManager();

    /**
     * 设置X509TrustManager
     * @param x509TrustManager X509TrustManager
     * @return this
     */
    SSLHolder setX509TrustManager(X509TrustManager x509TrustManager);

}
