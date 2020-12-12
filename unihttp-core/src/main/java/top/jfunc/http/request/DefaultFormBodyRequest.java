package top.jfunc.http.request;

import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.MapUtil;
import top.jfunc.common.utils.MultiValueMap;
import top.jfunc.http.config.Config;

import java.util.Map;

/**
 * Form表单请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultFormBodyRequest extends BaseHttpRequest<DefaultFormBodyRequest> implements FormRequest {

    public DefaultFormBodyRequest(String url){
        super(url);
    }
    public DefaultFormBodyRequest(){
    }
    public static DefaultFormBodyRequest of(String url){
        return new DefaultFormBodyRequest(url);
    }
    public static DefaultFormBodyRequest of(){
        return new DefaultFormBodyRequest();
    }

    private MultiValueMap<String , String> formParams = new ArrayListMultiValueMap<>(2);
    private String formParamCharset = Config.DEFAULT_CHARSET;

    @Override
    public FormRequest setFormParams(Map<String, String> params) {
        if(MapUtil.notEmpty(params)){
            this.formParams = ArrayListMultiValueMap.fromMap(params);
        }
        return myself();
    }
    @Override
    public FormRequest setFormParams(MultiValueMap<String, String> params) {
        this.formParams = params;
        return myself();
    }

    @Override
    public FormRequest addFormParam(String key, String value, String... values) {
        formParams.add(key, value, values);
        return myself();
    }

    @Override
    public MultiValueMap<String, String> getFormParams() {
        return formParams;
    }

    @Override
    public String getBodyCharset() {
        return formParamCharset;
    }

    @Override
    public FormRequest setBodyCharset(String bodyCharset) {
        this.formParamCharset = bodyCharset;
        return myself();
    }
}
