package top.jfunc.http.util;

import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import top.jfunc.http.base.FormFile;
import top.jfunc.http.base.HttpHeaders;
import top.jfunc.http.base.Method;
import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.IoUtil;
import top.jfunc.common.utils.MapUtil;
import top.jfunc.common.utils.MultiValueMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author xiongshiyan at 2019/7/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class OkHttp3Util {

    public static void closeQuietly(Response response) {
        if(null != response){
            try {
                response.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * @see top.jfunc.http.ssl.SSLSocketFactoryBuilder
     */
    public static void initSSL(OkHttpClient.Builder builder , HostnameVerifier hostnameVerifier , SSLSocketFactory sslSocketFactory , X509TrustManager trustManager) {
        // 验证域
        if(null != hostnameVerifier){
            builder.hostnameVerifier(hostnameVerifier);
        }
        if(null != sslSocketFactory) {
            builder.sslSocketFactory(sslSocketFactory , trustManager);
        }
    }

    public static void setRequestBody(Request.Builder builder , Method method , RequestBody requestBody){
        builder.method(method.name() , requestBody);
    }

    public static RequestBody stringBody(String body , String bodyCharset , String contentType){
        MediaType mediaType = null;
        if(null != contentType){
            mediaType = MediaType.parse(contentType);
        }
        return RequestBody.create(mediaType, body.getBytes(Charset.forName(bodyCharset)));
    }

    /**
     * 文件上传body
     * @param params 伴随文件上传的参数key=value，可以为空
     * @param formFiles 上传文件信息
     */
    public static MultipartBody filesBody(MultiValueMap<String, String> params , Iterable<FormFile> formFiles) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(null != params){
            params.forEachKeyValue(builder::addFormDataPart);
        }

        if(null != formFiles){
            for (FormFile formFile : formFiles) {
                builder.addPart(Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"" + formFile.getParameterName() + "\"; filename=\"" + formFile.getFilName() + "\"") ,
                        inputStreamBody(formFile.getContentType() , formFile.getInStream() , formFile.getFileLen()));
            }
        }

        return builder.build();
    }

    public static RequestBody inputStreamBody(String contentType , InputStream inputStream , long length){
        return new InputStreamRequestBody(contentType , inputStream , length);
    }

    public static void setRequestHeaders(Request.Builder builder, String contentType,
                                     MultiValueMap<String, String> headers) {
        if(MapUtil.notEmpty(headers)) {
            headers.forEachKeyValue(builder::addHeader);
        }

        if(null != contentType){
            builder.header(HttpHeaders.CONTENT_TYPE, contentType);
        }
    }

    /**
     * 获取响应中的headers
     */
    public static MultiValueMap<String , String> parseHeaders(Response response) {
        Headers resHeaders = response.headers();
        MultiValueMap<String , String> headers = new ArrayListMultiValueMap<>(resHeaders.size());
        Map<String, List<String>> stringListMap = resHeaders.toMultimap();
        stringListMap.forEach((k,l)->l.forEach(v->headers.add(k,v)));
        return headers;
    }

    /**
     * 根据inputStream生成requestBody的工具类
     * @see RequestBody#create(MediaType, File)
     */
    private static class InputStreamRequestBody extends RequestBody {
        private String contentType;
        private long len;
        private InputStream stream;

        public InputStreamRequestBody(String contentType, InputStream stream, long len) {
            this.contentType = contentType;
            this.stream = stream;
            this.len = len;
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(contentType);
        }

        @Override
        public long contentLength() throws IOException {
            return len;
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            Source source = Okio.source(stream);
            sink.writeAll(source);

            IoUtil.close(stream);
            IoUtil.close(source);
        }
    }
}
