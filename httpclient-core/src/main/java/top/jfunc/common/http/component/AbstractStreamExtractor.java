package top.jfunc.common.http.component;

import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.utils.IoUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * 从请求响应中获取stream
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public abstract class AbstractStreamExtractor<S> implements StreamExtractor<S> {
    /**
     * 忽略body的话，就返回空stream
     * @param s 请求响应
     * @param httpRequest HttpRequest
     * @return InputStream
     * @throws IOException IOException
     */
    @Override
    public InputStream extract(S s, HttpRequest httpRequest) throws IOException {
        if(httpRequest.isIgnoreResponseBody()){
            return IoUtil.emptyStream();
        }
        return doExtract(s , httpRequest);
    }

    /**
     * 真正的获取响应中的InputStream
     * @param s S
     * @param httpRequest HttpRequest
     * @return InputStream
     * @throws IOException IOException
     */
    protected abstract InputStream doExtract(S s, HttpRequest httpRequest) throws IOException;
}