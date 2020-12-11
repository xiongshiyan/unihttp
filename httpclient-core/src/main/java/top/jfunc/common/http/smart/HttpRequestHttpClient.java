package top.jfunc.common.http.smart;

import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.FreezableConfigAccessor;
import top.jfunc.common.http.base.Method;
import top.jfunc.common.http.request.*;
import top.jfunc.common.http.response.ClientHttpResponseConverter;
import top.jfunc.common.http.util.ResponseExtractor;
import top.jfunc.common.utils.IoUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.File;
import java.io.IOException;

/**
 * 定义基于 {@link HttpRequest} 和 {@link ClientHttpResponseConverter} 的http支持
 * <ul>
 *     <li>1. {@link HttpRequest} 及子类定义请求参数</li>
 *     <li>2. {@linkplain ClientHttpResponseConverter} 提供对返回值的全面处理</li>
 * </ul>
 * @author xiongshiyan at 2019/11/10
 * @since 1.1.10
 */
public interface HttpRequestHttpClient extends FreezableConfigAccessor {
    /**
     * 接口对http方法的支持
     * @param httpRequest Request
     * @param method Method
     * @param clientHttpResponseConverter responseConverter对结果的处理
     * @return <R>R
     * @throws IOException IOException
     */
    <R> R http(HttpRequest httpRequest, Method method, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException;

    /**
     * 接口对其他http方法的支持
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @param httpRequest Request
     * @param method Method
     * @return Response
     * @throws IOException IOException
     */
    default Response http(HttpRequest httpRequest, Method method) throws IOException{
        return http(httpRequest, method, ResponseExtractor::toResponse);
    }


    /**
     * GET方法，用于获取某个资源
     * @param httpRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default <R> R get(HttpRequest httpRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return http(httpRequest, Method.GET, clientHttpResponseConverter);
    }

    /**
     * GET方法，用于获取某个资源
     * @param httpRequest 请求参数
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default Response get(HttpRequest httpRequest) throws IOException{
        return get(httpRequest, ResponseExtractor::toResponse);
    }

    /**
     * 下载为字节数组
     * 也可以使用{@link SmartHttpClient#get(HttpRequest)}得到，再根据{@link Response#getBodyAsBytes()} 达到相同的效果
     * @param httpRequest 请求参数
     * @return byte[]
     * @throws IOException IOException
     */
    default byte[] getAsBytes(HttpRequest httpRequest) throws IOException{
        return get(httpRequest, ResponseExtractor::extractBytes);
    }

    /**
     * POST方法，用于新增
     * @param stringBodyRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default <R> R post(StringBodyRequest stringBodyRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return http(stringBodyRequest, Method.POST, clientHttpResponseConverter);
    }

    /**
     * POST方法，用于新增
     * @param stringBodyRequest 请求参数
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default Response post(StringBodyRequest stringBodyRequest) throws IOException{
        return post(stringBodyRequest , ResponseExtractor::toResponse);
    }

    /**
     * POST方法，对form表单的语义化支持
     * @param formRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default <R> R form(FormRequest formRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return post(formRequest, clientHttpResponseConverter);
    }

    /**
     * POST方法，对form表单的语义化支持
     * @param formRequest 请求参数
     * @return 响应
     * @throws IOException 超时等IO异常
     */
    default Response form(FormRequest formRequest) throws IOException{
        return post(formRequest , ResponseExtractor::toResponse);
    }

    /**
     * 下载文件
     * @param downloadRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return File 下载的文件
     * @throws IOException IOException
     */
    default <R> R download(DownloadRequest downloadRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return get(downloadRequest , clientHttpResponseConverter);
    }

    /**
     * 下载文件
     * @param downloadRequest 请求参数
     * @return File 下载的文件
     * @throws IOException IOException
     */
    default File download(DownloadRequest downloadRequest) throws IOException{
        return download(downloadRequest ,
                (chr, rc) -> IoUtil.copy2File(chr.getBody(), downloadRequest.getFile()));
    }

    /**
     * 文件上传
     * @param uploadRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return Response
     * @throws IOException IOException
     */
    <R> R upload(UploadRequest uploadRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException;

    /**
     * 文件上传
     * @param uploadRequest 请求参数
     * @return Response
     * @throws IOException IOException
     */
    default Response upload(UploadRequest uploadRequest) throws IOException{
        return upload(uploadRequest , ResponseExtractor::toResponse);
    }

    /**
     * HEAD方法，一般返回某个接口的响应头，而没有响应体，用于探测Content-Length等信息
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see Method#HEAD
     * @param httpRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 一般只有请求头，即使有body也应该忽略
     * @throws IOException IOException
     */
    default <R> R head(HttpRequest httpRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        //##必须要响应头,未明确指定时##
        if(null == httpRequest.retainResponseHeaders()){
            httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        }
        //##设置忽略响应体,未明确指定时##
        if(null == httpRequest.ignoreResponseBody()){
            httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        }

        return http(httpRequest, Method.HEAD, clientHttpResponseConverter);
    }

    default MultiValueMap<String , String> head(HttpRequest httpRequest) throws IOException{
        return head(httpRequest, ResponseExtractor::extractHeaders);
    }

    /**
     * OPTIONS方法
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see Method#OPTIONS
            access-control-allow-credentials →true
            access-control-allow-headers →Origin,X-Requested-With,Content-Type,Accept,Authorization,sourcetype,token
            access-control-allow-methods →POST,GET,PUT,OPTIONS,DELETE
            access-control-allow-origin →https://ossh5.palmte.cn
            access-control-max-age →3600
            connection →keep-alive
            content-length →0, 0
            content-type →application/octet-stream, text/plain
            date →Thu, 01 Aug 2019 06:29:43 GMT
            server →nginx
     * @param httpRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 一般只有请求头，即使有body也应该忽略
     * @throws IOException IOException
     */
    default <R> R options(HttpRequest httpRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        //##必须要响应头,未明确指定时##
        if(null == httpRequest.retainResponseHeaders()){
            httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        }
        //##设置忽略响应体,未明确指定时##
        if(null == httpRequest.ignoreResponseBody()){
            httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        }

        return http(httpRequest, Method.OPTIONS, clientHttpResponseConverter);
    }

    default MultiValueMap<String , String> options(HttpRequest httpRequest) throws IOException{
        return options(httpRequest, ResponseExtractor::extractHeaders);
    }

    /**
     * PUT方法，用于更新
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see SmartHttpClient#post(StringBodyRequest)
     * @see Method#PUT
     * @param stringBodyRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException IOException
     */
    default <R> R put(StringBodyRequest stringBodyRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return http(stringBodyRequest, Method.PUT, clientHttpResponseConverter);
    }

    default Response put(StringBodyRequest httpRequest) throws IOException{
        return put(httpRequest , ResponseExtractor::toResponse);
    }

    /**
     * PATCH方法，用于部分更新
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see SmartHttpClient#post(StringBodyRequest)
     * @see Method#PATCH
     * @param stringBodyRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException IOException
     */
    default <R> R patch(StringBodyRequest stringBodyRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return http(stringBodyRequest, Method.PATCH, clientHttpResponseConverter);
    }

    default Response patch(StringBodyRequest stringBodyRequest) throws IOException{
        return patch(stringBodyRequest , ResponseExtractor::toResponse);
    }

    /**
     * DELETE方法，用于删除某个资源
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see Method#DELETE
     * @param httpRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException IOException
     */
    default <R> R delete(HttpRequest httpRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        return http(httpRequest, Method.DELETE, clientHttpResponseConverter);
    }

    default Response delete(HttpRequest httpRequest) throws IOException{
        return delete(httpRequest, ResponseExtractor::toResponse);
    }

    /**
     * TRACE方法，一般用于调试，在服务器支持的情况下会返回请求的头和body
     * @see SmartHttpClient#http(HttpRequest, Method, ClientHttpResponseConverter)
     * @see Method#TRACE
     * @param httpRequest 请求参数
     * @param clientHttpResponseConverter 处理返回值
     * @return 响应
     * @throws IOException IOException
     */
    default <R> R trace(HttpRequest httpRequest, ClientHttpResponseConverter<R> clientHttpResponseConverter) throws IOException{
        //##必须要响应头,未明确指定时##
        if(null == httpRequest.retainResponseHeaders()){
            httpRequest.retainResponseHeaders(Config.RETAIN_RESPONSE_HEADERS);
        }
        //##设置忽略响应体,未明确指定时##
        if(null == httpRequest.ignoreResponseBody()){
            httpRequest.ignoreResponseBody(Config.IGNORE_RESPONSE_BODY);
        }

        return http(httpRequest, Method.TRACE, clientHttpResponseConverter);
    }

    default Response trace(HttpRequest httpRequest) throws IOException{
        return trace(httpRequest, ResponseExtractor::toResponse);
    }
}
