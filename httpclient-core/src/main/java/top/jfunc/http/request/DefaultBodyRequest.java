package top.jfunc.http.request;

import top.jfunc.http.config.Config;

/**
 * 通用的StringBody请求
 * @author xiongshiyan at 2019/5/21 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultBodyRequest extends BaseHttpRequest<DefaultBodyRequest> implements MutableStringBodyRequest {
    public DefaultBodyRequest(String url){
        super(url);
    }
    public DefaultBodyRequest(){
    }
    public static MutableStringBodyRequest of(){
        return new DefaultBodyRequest();
    }
    public static MutableStringBodyRequest of(String url){
        return new DefaultBodyRequest(url);
    }
    public static MutableStringBodyRequest of(String url , String body , String contentType){
        DefaultBodyRequest defaultBodyRequest = new DefaultBodyRequest(url);
        defaultBodyRequest.setBody(body, contentType);
        return defaultBodyRequest;
    }
    private String body;
    private String bodyCharset = Config.DEFAULT_CHARSET;

    @Override
    public MutableStringBodyRequest setBody(String body) {
        this.body = body;
        return myself();
    }

    @Override
    public MutableStringBodyRequest setBody(String body, String contentType) {
        this.body = body;
        setContentType(contentType);
        return myself();
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getBodyCharset() {
        return bodyCharset;
    }

    @Override
    public MutableStringBodyRequest setBodyCharset(String bodyCharset) {
        this.bodyCharset = bodyCharset;
        return myself();
    }
}
