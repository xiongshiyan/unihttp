package top.jfunc.common.http.component;

import top.jfunc.common.http.base.ContentCallback;
import top.jfunc.common.http.request.HttpRequest;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public interface ContentCallbackHandler<CC> {
    /**
     * 处理contentCallback
     * @param cc 给谁设置body
     * @param contentCallback ContentCallback,可能为null
     * @param httpRequest HttpRequest
     * @throws IOException IOException
     */
    void handle(CC cc , ContentCallback<CC> contentCallback , HttpRequest httpRequest) throws IOException;
}
