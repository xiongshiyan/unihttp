package top.jfunc.http.holder;

import top.jfunc.http.request.HttpRequest;

/**
 * 属性处理器
 * 可以在请求的时候设置属性，在其他的地方比如拦截器中可以获取到，便于统一处理
 * @see HttpRequest
 * @see top.jfunc.http.interceptor.Interceptor
 * @author xiongshiyan at 2019/6/3 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface AttributeHolder extends MapHolder<String, Object> {
}
