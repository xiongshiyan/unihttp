package top.jfunc.http.holderrequest;

import java.net.URL;

/**
 * 通用的代表没有更多参数的请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultRequest extends BaseHttpRequest<DefaultRequest> {
    public DefaultRequest(String url){
        super(url);
    }
    public DefaultRequest(URL url){
        super(url);
    }
    public DefaultRequest(){
    }

    public static HttpRequest of(){
        return new DefaultRequest();
    }
    public static HttpRequest of(URL url){
        return new DefaultRequest(url);
    }
    public static HttpRequest of(String url){
        return new DefaultRequest(url);
    }
}
