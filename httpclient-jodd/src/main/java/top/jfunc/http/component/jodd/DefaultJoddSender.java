package top.jfunc.http.component.jodd;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import top.jfunc.http.component.RequestSender;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public class DefaultJoddSender implements RequestSender<HttpRequest , HttpResponse> {
    @Override
    public HttpResponse send(HttpRequest request , top.jfunc.http.request.HttpRequest httpRequest) throws IOException{
        return request.send();
    }
}
