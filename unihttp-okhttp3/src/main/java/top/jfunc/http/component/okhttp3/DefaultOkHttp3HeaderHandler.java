package top.jfunc.http.component.okhttp3;

import okhttp3.Request;
import top.jfunc.http.component.AbstractHeaderHandler;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.OkHttp3Util;
import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultOkHttp3HeaderHandler extends AbstractHeaderHandler<Request.Builder> {
    @Override
    protected void doConfigHeaders(Request.Builder builder, HttpRequest httpRequest, MultiValueMap<String, String> handledHeaders) throws IOException {
        OkHttp3Util.setRequestHeaders(builder , httpRequest.getContentType() , handledHeaders);
    }
}
