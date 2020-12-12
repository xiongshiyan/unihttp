package top.jfunc.http.component;

import top.jfunc.http.request.HttpRequest;

import java.io.IOException;

/**
 * 处理请求header
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface HeaderHandler<C> {
    /**
     * 处理header
     * @param target 设置给谁
     * @param httpRequest HttpRequest
     * @throws IOException IOException
     */
    void configHeaders(C target, HttpRequest httpRequest)  throws IOException;
}
