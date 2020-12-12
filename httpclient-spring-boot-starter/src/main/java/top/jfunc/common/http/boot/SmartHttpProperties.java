package top.jfunc.common.http.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.jfunc.common.http.HttpConstants;

import java.util.List;
import java.util.Map;

/**
 * @see top.jfunc.http.base.Config
 * @author xiongshiyan at 2019/5/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
//@ConfigurationProperties("http.smart")
public class SmartHttpProperties{
    /**
     * BaseUrl,如果设置了就在正常传送的URL之前添加上
     */
    private String baseUrl                                  = null;
    /**
     * 连接超时时间
     */
    private int defaultConnectionTimeout                = HttpConstants.DEFAULT_CONNECT_TIMEOUT;
    /**
     * 读数据超时时间
     */
    private int defaultReadTimeout                      = HttpConstants.DEFAULT_READ_TIMEOUT;
    /**
     * 请求体编码
     */
    private int defaultBodyCharset                       = HttpConstants.DEFAULT_CHARSET;
    /**
     * 返回体编码
     */
    private int defaultResultCharset                     = HttpConstants.DEFAULT_CHARSET;
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
    private Proxy proxy                                     = null;
    /**
     * 拦截器配置:拦截器实现的全类名
     */
    private List<String> interceptors;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getDefaultConnectionTimeout() {
        return defaultConnectionTimeout;
    }

    public void setDefaultConnectionTimeout(int defaultConnectionTimeout) {
        this.defaultConnectionTimeout = defaultConnectionTimeout;
    }

    public int getDefaultReadTimeout() {
        return defaultReadTimeout;
    }

    public void setDefaultReadTimeout(int defaultReadTimeout) {
        this.defaultReadTimeout = defaultReadTimeout;
    }

    public String getDefaultBodyCharset() {
        return defaultBodyCharset;
    }

    public void setDefaultBodyCharset(String defaultBodyCharset) {
        this.defaultBodyCharset = defaultBodyCharset;
    }

    public String getDefaultResultCharset() {
        return defaultResultCharset;
    }

    public void setDefaultResultCharset(String defaultResultCharset) {
        this.defaultResultCharset = defaultResultCharset;
    }

    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    public Map<String, String> getDefaultQueryParams() {
        return defaultQueryParams;
    }

    public void setDefaultQueryParams(Map<String, String> defaultQueryParams) {
        this.defaultQueryParams = defaultQueryParams;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public List<String> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<String> interceptors) {
        this.interceptors = interceptors;
    }

    final class Proxy{
        private String type = java.net.Proxy.Type.HTTP.name();
        private String hostName;
        private int port;
        private String username;
        private String password;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
