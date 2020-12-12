package top.jfunc.http.component.jdk;

import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.component.AbstractBodyContentCallbackCreator;
import top.jfunc.http.request.StringBodyRequest;
import top.jfunc.http.util.NativeUtil;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultJdkBodyContentCallbackCreator extends AbstractBodyContentCallbackCreator<HttpURLConnection> {
    @Override
    protected ContentCallback<HttpURLConnection> doCreate(StringBodyRequest stringBodyRequest) throws IOException {
        return connection -> NativeUtil.writeContent(connection , stringBodyRequest.getBody() , stringBodyRequest.calculateBodyCharset());
    }
}
