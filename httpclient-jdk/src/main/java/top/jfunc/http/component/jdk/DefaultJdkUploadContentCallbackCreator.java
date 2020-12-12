package top.jfunc.http.component.jdk;

import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.component.AbstractUploadContentCallbackCreator;
import top.jfunc.http.request.UploadRequest;
import top.jfunc.http.util.NativeUtil;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultJdkUploadContentCallbackCreator extends AbstractUploadContentCallbackCreator<HttpURLConnection> {
    @Override
    protected ContentCallback<HttpURLConnection> doCreate(UploadRequest uploadRequest) throws IOException {
        return connection -> NativeUtil.upload0(connection , uploadRequest.getFormParams() , uploadRequest.getParamCharset() , uploadRequest.getFormFiles());
    }
}
