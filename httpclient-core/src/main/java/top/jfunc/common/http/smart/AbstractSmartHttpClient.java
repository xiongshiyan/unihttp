package top.jfunc.common.http.smart;

import top.jfunc.common.http.base.*;
import top.jfunc.common.http.component.*;
import top.jfunc.common.http.component.httprequest.*;
import top.jfunc.common.http.cookie.CookieAccessor;
import top.jfunc.common.http.cookie.DefaultCookieAccessor;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.request.StringBodyRequest;
import top.jfunc.common.http.request.UploadRequest;
import top.jfunc.common.utils.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * 提供了{@link SmartHttpClient}接口的实现
 * @see SimpleHttpClient
 * @see HttpRequestHttpClient
 * @author xiongshiyan at 2019/5/8 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public abstract class AbstractSmartHttpClient<CC> implements SmartHttpClient, SmartHttpTemplate<CC> {

    /**处理一般包含body的*/
    private BodyContentCallbackCreator<CC> bodyContentCallbackCreator;
    /**处理文件上传*/
    private UploadContentCallbackCreator<CC> uploadContentCallbackCreator;
    /**ContentCallback处理器*/
    private ContentCallbackHandler<CC> contentCallbackHandler;
    /**InputStream关闭器*/
    private Closer inputStreamCloser;
    /**Response关闭器*/
    ///private Closer responseCloser;

    private HttpRequestFactory httpRequestFactory;
    private StringBodyHttpRequestFactory stringBodyHttpRequestFactory;
    private UploadRequestFactory uploadRequestFactory;

    /**处理Cookie*/
    private CookieAccessor cookieAccessor;

    /**配置冻结器*/
    private ConfigFrozen configFrozen = new ConfigFrozen();
    /**保存系统默认配置*/
    private Config config = Config.defaultConfig();

    public AbstractSmartHttpClient(){
        init();
    }

    /**
     * 初始化方法，子类可以复写，但要先调用父类的
     */
    protected void init() {
        setContentCallbackHandler(new DefaultContentCallbackHandler<>());
        setInputStreamCloser(new DefaultCloser());
        ///setResponseCloser(new DefaultCloser());

        setHttpRequestFactory(new DefaultHttpRequestFactory());
        setStringBodyHttpRequestFactory(new DefaultStringBodyHttpRequestFactory());
        setUploadRequestFactory(new DefaultUploadRequestFactory());

        setCookieAccessor(new DefaultCookieAccessor());
    }


    @Override
    public <R> R get(HttpRequest httpRequest , ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest, Method.GET);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback , resultCallback);
    }

    @Override
    public <R> R post(StringBodyRequest stringBodyRequest , ResultCallback<R> resultCallback) throws IOException {
        init(stringBodyRequest, Method.POST);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(stringBodyRequest);
        return template(stringBodyRequest , contentCallback , resultCallback);
    }

    @Override
    public <R> R upload(UploadRequest uploadRequest , ResultCallback<R> resultCallback) throws IOException {
        init(uploadRequest, Method.POST);
        ContentCallback<CC> contentCallback = getUploadContentCallbackCreator().create(uploadRequest);
        return template(uploadRequest, contentCallback , resultCallback);
    }

    /*------------------------HTTP方法通用支持---------------------------*/

    @Override
    public <R> R http(HttpRequest httpRequest, Method method, ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest , method);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback, resultCallback);
    }



    /*------------------------HEAD---------------------------*/

    @Override
    public <R> R head(HttpRequest httpRequest, ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest , Method.HEAD);
        //必须要响应头
        httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        //设置忽略响应体
        httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback , resultCallback);
    }



    /*------------------------OPTIONS---------------------------*/

    @Override
    public <R> R options(HttpRequest httpRequest, ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest , Method.OPTIONS);
        //必须要响应头
        httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        //设置忽略响应体
        httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback , resultCallback);
    }



    /*------------------------PUT---------------------------*/

    @Override
    public <R> R put(StringBodyRequest stringBodyRequest, ResultCallback<R> resultCallback) throws IOException {
        init(stringBodyRequest , Method.PUT);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(stringBodyRequest);
        return template(stringBodyRequest , contentCallback , resultCallback);
    }



    /*------------------------PATCH---------------------------*/

    @Override
    public <R> R patch(StringBodyRequest stringBodyRequest, ResultCallback<R> resultCallback) throws IOException {
        init(stringBodyRequest , Method.PATCH);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(stringBodyRequest);
        return template(stringBodyRequest ,contentCallback , resultCallback);
    }



    /*------------------------DELETE---------------------------*/

    @Override
    public <R> R delete(HttpRequest httpRequest, ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest , Method.DELETE);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback , resultCallback);
    }



    /*------------------------TRACE---------------------------*/

    @Override
    public <R> R trace(HttpRequest httpRequest, ResultCallback<R> resultCallback) throws IOException {
        init(httpRequest , Method.TRACE);
        //必须要响应头
        httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        //设置忽略响应体
        httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        ContentCallback<CC> contentCallback = getBodyContentCallbackCreator().create(httpRequest);
        return template(httpRequest , contentCallback, resultCallback);
    }

    protected void init(HttpRequest httpRequest , Method method){
        if(null == httpRequest.getConfig()){
            httpRequest.setConfig(getConfig());
        }

        if(null == httpRequest.getMethod()){
            httpRequest.setMethod(method);
        }
    }


    /////////////////////////////////////////////////////SimpleHttpClient///////////////////////////////////////////////////////////////

    @Override
    public String get(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout, int readTimeout, String resultCharset) throws IOException{
        MultiValueMap<String , String> p = null;
        if(MapUtil.notEmpty(params)){
            p = ArrayListMultiValueMap.fromMap(params);
        }
        MultiValueMap<String , String> h = null;
        if(MapUtil.notEmpty(headers)){
            h = ArrayListMultiValueMap.fromMap(headers);
        }
        HttpRequest httpRequest = getHttpRequestFactory().create(url, p, h, connectTimeout, readTimeout, resultCharset);
        return get(httpRequest , ResponseExtractor::extractString);
    }

    @Override
    public String post(String url, String body, String contentType, Map<String, String> headers, int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException {
        StringBodyRequest stringBodyRequest = getStringBodyHttpRequestFactory().create(url, body, contentType, headers, connectTimeout, readTimeout, bodyCharset, resultCharset);
        return post(stringBodyRequest , ResponseExtractor::extractString);
    }

    @Override
    public byte[] getAsBytes(String url, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        HttpRequest httpRequest = getHttpRequestFactory().create(url, null, headers, connectTimeout, readTimeout, null);
        return get(httpRequest , ResponseExtractor::extractBytes);
    }

    @Override
    public File getAsFile(String url, MultiValueMap<String, String> headers, File file, int connectTimeout, int readTimeout) throws IOException {
        HttpRequest httpRequest = getHttpRequestFactory().create(url, null, headers, connectTimeout, readTimeout, null);
        return get(httpRequest , (statusCode, inputStream, rc, hd)-> IoUtil.copy2File(inputStream, file));
    }


    @Override
    public String upload(String url, MultiValueMap<String,String> headers, int connectTimeout, int readTimeout, String resultCharset, FormFile... files) throws IOException{
        UploadRequest uploadRequest = getUploadRequestFactory().create(url, null, headers, connectTimeout, readTimeout, resultCharset, files);
        return upload(uploadRequest , ResponseExtractor::extractString);
    }

    @Override
    public String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout, String resultCharset, FormFile... files) throws IOException {
        UploadRequest uploadRequest = getUploadRequestFactory().create(url, params, headers, connectTimeout, readTimeout, resultCharset, files);
        return upload(uploadRequest , ResponseExtractor::extractString);
    }


    protected String calculateResultCharset(HttpRequest httpRequest) {
        Config config = getConfig();
        return ObjectUtil.defaultIfNull(httpRequest.getResultCharset(), config.getDefaultResultCharset());
    }

    protected void closeInputStream(InputStream inputStream) throws IOException {
        getInputStreamCloser().close(inputStream);
    }

    public BodyContentCallbackCreator<CC> getBodyContentCallbackCreator() {
        return bodyContentCallbackCreator;
    }

    public void setBodyContentCallbackCreator(BodyContentCallbackCreator<CC> bodyContentCallbackCreator) {
        this.bodyContentCallbackCreator = Objects.requireNonNull(bodyContentCallbackCreator);
    }

    public UploadContentCallbackCreator<CC> getUploadContentCallbackCreator() {
        return uploadContentCallbackCreator;
    }

    public void setUploadContentCallbackCreator(UploadContentCallbackCreator<CC> uploadContentCallbackCreator) {
        this.uploadContentCallbackCreator = Objects.requireNonNull(uploadContentCallbackCreator);
    }

    public ContentCallbackHandler<CC> getContentCallbackHandler() {
        return contentCallbackHandler;
    }

    public void setContentCallbackHandler(ContentCallbackHandler<CC> contentCallbackHandler) {
        this.contentCallbackHandler = Objects.requireNonNull(contentCallbackHandler);
    }

    public Closer getInputStreamCloser() {
        return inputStreamCloser;
    }

    public void setInputStreamCloser(Closer inputStreamCloser) {
        this.inputStreamCloser = Objects.requireNonNull(inputStreamCloser);
    }

    ///
    /*public Closer getResponseCloser() {
        return responseCloser;
    }

    public void setResponseCloser(Closer responseCloser) {
        this.responseCloser = Objects.requireNonNull(responseCloser);
    }*/

    public HttpRequestFactory getHttpRequestFactory() {
        return httpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = Objects.requireNonNull(httpRequestFactory);
    }

    public StringBodyHttpRequestFactory getStringBodyHttpRequestFactory() {
        return stringBodyHttpRequestFactory;
    }

    public void setStringBodyHttpRequestFactory(StringBodyHttpRequestFactory stringBodyHttpRequestFactory) {
        this.stringBodyHttpRequestFactory = Objects.requireNonNull(stringBodyHttpRequestFactory);
    }

    public UploadRequestFactory getUploadRequestFactory() {
        return uploadRequestFactory;
    }

    public void setUploadRequestFactory(UploadRequestFactory uploadRequestFactory) {
        this.uploadRequestFactory = Objects.requireNonNull(uploadRequestFactory);
    }

    public CookieAccessor getCookieAccessor() {
        return cookieAccessor;
    }

    public void setCookieAccessor(CookieAccessor cookieAccessor) {
        this.cookieAccessor = Objects.requireNonNull(cookieAccessor);
    }


    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void setConfig(Config config) {
        configFrozen.ensureConfigNotFreeze();
        this.config = Objects.requireNonNull(config);
    }

    @Override
    public void freezeConfig() {
        //本身冻结
        configFrozen.freezeConfig();
        //Config冻结
        config.freezeConfig();
    }
}

