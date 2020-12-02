package top.jfunc.common.http.component.okhttp3;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.jfunc.common.http.base.ContentCallback;
import top.jfunc.common.http.component.*;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.response.ClientHttpResponse;

import java.io.IOException;

/**
 * OkHttp3实现的处理
 * @author xiongshiyan
 * @see 2020.12.01
 * @since 1.2.12
 */
public class OkHttp3HttpRequestExecutor extends BaseHttpRequestExecutor<Request.Builder, Response> implements HttpRequestExecutor<Request.Builder> {
    private RequesterFactory<OkHttpClient> okHttpClientFactory;
    private RequesterFactory<Request.Builder> requestBuilderFactory;

    public OkHttp3HttpRequestExecutor() {
        super(new DefaultOkHttp3StreamExtractor(), new DefaultOkHttp3HeaderExtractor(), new DefaultOkHttp3HeaderHandler());
        this.okHttpClientFactory = new SingleOkHttp3ClientFactory();
        this.requestBuilderFactory = new DefaultOkHttp3RequestBuilderFactory();
    }

    public OkHttp3HttpRequestExecutor(StreamExtractor<Response> responseStreamExtractor,
                                      HeaderExtractor<Response> responseHeaderExtractor,
                                      RequesterFactory<OkHttpClient> okHttpClientFactory,
                                      RequesterFactory<Request.Builder> requestBuilderFactory,
                                      HeaderHandler<Request.Builder> requestBuilderHeaderHandler) {
        super(responseStreamExtractor, responseHeaderExtractor, requestBuilderHeaderHandler);
        this.okHttpClientFactory = okHttpClientFactory;
        this.requestBuilderFactory = requestBuilderFactory;
    }

    public OkHttp3HttpRequestExecutor(ContentCallbackHandler<Request.Builder> contentCallbackHandler,
                                      StreamExtractor<Response> responseStreamExtractor,
                                      HeaderExtractor<Response> responseHeaderExtractor,
                                      HeaderHandler<Request.Builder> requestHeaderHandler,
                                      RequesterFactory<OkHttpClient> okHttpClientFactory,
                                      RequesterFactory<Request.Builder> requestBuilderFactory) {
        super(contentCallbackHandler, responseStreamExtractor, responseHeaderExtractor, requestHeaderHandler);
        this.okHttpClientFactory = okHttpClientFactory;
        this.requestBuilderFactory = requestBuilderFactory;
    }

    @Override
    public ClientHttpResponse execute(HttpRequest httpRequest, ContentCallback<Request.Builder> contentCallback) throws IOException{
        //1.创建并配置OkHttpClient
        OkHttpClient client = getOkHttpClientFactory().create(httpRequest);

        //2.1设置URL
        Request.Builder builder = getRequestBuilderFactory().create(httpRequest);

        //2.2处理请求体
        handleBody(builder , contentCallback , httpRequest);

        //2.3设置headers
        handleHeaders(builder , httpRequest);

        //3.执行请求
        Response response = getResponse(client, builder, httpRequest);

        return new OkHttp3ClientHttpResponse(response, httpRequest, getResponseStreamExtractor(), getResponseHeaderExtractor());
    }

    protected Response getResponse(OkHttpClient client, Request.Builder builder, HttpRequest httpRequest) throws IOException {
        return client.newCall(builder.build()).execute();
    }

    public RequesterFactory<OkHttpClient> getOkHttpClientFactory() {
        return okHttpClientFactory;
    }

    public RequesterFactory<Request.Builder> getRequestBuilderFactory() {
        return requestBuilderFactory;
    }
}
