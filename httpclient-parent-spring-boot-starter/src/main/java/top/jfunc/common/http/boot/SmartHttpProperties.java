package top.jfunc.common.http.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.jfunc.common.http.HttpConstants;

import java.util.Map;

/**
 * @see top.jfunc.common.http.base.Config
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@ConfigurationProperties("spring.http.smart")
public class SmartHttpProperties implements SmartHttpConfig{
    /**
     * BaseUrl,如果设置了就在正常传送的URL之前添加上
     */
    private String baseUrl                                  = null;
    /**
     * 连接超时时间
     */
    private Integer defaultConnectionTimeout                = HttpConstants.DEFAULT_CONNECT_TIMEOUT;
    /**
     * 读数据超时时间
     */
    private Integer defaultReadTimeout                      = HttpConstants.DEFAULT_READ_TIMEOUT;
    /**
     * 请求体编码
     */
    private String defaultBodyCharset                       = HttpConstants.DEFAULT_CHARSET;
    /**
     * 返回体编码
     */
    private String defaultResultCharset                     = HttpConstants.DEFAULT_CHARSET;
    /**
     * 默认headers
     */
    private Map<String , String> defaultHeaders             = null;
    /**
     * 默认查询参数
     */
    private Map<String , String> defaultQueryParams         = null;
    /**
     * 代理设置
     */
    private SmartHttpConfig.Proxy proxy                                     = null;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Integer getDefaultConnectionTimeout() {
        return defaultConnectionTimeout;
    }

    public void setDefaultConnectionTimeout(Integer defaultConnectionTimeout) {
        this.defaultConnectionTimeout = defaultConnectionTimeout;
    }

    @Override
    public Integer getDefaultReadTimeout() {
        return defaultReadTimeout;
    }

    public void setDefaultReadTimeout(Integer defaultReadTimeout) {
        this.defaultReadTimeout = defaultReadTimeout;
    }

    @Override
    public String getDefaultBodyCharset() {
        return defaultBodyCharset;
    }

    public void setDefaultBodyCharset(String defaultBodyCharset) {
        this.defaultBodyCharset = defaultBodyCharset;
    }

    @Override
    public String getDefaultResultCharset() {
        return defaultResultCharset;
    }

    public void setDefaultResultCharset(String defaultResultCharset) {
        this.defaultResultCharset = defaultResultCharset;
    }

    @Override
    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    @Override
    public Map<String, String> getDefaultQueryParams() {
        return defaultQueryParams;
    }

    public void setDefaultQueryParams(Map<String, String> defaultQueryParams) {
        this.defaultQueryParams = defaultQueryParams;
    }

    @Override
    public SmartHttpConfig.Proxy getProxy() {
        return proxy;
    }

    public void setProxy(SmartHttpConfig.Proxy proxy) {
        this.proxy = proxy;
    }
}
