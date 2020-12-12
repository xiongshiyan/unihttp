package top.jfunc.http.component.apache;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import top.jfunc.http.base.ContentCallback;
import top.jfunc.http.base.FormFile;
import top.jfunc.http.component.AbstractUploadContentCallbackCreator;
import top.jfunc.http.request.UploadRequest;
import top.jfunc.http.util.ApacheUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/7 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultApacheUploadContentCallbackCreator extends AbstractUploadContentCallbackCreator<HttpRequest> {
    @Override
    protected ContentCallback<HttpRequest> doCreate(UploadRequest uploadRequest) throws IOException {
        return request -> upload(request, uploadRequest.getFormParams() , uploadRequest.getParamCharset() , uploadRequest.getFormFiles());
    }

    protected void upload(HttpRequest request, MultiValueMap<String, String> params , String charset , Iterable<FormFile> formFiles) throws IOException{
        if(!(request instanceof HttpEntityEnclosingRequest)){
            return;
        }

        ApacheUtil.upload0((HttpEntityEnclosingRequest)request, params, charset, formFiles);
    }
}
