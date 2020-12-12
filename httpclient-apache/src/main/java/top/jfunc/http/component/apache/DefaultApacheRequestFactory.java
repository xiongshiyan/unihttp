package top.jfunc.http.component.apache;

import org.apache.http.client.methods.HttpRequestBase;
import top.jfunc.http.config.Config;
import top.jfunc.http.base.ProxyInfo;
import top.jfunc.http.component.AbstractRequesterFactory;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.ApacheUtil;
import top.jfunc.common.utils.ObjectUtil;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultApacheRequestFactory extends AbstractRequesterFactory<org.apache.http.HttpRequest> {

    @Override
    public org.apache.http.HttpRequest doCreate(HttpRequest httpRequest) throws IOException{
        Config config = httpRequest.getConfig();
        org.apache.http.HttpRequest request = ApacheUtil.createHttpUriRequest(httpRequest.getCompletedUrl(), httpRequest.getMethod());

        ProxyInfo proxyInfo = ObjectUtil.defaultIfNull(httpRequest.getProxyInfo(), config.getDefaultProxyInfo());

        //2.设置请求参数
        ApacheUtil.setRequestProperty((HttpRequestBase) request,
                config.getConnectionTimeoutWithDefault(httpRequest.getConnectionTimeout()),
                config.getReadTimeoutWithDefault(httpRequest.getReadTimeout()),
                proxyInfo);
        return request;
    }
}
