package top.jfunc.http.component.okhttp3;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.jfunc.http.component.RequestExecutor;
import top.jfunc.http.request.HttpRequest;

import java.io.IOException;

/**
 * @author xiongshiyan at 2020/1/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Deprecated
public class DefaultOkHttp3RequestExecutor implements RequestExecutor<OkHttpClient, Request, Response> {
    @Override
    public Response execute(OkHttpClient okHttpClient, Request request , HttpRequest httpRequest) throws IOException {
        return okHttpClient.newCall(request).execute();
    }
}
