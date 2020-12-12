package top.jfunc.http.holderrequest;

import top.jfunc.http.holder.BodyHolder;
import top.jfunc.http.holder.DefaultBodyHolder;

import java.net.URL;

/**
 * 通用的StringBody请求
 * @author xiongshiyan at 2019/5/21 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultBodyRequest extends BaseHttpRequest<DefaultBodyRequest> implements MutableStringBodyRequest {
    public DefaultBodyRequest(String url){
        super(url);
    }
    public DefaultBodyRequest(URL url){
        super(url);
    }
    public DefaultBodyRequest(){
    }

    public static DefaultBodyRequest of(){
        return new DefaultBodyRequest();
    }
    public static DefaultBodyRequest of(URL url){
        return new DefaultBodyRequest(url);
    }


    public static DefaultBodyRequest of(String url){
        return new DefaultBodyRequest(url);
    }
    public static DefaultBodyRequest of(String url , String body , String contentType){
        DefaultBodyRequest defaultBodyRequest = new DefaultBodyRequest(url);
        defaultBodyRequest.setBody(body, contentType);
        return defaultBodyRequest;
    }
    /**
     * 针对POST等存在String body
     */
    private BodyHolder bodyHolder = new DefaultBodyHolder();

    @Override
    public DefaultBodyRequest setBody(String body , String contentType) {
        bodyHolder().setBody(body);
        setContentType(contentType);
        return myself();
    }

    @Override
    public BodyHolder bodyHolder() {
        return bodyHolder;
    }

    public DefaultBodyRequest setBodyHolder(BodyHolder bodyHolder) {
        this.bodyHolder = bodyHolder;
        return myself();
    }
}
