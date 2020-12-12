package top.jfunc.http;

import top.jfunc.http.base.Config;
import top.jfunc.http.base.FormFile;
import top.jfunc.http.base.FreezableConfigAccessor;
import top.jfunc.http.base.MediaType;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.ParamUtil;
import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.MapUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * GET POST接口
 * 从接口定义看出，参数直接在接口方法中，改变一个参数就需要重新定义个方法，
 * 实在太啰嗦，并且不好维护， 所以墙裂建议使用其兄弟类
 * {@link HttpRequestHttpClient} 或者子类{@link SmartHttpClient}，
 * @author 熊诗言
 * @since 2017/11/24
 */
public interface SimpleHttpClient extends FreezableConfigAccessor {
     /**
     * HTTP GET请求
     * @param url URL，可以帶参数
     * @param queryParams 参数列表，可以为 null, 此系列get方法的params按照URLEncoder(UTF-8)拼装,
     *               如果是其他的编码请使用{@link SmartHttpClient#get(HttpRequest)},然后Request中设置bodyCharset
     * @param headers HTTP header 可以为 null
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读超时时间
     * @param resultCharset 返回编码
     * @return 返回的内容
     * @throws IOException 超时异常 {@link java.net.SocketTimeoutException connect timed out/read time out}
     */
    String get(String url, Map<String, String> queryParams, Map<String, String> headers,
               int connectTimeout, int readTimeout, String resultCharset) throws IOException;

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams queryParams
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams, Map<String, String> headers,
                       int connectTimeout, int readTimeout) throws IOException{
        return get(url, queryParams, headers, connectTimeout, readTimeout , null);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @param headers headers
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams, Map<String, String> headers, String resultCharset) throws IOException{
        return get(url, queryParams, headers, Config.UNSIGNED, Config.UNSIGNED, resultCharset);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @param headers headers
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams, Map<String, String> headers) throws IOException{
        return get(url, queryParams, headers, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams,
                       int connectTimeout, int readTimeout, String resultCharset) throws IOException{
        return get(url, queryParams,null, connectTimeout, readTimeout, resultCharset);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams,
                       int connectTimeout, int readTimeout) throws IOException{
        return get(url, queryParams, null, connectTimeout, readTimeout);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams, String resultCharset) throws IOException{
        return get(url, queryParams,null, Config.UNSIGNED, Config.UNSIGNED,resultCharset);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param queryParams params
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, Map<String, String> queryParams) throws IOException{
        return get(url, queryParams, null, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String get(String url, String resultCharset) throws IOException{
        return get(url,null,null, Config.UNSIGNED, Config.UNSIGNED, resultCharset);
    }

    /**
     * HTTP GET请求
     * @see SimpleHttpClient#get(String, Map, Map, int, int, String)
     * @param url url
     * @return String
     * @throws IOException IOException
     */
    default String get(String url) throws IOException{
        return get(url,null,null, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP POST
     * @param url URL
     * @param queryParams params
     * @param body 请求体
     * @param contentType 请求体类型
     * @param headers 头
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读超时时间
     * @param bodyCharset 请求体编码
     * @param resultCharset 返回编码
     * @return 请求返回
     * @throws IOException 超时异常 {@link java.net.SocketTimeoutException connect timed out/read time out}
     */
    String post(String url, Map<String, String> queryParams, String body, String contentType, Map<String, String> headers,
                int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException;

    /**
     * HTTP POST
     * @param url URL
     * @param body 请求体
     * @param contentType 请求体类型
     * @param headers 头
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读超时时间
     * @param bodyCharset 请求体编码
     * @param resultCharset 返回编码
     * @return 请求返回
     * @throws IOException 超时异常 {@link java.net.SocketTimeoutException connect timed out/read time out}
     */
    default String post(String url, String body, String contentType, Map<String, String> headers,
                        int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException{
        return post(url, null, body, contentType, headers, connectTimeout, readTimeout, bodyCharset, resultCharset);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType, Map<String, String> headers,
                        int connectTimeout, int readTimeout) throws IOException{
        return post(url, body, contentType, headers, connectTimeout, readTimeout, null,null);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param headers headers
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType, Map<String, String> headers,
                        String bodyCharset, String resultCharset) throws IOException{
        return post(url,body,contentType,headers, Config.UNSIGNED, Config.UNSIGNED,bodyCharset,resultCharset);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param headers headers
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType, Map<String, String> headers) throws IOException{
        return post(url, body, contentType, headers, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType,
                        int connectTimeout, int readTimeout, String bodyCharset, String resultCharset) throws IOException{
        return post(url, body, contentType,null, connectTimeout, readTimeout, bodyCharset, resultCharset);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType, int connectTimeout, int readTimeout) throws IOException{
        return post(url, body, contentType,null, connectTimeout, readTimeout);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType, String bodyCharset, String resultCharset) throws IOException{
        return post(url, body, contentType,null, Config.UNSIGNED, Config.UNSIGNED,bodyCharset,resultCharset);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param contentType contentType
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, String body, String contentType) throws IOException{
        return post(url, body, contentType,null, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String postJson(String url, String body, String bodyCharset, String resultCharset) throws IOException{
        MediaType jsonType = MediaType.APPLICATION_JSON.withCharset(Config.DEFAULT_CHARSET);
        return post(url, body, jsonType.toString(),null, Config.UNSIGNED, Config.UNSIGNED,bodyCharset, resultCharset);
    }

    /**
     * HTTP POST
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @param url url
     * @param body body
     * @return String
     * @throws IOException IOException
     */
    default String postJson(String url, String body) throws IOException{
        MediaType jsonType = MediaType.APPLICATION_JSON.withCharset(Config.DEFAULT_CHARSET);
        return post(url, body, jsonType.toString(),null, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * HTTP POST form
     * @see SimpleHttpClient#post(String, String, String, Map, int, int, String, String)
     * @see ParamUtil#contactMap(Map)
     * @param url url
     * @param formParams params 参数用 =和& 连接
     * @param headers headers
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, Map<String, String> formParams, Map<String, String> headers,
                        String bodyCharset, String resultCharset) throws IOException{
        return post(url, ParamUtil.contactMap(formParams, bodyCharset), MediaType.APPLICATION_FORM_DATA.withCharset(bodyCharset).toString(),headers,bodyCharset,resultCharset);
    }

    /**
     * HTTP POST form
     * @see SimpleHttpClient#post(String, Map, Map, String, String)
     * @param url url
     * @param formParams params 参数用 =和& 连接
     * @param headers headers
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, Map<String, String> formParams, Map<String, String> headers) throws IOException{
        MediaType mediaType = MediaType.APPLICATION_FORM_DATA.withCharset(Config.DEFAULT_CHARSET);
        return post(url, ParamUtil.contactMap(formParams, Config.DEFAULT_CHARSET), mediaType.toString(), headers);
    }

    /**
     * HTTP POST form
     * @see SimpleHttpClient#post(String, Map, Map, String, String)
     * @param url url
     * @param formParams params 参数用 =和& 连接
     * @param bodyCharset bodyCharset
     * @param resultCharset resultCharset
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, Map<String, String> formParams, String bodyCharset, String resultCharset) throws IOException{
        MediaType mediaType = MediaType.APPLICATION_FORM_DATA.withCharset(Config.DEFAULT_CHARSET);
        return post(url, ParamUtil.contactMap(formParams, bodyCharset), mediaType.toString(),null, bodyCharset, resultCharset);
    }

    /**
     * HTTP POST form
     * @see SimpleHttpClient#post(String, Map, Map, String, String)
     * @param url url
     * @param formParams params 参数用 =和& 连接
     * @return String
     * @throws IOException IOException
     */
    default String post(String url, Map<String, String> formParams) throws IOException{
        MediaType mediaType = MediaType.APPLICATION_FORM_DATA.withCharset(Config.DEFAULT_CHARSET);
        return post(url, ParamUtil.contactMap(formParams, Config.DEFAULT_CHARSET), mediaType.toString(),null);
    }

    /**
     * 下载为字节数组
     * @param url url
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return byte[]
     * @throws IOException IOException
     */
    byte[] getAsBytes(String url, MultiValueMap<String, String> headers, int connectTimeout, int readTimeout) throws IOException;

    /**
     * 下载为字节数组
     * @see SimpleHttpClient#getAsBytes(String, MultiValueMap, int, int)
     * @param url url
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return byte[]
     * @throws IOException IOException
     */
    default byte[] getAsBytes(String url, int connectTimeout, int readTimeout) throws IOException{
        return getAsBytes(url, null, connectTimeout , readTimeout);
    }

    /**
     * 下载为字节数组
     * @see SimpleHttpClient#getAsBytes(String, MultiValueMap, int, int)
     * @param url url
     * @param headers headers
     * @return byte[]
     * @throws IOException IOException
     */
    default byte[] getAsBytes(String url, MultiValueMap<String, String> headers) throws IOException{
        return getAsBytes(url, headers, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * 下载为字节数组
     * @see SimpleHttpClient#getAsBytes(String, MultiValueMap, int, int)
     * @param url url
     * @return byte[]
     * @throws IOException IOException
     */
    default byte[] getAsBytes(String url) throws IOException{
        return getAsBytes(url, null, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * 下载为文件
     * @param url url
     * @param headers headers
     * @param file file
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return File
     * @throws IOException IOException
     */
    File getAsFile(String url, MultiValueMap<String, String> headers, File file, int connectTimeout, int readTimeout) throws IOException;

    /**
     * 下载为文件
     * @see SimpleHttpClient#getAsFile(String, File, int, int)
     * @param url url
     * @param file file
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @return File
     * @throws IOException IOException
     */
    default File getAsFile(String url, File file, int connectTimeout, int readTimeout) throws IOException{
        return getAsFile(url, null, file, connectTimeout, readTimeout);
    }
    /**
     * 下载为文件
     * @see SimpleHttpClient#getAsFile(String, File, int, int)
     * @param url url
     * @param headers headers
     * @param file file
     * @return File
     * @throws IOException IOException
     */
    default File getAsFile(String url, MultiValueMap<String, String> headers, File file) throws IOException{
        return getAsFile(url, headers, file, Config.UNSIGNED, Config.UNSIGNED);
    }
    /**
     * 下载为文件
     * @see SimpleHttpClient#getAsFile(String, File, int, int)
     * @param url url
     * @param file file
     * @return File
     * @throws IOException IOException
     */
    default File getAsFile(String url, File file) throws IOException{
        return getAsFile(url, null, file, Config.UNSIGNED, Config.UNSIGNED);
    }

    /**
     * 上传文件
     * @param url url
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param resultCharset resultCharset
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    String upload(String url, MultiValueMap<String, String> headers,
                  int connectTimeout, int readTimeout, String resultCharset, FormFile... formFiles) throws IOException;

    /**
     * 上传文件
     * @see SimpleHttpClient#upload(String, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, MultiValueMap<String, String> headers,
                          int connectTimeout, int readTimeout, FormFile... formFiles) throws IOException{
        return upload(url, headers, connectTimeout, readTimeout, null, formFiles);
    }

    /**
     * 上传文件
     * @see SimpleHttpClient#upload(String, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param headers headers
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, MultiValueMap<String, String> headers, FormFile... formFiles) throws IOException{
        return upload(url, headers ,null, Config.UNSIGNED, Config.UNSIGNED, formFiles);
    }

    /**
     * 上传文件
     * @see SimpleHttpClient#upload(String, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, int connectTimeout, int readTimeout, FormFile... formFiles) throws IOException{
        return upload(url, null, connectTimeout, readTimeout, null, formFiles);
    }

    /**
     * 上传文件
     * @see SimpleHttpClient#upload(String, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, FormFile... formFiles) throws IOException{
        return upload(url, null, null, Config.UNSIGNED, Config.UNSIGNED, formFiles);
    }

    /**
     * 上传文件和key-value数据
     * @param url url
     * @param params params
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param resultCharset resultCharset
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers,
                  int connectTimeout, int readTimeout, String resultCharset, FormFile... formFiles) throws IOException;

    /**
     * 上传文件和key-value数据
     * @see SimpleHttpClient#upload(String, MultiValueMap, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param params params
     * @param headers headers
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, MultiValueMap<String, String> params, MultiValueMap<String, String> headers,
                          int connectTimeout, int readTimeout, FormFile... formFiles) throws IOException{
        return upload(url, params, headers, connectTimeout, readTimeout, null, formFiles);
    }

    /**
     * 上传文件和key-value数据
     * @see SimpleHttpClient#upload(String, MultiValueMap, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param params params
     * @param headers headers
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, MultiValueMap<String, String> params,
                          MultiValueMap<String, String> headers, FormFile... formFiles) throws IOException{
        return upload(url, params, headers, Config.UNSIGNED, Config.UNSIGNED, null, formFiles);
    }

    /**
     * 上传文件和key-value数据
     * @see SimpleHttpClient#upload(String, MultiValueMap, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param params params
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, int connectTimeout, int readTimeout,
                          MultiValueMap<String, String> params, FormFile... formFiles) throws IOException{
        return upload(url, params,null, connectTimeout, readTimeout, null, formFiles);
    }

    /**
     * 上传文件和key-value数据
     * @see SimpleHttpClient#upload(String, MultiValueMap, MultiValueMap, int, int, String, FormFile...)
     * @param url url
     * @param params params
     * @param formFiles formFiles
     * @return String
     * @throws IOException IOException
     */
    default String upload(String url, Map<String, String> params, FormFile... formFiles) throws IOException{
        MultiValueMap<String , String> p = null;
        if(MapUtil.notEmpty(params)){
            p = ArrayListMultiValueMap.fromMap(params);
        }
        return upload(url, p,null, Config.UNSIGNED, Config.UNSIGNED, null, formFiles);
    }
}
