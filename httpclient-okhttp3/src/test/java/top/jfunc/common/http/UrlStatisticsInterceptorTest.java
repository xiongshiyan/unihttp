package top.jfunc.common.http;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import top.jfunc.common.http.holderrequest.DefaultRequest;
import top.jfunc.common.http.interceptor.UrlStatisticsInterceptor;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.smart.OkHttp3SmartHttpClient;

import java.util.HashSet;
import java.util.Set;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author xiongshiyan at 2019/12/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class UrlStatisticsInterceptorTest {
    @Rule
    public MockServerRule server = new MockServerRule(this, 50000);
    UrlStatisticsInterceptor statisticInterceptor = new UrlStatisticsInterceptor();
    UrlStatisticsInterceptor statisticInterceptor2 = new UrlStatisticsInterceptor(true , true);
    private static final String BODY = "{ message: 'incorrect username and password combination' }";

    @Test
    public void testOkhttp3() throws Exception{
        OkHttp3SmartHttpClient smartHttpClient = new OkHttp3SmartHttpClient();
        smartHttpClient.getConfig().addInterceptor(statisticInterceptor , statisticInterceptor2);
        testGet(smartHttpClient);
    }
    private void testGet(SmartHttpClient smartHttpClient) throws Exception{

        statisticInterceptor.start();

        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET") )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(BODY)
                );

        HttpRequest r1 = DefaultRequest.of("http://localhost:50000/hello/{name}");
        r1.addRouteParam("name" , "John");
        smartHttpClient.get(r1);

        HttpRequest r2 = DefaultRequest.of("http://localhost:50000/hello/{name}");
        r2.addRouteParam("name" , "Mark");
        smartHttpClient.get(r2);

        HttpRequest r3 = DefaultRequest.of("http://localhost:50000/hello/{name}");
        r3.addRouteParam("name" , "Mark");
        r3.addQueryParam("k1" , "k2");
        smartHttpClient.get(r3);

        HttpRequest r4 = DefaultRequest.of("http://localhost:50000/hell/ff");
        r4.addQueryParam("k1" , "k2");
        smartHttpClient.get(r4);

        statisticInterceptor.stop();
        statisticInterceptor2.stop();

        HttpRequest r5 = DefaultRequest.of("http://localhost:50000/hellrrr/ff");
        r5.addQueryParam("k1" , "k2");
        smartHttpClient.get(r5);


        Set<String> set = new HashSet<>(2);
        set.add("http://localhost:50000/hello/{name}");
        set.add("http://localhost:50000/hell/ff");
        Assert.assertEquals(set , statisticInterceptor.getStatisticsUrls());

        Set<String> set2 = new HashSet<>(2);
        set2.add("http://localhost:50000/hello/John");
        set2.add("http://localhost:50000/hello/Mark");
        set2.add("http://localhost:50000/hello/Mark?k1=k2");
        set2.add("http://localhost:50000/hell/ff?k1=k2");
        Assert.assertEquals(set2 , statisticInterceptor2.getStatisticsUrls());
    }
}
