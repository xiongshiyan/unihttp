package top.jfunc.http.request;

/**
 * @author xiongshiyan at 2019/7/5 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultRequest extends BaseHttpRequest<DefaultRequest> {
    public DefaultRequest(String url){
        super(url);
    }
    public DefaultRequest(){
    }
    public static HttpRequest of(String url){
        return new DefaultRequest(url);
    }
    public static HttpRequest of(){
        return new DefaultRequest();
    }
}
