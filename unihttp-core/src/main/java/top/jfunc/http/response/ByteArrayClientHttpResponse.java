package top.jfunc.http.response;

import top.jfunc.common.utils.MultiValueMap;
import top.jfunc.http.interceptor.Interceptor;
import top.jfunc.http.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 因为{@link ClientHttpResponse#getBody()}只能被使用一次，
 * 所以在使用了其body之后还需要返回{@link ClientHttpResponse}的时候就可以用此类了。
 * 例如在{@link Interceptor#onBeforeReturn(HttpRequest, ClientHttpResponse)}中需要访问body，返回此类的实例即可。
 * @author xiongshiyan at 2021/1/19 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class ByteArrayClientHttpResponse implements ClientHttpResponse{
    private byte[] bodyBytes;
    private ClientHttpResponse clientHttpResponse;

    public ByteArrayClientHttpResponse(byte[] bodyBytes, ClientHttpResponse clientHttpResponse) {
        this.bodyBytes = bodyBytes;
        this.clientHttpResponse = clientHttpResponse;
    }

    @Override
    public int getStatusCode() throws IOException {
        return clientHttpResponse.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return clientHttpResponse.getStatusText();
    }

    @Override
    public void close() throws IOException {
        //help GC
        this.bodyBytes = null;
        clientHttpResponse.close();
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(bodyBytes);
    }

    @Override
    public MultiValueMap<String, String> getHeaders() throws IOException {
        return clientHttpResponse.getHeaders();
    }
}
