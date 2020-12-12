package top.jfunc.http.component;

import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.request.UploadRequest;

import java.io.IOException;

/**
 * @see top.jfunc.http.smart.AbstractSmartHttpClient
 * @author xiongshiyan at 2020/10/15 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class CompositeContentCallBackCreator<CC> implements ContentCallbackCreator<CC> {
    private ContentCallbackCreator<CC> bodyContentCallbackCreator;
    private ContentCallbackCreator<CC> uploadContentCallbackCreator;


    public CompositeContentCallBackCreator(ContentCallbackCreator<CC> bodyContentCallbackCreator,
                                           ContentCallbackCreator<CC> uploadContentCallbackCreator) {
        this.bodyContentCallbackCreator = bodyContentCallbackCreator;
        this.uploadContentCallbackCreator = uploadContentCallbackCreator;
    }

    @Override
    public ContentCallback<CC> create(HttpRequest httpRequest) throws IOException {
        if(httpRequest instanceof UploadRequest){
            return getUploadContentCallbackCreator().create(httpRequest);
        }
        return getBodyContentCallbackCreator().create(httpRequest);
    }

    public ContentCallbackCreator<CC> getBodyContentCallbackCreator() {
        return bodyContentCallbackCreator;
    }

    public ContentCallbackCreator<CC> getUploadContentCallbackCreator() {
        return uploadContentCallbackCreator;
    }
}
