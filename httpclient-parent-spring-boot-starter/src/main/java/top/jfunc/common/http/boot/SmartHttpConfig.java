package top.jfunc.common.http.boot;

import java.util.Map;

public interface SmartHttpConfig {
    /**
     * BaseUrl,如果设置了就在正常传送的URL之前添加上
     */
    String getBaseUrl();

    Integer getDefaultConnectionTimeout();

    Integer getDefaultReadTimeout();

    String getDefaultBodyCharset();

    String getDefaultResultCharset();

    Map<String, String> getDefaultHeaders();

    Map<String, String> getDefaultQueryParams();

    Proxy getProxy();

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
