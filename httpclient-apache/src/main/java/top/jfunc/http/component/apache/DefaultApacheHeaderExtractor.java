package top.jfunc.http.component.apache;

import org.apache.http.HttpResponse;
import top.jfunc.http.component.AbstractHeaderExtractor;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.ApacheUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultApacheHeaderExtractor extends AbstractHeaderExtractor<HttpResponse> {
    @Override
    protected MultiValueMap<String, String> doExtractHeaders(HttpResponse httpResponse, HttpRequest httpRequest) throws IOException {
        return ApacheUtil.parseHeaders(httpResponse);
    }
}
