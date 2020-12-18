package top.jfunc.http.base;

import top.jfunc.http.config.Config;

/**
 * @author xiongshiyan at 2020/10/15 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultMethodContentStrategy implements MethodContentStrategy {
    /**
     默认{@link Method#POST}、{@link Method#PUT}、{@link Method#PATCH}支持，
     但是在es这种rest风格的应用中，可能{@link Method#GET}也需要支持BODY，
     那么只需要设置{@link Config#methodContentStrategy}即可
    */
    @Override
    public boolean supportContent(Method method) {
        return method == Method.POST || method == Method.PUT | method == Method.PATCH;
    }
}
