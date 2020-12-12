package top.jfunc.http.component.jdk;

import top.jfunc.http.component.AbstractHeaderExtractor;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.NativeUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultJdkHeaderExtractor extends AbstractHeaderExtractor<HttpURLConnection> {
    @Override
    public MultiValueMap<String, String> doExtractHeaders(HttpURLConnection connection, HttpRequest httpRequest) throws IOException {
        return NativeUtil.parseHeaders(connection);
    }
}
