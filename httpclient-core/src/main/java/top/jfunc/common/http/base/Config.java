package top.jfunc.common.http.base;

import top.jfunc.common.http.HttpConstants;
import top.jfunc.common.http.ParamUtil;
import top.jfunc.common.http.base.ssl.DefaultTrustManager2;
import top.jfunc.common.http.base.ssl.SSLSocketFactoryBuilder;
import top.jfunc.common.http.base.ssl.TrustAnyHostnameVerifier;
import top.jfunc.common.http.interceptor.CompositeInterceptor;
import top.jfunc.common.http.interceptor.Interceptor;
import top.jfunc.common.http.kv.Header;
import top.jfunc.common.http.kv.Parameter;
import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.ArrayListMultimap;
import top.jfunc.common.utils.MultiValueMap;
import top.jfunc.common.utils.StrUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.net.CookieHandler;
import java.util.Map;

/**
 * 全局公共配置
 * @author xiongshiyan at 2018/8/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class Config {
    /**
     * BaseUrl,如果设置了就在正常传送的URL之前添加上
     */
    private String baseUrl                                  = null;
    /**
     * 连接超时时间
     */
    private Integer defaultConnectionTimeout                = HttpConstants.DEFAULT_CONNECT_TIMEOUT;
    /**
     * 读数据超时时间
     */
    private Integer defaultReadTimeout                      = HttpConstants.DEFAULT_READ_TIMEOUT;
    /**
     * 请求体编码
     */
    private String defaultBodyCharset                       = HttpConstants.DEFAULT_CHARSET;
    /**
     * 返回体编码
     */
    private String defaultResultCharset                     = HttpConstants.DEFAULT_CHARSET;
    /**
     * 代理设置,如果有就设置
     * Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));
     */
    private ProxyInfo proxyInfo = null;
    /**
     * HostnameVerifier
     */
    private HostnameVerifier hostnameVerifier               = new TrustAnyHostnameVerifier();
    /**
     * SSLContext
     */
    private SSLContext sslContext                           = getDefaultSSLContext();
    /**
     * SSLSocketFactory
     */
    private SSLSocketFactory sslSocketFactory               = getDefaultSSLSocketFactory();
    /**
     * X509TrustManager
     */
    private X509TrustManager x509TrustManager               = new DefaultTrustManager2();
    /**
     * 默认的请求头,每个请求都会加上
     */
    private MultiValueMap<String,String> defaultHeaders = null;
    /**
     * 默认的请求Query参数,每个请求都会加上
     */
    private MultiValueMap<String,String> defaultQueryParams = null;
    /**
     * CookieHandler，只要设置了就表示支持Cookie
     */
    private CookieHandler cookieHandler = null;
    /**
     * 拦截器链
     */
    private CompositeInterceptor compositeInterceptor;

    public static Config defaultConfig(){
        return new Config();
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public Config setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String addBaseUrlIfNecessary(String inputUrl){
        return ParamUtil.addBaseUrlIfNecessary(getBaseUrl() , inputUrl);
    }

    public Integer getDefaultConnectionTimeout() {
        return defaultConnectionTimeout;
    }

    public Config setDefaultConnectionTimeout(Integer defaultConnectionTimeout) {
        this.defaultConnectionTimeout = defaultConnectionTimeout;
        return this;
    }

    public Integer getConnectionTimeoutWithDefault(Integer connectionTimeout){
        return null == connectionTimeout ? defaultConnectionTimeout : connectionTimeout;
    }

    public Integer getDefaultReadTimeout() {
        return defaultReadTimeout;
    }

    public Config setDefaultReadTimeout(Integer defaultReadTimeout) {
        this.defaultReadTimeout = defaultReadTimeout;
        return this;
    }

    public Integer getReadTimeoutWithDefault(Integer readTimeout){
        return null == readTimeout ? defaultReadTimeout : readTimeout;
    }
    public String getDefaultBodyCharset() {
        return defaultBodyCharset;
    }

    public Config setDefaultBodyCharset(String defaultBodyCharset) {
        this.defaultBodyCharset = defaultBodyCharset;
        return this;
    }

    public String getBodyCharsetWithDefault(String bodyCharset){
        return StrUtil.isEmpty(bodyCharset) ? defaultBodyCharset : bodyCharset;
    }

    public String getDefaultResultCharset() {
        return defaultResultCharset;
    }

    public Config setDefaultResultCharset(String defaultResultCharset) {
        this.defaultResultCharset = defaultResultCharset;
        return this;
    }

    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public Config setProxyInfo(ProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
        return this;
    }

    public ProxyInfo getProxyInfoWithDefault(ProxyInfo proxyInfo){
        return null == proxyInfo ? this.proxyInfo : proxyInfo;
    }

    public String getResultCharsetWithDefault(String resultCharset){
        return StrUtil.isEmpty(resultCharset) ? defaultResultCharset : resultCharset;
    }
    public Config setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public Config setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public Config setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public Config setX509TrustManager(X509TrustManager x509TrustManager) {
        this.x509TrustManager = x509TrustManager;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public HostnameVerifier getHostnameVerifierWithDefault(HostnameVerifier hostnameVerifier){
        return null == hostnameVerifier ? this.hostnameVerifier : hostnameVerifier;
    }

    public SSLContext getSSLContext() {
        return sslContext;
    }

    public SSLContext getSSLContextWithDefault(SSLContext sslContext) {
        return null == sslContext ? this.sslContext : sslContext;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return sslSocketFactory;
    }

    public SSLSocketFactory getSSLSocketFactoryWithDefault(SSLSocketFactory sslSocketFactory) {
        return null == sslSocketFactory ? this.sslSocketFactory : sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }

    public X509TrustManager getX509TrustManagerWithDefault(X509TrustManager x509TrustManager){
        return null == x509TrustManager ? this.x509TrustManager : x509TrustManager;
    }

    public Config setDefaultHeaders(MultiValueMap<String, String> headers) {
        this.defaultHeaders = headers;
        return this;
    }
    public Config setDefaultHeaders(ArrayListMultimap<String, String> headers) {
        this.defaultHeaders = ArrayListMultiValueMap.fromMap(headers);
        return this;
    }
    public Config setDefaultHeaders(Map<String, String> headers) {
        this.defaultHeaders = ArrayListMultiValueMap.fromMap(headers);
        return this;
    }
    public Config addDefaultHeader(String key, String value){
        initHeaders();
        this.defaultHeaders.add(key, value);
        return this;
    }
    public Config addHeader(String key, String value , String... values){
        initHeaders();
        this.defaultHeaders.add(key, value);
        for (String val : values) {
            this.defaultHeaders.add(key , val);
        }
        return this;
    }
    public Config addHeader(String key, Iterable<String> values){
        initHeaders();
        for (String value : values) {
            this.defaultHeaders.add(key , value);
        }
        return this;
    }
    public Config addHeader(Header header , Header... headers){
        addHeader(header.getKey() , header.getValue());
        for (Header h : headers) {
            addHeader(h.getKey() , h.getValue());
        }
        return this;
    }
    public Config addHeader(Iterable<Header> headers){
        for (Header header : headers) {
            addHeader(header.getKey() , header.getValue());
        }
        return this;
    }
    public Config addHeader(Map.Entry<String , Iterable<String>> entry , Map.Entry<String , Iterable<String>>... entries){
        addHeader(entry.getKey() , entry.getValue());
        for (Map.Entry<String , Iterable<String>> header : entries) {
            addHeader(header.getKey() , header.getValue());
        }
        return this;
    }
    private void initHeaders(){
        if(null == this.defaultHeaders){
            this.defaultHeaders = new ArrayListMultiValueMap<>(2);
        }
    }

    public MultiValueMap<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public Config setDefaultQueryParams(MultiValueMap<String, String> defaultQueryParams) {
        this.defaultQueryParams = defaultQueryParams;
        return this;
    }
    public Config setDefaultQueryParams(ArrayListMultimap<String, String> defaultQueryParams) {
        this.defaultQueryParams = ArrayListMultiValueMap.fromMap(defaultQueryParams);
        return this;
    }
    public Config setDefaultQueryParams(Map<String, String> defaultQueryParams) {
        this.defaultQueryParams = ArrayListMultiValueMap.fromMap(defaultQueryParams);
        return this;
    }
    public Config addDefaultQueryParams(String key, String value){
        initQueryParams();
        this.defaultQueryParams.add(key, value);
        return this;
    }
    public Config addQueryParam(String key, String value , String... values){
        initQueryParams();
        this.defaultQueryParams.add(key , value);
        for (String val : values) {
            this.defaultQueryParams.add(key , val);
        }
        return this;
    }
    public Config addQueryParam(String key, Iterable<String> values){
        initQueryParams();
        for (String value : values) {
            this.defaultQueryParams.add(key , value);
        }
        return this;
    }
    public Config addQueryParam(Parameter parameter , Parameter... parameters){
        addQueryParam(parameter.getKey() , parameter.getValue());
        for (Parameter param : parameters) {
            addQueryParam(param.getKey() , param.getValue());
        }
        return this;
    }
    public Config addQueryParam(Iterable<Parameter> parameters){
        for (Map.Entry<String , Iterable<String>> parameter : parameters) {
            addQueryParam(parameter.getKey() , parameter.getValue());
        }
        return this;
    }
    public Config addQueryParam(Map.Entry<String , Iterable<String>> entry , Map.Entry<String , Iterable<String>>... entries){
        addQueryParam(entry.getKey() , entry.getValue());
        for (Map.Entry<String , Iterable<String>> parameter : entries) {
            addQueryParam(parameter.getKey() , parameter.getValue());
        }
        return this;
    }
    private void initQueryParams(){
        if(null == this.defaultQueryParams){
            this.defaultQueryParams = new ArrayListMultiValueMap<>(2);
        }
    }

    public MultiValueMap<String, String> getDefaultQueryParams() {
        return defaultQueryParams;
    }

    private SSLContext getDefaultSSLContext(){
        return SSLSocketFactoryBuilder.create().getSSLContext();
    }
    private SSLSocketFactory getDefaultSSLSocketFactory(){
        if(null == sslContext){
            return SSLSocketFactoryBuilder.create().getSSLContext().getSocketFactory();
        }
        return sslContext.getSocketFactory();
    }

    public CookieHandler getCookieHandler() {
        return cookieHandler;
    }

    public Config setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
        if(null == CookieHandler.getDefault()){
            CookieHandler.setDefault(cookieHandler);
        }
        return this;
    }

    public CompositeInterceptor getCompositeInterceptor() {
        return compositeInterceptor;
    }

    public Config setCompositeInterceptor(CompositeInterceptor compositeInterceptor) {
        this.compositeInterceptor = compositeInterceptor;
        return this;
    }
    public Config addInterceptor(Interceptor... interceptors){
        if(null == this.compositeInterceptor){
            this.compositeInterceptor = new CompositeInterceptor(interceptors);
        }else {
            this.compositeInterceptor.add(interceptors);
        }
        return this;
    }

    public boolean hasInterceptors(){
        return null != this.compositeInterceptor
                && this.compositeInterceptor.hasInterceptors();
    }
}
