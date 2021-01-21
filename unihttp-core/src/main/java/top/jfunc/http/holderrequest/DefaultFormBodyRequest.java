package top.jfunc.http.holderrequest;

import top.jfunc.http.holder.DefaultParamHolder;
import top.jfunc.http.holder.ParamHolder;

import java.net.URL;

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

    public FormRequest setFormParamHolder(ParamHolder formParamHolder) {
        this.formParamHolder = formParamHolder;
        return myself();
    }
}
