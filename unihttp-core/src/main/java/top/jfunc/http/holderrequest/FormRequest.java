package top.jfunc.http.holderrequest;

import top.jfunc.http.base.MediaType;
import top.jfunc.http.holder.ParamHolder;
import top.jfunc.http.util.ParamUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.util.Map;

/**
 * form-urlencoded请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface FormRequest extends StringBodyRequest, top.jfunc.http.request.FormRequest {
    /**
     * 接管Form param的处理
     * @return ParamHolder must not null
     */
    ParamHolder formParamHolder();

    /**
     * Form参数
     * @return Form参数
     */
    @Override
    default MultiValueMap<String, String> getFormParams(){
        return formParamHolder().get();
    }

    /**
     * 设置Form参数
     * @param params Form参数
     * @return this
     */
    @Override
    default FormRequest setFormParams(Map<String, String> params){
        formParamHolder().set(params);
        return this;
    }
    /**
     * 设置Form参数
     * @param params Form参数
     * @return this
     */
    @Override
    default FormRequest setFormParams(MultiValueMap<String, String> params){
        formParamHolder().set(params);
        return this;
    }

    /**
     * 新增form参数的便捷方法
     * @param key key
     * @param value value
     * @param values values
     * @return this
     */
    @Override
    default FormRequest addFormParam(String key, String value, String... values){
        formParamHolder().add(key, value, values);
        return this;
    }

    /**
     * 提供便捷设置编码的方法
     * @param paramCharset 参数编码
     * @return this
     */
    default FormRequest setParamCharset(String paramCharset){
        formParamHolder().setParamCharset(paramCharset);
        return this;
    }
    /**
     * 提供便捷设置编码的方法
     * @param bodyCharset 参数编码
     * @return this
     */
    @Override
    default FormRequest setBodyCharset(String bodyCharset){
        setParamCharset(bodyCharset);
        return this;
    }

    /**
     * form参数生成body
     * @see ParamUtil#contactMap(MultiValueMap)
     * @return body
     */
    @Override
    default String getBody() {
        ParamHolder formParamHolder = formParamHolder();
        String bodyCharset = calculateBodyCharset();
        //没有显式设置就设置默认的
        if(null == getContentType()){
            setContentType(MediaType.APPLICATION_FORM_DATA.withCharset(bodyCharset));
        }
        return ParamUtil.contactMap(formParamHolder.get(), bodyCharset);
    }
    /**
     * 获取请求体编码
     * @return charset
     */
    @Override
    default String getBodyCharset() {
        return formParamHolder().getParamCharset();
    }
}
