package top.jfunc.http.component.jdk;

import top.jfunc.http.component.RequestSender;
import top.jfunc.http.request.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public class DefaultJdkConnectionSender implements RequestSender<HttpURLConnection , HttpURLConnection> {
    @Override
    public HttpURLConnection send(HttpURLConnection connection , HttpRequest httpRequest) throws IOException{
        connection.connect();
        return connection;
    }
}
