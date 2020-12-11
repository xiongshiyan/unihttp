package top.jfunc.common.http.holderrequest;

import top.jfunc.common.http.base.Method;

import java.io.File;

/**
 * 提供一些静态方法帮助创建各种{@link HttpRequest}
 * 拿到各种{@link HttpRequest}之后还可以相应地做些细致具体的设置
 * 没有Body这种,像{@link Method#HEAD}可以参考Get一样
 * 有Body这种,像{@link Method#PUT}可以参考Post一样
 * @see HttpRequest
 * @see StringBodyRequest
 * @see MutableStringBodyRequest
 * @see FormRequest
 * @see UploadRequest
 * @see DownloadRequest
 * @author xiongshiyan at 2019/7/1 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class RequestCreator {
    private RequestCreator(){}

    public static DefaultRequest get(String url){
        return DefaultRequest.of(url);
    }
    public static DefaultBodyRequest post(String url , String body){
        return DefaultBodyRequest.of(url, body, null);
    }
    public static DefaultBodyRequest post(String url , String body , String contentType){
        return DefaultBodyRequest.of(url, body, contentType);
    }
    public static DefaultFormBodyRequest form(String url){
        return DefaultFormBodyRequest.of(url);
    }
    public static DefaultDownLoadRequest download(String url , File fileToSave){
        return DefaultDownLoadRequest.of(url, fileToSave);
    }
    public static DefaultUpLoadRequest upload(String url){
        return DefaultUpLoadRequest.of(url);
    }

}
