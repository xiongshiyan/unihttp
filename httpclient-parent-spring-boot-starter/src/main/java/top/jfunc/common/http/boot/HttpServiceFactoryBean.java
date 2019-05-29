package top.jfunc.common.http.boot;

import org.springframework.beans.factory.FactoryBean;
import top.jfunc.common.http.interfacing.JFuncHttp;

/**
 * 用于通过jFuncHttp生成HttpService接口的代理类
 * @param <T>
 * @author xiongshiyan
 */
public class HttpServiceFactoryBean<T> implements FactoryBean<T>{
    private Class<T> c;
    private JFuncHttp jFuncHttp;

    public HttpServiceFactoryBean() {
    }

    public HttpServiceFactoryBean(JFuncHttp jFuncHttp , Class<T> c) {
        this.jFuncHttp = jFuncHttp;
        this.c = c;
    }

    @Override
    public T getObject() throws Exception {
        return jFuncHttp.create(c);
    }

    @Override
    public Class<?> getObjectType() {
        return c;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getC() {
        return c;
    }

    public void setC(Class<T> c) {
        this.c = c;
    }

    public JFuncHttp getjFuncHttp() {
        return jFuncHttp;
    }

    public void setjFuncHttp(JFuncHttp jFuncHttp) {
        this.jFuncHttp = jFuncHttp;
    }
}
