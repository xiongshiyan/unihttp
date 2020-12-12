package top.jfunc.http.component.jodd;

import jodd.http.HttpRequest;
import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.component.AbstractUploadContentCallbackCreator;
import top.jfunc.http.request.UploadRequest;
import top.jfunc.http.util.JoddUtil;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultJoddUploadContentCallbackCreator extends AbstractUploadContentCallbackCreator<HttpRequest> {
    @Override
    protected ContentCallback<HttpRequest> doCreate(UploadRequest uploadRequest) throws IOException {
        return httpRequest -> JoddUtil.upload0(httpRequest , uploadRequest.getFormParams() , uploadRequest.getParamCharset() , uploadRequest.getFormFiles());
    }
}
