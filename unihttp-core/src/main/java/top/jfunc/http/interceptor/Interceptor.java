package top.jfunc.http.interceptor;

import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.response.ClientHttpResponse;

import java.io.IOException;

/**
 * 执行拦截器，begin、returnValue、exception、finally这几个节点
 * 典型的就是记录日志
 * @author xiongshiyan
 */
public interface Interceptor {
    /**
     * 执行之前拦截 before
     * @param httpRequest HttpRequest
     * @return 可能被修改后的HttpRequest
     * @throws IOException IOException
     */
    default HttpRequest onBefore(HttpRequest httpRequest) throws IOException{
        return httpRequest;
    }

    /**
     * 执行之后拦截 beforeReturn
     * @param httpRequest HttpRequest
     * @param clientHttpResponse 返回的值
     * @throws IOException IOException
     * @return ClientHttpResponse
     */
    default ClientHttpResponse onBeforeReturn(HttpRequest httpRequest, ClientHttpResponse clientHttpResponse) throws IOException{
        return clientHttpResponse;
    }

    /**
     * 发生异常的时候 exception
     * @param httpRequest HttpRequest
     * @param exception Exception
     */
    default void onError(HttpRequest httpRequest, Exception exception){}

    /**
     * finally执行 finally
     * @param httpRequest HttpRequest
     */
    default void onFinally(HttpRequest httpRequest){}
}
