package top.jfunc.http;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import top.jfunc.common.utils.IoUtil;
import top.jfunc.http.holderrequest.Request;
import top.jfunc.http.interceptor.Interceptor;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.response.ByteArrayClientHttpResponse;
import top.jfunc.http.response.ClientHttpResponse;
import top.jfunc.http.response.Response;
import top.jfunc.http.smart.ApacheSmartHttpClient;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class ByteArrayClientHttpResponseTest {
    @Rule
    public MockServerRule server = new MockServerRule(this, 50000);

    private SmartHttpClient smartHttpClient = new ApacheSmartHttpClient();
    {
        smartHttpClient.getConfig().addInterceptor(new Interceptor() {
            @Override
            public ClientHttpResponse onBeforeReturn(HttpRequest httpRequest, ClientHttpResponse clientHttpResponse) throws IOException {
                String read = IoUtil.read(clientHttpResponse.getBody(), "UTF-8");
                System.out.println(read);
                //在拦截器中需要使用到body，就返回此实例
                return new ByteArrayClientHttpResponse(read.getBytes(), clientHttpResponse);
                //return clientHttpResponse;
            }
        });
    }

    @Test
    public void testGet() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        String expected = "bodybodybodybodybody";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET") )
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        Request request = Request.of("http://localhost:50000/hello/{name}");
        request.routeParamHolder().put("name" , "John");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals(expected , response.getBodyAsString());
    }
}
