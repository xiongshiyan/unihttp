package top.jfunc.common.http.request.impl;

import top.jfunc.common.http.HttpConstants;
import top.jfunc.common.http.ParamUtil;
import top.jfunc.common.http.kv.Parameter;
import top.jfunc.common.http.request.StringBodyRequest;
import top.jfunc.common.utils.ArrayListMultimap;

import java.util.Map;
import java.util.Objects;

/**
 * Form表单请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class FormBodyRequest extends BaseRequest<FormBodyRequest> implements StringBodyRequest {

    public FormBodyRequest(String url){
        super(url);
    }
    public FormBodyRequest(){}

    public static FormBodyRequest of(String url){
        return new FormBodyRequest(url);
    }

    /**
     * form参数
     * POST请求，会作为body存在 并且设置Content-Type为 application/xxx-form-url-encoded
     */
    private ArrayListMultimap<String,String> formParams;

    @Override
    public String getBody() {
        String bodyCharset = getBodyCharset();
        //没有显式设置就设置默认的
        if(null == getContentType()){
            setContentType(HttpConstants.FORM_URLENCODED + ";charset=" + bodyCharset);
        }
        return ParamUtil.contactMap(formParams, bodyCharset);
    }

    public ArrayListMultimap<String, String> getFormParams() {
        return formParams;
    }

    public FormBodyRequest setFormParams(ArrayListMultimap<String, String> formParams) {
        this.formParams = Objects.requireNonNull(formParams);
        return this;
    }
    public FormBodyRequest setFormParams(Map<String, String> formParams) {
        initFormParams();
        formParams.forEach((k,v)->this.formParams.put(k,v));
        return this;
    }
    public FormBodyRequest addFormParam(String key, String value){
        initFormParams();
        this.formParams.put(key, value);
        return this;
    }
    public FormBodyRequest addFormParam(String key, String... values){
        initFormParams();
        for (String value : values) {
            this.formParams.put(key , value);
        }
        return this;
    }
    public FormBodyRequest addFormParam(String key, Iterable<String> values){
        initFormParams();
        for (String value : values) {
            this.formParams.put(key , value);
        }
        return this;
    }
    public FormBodyRequest addFormParam(Parameter... parameters){
        for (Parameter parameter : parameters) {
            addFormParam(parameter.getKey() , parameter.getValue());
        }
        return this;
    }
    public FormBodyRequest addFormParam(Map.Entry<String , Iterable<String>>... parameters){
        for (Map.Entry<String , Iterable<String>> parameter : parameters) {
            addFormParam(parameter.getKey() , parameter.getValue());
        }
        return this;
    }
    private void initFormParams(){
        if(null == this.formParams){
            this.formParams = new ArrayListMultimap<>(2);
        }
    }
}