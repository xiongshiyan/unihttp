package top.jfunc.common.http.smart;

import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.ContentCallback;
import top.jfunc.common.http.component.AssemblingFactory;
import top.jfunc.common.http.component.ContentCallbackCreator;
import top.jfunc.common.http.component.HttpRequestExecutor;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.response.ClientHttpResponse;
import top.jfunc.common.http.response.ClientHttpResponseConverter;
import top.jfunc.common.utils.IoUtil;

import java.io.IOException;

/**
 * 统一异常处理、拦截方法
 * @see SmartHttpTemplate
 * @see SimpleHttpClient
 * @see HttpRequestHttpClient
 * @see SmartHttpClient
 * @author xiongshiyan at 2019/5/8 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public abstract class AbstractImplementSmartHttpClient<CC> extends AbstractSmartHttpClient<CC> implements TemplateInterceptor {
    /**执行请求获取响应*/
    private HttpRequestExecutor<CC> httpRequestExecutor;

    protected AbstractImplementSmartHttpClient(ContentCallbackCreator<CC> bodyContentCallbackCreator,
                                               ContentCallbackCreator<CC> uploadContentCallbackCreator,
                                               HttpRequestExecutor<CC> httpRequestExecutor) {
        super(bodyContentCallbackCreator, uploadContentCallbackCreator);
        this.httpRequestExecutor = httpRequestExecutor;
    }
    protected AbstractImplementSmartHttpClient(AssemblingFactory assemblingFactory,
                                               ContentCallbackCreator<CC> bodyContentCallbackCreator,
                                               ContentCallbackCreator<CC> uploadContentCallbackCreator,
                                               HttpRequestExecutor<CC> httpRequestExecutor) {
        super(assemblingFactory, bodyContentCallbackCreator, uploadContentCallbackCreator);
        this.httpRequestExecutor = httpRequestExecutor;
    }

    /**
     * 统一的拦截和异常处理：最佳实践使用拦截器代替子类复写
     * @inheritDoc
     */
    @Override
    public <R> R template(HttpRequest httpRequest, ContentCallback<CC> contentCallback, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException {
        //在接口方法入口处调用了init(HttpRequest)方法将系统Config设置到HttpRequest了
        Config config = httpRequest.getConfig();

        //1.子类处理
        HttpRequest h = beforeTemplate(httpRequest);
        //2.拦截器在之前处理
        HttpRequest request = config.onBeforeIfNecessary(h);
        ClientHttpResponse clientHttpResponse = null;
        try {
            //3.真正的实现
            clientHttpResponse = execute(request , contentCallback);
            //4.拦截器过滤
            clientHttpResponse = config.onBeforeReturnIfNecessary(request , clientHttpResponse);
            //5.子类处理
            clientHttpResponse = afterTemplate(request, clientHttpResponse);
            //6.结果传唤
            return clientHttpResponseConverter.convert(clientHttpResponse, calculateResultCharset(h));
        } catch (IOException e) {
            //7.1.拦截器在抛异常的时候处理
            config.onErrorIfNecessary(request , e);
            throw e;
        } catch (Exception e) {
            //7.2.拦截器在抛异常的时候处理
            config.onErrorIfNecessary(request, e);
            throw new RuntimeException(e);
        }finally {
            //8.拦截器在任何时候都处理
            config.onFinallyIfNecessary(httpRequest);
            //9.这一步特别关键，保证资源关闭
            IoUtil.close(clientHttpResponse);
        }
    }

    protected ClientHttpResponse execute(HttpRequest httpRequest , ContentCallback<CC> contentCallback) throws Exception {
        return getHttpRequestExecutor().execute(httpRequest, contentCallback);
    }

    public HttpRequestExecutor<CC> getHttpRequestExecutor() {
        return httpRequestExecutor;
    }
}

