package top.jfunc.http.holderrequest;

import top.jfunc.http.holder.*;
import top.jfunc.http.request.AbstractHttpRequest;
import top.jfunc.common.utils.MultiValueMap;

import java.net.URL;
import java.util.Map;

/**
 * 基本请求参数实现:可用于无请求体如Get等的请求
 * T泛型为了变种的setter返回this便于链式调用
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public abstract class BaseHttpRequest<THIS extends BaseHttpRequest> extends AbstractHttpRequest<THIS> implements HttpRequest {
    /**
     * 请求的URL
     */
    private UrlHolder urlHolder = new DefaultUrlHolder();
    /**
     * 请求头
     */
    private HeaderHolder headerHolder = new DefaultHeaderHolder();
    /**
     * SSL相关设置
     */
    private SSLHolder sslHolder = new DefaultSSLHolder();
    /**
     * 属性设置器
     */
    private AttributeHolder attributeHolder = new DefaultAttributeHolder();

    public BaseHttpRequest(String url){this.urlHolder.setUrl(url);}
    public BaseHttpRequest(URL url){this.urlHolder.setUrl(url);}
    public BaseHttpRequest(){}

    @Override
    public THIS setUrl(String url) {
        urlHolder().setUrl(url);
        return myself();
    }
    @Override
    public THIS setUrl(URL url) {
        urlHolder().setUrl(url);
        return myself();
    }

    @Override
    public THIS addRouteParam(String key, String value) {
        routeParamHolder().put(key, value);
        return myself();
    }

    @Override
    public THIS setRouteParams(Map<String, String> routeParams) {
        routeParamHolder().setMap(routeParams);
        return myself();
    }

    @Override
    public THIS addQueryParam(String key, String value , String... values){
        queryParamHolder().add(key, value, values);
        return myself();
    }

    @Override
    public THIS setQueryParams(MultiValueMap<String, String> queryParams) {
        queryParamHolder().set(queryParams);
        return myself();
    }

    @Override
    public THIS setQueryParams(Map<String, String> queryParams) {
        queryParamHolder().set(queryParams);
        return myself();
    }

    @Override
    public HeaderHolder headerHolder() {
        return headerHolder;
    }

    @Override
    public THIS setHeader(String key, String value) {
        headerHolder().set(key, value);
        return myself();
    }

    @Override
    public THIS addHeader(String key, String value , String... values){
        headerHolder().add(key, value, values);
        return myself();
    }

    @Override
    public THIS setHeaders(Map<String, String> headers) {
        headerHolder().set(headers);
        return myself();
    }

    @Override
    public THIS setHeaders(MultiValueMap<String, String> headers) {
        headerHolder().set(headers);
        return myself();
    }

    @Override
    public UrlHolder urlHolder() {
        return urlHolder;
    }

    @Override
    public THIS urlHolder(UrlHolder urlHolder) {
        this.urlHolder = urlHolder;
        return myself();
    }

    @Override
    public SSLHolder sslHolder() {
        return sslHolder;
    }

    @Override
    public AttributeHolder attributeHolder() {
        return attributeHolder;
    }

    @Override
    public THIS addAttribute(String key, Object value) {
        attributeHolder().put(key, value);
        return myself();
    }


    ///////////////////////////////////通过设置Holder的实现改变默认行为///////////////////////////////////////

    public THIS setUrlHolder(UrlHolder urlHolder) {
        this.urlHolder = urlHolder;
        return myself();
    }

    public THIS setHeaderHolder(HeaderHolder headerHolder) {
        this.headerHolder = headerHolder;
        return myself();
    }

    public THIS setSslHolder(SSLHolder sslHolder) {
        this.sslHolder = sslHolder;
        return myself();
    }

    public THIS setAttributeHolder(AttributeHolder attributeHolder) {
        this.attributeHolder = attributeHolder;
        return myself();
    }
}
