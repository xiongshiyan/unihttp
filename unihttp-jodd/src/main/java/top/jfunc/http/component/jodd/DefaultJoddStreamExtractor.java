package top.jfunc.http.component.jodd;

import jodd.http.HttpResponse;
import top.jfunc.http.component.AbstractStreamExtractor;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.common.utils.ArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultJoddStreamExtractor extends AbstractStreamExtractor<HttpResponse> {
    @Override
    public InputStream doExtract(HttpResponse httpResponse, HttpRequest httpRequest) throws IOException {
        byte[] bodyBytes = httpResponse.bodyBytes();
        return ArrayUtil.isEmpty(bodyBytes) ? null : new ByteArrayInputStream(bodyBytes);
    }
}
