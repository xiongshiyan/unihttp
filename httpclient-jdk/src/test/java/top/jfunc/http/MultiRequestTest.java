package top.jfunc.http;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import top.jfunc.http.SmartHttpClient;
import top.jfunc.http.base.Config;
import top.jfunc.http.base.MediaType;
import top.jfunc.http.holderrequest.DefaultBodyRequest;
import top.jfunc.http.holderrequest.DefaultRequest;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.request.StringBodyRequest;
import top.jfunc.http.holderrequest.DefaultFormBodyRequest;
import top.jfunc.http.smart.NativeSmartHttpClient;
import top.jfunc.http.response.Response;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class MultiRequestTest {
    @Rule
    public MockServerRule server = new MockServerRule(this, 50000);

    private SmartHttpClient smartHttpClient = new NativeSmartHttpClient();

    @Test
    public void testGet() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        String expected = "{ message: 'incorrect username and password combination' }";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET") )
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        HttpRequest request = DefaultRequest.of("http://localhost:50000/hello/{name}");
        request.addRouteParam("name" , "John");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals(expected , response.getBodyAsString());
    }
    @Test
    public void testGetQueryParam() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        String expected = "{ message: 'incorrect username and password combination' }";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET")
                        .withQueryStringParameters(Parameter.param("key1" , "value1") ,
                                Parameter.param("key2" , "value2")))
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        HttpRequest request = DefaultRequest.of("http://localhost:50000/hello/{name}");
        request.addRouteParam("name" , "John");
        request.addQueryParam("key1" , "value1").addQueryParam("key2" , "value2");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals(expected , response.getBodyAsString());
    }
    @Test
    public void testPost() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        String expected = "{ message: 'incorrect username and password combination' }";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("POST").withBody(expected))
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        StringBodyRequest request = DefaultBodyRequest.of("http://localhost:50000/hello/{name}").setBody(expected);
        request.addRouteParam("name" , "John");
        Response response = smartHttpClient.post(request);
        Assert.assertEquals(expected , response.getBodyAsString());
    }
    @Test
    public void testPostForm() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        String expected = "{ message: 'incorrect username and password combination' }";
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("POST")
                        .withBody("key1=value1&key2=value2")
                        .withHeader(Header.header("Content-Type" , MediaType.APPLICATION_FORM_DATA.withCharset(Config.DEFAULT_CHARSET).toString())))
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        DefaultFormBodyRequest request = DefaultFormBodyRequest.of("http://localhost:50000/hello/{name}");
        request.routeParamHolder().put("name" , "John");
        request.formParamHolder().add("key1" , "value1").add("key2" , "value2");
        Response response = smartHttpClient.post(request);
        Assert.assertEquals(expected , response.getBodyAsString());
    }
    @Test
    public void testHeader() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET").withHeader(Header.header("sale" , "2")))
        .respond(
                response()
                        .withStatusCode(200)
                        .withHeader(Header.header("xx" , "xx"))
        );

        HttpRequest request = DefaultRequest.of("http://localhost:50000/hello/{name}").retainResponseHeaders(true);
        request.addRouteParam("name" , "John");
        request.addHeader("sale" , "2").addHeader("ca-xx" , "ca-xx");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals("xx" , response.getFirstHeader("xx"));
    }
}
