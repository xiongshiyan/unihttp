package top.jfunc.common.http.base;

import top.jfunc.common.http.holder.*;
import top.jfunc.common.http.interceptor.CompositeInterceptor;
import top.jfunc.common.http.interceptor.Interceptor;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.response.ClientHttpResponse;
import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.CharsetUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;

/**
 * 全局公共配置
 * @author xiongshiyan at 2018/8/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class Config {
    /**
     * 未指定的时候，相关设置就是{@link top.jfunc.common.http.base.Config}默认的
     */
    public static final int UNSIGNED                        = -1;
    /**
     * 结果包含headers
     */
    public static final Boolean RETAIN_RESPONSE_HEADERS     = Boolean.TRUE;
    /**
     * 结果忽略body
     */
    public static final Boolean IGNORE_RESPONSE_BODY        = Boolean.TRUE;
    /**
     * 支持重定向
     */
    public static final Boolean FOLLOW_REDIRECTS            = Boolean.TRUE;
    /**
     * 默认的超时控制
     */
    public static final int DEFAULT_TIMEOUT                 = 15000;
    /**
     * 系统默认编码UTF-8
     */
    public static final String DEFAULT_CHARSET              = CharsetUtil.UTF_8;

    /**BaseUrl,如果设置了就在正常传送的URL之前添加上*/
    private String baseUrl                                  = null;
    /**连接超时时间*/
    private int defaultConnectionTimeout                    = DEFAULT_TIMEOUT;
    /**读数据超时时间*/
    private int defaultReadTimeout                          = DEFAULT_TIMEOUT;
    /**请求体编码*/
    private String defaultBodyCharset                       = DEFAULT_CHARSET;
    /**返回体编码*/
    private String defaultResultCharset                     = DEFAULT_CHARSET;
    /**返回结果中是否保留headers,默认不保留*/
    private boolean retainResponseHeaders                   = !RETAIN_RESPONSE_HEADERS;
    /**返回结果中是否忽略body,默认不忽略*/
    private boolean ignoreResponseBody                      = !IGNORE_RESPONSE_BODY;
    /**是否支持重定向,默认不支持*/
    private boolean followRedirects                         = !FOLLOW_REDIRECTS;
    /**代理设置,如果有就设置*/
    private ProxyInfo proxyInfo                             = null;
    /**SSL相关设置处理器*/
    private SSLHolder sslHolder                             = new DefaultSSLHolder2();
    /**默认的请求头,每个请求都会加上*/
    private HeaderHolder headerHolder                       = new DefaultHeaderHolder();
    /**默认的请求Query参数,每个请求都会加上*/
    private ParamHolder queryParamHolder                    = new DefaultParamHolder();
    /**拦截器链*/
    private CompositeInterceptor compositeInterceptor       = null;
    /**方法是否支持body的策略*/
    private MethodContentStrategy methodContentStrategy     = new DefaultMethodContentStrategy();


    /**配置冻结器*/
    private ConfigFrozen configFrozen = new ConfigFrozen();

    public static Config defaultConfig(){
        return new Config();
    }

    public Config freezeConfig(){
        configFrozen.freezeConfig();
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Config setBaseUrl(String baseUrl) {
        configFrozen.ensureConfigNotFreeze();
        this.baseUrl = baseUrl;
        return this;
    }

    public int getDefaultConnectionTimeout() {
        return defaultConnectionTimeout;
    }
    public int getConnectionTimeoutWithDefault(int connectionTimeout){
        return UNSIGNED == connectionTimeout ? getDefaultConnectionTimeout() : connectionTimeout;
    }
    public Config setDefaultConnectionTimeout(int defaultConnectionTimeout) {
        configFrozen.ensureConfigNotFreeze();
        this.defaultConnectionTimeout = defaultConnectionTimeout;
        return this;
    }

    public int getDefaultReadTimeout() {
        return defaultReadTimeout;
    }
    public int getReadTimeoutWithDefault(int readTimeout){
        return UNSIGNED == readTimeout ? getDefaultReadTimeout() : readTimeout;
    }
    public Config setDefaultReadTimeout(int defaultReadTimeout) {
        configFrozen.ensureConfigNotFreeze();
        this.defaultReadTimeout = defaultReadTimeout;
        return this;
    }

    public String getDefaultQueryCharset() {
        return queryParamHolder.getParamCharset();
    }

    public Config setDefaultQueryCharset(String defaultQueryCharset) {
        configFrozen.ensureConfigNotFreeze();
        this.queryParamHolder.setParamCharset(defaultQueryCharset);
        return this;
    }

    public String getDefaultBodyCharset() {
        return defaultBodyCharset;
    }

    public Config setDefaultBodyCharset(String defaultBodyCharset) {
        configFrozen.ensureConfigNotFreeze();
        this.defaultBodyCharset = defaultBodyCharset;
        return this;
    }

    public String getDefaultResultCharset() {
        return defaultResultCharset;
    }
    public Config setDefaultResultCharset(String defaultResultCharset) {
        configFrozen.ensureConfigNotFreeze();
        this.defaultResultCharset = defaultResultCharset;
        return this;
    }

    public Config retainResponseHeaders(Boolean retainResponseHeaders) {
        this.retainResponseHeaders = retainResponseHeaders;
        return this;
    }

    public boolean retainResponseHeaders() {
        return retainResponseHeaders;
    }

    public Config ignoreResponseBody(Boolean ignoreResponseBody) {
        this.ignoreResponseBody = ignoreResponseBody;
        return this;
    }

    public boolean ignoreResponseBody() {
        return ignoreResponseBody;
    }

    public Config followRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    public boolean followRedirects() {
        return followRedirects;
    }

    public ProxyInfo getDefaultProxyInfo() {
        return proxyInfo;
    }

    public Config setProxyInfo(ProxyInfo proxyInfo) {
        configFrozen.ensureConfigNotFreeze();
        this.proxyInfo = proxyInfo;
        return this;
    }

    public SSLHolder sslHolder(){
        return sslHolder;
    }

    public HeaderHolder headerHolder() {
        return headerHolder;
    }

    public ParamHolder queryParamHolder(){
        return queryParamHolder;
    }

    public CompositeInterceptor getCompositeInterceptor() {
        return compositeInterceptor;
    }

    public Config setCompositeInterceptor(CompositeInterceptor compositeInterceptor) {
        configFrozen.ensureConfigNotFreeze();
        this.compositeInterceptor = compositeInterceptor;
        return this;
    }
    public Config addInterceptor(Interceptor interceptor , Interceptor... interceptors){
        configFrozen.ensureConfigNotFreeze();
        if(null == this.compositeInterceptor){
            this.compositeInterceptor = new CompositeInterceptor();
        }
        this.compositeInterceptor.add(interceptor , interceptors);
        return this;
    }

    public HttpRequest onBeforeIfNecessary(HttpRequest httpRequest) throws IOException {
        if(hasInterceptors()){
            return compositeInterceptor.onBefore(httpRequest);
        }
        return httpRequest;
    }

    public ClientHttpResponse onBeforeReturnIfNecessary(HttpRequest httpRequest , ClientHttpResponse clientHttpResponse) throws IOException {
        if(hasInterceptors()){
            return compositeInterceptor.onBeforeReturn(httpRequest, clientHttpResponse);
        }
        return clientHttpResponse;
    }
    public void onErrorIfNecessary(HttpRequest httpRequest , Exception exception){
        if(hasInterceptors()){
            compositeInterceptor.onError(httpRequest, exception);
        }
    }
    public void onFinallyIfNecessary(HttpRequest httpRequest){
        if(hasInterceptors()){
            compositeInterceptor.onFinally(httpRequest);
        }
    }
    private boolean hasInterceptors(){
        return null != this.compositeInterceptor
                && this.compositeInterceptor.hasInterceptors();
    }

    public MethodContentStrategy getMethodContentStrategy() {
        return methodContentStrategy;
    }

    public Config setMethodContentStrategy(MethodContentStrategy methodContentStrategy) {
        configFrozen.ensureConfigNotFreeze();
        this.methodContentStrategy = methodContentStrategy;
        return this;
    }

    /**
     * clone一份，防止全局设置被无意修改
     */
    public MultiValueMap<String , String> getDefaultQueryParams(){
        MultiValueMap<String, String> params = queryParamHolder().get();
        if(null == params){
            return null;
        }
        //clone一份，防止全局设置被修改
        final ArrayListMultiValueMap<String, String> temp = new ArrayListMultiValueMap<>(params.size());
        params.forEachKeyValue(temp::add);
        return temp;
    }
    /**
     * clone一份，防止全局设置被无意修改
     */
    public MultiValueMap<String , String> getDefaultHeaders(){
        MultiValueMap<String, String> headers = headerHolder().get();
        if(null == headers){
            return null;
        }
        final ArrayListMultiValueMap<String, String> temp = new ArrayListMultiValueMap<>(headers.size());
        headers.forEachKeyValue(temp::add);
        return temp;
    }

    public static void throwExIfNull(Config config){
        if(null == config){
            throw new IllegalStateException("还未进行Config初始化，无法调用，请使用setConfig()方法设置");
        }
    }
}
