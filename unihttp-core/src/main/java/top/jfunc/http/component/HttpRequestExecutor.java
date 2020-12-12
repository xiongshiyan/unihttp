package top.jfunc.http.component;

import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.response.ClientHttpResponse;

import java.io.IOException;

/**
 * 执行请求获取响应
 * @author xiongshiyan
 */
@FunctionalInterface
public interface HttpRequestExecutor<CC> {
    /**
     * 执行请求获取响应
     * @param httpRequest 代表一个请求的所有参数
     * @param contentCallback 对body的处理
     * @return http响应
     * @throws IOException IOException
     */
    ClientHttpResponse execute(HttpRequest httpRequest, ContentCallback<CC> contentCallback) throws IOException;
}
