package top.jfunc.http.util;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SSLSocketHttpConnectionProvider;
import jodd.http.net.SocketHttpConnectionProvider;
import jodd.http.up.Uploadable;
import top.jfunc.http.base.FormFile;
import top.jfunc.common.utils.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;

/**
 * @author xiongshiyan at 2019/7/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class JoddUtil {

    public static void setRequestHeaders(HttpRequest request, String contentType,
                                     MultiValueMap<String, String> headers) {
        if(MapUtil.notEmpty(headers)) {
            headers.forEachKeyValue(request::header);
        }

        if(null != contentType){
            request.contentType(contentType);
        }
    }

    public static MultiValueMap<String , String> parseHeaders(HttpResponse response) {
        Collection<String> headerNames = response.headerNames();
        MultiValueMap<String,String> multiValueMap = new ArrayListMultiValueMap<>(headerNames.size());
        for (String headerName : headerNames) {
            List<String> headers = response.headers(headerName);
            for (String headerValue : headers) {
                multiValueMap.add(headerName , headerValue);
            }
        }
        return multiValueMap;
    }

    /**
     * @see top.jfunc.http.ssl.SSLSocketFactoryBuilder#build()
     * @see top.jfunc.http.ssl.SSLSocketFactoryBuilder#build(String, String)
     */
    public static void initSSL(HttpRequest httpRequest, HostnameVerifier hostnameVerifier , SSLSocketFactory sslSocketFactory , X509TrustManager trustManager , top.jfunc.http.base.ProxyInfo proxyInfo) {
        SocketHttpConnectionProvider httpConnectionProvider = null;
        if(StrUtil.HTTPS.equalsIgnoreCase(httpRequest.protocol())){
            httpConnectionProvider = new SSLSocketHttpConnectionProvider(sslSocketFactory);
        }else {
            httpConnectionProvider = new SocketHttpConnectionProvider();
        }
        if(null != proxyInfo){
            Proxy proxy = proxyInfo.getProxy();
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            if(Proxy.Type.DIRECT.equals(proxy.type())){
                httpConnectionProvider.useProxy(ProxyInfo.directProxy());
            }else if(Proxy.Type.HTTP.equals(proxy.type())){
                httpConnectionProvider.useProxy(ProxyInfo.httpProxy(address.getHostName() , address.getPort() , proxyInfo.getProxyUserName() , proxyInfo.getProxyPassword()));
            }else if(Proxy.Type.SOCKS.equals(proxy.type())){
                httpConnectionProvider.useProxy(ProxyInfo.socks5Proxy(address.getHostName() , address.getPort() , proxyInfo.getProxyUserName() , proxyInfo.getProxyPassword()));
            }
        }

        httpRequest.withConnectionProvider(httpConnectionProvider);
    }

    public static void closeQuietly(HttpResponse httpResponse) {
        if(null != httpResponse){
            try {
                httpResponse.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 文件上传
     * @param httpRequest HttpRequest
     * @param params Key-Value参数
     * @param charset 编码
     * @param formFiles 上传文件信息
     */
    public static void upload0(HttpRequest httpRequest , MultiValueMap<String, String> params, String charset, Iterable<FormFile> formFiles){
        httpRequest.multipart(true);
        httpRequest.formEncoding(charset);
        if(MapUtil.notEmpty(params)){
            params.forEachKeyValue(httpRequest::form);
        }
        if(ArrayUtil.isNotEmpty(formFiles)){
            for (FormFile formFile : formFiles) {
                httpRequest.form(formFile.getParameterName() , new FormFileUpload(formFile));
            }
        }
    }

    private static class FormFileUpload implements Uploadable<FormFile> {
        private final FormFile formFile;

        public FormFileUpload(FormFile formFile){
            this.formFile = formFile;
        }
        @Override
        public FormFile getContent() {
            return this.formFile;
        }

        @Override
        public byte[] getBytes() {
            try {
                return IoUtil.stream2Bytes(this.formFile.getInStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getFileName() {
            return this.formFile.getFilName();
        }

        @Override
        public String getMimeType() {
            return this.formFile.getContentType();
        }

        @Override
        public int getSize() {
            return Long.valueOf(this.formFile.getFileLen()).intValue();
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return this.formFile.getInStream();
        }
    }
}
