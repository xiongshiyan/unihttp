package top.jfunc.common.http.scan;

import org.springframework.beans.factory.FactoryBean;
import top.jfunc.common.http.interfacing.HttpServiceCreator;

/**
 * 用于通过HttpServiceCreator生成HttpService接口的代理类
 * @see HttpServiceCreator
 * @param <T> HttpService标注的接口
 * @author xiongshiyan
 */
public class HttpServiceFactoryBean<T> implements FactoryBean<T>{
    private Class<T> interfaceClass;
    private HttpServiceCreator httpServiceCreator;

    public HttpServiceFactoryBean() {
    }

    public HttpServiceFactoryBean(HttpServiceCreator httpServiceCreator, Class<T> interfaceClass) {
        this.httpServiceCreator = httpServiceCreator;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        return httpServiceCreator.create(interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public HttpServiceCreator getHttpServiceCreator() {
        return httpServiceCreator;
    }

    public void setHttpServiceCreator(HttpServiceCreator httpServiceCreator) {
        this.httpServiceCreator = httpServiceCreator;
    }
}
