package top.jfunc.http.interceptor;

/**
 * 此包定义网络请求的拦截器，拦截接口 {@link top.jfunc.http.smart.SmartHttpTemplate SmartHttpTemplate} 的
 * begin、returnValue、exception、finally这几个节点，{@link top.jfunc.http.interceptor.CompositeInterceptor CompositeInterceptor}
 * 可以管理多个拦截器，{@link top.jfunc.http.base.Config Config} 就是使用它来管理
 * @see top.jfunc.http.interceptor.Interceptor
 * @see top.jfunc.http.interceptor.CompositeInterceptor
 * @see top.jfunc.http.smart.SmartHttpTemplate
 * @see top.jfunc.http.base.Config#getCompositeInterceptor()
 */