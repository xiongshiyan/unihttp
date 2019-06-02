package top.jfunc.common.http;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import top.jfunc.common.http.smart.*;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class MockServerTest{
    @Rule
    public MockServerRule server = new MockServerRule(this, 50000);

    private SmartHttpClient smartHttpClient = new ApacheSmartHttpClient();

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

        Request request = Request.of("http://localhost:50000/hello/{name}").addRouteParam("name" , "John");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals(expected , response.asString());
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

        Request request = Request.of("http://localhost:50000/hello/{name}")
                .addRouteParam("name" , "John");
        request.queryParamHolder().addParam("key1" , "value1").addParam("key2" , "value2");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals(expected , response.asString());
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

        Request request = Request.of("http://localhost:50000/hello/{name}").addRouteParam("name" , "John").setBody(expected);
        Response response = smartHttpClient.post(request);
        Assert.assertEquals(expected , response.asString());
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
                        .withHeader(Header.header("Content-Type" , HttpConstants.FORM_URLENCODED_WITH_DEFAULT_CHARSET)))
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        Request request = Request.of("http://localhost:50000/hello/{name}").addRouteParam("name" , "John");
        request.formParamHolder().addParam("key1" , "value1").addParam("key2" , "value2");
        Response response = smartHttpClient.post(request);
        Assert.assertEquals(expected , response.asString());
    }
    @Test
    public void testHeader() throws Exception{
        MockServerClient mockClient = new MockServerClient("127.0.0.1", 50000);
        mockClient.when(
                request()
                        .withPath("/hello/John")
                        .withMethod("GET").withHeader(org.mockserver.model.Header.header("sale" , "2")))
        .respond(
                response()
                        .withStatusCode(200)
                        .withHeader(org.mockserver.model.Header.header("xx" , "xx"))
        );

        Request request = Request.of("http://localhost:50000/hello/{name}").addRouteParam("name" , "John").setIncludeHeaders(true);
        request.headerHolder().addHeader("sale" , "2").addHeader("ca-xx" , "ca-xx");
        Response response = smartHttpClient.get(request);
        Assert.assertEquals("xx" , response.getOneHeader("xx"));
    }
}
