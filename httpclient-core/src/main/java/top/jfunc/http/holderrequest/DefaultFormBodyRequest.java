package top.jfunc.http.holderrequest;

import top.jfunc.http.holder.DefaultParamHolder;
import top.jfunc.http.holder.ParamHolder;
import top.jfunc.common.utils.MultiValueMap;

import java.net.URL;
import java.util.Map;

/**
 * Form表单请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultFormBodyRequest extends BaseHttpRequest<DefaultFormBodyRequest> implements FormRequest {

    public DefaultFormBodyRequest(String url){
        super(url);
    }
    public DefaultFormBodyRequest(URL url){
        super(url);
    }
    public DefaultFormBodyRequest(){
    }

    public static DefaultFormBodyRequest of(URL url){
        return new DefaultFormBodyRequest(url);
    }
    public static DefaultFormBodyRequest of(String url){
        return new DefaultFormBodyRequest(url);
    }
    public static DefaultFormBodyRequest of(){
        return new DefaultFormBodyRequest();
    }

    /**
     * form参数
     * POST请求，会作为body存在 并且设置Content-Type为 application/xxx-form-url-encoded
     * //private MultiValueMap<String,String> formParamHolder;
     */
    private ParamHolder formParamHolder = new DefaultParamHolder();

    @Override
    public ParamHolder formParamHolder() {
        return formParamHolder;
    }

    @Override
    public DefaultFormBodyRequest setFormParams(Map<String, String> params) {
        formParamHolder().set(params);
        return myself();
    }
    @Override
    public DefaultFormBodyRequest setFormParams(MultiValueMap<String, String> params) {
        formParamHolder().set(params);
        return myself();
    }

    @Override
    public DefaultFormBodyRequest addFormParam(String key, String value, String... values) {
        formParamHolder().add(key, value, values);
        return myself();
    }

    @Override
    public DefaultFormBodyRequest setParamCharset(String paramCharset) {
        formParamHolder().setParamCharset(paramCharset);
        return myself();
    }

    public DefaultFormBodyRequest setFormParamHolder(ParamHolder formParamHolder) {
        this.formParamHolder = formParamHolder;
        return myself();
    }
}
