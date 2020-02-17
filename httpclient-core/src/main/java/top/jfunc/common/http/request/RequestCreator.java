package top.jfunc.common.http.request;

import top.jfunc.common.http.base.Method;
import top.jfunc.common.http.request.basic.*;

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
 * @see DownLoadRequest
 * @author xiongshiyan at 2019/7/1 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class RequestCreator {
    private RequestCreator(){}

    public static CommonRequest get(String url){
        return CommonRequest.of(url);
    }
    public static CommonBodyRequest post(String url , String body){
        return CommonBodyRequest.of(url, body, null);
    }
    public static CommonBodyRequest post(String url , String body , String contentType){
        return CommonBodyRequest.of(url, body, contentType);
    }
    public static FormBodyRequest form(String url){
        return FormBodyRequest.of(url);
    }
    public static DownLoadRequest download(String url , File fileToSave){
        return DownLoadRequest.of(url, fileToSave);
    }
    public static UpLoadRequest upload(String url){
        return UpLoadRequest.of(url);
    }


    /**
     * 从一个请求复制一个
     * 保证实现类重写了{@link Object#clone()}方法
     * @see BaseHttpRequest#clone()
     * @see BaseHttpRequest#clone()
     */
    @SuppressWarnings("unchecked")
    public static <T extends HttpRequest> T clone(T t){
        try {
            java.lang.reflect.Method method = t.getClass().getMethod("clone");
            method.setAccessible(true);
            return (T)method.invoke(t);
        } catch (Exception e) {
            throw new RuntimeException("不支持clone,请检查是否重写clone并且实现Cloneable接口" , e);
        }
    }
}
