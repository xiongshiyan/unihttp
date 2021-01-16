package top.jfunc.http.interceptor;

import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.response.ClientHttpResponse;

import java.io.IOException;

/**
 * 拦截器适配器
 * 此类废弃，{@link Interceptor}利用default关键字，baseLine提升至jdk8
 * @author xiongshiyan at 2019/5/31 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public class InterceptorAdapter implements Interceptor {
    @Override
    public HttpRequest onBefore(HttpRequest httpRequest) throws IOException {
        return httpRequest;
    }

    @Override
    public ClientHttpResponse onBeforeReturn(HttpRequest httpRequest, ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse;
    }

    @Override
    public void onError(HttpRequest httpRequest, Exception exception) {

    }

    @Override
    public void onFinally(HttpRequest httpRequest) {

    }
}
