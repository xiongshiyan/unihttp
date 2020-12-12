package top.jfunc.http.component;

import top.jfunc.http.SmartHttpClient;
import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.request.HttpRequest;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface ContentCallbackCreator<CC> {
    /**
     * 创建body设置器(为{@link SmartHttpClient}体系使用)
     * @param httpRequest HttpRequest
     * @return ContentCallback<CC> 可能为null，当不支持BODY的时候
     * @throws IOException IOException
     */
    ContentCallback<CC> create(HttpRequest httpRequest) throws IOException;
}
